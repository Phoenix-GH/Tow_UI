package com.noname.akio.named;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noname.akio.named.clientAPI.Response.Contracts.Contract;
import com.noname.akio.named.clientAPI.Response.Vehicles.Vehicle;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleItemData;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleList;
import com.noname.akio.named.clientAPI.Response.Vehicles.Vehicles;
import com.noname.akio.named.clientAPI.RestAPI;
import com.noname.akio.named.clientAPI.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
public class NewVehicleActivity extends AppCompatActivity implements OnClickListener{
    TextView bar_title, bar_subtitle, date_value1,date_value2;
    RestAPI service;
    Contract contract;
    ArrayList<Vehicle> vehicleList;
    ListView listView;
    HashMap<Integer, VehicleList> vehicleMap;
    boolean stick_or_tow=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getString(R.string.dark_selected),false)==true)
            setTheme(R.style.NobarDarkTheme);
        setContentView(R.layout.activity_newvehicle);
        contract = (Contract)getIntent().getSerializableExtra("contract_data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.vehicle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        listView = (ListView)findViewById(R.id.vehicle_list);
        ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        date_value1 = (TextView)findViewById(R.id.date_value1);
        date_value2 = (TextView)findViewById(R.id.date_value2);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        LinearLayout layout = (LinearLayout)findViewById(R.id.btn_touch_to_add);
        layout.setOnClickListener(this);

        ImageView stick_date_calendar = (ImageView)findViewById(R.id.stick_date_calendar);
        stick_date_calendar.setOnClickListener(this);

        ImageView tow_date_calendar = (ImageView)findViewById(R.id.tow_date_calendar);
        tow_date_calendar.setOnClickListener(this);
        bar_title = (TextView)toolbar.findViewById(R.id.bar_title);
        bar_subtitle = (TextView)toolbar.findViewById(R.id.bar_subtitle);

        if(contract!=null)
        {
            bar_title.setText(contract.getAddress());
            String subAddress = String.format("%s %s, %s", contract.getCity(), contract.getZip(), contract.getState());
            bar_subtitle.setText(subAddress);
        }
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("api-token", getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).build();
                return chain.proceed(request);
            }
        }).build();
        vehicleList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
        service = retrofit.create(RestAPI.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVehicleInfo();
    }

    void loadVehicleInfo()
    {
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading vehicle information...");
        mDialog.setCancelable(true);
        mDialog.show();

        int vehicle_list_id = getIntent().getIntExtra("vehicle_list_id", 0);
        Call<Vehicles> response = service.getVehicleById(contract.getId(),vehicle_list_id);
        response.enqueue(new Callback<Vehicles>() {
                             @Override
                             public void onResponse(Call<Vehicles> call, Response<Vehicles> response) {
                                 mDialog.hide();
                                 mDialog.dismiss();
                                 if (response.isSuccessful()) {
                                     Vehicles body = response.body();
                                     if (body.getSuccess() == true) {
                                         VehicleItemData data = body.getData();
                                         vehicleList = data.getVehicles();
                                         listView.setAdapter(new VehicleAdapter(NewVehicleActivity.this,vehicleList));
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
                                 Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                 mDialog.hide();
                                 mDialog.dismiss();
                             }
                         }

        );
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_done:
                break;
            case R.id.btn_touch_to_add:
                Intent intent = new Intent(this, AddVehicleActivity.class);
                intent.putExtra("contract_data",contract);
                intent.putExtra("vehicle_list_id",getIntent().getIntExtra("vehicle_list_id",0));
                startActivity(intent);
                break;
            case R.id.stick_date_calendar:
                stick_or_tow = false;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "stick_date_calendar");
                break;
            case R.id.tow_date_calendar:
                stick_or_tow = true;
                DialogFragment towFragment = new DatePickerFragment();
                towFragment.show(getSupportFragmentManager(), "tow_date_calendar");
                break;
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
            String dateText = generateDate(year,month,day);
            NewVehicleActivity activity = (NewVehicleActivity)getActivity();
            if(((NewVehicleActivity)getActivity()).stick_or_tow)
                activity.date_value2.setText(dateText);
            else
                activity.date_value1.setText(dateText);
        }
        String generateDate(int year, int month, int day)
        {
            String[] months ={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Sep","Oct","Nov","Dec"};
            String ret = String.format("%s-%s-%d", months[month],day,year);
            return ret;
        }
    }

}

