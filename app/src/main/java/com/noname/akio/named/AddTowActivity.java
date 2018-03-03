package com.noname.akio.named;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.noname.akio.named.clientAPI.Request.TowImageRequest;
import com.noname.akio.named.clientAPI.Request.TowRequest;
import com.noname.akio.named.clientAPI.Response.Contracts.Contract;
import com.noname.akio.named.clientAPI.Response.Contracts.ContractData;
import com.noname.akio.named.clientAPI.Response.Contracts.Contracts;
import com.noname.akio.named.clientAPI.Response.Prices.Price;
import com.noname.akio.named.clientAPI.Response.Prices.PriceData;
import com.noname.akio.named.clientAPI.Response.Prices.Prices;
import com.noname.akio.named.clientAPI.Response.TowImage.TowImageResponse;
import com.noname.akio.named.clientAPI.Response.Tows.TowSingleResponse;
import com.noname.akio.named.clientAPI.RestAPI;
import com.noname.akio.named.clientAPI.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddTowActivity extends AppCompatActivity implements View.OnClickListener {
    public static int PICK_IMAGE_MULTIPLE = 100;
    EditText year_edit, note_edit, vin_edit;
    EditText license_plate;
    Spinner color_edit, reason_edit,charge_edit;
    AutoCompleteTextView make_model, contract_edit;
    ArrayList<Price> priceList;
    ArrayList<String> imagePaths;
    int selected_image_count = 0;
    ImageView[] imgViewArray,closeButtonArray;
    RelativeLayout[] layoutArray;
    ArrayList<Contract> contractList;

    RestAPI service;
    int textlength = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getBoolean(getString(R.string.dark_selected),false)==true)
            setTheme(R.style.NobarDarkTheme);
        setContentView(R.layout.activity_addtow);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.color.dark_backcolor, typedValue, true);
        int color = typedValue.data;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(getResources().getColor(R.color.dark_backcolor));
        } else{
            // do something for phones running an SDK before lollipop
        }

        ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        TextView btn_done=(TextView)findViewById(R.id.btn_done);
        btn_done.setOnClickListener(this);
        contract_edit = (AutoCompleteTextView)findViewById(R.id.contract_spinner);
        contract_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contract_edit.showDropDown();
            }
        });
        year_edit = (EditText)findViewById(R.id.year_edit);
        //year_edit.setOnClickListener(this);
        make_model = (AutoCompleteTextView)findViewById(R.id.make_model_edit);
        make_model.setDropDownHeight(1000);

        color_edit = (Spinner)findViewById(R.id.color_edit);
        license_plate = (EditText)findViewById(R.id.license_place_edit);
        note_edit = (EditText)findViewById(R.id.note_edit);
        vin_edit = (EditText)findViewById(R.id.vin_edit);

        reason_edit = (Spinner)findViewById(R.id.reason_spinner);
        charge_edit = (Spinner)findViewById(R.id.charges_edit);
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        layout.setOnClickListener(this);
        Button btnAddPhotos = (Button)findViewById(R.id.btn_addphotos);
        btnAddPhotos.setOnClickListener(this);

        priceList = new ArrayList<>();

        String [] arraySpinner;
        arraySpinner = new String[]{"Beige", "Black", "Blue", "Bronze","Brown", "Burgundy","Champagne","Gold","Gray","Green","Maroon","Navy","Orange","Other","Pink","Purple","Red","Silver","Tan","Teal","White","Yellow"};
        if(arraySpinner.length>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,

                    R.layout.spinner_item, arraySpinner);
            color_edit.setAdapter(adapter);

        }
        String [] reasonSpinner;
        reasonSpinner = new String[]{"Expired Inspection", "Wrecked", "Expired Plates", "Flat Tires","Inoperable","On Blocks","No Permit", "Non-Matching Permit", "Reserved Space", "Unregistered/Unauthorized Vehicle","Unauthorized Visitor","Fire Lane", "Handicapped","Missing Parts","Abandoned"};
        if(arraySpinner.length>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, reasonSpinner);
            reason_edit.setAdapter(adapter);

        }
        vin_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (!str.equals(str.toUpperCase())) {
                    str = str.toUpperCase();
                    vin_edit.setText(str);
                    vin_edit.setSelection(vin_edit.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        license_plate.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                String text = license_plate.getText().toString();
                textlength = license_plate.getText().length();
                if(text.endsWith(" "))
                    return;
                if(count == 0)
                {
                    return;
                }

                else if(textlength == 4)
                {
                    license_plate.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());

                }
                String str = s.toString();
                if (!str.equals(str.toUpperCase())) {
                    str = str.toUpperCase();
                    license_plate.setText(str);

                }
                license_plate.setSelection(license_plate.getText().length());
            }});

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("api-token", getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).build();
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
        service = retrofit.create(RestAPI.class);

        loadPrices();
        imgViewArray = new ImageView[6];
        layoutArray = new RelativeLayout[6];
        closeButtonArray = new ImageView[6];
        imgViewArray[0] = (ImageView)findViewById(R.id.image1);
        imgViewArray[1] = (ImageView)findViewById(R.id.image2);
        imgViewArray[2] = (ImageView)findViewById(R.id.image3);
        imgViewArray[3] = (ImageView)findViewById(R.id.image4);
        imgViewArray[4] = (ImageView)findViewById(R.id.image5);
        imgViewArray[5] = (ImageView)findViewById(R.id.image6);
        layoutArray[0] = (RelativeLayout)findViewById(R.id.imagelayout1);
        layoutArray[1] = (RelativeLayout)findViewById(R.id.imagelayout2);
        layoutArray[2] = (RelativeLayout)findViewById(R.id.imagelayout3);
        layoutArray[3] = (RelativeLayout)findViewById(R.id.imagelayout4);
        layoutArray[4] = (RelativeLayout)findViewById(R.id.imagelayout5);
        layoutArray[5] = (RelativeLayout)findViewById(R.id.imagelayout6);
        closeButtonArray[0] = (ImageView)findViewById(R.id.closebutton1);
        closeButtonArray[1] = (ImageView)findViewById(R.id.closebutton2);
        closeButtonArray[2] = (ImageView)findViewById(R.id.closebutton3);
        closeButtonArray[3] = (ImageView)findViewById(R.id.closebutton4);
        closeButtonArray[4] = (ImageView)findViewById(R.id.closebutton5);
        closeButtonArray[5] = (ImageView)findViewById(R.id.closebutton6);
        for(int i=0;i<layoutArray.length;i++)
        {
            layoutArray[i].setVisibility(View.GONE);
            closeButtonArray[i].setOnClickListener(this);
        }
        imagePaths = new ArrayList<>();
        //Importing database
        InputStream inputStream = getResources().openRawResource(R.raw.vehiclemodel);
        CSVFile csvFile = new CSVFile(inputStream);
        List makeModelList = csvFile.read();

        String[] makeModelArray = new String[makeModelList.size()];
        int i=0;
        for(Object item:makeModelList)
        {

            String[] modelItem = (String[]) item;
            if(modelItem.length>1) {
                makeModelArray[i] = modelItem[0] + " / " + modelItem[1];
            }
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTowActivity.this,
                R.layout.spinner_item, makeModelArray);
        make_model.setAdapter(adapter);

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading contracts...");
        mDialog.setCancelable(true);
        mDialog.show();

        Call<Contracts> response2 = service.getContracts();
        response2.enqueue(new Callback<Contracts>() {
                              @Override
                              public void onResponse(Call<Contracts> call, Response<Contracts> response) {
                                  mDialog.hide();
                                  if (response.isSuccessful()) {
                                      Contracts body = response.body();
                                      if (body.getSuccess() == true) {
                                          ContractData data = body.getData();
                                          contractList = data.getContracts();
                                          String[] contractArray = new String [contractList.size()];
                                          int i=0;
                                          for(Contract item:contractList)
                                          {
                                            contractArray[i] = item.getName();
                                                    i++;
                                          }
                                          ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTowActivity.this,
                                                  R.layout.spinner_item, contractArray);
                                          contract_edit.setAdapter(adapter);
                                      } else
                                          Toast.makeText(getApplicationContext(), R.string.loading_failed, Toast.LENGTH_LONG).show();


                                  } else

                                  {
                                      Toast.makeText(getApplicationContext(), R.string.loading_failed, Toast.LENGTH_LONG).show();
                                  }
                              }

                              @Override
                              public void onFailure(Call<Contracts> call, Throwable t) {
                                  Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                  mDialog.hide();
                              }
                          }

        );
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.closebutton1:
                imgViewArray[0].setImageBitmap(null);
                layoutArray[0].setVisibility(View.GONE);
                imagePaths.set(0,"");
                break;
            case R.id.closebutton2:
                imgViewArray[1].setImageBitmap(null);
                layoutArray[1].setVisibility(View.GONE);
                imagePaths.set(1,"");
                break;
            case R.id.closebutton3:
                imgViewArray[2].setImageBitmap(null);
                layoutArray[2].setVisibility(View.GONE);
                imagePaths.set(2,"");
                break;
            case R.id.closebutton4:
                imgViewArray[3].setImageBitmap(null);
                layoutArray[3].setVisibility(View.GONE);
                imagePaths.set(3,"");
                break;
            case R.id.closebutton5:
                imgViewArray[4].setImageBitmap(null);
                layoutArray[4].setVisibility(View.GONE);
                imagePaths.set(4,"");
                break;
            case R.id.closebutton6:
                imgViewArray[5].setImageBitmap(null);
                layoutArray[5].setVisibility(View.GONE);
                imagePaths.set(5,"");
                break;
            case R.id.mainLayout:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_done:
                if(validateFields()) {
                    Contract contract= null;
                    int contract_id = -1;
                    for(int i=0;i<contractList.size();i++)
                    {
                        contract = contractList.get(i);
                        if(contract.getName().equalsIgnoreCase(contract_edit.getText().toString())) {
                            contract_id = contract.getId();
                            break;
                        }
                    }
                    if(contract_id<0)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid contract.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder().addHeader("api-token", getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        }).build();
                        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
                        final RestAPI service = retrofit.create(RestAPI.class);
                        final TowRequest request = new TowRequest();
                        request.setTow_date(year_edit.getText().toString());
                        String[] array = make_model.getText().toString().split("/");
                        String model = "";
                        String make = array[0];
                        if (array.length > 1)
                            model = array[1];

                        request.setMake(make);
                        request.setModel(model);
                        request.setLicense(license_plate.getText().toString());
                        request.setReason(reason_edit.getSelectedItem().toString());
                        Price item = priceList.get(charge_edit.getSelectedItemPosition());
                        request.setPrice_id(item.getId());
                        request.setDriver_id(getSharedPreferences("MyPreference", MODE_PRIVATE).getInt("user_id", 0));
                        request.setColor(color_edit.getSelectedItem().toString());
                        request.setTowed_from("towed_from");
                        request.setContract_id(contract_id);

                        request.setStorage_id(1);
                        request.setNotes(note_edit.getText().toString());
                        request.setVin(vin_edit.getText().toString());
                        final ProgressDialog mDialog = new ProgressDialog(AddTowActivity.this);
                        mDialog.setMessage("Saving...");
                        mDialog.setCancelable(true);
                        mDialog.show();
                        Call<TowSingleResponse> response = service.postTows(request);
                        response.enqueue(new Callback<TowSingleResponse>() {
                                             @Override
                                             public void onResponse(Call<TowSingleResponse> call, Response<TowSingleResponse> response) {
                                                 mDialog.hide();
                                                 if (response.isSuccessful()) {
                                                     TowSingleResponse body = response.body();

                                                     if (body.getSuccess() == true) {
                                                         int tow_id = body.getData().getTow().getId();
                                                         for (int i = 0; i < imagePaths.size(); i++) {
                                                            if(imagePaths.get(i).length()>0) {
                                                                mDialog.setMessage("Uploading images...");
                                                                mDialog.show();
                                                                TowImageRequest imgRequst = new TowImageRequest();

                                                                imgRequst.setFormat("jpg");
                                                                imgRequst.setContent(Util.baseEncode(imagePaths.get(i)));
                                                                Call<TowImageResponse> imgResponse = service.postImagesForTows(tow_id, imgRequst);
                                                                imgResponse.enqueue(new Callback<TowImageResponse>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<TowImageResponse> call, Response<TowImageResponse> response) {
                                                                                            mDialog.hide();
                                                                                            if (response.isSuccessful()) {
                                                                                                TowImageResponse body = response.body();

                                                                                                if (body.getSuccess() == true) {

                                                                                                } else {
                                                                                                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                                                                }

                                                                                            } else

                                                                                            {
                                                                                                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<TowImageResponse> call, Throwable t) {
                                                                                            t.printStackTrace();
                                                                                            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                                                            mDialog.hide();
                                                                                        }
                                                                                    }

                                                                );
                                                            }
                                                         }
                                                         Toast.makeText(getApplicationContext(), R.string.update_success, Toast.LENGTH_LONG).show();
                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                     }

                                                 } else

                                                 {
                                                     Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                 }
                                             }

                                             @Override
                                             public void onFailure(Call<TowSingleResponse> call, Throwable t) {
                                                 t.printStackTrace();
                                                 Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                 mDialog.hide();
                                             }
                                         }

                        );
                    }
                }

                break;
            case R.id.btn_addphotos:
                ImagePicker.create(this) // Activity or Fragment
                        .start(PICK_IMAGE_MULTIPLE);
                break;
            case R.id.year_edit:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "stick_date_calendar");
                break;
        }

    }
    boolean validateFields()
    {

        String replaced_license = license_plate.getText().toString();
        if(replaced_license.length()<5)
        {

            Toast.makeText(getApplicationContext(), "License plate should be at least 5 letters.", Toast.LENGTH_LONG).show();
            return false;
        }
        String vin_text = vin_edit.getText().toString();
        if(vin_text.length()>0) {
            if (vin_text.length() < 16) {
                Toast.makeText(getApplicationContext(), "Vin should be at least 16 letters.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            selected_image_count = Math.min(6, images.size());

                        for (int i = 0; i < selected_image_count; i++) {
                            Image item = images.get(i);

                            String filePath = item.getPath();
                            imagePaths.add(filePath);
                            Bitmap yourbitmap = BitmapFactory.decodeFile(filePath);

                            imgViewArray[i].setImageBitmap(yourbitmap);
                            layoutArray[i].setVisibility(View.VISIBLE);
                            //imgViewArray[i].setAdjustViewBounds(true);

                        }
        }

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dateText = String.format("%d-%d-%d",year,month+1,day);
            AddTowActivity activity = (AddTowActivity)getActivity();
            activity.year_edit.setText(dateText);
        }
    }

    void loadPrices()
    {

        final ProgressDialog mDialog = new ProgressDialog(AddTowActivity.this);
        mDialog.setMessage("Loading prices...");
        mDialog.setCancelable(true);
        mDialog.show();

        Call<Prices> response = service.getPrices();
        response.enqueue(new Callback<Prices>() {
                             @Override
                             public void onResponse(Call<Prices> call, Response<Prices> response) {
                                 mDialog.hide();
                                 if (response.isSuccessful()) {
                                     Prices body = response.body();
                                     if (body.getSuccess() == true) {
                                         PriceData data = body.getData();
                                         priceList = data.getPrices();
                                         ArrayList<String> adapterArray = new ArrayList<String>();
                                         for(Price item:priceList)
                                            adapterArray.add(item.getName());
                                         ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddTowActivity.this, R.layout.spinner_item, adapterArray); //selected item will look like a spinner set from XML
                                         spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                         charge_edit.setAdapter(spinnerArrayAdapter);
                                     } else {
                                         Toast.makeText(getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                     }

                                 } else

                                 {
                                     Toast.makeText(getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                 }
                             }

                             @Override
                             public void onFailure(Call<Prices> call, Throwable t) {
                                 t.printStackTrace();
                                 Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                 mDialog.hide();
                             }
                         }

        );
    }
}

