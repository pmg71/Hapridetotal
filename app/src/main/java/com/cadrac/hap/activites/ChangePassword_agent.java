package com.cadrac.hap.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cadrac.hap.R;
import com.cadrac.hap.responses.supervisior_Login_Response;
import com.cadrac.hap.supporter_classes.Connection_Detector;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword_agent extends AppCompatActivity implements View.OnClickListener  {
    EditText u_id,u_num,u_pwd;
    Button submit;
    supervisior_Login_Response supervisor_loginResponse;
    Connection_Detector connection_detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_change_password );

        u_id=findViewById( R.id.supervisior_uid );
        u_num=findViewById( R.id.supervisior_Mobile );
        u_pwd=findViewById( R.id.supervisior_New_password );
        submit=findViewById( R.id.submit );
        connection_detector = new Connection_Detector(this);

        submit.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submit){
            if(u_id.getText().toString().length()==0 && u_num.getText().toString().length()==0 &&u_pwd.getText().toString().length()==0)
            {
                u_id.setError( "plz enter ur ID" );
                u_num.setError( "plz enter ur mobile number" );
                u_pwd.setError( "plz enter ur new password" );

            }
            else{
                changepassword();
            }
        }
    }

    private void changepassword() {
        if (connection_detector.isConnectingToInternet()) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<supervisior_Login_Response> call = api.supervisor_change_pwd(u_id.getText().toString(),
                        u_num.getText().toString(), u_pwd.getText().toString(), "agent");
                call.enqueue(new Callback<supervisior_Login_Response>() {
                    @Override
                    public void onResponse(Call<supervisior_Login_Response> call,
                                           Response<supervisior_Login_Response> response) {

                        supervisor_loginResponse = response.body();
                        Log.d("TAG", "onResponse: login" + supervisor_loginResponse);
                        Log.d("TAG", "onResponse: s" + supervisor_loginResponse.getStatus());
                        Log.d("TAG", "onResponse: uid" + supervisor_loginResponse.getU_id());
                        Log.d("TAG", "onResponse: unum" + supervisor_loginResponse.getU_num());
                        Log.d("TAG", "onResponse: upwd" + supervisor_loginResponse.getU_pwd());
                        if (supervisor_loginResponse.getStatus().equalsIgnoreCase("TRUE")) {
                            Toast.makeText(ChangePassword_agent.this, "Succesfully Changed!", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(getApplicationContext(), agent_login.class);
                            startActivity(i1);
                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid Agent UserId &  Agent Mobile Number",
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
        }else
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }}

