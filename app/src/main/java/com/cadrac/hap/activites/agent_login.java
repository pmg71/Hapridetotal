package com.cadrac.hap.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.R;
import com.cadrac.hap.responses.PlacesResponse;
import com.cadrac.hap.responses.agent_Login_Response;

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

public class agent_login extends AppCompatActivity implements View.OnClickListener {

Connection_Detector connection_detector;
    EditText agent_username, agent_password;
    Spinner agent_from;
    Button agent_login;
    agent_Login_Response loginResponse;
    PlacesResponse placesResponse;
    ArrayList<String> places;
    String agent_source;
    TextView agent_changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_login);

        agent_username = (EditText) findViewById(R.id.agent_username);
        agent_password = (EditText) findViewById(R.id.agent_password);
        agent_from = (Spinner) findViewById(R.id.agent_sourcespinner);
        agent_login = (Button) findViewById(R.id.agent_login);
        connection_detector = new Connection_Detector(this);
        agent_changepassword = (TextView) findViewById(R.id.agent_changepassword);
        agent_login.setOnClickListener(this);
        agent_changepassword.setOnClickListener(this);

        agent_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                agent_source= agent_from.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+agent_source);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sourceSpinner();

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.agent_login) {
            if (agent_username.getText().length() == 0) {
                agent_username.setError("Enter Username");
            } else if (agent_password.getText().length() == 0) {
                agent_password.setError("Enter Password");
            }else if(agent_source.equalsIgnoreCase("Please Select Your source")){
                Toast.makeText(getApplicationContext(),
                        "Please enter valid source",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Log.d( "TAG", "onClick: "+agent_login );
                userLogin();
            }
        }
        if (v.getId()==R.id.agent_changepassword)
        {
            changePassword();
        }
    }

    private void changePassword() {
        Intent i=new Intent(getApplicationContext(),ChangePassword_agent.class);
        startActivity(i);
    }

    private void userLogin() {
        if (connection_detector.isConnectingToInternet()) {
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
                //editor.putString("from",source);
                editor.putString("username", agent_username.getText().toString());

                editor.commit();
                Log.d("TAG", "userLogin:pass" + agent_password.getText().toString());
                Log.d("TAG", "userLogin:user" + agent_username.getText().toString());
                Call<agent_Login_Response> call = api.agentlogin(agent_username.getText().toString(),
                        agent_password.getText().toString(), "agent", agent_source);
                call.enqueue(new Callback<agent_Login_Response>() {
                    @Override
                    public void onResponse(Call<agent_Login_Response> call,
                                           Response<agent_Login_Response> response) {

                        loginResponse = response.body();
                        Log.d("TAG", "onResponse: login" + loginResponse);
                        Log.d("TAG", "onResponse: s" + loginResponse.getStatus());

                        if (loginResponse.getStatus().equalsIgnoreCase("True")) {
                            Config.saveLoginusername(getApplicationContext(), agent_username.getText().toString());
                            Config.saveLoginpassword(getApplicationContext(), agent_password.getText().toString());
                            Config.saveLoginfrom(getApplicationContext(), agent_source);
                            Config.saveLoginStatus(getApplicationContext(), "1");
                            Config.savecode(getApplicationContext(),loginResponse.getCode());
                            Config.saveLoginsupervisorId(getApplicationContext(), loginResponse.getS_id());
                            Intent i1 = new Intent(getApplicationContext(), Home_agent.class);
                            i1.putExtra("username", loginResponse.getUsername());
                            startActivity(i1);
                            finish();
                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid username& password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<agent_Login_Response> call, Throwable t) {
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
    }



    private void sourceSpinner() {

        Log.d("TAG", "sourceSpinner: ");

        try {

            placesResponse=new PlacesResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<PlacesResponse> call = api.source();
            call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(Call<PlacesResponse> call,
                                       Response<PlacesResponse> response) {

                    placesResponse = response.body();
                    places=new ArrayList<String>();
                    Log.d("TAG", "onResponse: 1");
                    if (placesResponse.getStatus().equalsIgnoreCase("sucess")) {
                        Log.d("TAG", "onResponse: 2");
                        for(int i=0;i<placesResponse.getData().length;i++) {
                            places.add(placesResponse.getData()[i].getPlaces().toString());
                        }
                        setPlacesAdapter(places);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"sorry",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call <PlacesResponse> call,Throwable t) {
                    Log.d("TAG", "onFailure: "+t);
                }

            });
        }
        catch (Exception e){
            System.out.print( "Exception e" + e );

        }

    }





    public void setPlacesAdapter(ArrayList<String> place) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Please Select Your source");

        fun.addAll(place);



        agent_from.setSelection(0);
        agent_from.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }
    /*public void onBackPressed() {

        Intent intent=new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );

    }*/

}







