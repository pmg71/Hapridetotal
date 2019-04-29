package com.cadrac.hap.activites;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.cadrac.hap.R;

        import com.cadrac.hap.responses.supervisior_Login_Response;
        import com.cadrac.hap.supporter_classes.Connection_Detector;
        import com.cadrac.hap.utils.Config;
        import com.cadrac.hap.webservices.API;
        import com.cadrac.hap.webservices.RestClient;

        import java.util.ArrayList;

        import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;


public class supervisior_login extends AppCompatActivity implements View.OnClickListener {

    EditText supervisorUsername, supervisorPassword;
    Button supervisorLogin;
    supervisior_Login_Response supervisor_loginResponse;
    TextView supervisor_changepassword;
    Connection_Detector connection_detector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisior_login);

        supervisorUsername = (EditText) findViewById(R.id.supervisior_username);
        supervisorPassword = (EditText) findViewById(R.id.supervisior_password);
        supervisorLogin = (Button) findViewById(R.id.supervisior_login);
        supervisor_changepassword = (TextView)findViewById(R.id.supervisior_changepassword);
connection_detector = new Connection_Detector(this);
        supervisorLogin.setOnClickListener(this);
        supervisor_changepassword.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.supervisior_login) {
            if (supervisorUsername.getText().length() == 0) {
                supervisorUsername.setError("Enter Username");
            } else if (supervisorPassword.getText().length() == 0) {
                supervisorPassword.setError("Enter Password");
            }

            else{
                Log.d( "TAG", "onClick: "+supervisorLogin );
                if (connection_detector.isConnectingToInternet()) {
                    userLogin();
                }else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId()==R.id.supervisior_changepassword)
        {
            changePassword();
        }
    }

    private void changePassword() {
        Intent i=new Intent(getApplicationContext(),ChangePassword.class);
        startActivity(i);
    }

    private void userLogin() {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d("TAG", "userLogin:resp");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("supervisiorusername", supervisorUsername.getText().toString());

            editor.commit();
            Log.d("TAG", "userLogin:pass" + supervisorPassword.getText().toString());
            Log.d("TAG", "userLogin:user" + supervisorUsername.getText().toString());
            Call<supervisior_Login_Response> call = api.supervisorlogin(supervisorUsername.getText().toString(),
                    supervisorPassword.getText().toString(),"supervisor");
            call.enqueue(new Callback<supervisior_Login_Response>() {
                @Override
                public void onResponse(Call<supervisior_Login_Response> call,
                                       Response<supervisior_Login_Response> response) {

                    supervisor_loginResponse = response.body();
                    Log.d("TAG", "onResponse: login" + supervisor_loginResponse);
                    Log.d("TAG", "onResponse: s" + supervisor_loginResponse.getStatus());

                    if (supervisor_loginResponse.getStatus().equalsIgnoreCase("True33")) {
                        Config.saveLoginusername(getApplicationContext(),supervisorUsername.getText().toString());
                        Config.saveLoginpassword(getApplicationContext(),supervisorPassword.getText().toString());
                        Intent i1 = new Intent(getApplicationContext(), home_supervisor.class);
                        Config.saveLoginStatus(getApplicationContext(),"2");
                        i1.putExtra("Supervisior Username", supervisor_loginResponse.getUsername());
                        startActivity(i1);
                        finish();
                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Please enter valid Supervisor Username&  Supervisor Password",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<supervisior_Login_Response> call, Throwable t) {
                    t.getMessage();
                    Log.d(" TAG", "onFailure:fail" + t);
                    Toast.makeText(getApplicationContext(),
                            "Try Again!",
                            Toast.LENGTH_LONG).show();
                }

            });
        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }
    }


   /* public void onBackPressed() {

        Intent intent=new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );

    }*/


}