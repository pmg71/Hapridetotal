package com.cadrac.hap.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.cadrac.hap.responses.Agent_Direct_Response;
import com.cadrac.hap.responses.Agent_status_response;
import com.cadrac.hap.responses.Agent_status_values_response;
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

public class agent_rideout_status extends AppCompatActivity {

    Spinner from;
    Agent_Direct_Response agent_response;

    ArrayList<String> places;
    String source;
    Button button;
    Agent_status_response agent_status_response;
    TextView agent_status;
    String s1="";
    Connection_Detector connection_detector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_rideout_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Status");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        from = (Spinner) findViewById(R.id.sourcespinner);
        button=(Button) findViewById(R.id.button);
        agent_status=(TextView)findViewById(R.id.status_value);
        connection_detector = new Connection_Detector(this);




        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                source=from.getSelectedItem().toString();

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d("TAG", "status:ag"+Config.getLoginusername(getApplicationContext()));

                Call<Agent_Direct_Response> call = api.sourcing1(Config.getLoginusername(getApplicationContext()));
                call.enqueue(new Callback<Agent_Direct_Response>() {
                    @Override
                    public void onResponse(Call<Agent_Direct_Response> call, Response<Agent_Direct_Response> response) {

                        agent_response=response.body();
                        if (agent_response.getStatus().equalsIgnoreCase("True")) {
                            try{
                                for (int i = 0; i < agent_response.getData().length; i++) {
                                    Log.d("TAG", "onResponse:get" + agent_response.getData()[i].getAgentAvailability());
                                    s1 = agent_response.getData()[i].getAgentAvailability();
                                    Log.d("TAG", "onResponse:sss" + s1);


                        if(s1.equalsIgnoreCase("1")){
                    agent_status.setText("I am Ready For Rideouts");
                }else {
                    agent_status.setText("I am Not Ready For Rideouts");
                }
                                }
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid username& password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Agent_Direct_Response> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<String> type=new ArrayList<String>();
        type.add("Please Select Your Status");
        type.add("I am Ready For Rideouts");
        type.add("I am Not Ready For Rideouts");


        from.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, type));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (source.equalsIgnoreCase("Please Select Your Status")) {
                    Toast.makeText(agent_rideout_status.this, "Please Select Your Status First ", Toast.LENGTH_SHORT).show();
                } else {

                    if (connection_detector.isConnectingToInternet()) {
                        String status = "";
                        if (source.equalsIgnoreCase("I am Ready For Rideouts")) {
                            status = "1";
                            agent_status.setText("I am Ready For Rideouts");
                   /* status();
                    Log.d("TAG", "onResponse:sss:"+s1);
                    if(s1.equalsIgnoreCase(status)){
                        agent_status.setText("I am Ready For Rideouts");
                    }*/

                        }

                        if (source.equalsIgnoreCase("I am Not Ready For Rideouts")) {
                            status = "0";
                            agent_status.setText("I am Not Ready For Rideouts");
                    /*status();
                    if(s1.equalsIgnoreCase(status)){
                        agent_status.setText("bNot Ready For Rideouts");
                    }*/

                        }

                        OkHttpClient okHttpClient = new OkHttpClient();
                        RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                                client(okHttpClient).
                                addConverterFactory(GsonConverterFactory
                                        .create()).build();
                        API api = RestClient.client.create(API.class);
                        Log.d("TAG", "onClick:status" + status);
                        Call<Agent_status_response> call = api.sourcing(status, Config.getLoginusername(getApplicationContext()), "update");
                        call.enqueue(new Callback<Agent_status_response>() {
                            @Override
                            public void onResponse(Call<Agent_status_response> call,
                                                   Response<Agent_status_response> response) {

                                agent_status_response = response.body();

                                if (agent_status_response.getStatus().equalsIgnoreCase("True")) {
                                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getApplicationContext(),
                                            "Please enter valid username& password",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Agent_status_response> call, Throwable t) {
                                t.getMessage();
                                Log.d(" TAG", "onFailure:fail" + t);
                                Toast.makeText(getApplicationContext(),
                                        "Try Again!hhh",
                                        Toast.LENGTH_LONG).show();
                            }

                        });
                    } else
                        Toast.makeText(agent_rideout_status.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        status();
        Log.d("TAG", "onResponse:s8:"+s1);
    }
    public void status(){

        if (connection_detector.isConnectingToInternet()) {
            System.out.println("statusstatus");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d("TAG", "status:ag" + Config.getLoginusername(getApplicationContext()));

            Call<Agent_Direct_Response> call = api.sourcing1(Config.getLoginusername(getApplicationContext()));
            call.enqueue(new Callback<Agent_Direct_Response>() {
                @Override
                public void onResponse(Call<Agent_Direct_Response> call, Response<Agent_Direct_Response> response) {
                    agent_response = response.body();
                    Log.d("TAG", "onResponse:response" + agent_response);

                    if (agent_response.getStatus().equalsIgnoreCase("True")) {
                        try {
                            for (int i = 0; i < agent_response.getData().length; i++) {
                                Log.d("TAG", "onResponse:get" + agent_response.getData()[i].getAgentAvailability());
                                s1 = agent_response.getData()[i].getAgentAvailability();
                                Log.d("TAG", "onResponse:sss" + s1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Please enter valid username& password",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Agent_Direct_Response> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


}