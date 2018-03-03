package com.noname.akio.named;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.noname.akio.named.clientAPI.Request.VehicleItemRequest;
import com.noname.akio.named.clientAPI.Response.Contracts.Contract;
import com.noname.akio.named.clientAPI.Response.Vehicles.Vehicles;
import com.noname.akio.named.clientAPI.RestAPI;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class AddVehicleActivity extends AppCompatActivity implements OnClickListener{
    EditText  color_edit , checkbox_other;
    EditText license_plate_edit;
    AutoCompleteTextView make_model_edit;
    Contract contract;
    int vehicle_list_id;
    public static int PICK_IMAGE_MULTIPLE = 100;
    ArrayList<String> imagePaths;
    int selected_image_count = 0;
    ImageView[] imgViewArray,closeButtonArray;
    RelativeLayout[] layoutArray;
    int textlength = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getBoolean(getString(R.string.dark_selected),false) == true)
            setTheme(R.style.NobarDarkTheme);
        setContentView(R.layout.activity_addvehicle);
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
        make_model_edit = (AutoCompleteTextView)findViewById(R.id.make_model_edit);
        Button btnAddPhotos = (Button)findViewById(R.id.btn_addphotos);
        btnAddPhotos.setOnClickListener(this);
        //Importing database
        InputStream inputStream = getResources().openRawResource(R.raw.vehiclemodel);
        CSVFile csvFile = new CSVFile(inputStream);
        List makeModelList = csvFile.read();

        String[] makeModelArray = new String[makeModelList.size()];
        int i = 0;
        for(Object item:makeModelList)
        {

            String[] modelItem = (String[]) item;
            if(modelItem.length>1) {
                makeModelArray[i] = modelItem[0] + " / " + modelItem[1];
            }
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddVehicleActivity.this,
                R.layout.spinner_item, makeModelArray);
        make_model_edit.setAdapter(adapter);
        make_model_edit.setDropDownHeight(1000);
        color_edit = (EditText)findViewById(R.id.color_edit);
        license_plate_edit = (EditText)findViewById(R.id.license_place_edit);

        license_plate_edit.addTextChangedListener(new TextWatcher()
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
                String text = license_plate_edit.getText().toString();
                textlength = license_plate_edit.getText().length();

                if(text.endsWith(" "))
                    return;
                if(count == 0)
                {
                    return;
                }

                else if(textlength == 4)
                {
                    license_plate_edit.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());

                }
                String str = s.toString();
                if (!str.equals(str.toUpperCase())) {
                    str = str.toUpperCase();
                    license_plate_edit.setText(str);

                }
                license_plate_edit.setSelection(license_plate_edit.getText().length());

            }});

        contract = (Contract)getIntent().getSerializableExtra("contract_data");
        vehicle_list_id = getIntent().getIntExtra("vehicle_list_id",0);
        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);
        layout.setOnClickListener(this);
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
        for(i=0;i<layoutArray.length;i++)
        {
            layoutArray[i].setVisibility(View.GONE);
            closeButtonArray[i].setOnClickListener(this);
        }
        imagePaths = new ArrayList<>();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            case R.id.btn_addphotos:
                ImagePicker.create(this) // Activity or Fragment
                        .start(PICK_IMAGE_MULTIPLE);
                break;
            case R.id.main_layout:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_done:
                if(validateFields()) {


                    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("api-token", getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).addHeader("Content-Type", "application/json").build();
                            return chain.proceed(request);
                        }
                    }).build();
                    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
                    RestAPI service = retrofit.create(RestAPI.class);
                    VehicleItemRequest request = new VehicleItemRequest();

                    String[] array = make_model_edit.getText().toString().split("/");
                    String model = "";
                    String make = array[0];
                    if (array.length > 1)
                        model = array[1];

                    request.setMake(make);
                    request.setModel(model);
                    request.setColor(color_edit.getText().toString());
                    request.setLicense(license_plate_edit.getText().toString());
                    request.setComments("");
                    CheckBox[] checkArray = new CheckBox[9];
                    checkArray[0] = (CheckBox) findViewById(R.id.reason1);
                    checkArray[1] = (CheckBox) findViewById(R.id.reason2);
                    checkArray[2] = (CheckBox) findViewById(R.id.reason3);
                    checkArray[3] = (CheckBox) findViewById(R.id.reason4);
                    checkArray[4] = (CheckBox) findViewById(R.id.reason5);
                    checkArray[5] = (CheckBox) findViewById(R.id.reason6);
                    checkArray[6] = (CheckBox) findViewById(R.id.reason7);
                    checkArray[7] = (CheckBox) findViewById(R.id.reason8);
                    checkArray[8] = (CheckBox) findViewById(R.id.reason9);
                    String reason = "";
                    for (CheckBox check : checkArray) {
                        if (check.isChecked()) {
                            if (reason != "")
                                reason += "|";
                            reason += check.getText().toString();
                        }
                    }
                    request.setReasons(reason);
                    final ProgressDialog mDialog = new ProgressDialog(AddVehicleActivity.this);
                    mDialog.setMessage("Saving...");
                    mDialog.setCancelable(true);
                    mDialog.show();
                    Call<Vehicles> response = service.postVehicleById(contract.getId(), vehicle_list_id, request);
                    response.enqueue(new Callback<Vehicles>() {
                                         @Override
                                         public void onResponse(Call<Vehicles> call, Response<Vehicles> response) {
                                             mDialog.hide();
                                             if (response.isSuccessful()) {
                                                 Vehicles body = response.body();
                                                 if (body.getSuccess() == true) {
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
                                         public void onFailure(Call<Vehicles> call, Throwable t) {
                                             t.printStackTrace();
                                             Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                             mDialog.hide();
                                         }
                                     }

                    );
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "License plate should be at least 5 letters.", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    boolean validateFields()
    {

        String replaced_license = license_plate_edit.getText().toString();
        if(replaced_license.length()>=5)
        {
            return true;
        }
        return false;
    }

}

