package com.noname.akio.named;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.noname.akio.named.clientAPI.Util;

/**
 * A login screen that offers login via email/password.
 */
public class SettingActivity extends AppCompatActivity {

    // UI references.
    private Spinner theme_selector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getBoolean(getString(R.string.dark_selected),false)==true)
            setTheme(R.style.NobarDarkTheme);
        setContentView(R.layout.activity_settings);

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
        ImageView btnBack=(ImageView)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SettingActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        theme_selector=(Spinner)findViewById(R.id.theme_selector);

        Button btnChange = (Button)findViewById(R.id.btn_select);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                if(theme_selector.getSelectedItemPosition()==0) {
                    setTheme(R.style.DarkTheme);

                    edit.putBoolean(getString(R.string.dark_selected), true);
                }
                else if(theme_selector.getSelectedItemPosition()==1) {
                    edit.putBoolean(getString(R.string.dark_selected), false);
                    setTheme(R.style.LightTheme);
                }
                edit.commit();
                recreate();
            }
        });
    }

}