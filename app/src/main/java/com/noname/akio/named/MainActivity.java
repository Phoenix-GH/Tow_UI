package com.noname.akio.named;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noname.akio.named.clientAPI.Response.Contracts.Contract;
import com.noname.akio.named.clientAPI.Response.Contracts.ContractData;
import com.noname.akio.named.clientAPI.Response.Contracts.Contracts;
import com.noname.akio.named.clientAPI.Response.Storages.Storage;
import com.noname.akio.named.clientAPI.Response.Storages.StorageData;
import com.noname.akio.named.clientAPI.Response.Storages.Storages;
import com.noname.akio.named.clientAPI.Response.Tows.Tow;
import com.noname.akio.named.clientAPI.Response.Tows.TowData;
import com.noname.akio.named.clientAPI.Response.Tows.Tows;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleData;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleList;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleLists;
import com.noname.akio.named.clientAPI.RestAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private static int page_number = 0;
    int titleColor;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public static Typeface lato_regular, lato_semibold, lato_medium_italic, lato_bold, proxima_regular, lato_bold_italic;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static int selectedIndex = 0;
    public ArrayList<Contract> contractList;
    public ArrayList<VehicleList> vehicleList;
    public ArrayList<Tow> towList;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getBoolean(getString(R.string.dark_selected), false) == true)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_main);
        contractList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        towList = new ArrayList<>();
        //Setting the fonts
        lato_bold = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");
        lato_regular = Typeface.createFromAsset(getAssets(), "fonts/Lato-BoldItalic.ttf");
        lato_bold_italic = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        lato_semibold = Typeface.createFromAsset(getAssets(), "fonts/Lato-Semibold.ttf");
        lato_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Lato-MediumItalic.ttf");
        proxima_regular = Typeface.createFromAsset(getAssets(), "fonts/ProximaNovaSoft-Regular.ttf");

        //Setting the status bar
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.header_icon);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        TypedValue typedValue = new TypedValue();

        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        titleColor = typedValue.data;


        String title = "History";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(titleColor), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.color.dark_backcolor, typedValue, true);
        int color = typedValue.data;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(getResources().getColor(R.color.dark_backcolor));
        } else {
            // do something for phones running an SDK before lollipop
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getBoolean(getString(R.string.dark_selected), false) == true)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance())
                .commit();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {


        if (position == 1) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        if (position == 2) {
            SharedPreferences preferences = getSharedPreferences("MyPreference", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
        }
    }

    public void onSectionAttached(int number) {

        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(titleColor, PorterDuff.Mode.SRC_IN);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addNew) {
            Intent intent = null;
            if (page_number == 0) {
                intent = new Intent(MainActivity.this, AddTowActivity.class);
                ArrayList<String> towArray = new ArrayList<>();

                for (Tow tow : towList) {
                    towArray.add(tow.getMake() + "/" + tow.getModel());

                }
                intent.putStringArrayListExtra("tow_data", towArray);
            } else if (page_number == 1) {
                if (contractList.size() > 0) {
                    intent = new Intent(MainActivity.this, NewVehicleActivity.class);
                    intent.putExtra("contract_data", contractList.get(selectedIndex));
                    if (vehicleList.size() > 0)
                        intent.putExtra("vehicle_list_id", vehicleList.get(0).getId());
                }
            }
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        ListView listView;
        LinearLayout btn_history, btn_list;
        ImageView imgHistory, imgList, btnClear;
        TextView txtHistory, txtList;
        int color, titleColor;
        EditText search_text;

        HashMap<Integer, Storage> storageList;
        private static final String ARG_SECTION_NUMBER = "section_number";
        RestAPI service;

        SharedPreferences.Editor editor;
        ProgressDialog mDialog;


        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment() {
            storageList = new HashMap<>();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            listView = (ListView) rootView.findViewById(R.id.mainListView);
            listView.setAdapter(new HistoryAdapter(getContext(), ((MainActivity) getActivity()).towList, storageList));
            SharedPreferences preferences = getActivity().getSharedPreferences("MyPreference", MODE_PRIVATE);
            editor = preferences.edit();
            btn_history = (LinearLayout) rootView.findViewById(R.id.tab_history);
            btn_history.setOnClickListener(this);
            search_text = (EditText) rootView.findViewById(R.id.search_text);

            btn_list = (LinearLayout) rootView.findViewById(R.id.tab_list);
            btn_list.setOnClickListener(this);
            listView.setOnItemClickListener(this);

            mDialog = new ProgressDialog(getActivity());
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.attr.colorControlHighlight, typedValue, true);
            color = typedValue.data;

            getActivity().getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            titleColor = typedValue.data;
            imgHistory = (ImageView) rootView.findViewById(R.id.home_image);
            imgList = (ImageView) rootView.findViewById(R.id.list_image);
            btnClear = (ImageView) rootView.findViewById(R.id.btn_clear);
            txtHistory = (TextView) rootView.findViewById(R.id.home_text);
            txtList = (TextView) rootView.findViewById(R.id.list_text);

            imgHistory.setColorFilter(color);
            imgList.setColorFilter(android.R.color.white);
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("api-token", getActivity().getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).build();
                    return chain.proceed(request);
                }
            }).build();

            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
            service = retrofit.create(RestAPI.class);

            String[] searchArray;
            if (page_number == 0) {
                searchArray = new String[((MainActivity) getActivity()).towList.size()];
                for (int i = 0; i < searchArray.length; i++) {
                    String searchString = ((MainActivity) getActivity()).towList.get(i).getMake() + " " + ((MainActivity) getActivity()).towList.get(i).getModel();
                    searchArray[i] = searchString;
                }
            } else {
                searchArray = new String[((MainActivity) getActivity()).contractList.size()];
                for (int i = 0; i < searchArray.length; i++) {
                    Contract item = ((MainActivity) getActivity()).contractList.get(i);
                    searchArray[i] = item.getName();
                }
            }
            if (page_number == 0) {
                loadStorage();
            } else {
                page_number = 0;
                btn_list.performClick();
            }
            btnClear.setOnClickListener(this);
            search_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listView.smoothScrollToPosition(0);

                    if (count > 0)
                        btnClear.setVisibility(View.VISIBLE);
                    if (page_number == 0) {
                        ArrayList<Tow> newTowList = new ArrayList<Tow>();

                        ArrayList<String> resultList = new ArrayList<String>();
                        for (int i = 0; i < ((MainActivity) getActivity()).towList.size(); i++) {
                            String searchString = ((MainActivity) getActivity()).towList.get(i).getMake() + " " + ((MainActivity) getActivity()).towList.get(i).getModel() + " " + ((MainActivity) getActivity()).towList.get(i).getLicense() + " " + ((MainActivity) getActivity()).towList.get(i).getTowed_from();
                            int k = searchString.toLowerCase().indexOf(s.toString().toLowerCase());
                            if (k >= 0) {
                                resultList.add(searchString);
                                newTowList.add(((MainActivity) getActivity()).towList.get(i));
                            }
                        }

                        String[] resultArray = new String[resultList.size()];
                        for (int i = 0; i < resultList.size(); i++) {
                            resultArray[i] = resultList.get(i);

                        }

                        listView.setAdapter(new HistoryAdapter(getActivity(), newTowList, storageList));

                    } else {
                        ArrayList<Contract> newContractList = new ArrayList<>();

                        ArrayList<String> resultList = new ArrayList<>();
                        for (int i = 0; i < ((MainActivity) getActivity()).contractList.size(); i++) {
                            Contract item = ((MainActivity) getActivity()).contractList.get(i);

                            int k = item.getName().toLowerCase().indexOf(s.toString().toLowerCase());
                            if (k >= 0) {
                                resultList.add(item.getName());
                                newContractList.add(item);
                            }
                        }

                        String[] resultArray = new String[resultList.size()];
                        for (int i = 0; i < resultList.size(); i++) {
                            resultArray[i] = resultList.get(i);

                        }

                        listView.setAdapter(new CustomAdapter(getActivity(), newContractList, new ArrayList<VehicleList>()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_clear:
                    search_text.setText("");
                    btnClear.setVisibility(View.GONE);
                    break;
                case R.id.tab_history:
                    String title = "History";
                    SpannableString s = new SpannableString(title);
                    s.setSpan(new ForegroundColorSpan(titleColor), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((MainActivity) getActivity()).getSupportActionBar().setTitle(s);

                    imgHistory.setColorFilter(color);
                    imgList.setColorFilter(android.R.color.white);
                    txtHistory.setTextColor(color);
                    txtList.setTextColor(getResources().getColor(R.color.unselected_tab_bar));
                    if (page_number != 0)
                        loadHistory();
                    page_number = 0;
                    break;
                case R.id.tab_list:
                    title = "Vehicle List";
                    s = new SpannableString(title);
                    s.setSpan(new ForegroundColorSpan(titleColor), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((MainActivity) getActivity()).getSupportActionBar().setTitle(s);

                    imgList.setColorFilter(color);
                    imgHistory.setColorFilter(android.R.color.white);

                    txtList.setTextColor(color);
                    txtHistory.setTextColor(getResources().getColor(R.color.unselected_tab_bar));
                    if (page_number != 1) {
                        final ProgressDialog mDialog2 = new ProgressDialog(getActivity());
                        mDialog2.setMessage("Loading contracts...");
                        mDialog2.setCancelable(true);
                        mDialog2.show();

                        Call<Contracts> response2 = service.getContracts();
                        response2.enqueue(new Callback<Contracts>() {
                                              @Override
                                              public void onResponse(Call<Contracts> call, Response<Contracts> response) {
                                                  mDialog2.hide();

                                                  if (response.isSuccessful()) {
                                                      Contracts body = response.body();
                                                      if (body.getSuccess() == true) {
                                                          ContractData data = body.getData();
                                                          ((MainActivity) getActivity()).contractList = data.getContracts();
                                                          loadVehicleInfo(false);
                                                      } else
                                                          Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                                  } else

                                                  {
                                                      Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                                  }
                                              }

                                              @Override
                                              public void onFailure(Call<Contracts> call, Throwable t) {
                                                  Toast.makeText(getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                                  mDialog2.hide();
                                              }
                                          }

                        );
                    }
                    page_number = 1;
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            if (page_number == 1) {
                if (i < ((MainActivity) getActivity()).contractList.size()) {

                    selectedIndex = i;
                    loadVehicleInfo(true);
                }
            }

        }

        void loadVehicleInfo(final boolean scroll) {
            mDialog.setMessage("Loading vehicle information...");
            mDialog.setCancelable(true);
            mDialog.show();

            Call<VehicleLists> response = service.getVehicleLists(((MainActivity) getActivity()).contractList.get(selectedIndex).getId());
            response.enqueue(new Callback<VehicleLists>() {
                                 @Override
                                 public void onResponse(Call<VehicleLists> call, Response<VehicleLists> response) {
                                     mDialog.hide();
                                     if (response.isSuccessful()) {
                                         VehicleLists body = response.body();
                                         if (body.getSuccess() == true) {
                                             VehicleData data = body.getData();
                                             ((MainActivity) getActivity()).vehicleList = data.getVehicle_lists();

                                             listView.setAdapter(new CustomAdapter(getContext(), ((MainActivity) getActivity()).contractList, ((MainActivity) getActivity()).vehicleList));
                                             if(((MainActivity) getActivity()).vehicleList.size()>0) {
                                                 listView.post(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         // Select the last row so it will scroll into view...
                                                         if (scroll)
                                                             listView.setSelection(((MainActivity) getActivity()).contractList.size());
                                                     }
                                                 });
                                             }

                                         } else {
                                             Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                         }

                                     } else

                                     {
                                         Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<VehicleLists> call, Throwable t) {
                                     Toast.makeText(getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                     mDialog.hide();
                                 }
                             }
            );
        }

        void loadHistory() {
            mDialog.setMessage("Loading history...");
            mDialog.setCancelable(true);
            mDialog.show();
            Call<Tows> response = service.getTows();
            response.enqueue(new Callback<Tows>() {
                                 @Override
                                 public void onResponse(Call<Tows> call, Response<Tows> response) {
                                     mDialog.hide();
                                     InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                     imm.hideSoftInputFromWindow(search_text.getWindowToken(), 0);

                                     if (response.isSuccessful()) {
                                         Tows body = response.body();
                                         if (body.getSuccess() == true) {
                                             TowData data = body.getData();

                                             ((MainActivity) getActivity()).towList = data.getTow();
                                             listView.setAdapter(new HistoryAdapter(getActivity(), ((MainActivity) getActivity()).towList, storageList));

                                         } else {
                                             Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                         }

                                     } else

                                     {
                                         Toast.makeText(getActivity().getApplicationContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Tows> call, Throwable t) {
                                     t.printStackTrace();

                                     mDialog.hide();
                                 }
                             }

            );
        }

        void loadStorage() {
            mDialog.setMessage("Loading history...");
            mDialog.setCancelable(true);
            mDialog.show();

            Call<Storages> response = service.getStorages();
            response.enqueue(new Callback<Storages>() {
                                 @Override
                                 public void onResponse(Call<Storages> call, Response<Storages> response) {
                                     mDialog.hide();
                                     if (response.isSuccessful()) {
                                         Storages body = response.body();
                                         if (body.getSuccess() == true) {
                                             StorageData data = body.getData();
                                             List<Storage> list = data.getStorages();
                                             for (Storage item : list) {
                                                 storageList.put(item.getId(), item);
                                             }
                                             loadHistory();
                                         } else {
                                             Toast.makeText(getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                         }

                                     } else

                                     {
                                         Toast.makeText(getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Storages> call, Throwable t) {
                                     t.printStackTrace();
                                     Toast.makeText(getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                                     mDialog.hide();
                                 }
                             }

            );
        }
    }

}
