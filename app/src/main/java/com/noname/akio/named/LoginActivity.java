package com.noname.akio.named;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noname.akio.named.clientAPI.Request.User;
import com.noname.akio.named.clientAPI.Response.Login.LoginData;
import com.noname.akio.named.clientAPI.Response.Login.LoginResponse;
import com.noname.akio.named.clientAPI.Response.Me.Me;
import com.noname.akio.named.clientAPI.Response.Me.MeResponse;
import com.noname.akio.named.clientAPI.RestAPI;

import java.io.IOException;

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
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getApplicationContext().getSharedPreferences("MyPreference", MODE_PRIVATE);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {

                                                        login(mEmailView.getText().toString(), mPasswordView.getText().toString() );
                                                  }
                                              }

        );

        getWindow()

                .

                        setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(preferences.getString("username","").length()>0)
        {
            login(preferences.getString("username",""), preferences.getString("password",""));
        }
    }
    private void login(String userName, final String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RestAPI service = retrofit.create(RestAPI.class);
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(true);
        mDialog.show();
        Call<LoginResponse> response = service.postLogin(user);
        response.enqueue(new Callback<LoginResponse>() {
                             @Override
                             public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                 mDialog.hide();
                                 if (response.isSuccessful()) {
                                     LoginResponse body = response.body();
                                     if (body.getSuccess() == true) {
                                         mDialog.show();
                                        mPasswordView.setText("");
                                         final SharedPreferences.Editor editor = preferences.edit();
                                         LoginData data = body.getData();
                                         editor.putString("token", data.getToken());
                                         editor.commit();
                                         OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                                             @Override
                                             public okhttp3.Response intercept(Chain chain) throws IOException {
                                                 Request request = chain.request().newBuilder().addHeader("api-token", getSharedPreferences("MyPreference", MODE_PRIVATE).getString("token", "")).build();
                                                 return chain.proceed(request);
                                             }
                                         }).build();
                                         Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(RestAPI.BASE_URL).client(httpClient).build();
                                         RestAPI service = retrofit.create(RestAPI.class);
                                         final Call<MeResponse> meResponse = service.getMe();
                                         meResponse.enqueue(new Callback<MeResponse>() {
                                                                @Override
                                                                public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                                                                    mDialog.hide();
                                                                    if (response.isSuccessful()) {
                                                                        MeResponse body = response.body();
                                                                        if (body.getSuccess() == true) {

                                                                            Me data = body.getData();
                                                                            editor.putInt("user_id", data.getUser_id());
                                                                            editor.putString("name", data.getName());
                                                                            editor.putString("username", data.getUsername());
                                                                            editor.putString("role", data.getRole());
                                                                            editor.putString("password", password);
                                                                            editor.commit();
                                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), R.string.incorrect_password, Toast.LENGTH_LONG).show();
                                                                        }

                                                                    } else

                                                                    {
                                                                        Toast.makeText(getApplicationContext(), R.string.incorrect_password, Toast.LENGTH_LONG).show();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<MeResponse> call, Throwable t) {
                                                                    Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                                                                    mDialog.hide();
                                                                }
                                                            }

                                         );

                                     } else {
                                         Toast.makeText(getApplicationContext(), R.string.incorrect_password, Toast.LENGTH_LONG).show();
                                     }

                                 } else
                                 {
                                     Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                                 }
                             }

                             @Override
                             public void onFailure(Call<LoginResponse> call, Throwable t) {
                                 Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                                 mDialog.hide();
                             }
                         }

        );
    }

}

