package com.cadrac.hap.activites;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.Adapters.Agent_AssignedRides_Adapter;
import com.cadrac.hap.Adapters.RideIn_historyAdater;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.Agent_Assigned_Ride_Response;
import com.cadrac.hap.responses.RideInHistoryResponse;
import com.cadrac.hap.responses.statusResponse;
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

public class agent_assignedRides extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView norides;
    Agent_Assigned_Ride_Response agent_assigned_ride_response;
    ArrayList<Agent_Assigned_Ride_Response.data> data = new ArrayList<>();
    Agent_AssignedRides_Adapter agent_assignedRides_adapter;
    Connection_Detector connection_detector;
    String agentid;
    statusResponse status_response;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_rides);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Rides");
        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        norides=findViewById(R.id.norides);

        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );

        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        data = new ArrayList<Agent_Assigned_Ride_Response.data>();
        agentid=Config.getLoginusername(this);



        assignrides();
    }


    public void assignrides(){
      //  if (connection_detector.isConnectingToInternet()) {
        Config.showLoader(this);
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<Agent_Assigned_Ride_Response> call = api.ridesassign(agentid);
            Log.d("TAG", "agentid: "+agentid);

            call.enqueue(new Callback<Agent_Assigned_Ride_Response>() {
                @Override
                public void onResponse(Call<Agent_Assigned_Ride_Response> call, Response<Agent_Assigned_Ride_Response> response) {
                    agent_assigned_ride_response = response.body();
                    Config.closeLoader();
                    try {
                        if (agent_assigned_ride_response.getStatus().equalsIgnoreCase("true")) {
                            for (int i = 0; i < agent_assigned_ride_response.getData().length; i++) {
                                data.add(agent_assigned_ride_response.getData()[i]);

                                Log.d("TAG", "rideid: "+agent_assigned_ride_response.getData()[i].getRide_id());
                                Log.d("TAG", "name: "+agent_assigned_ride_response.getData()[i].getPassenger_Name());
                                Log.d("TAG", "number: "+agent_assigned_ride_response.getData()[i].getPass_Number());
                            }
                        }
                        setListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                        norides.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<Agent_Assigned_Ride_Response> call, Throwable t) {
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
   /*     }else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }
    public void setListView() {
        agent_assignedRides_adapter=new Agent_AssignedRides_Adapter( data,agent_assignedRides.this,getApplicationContext() );
        recyclerView.setAdapter(agent_assignedRides_adapter);

    }

    public void canceldialog(final String id, final String status) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert!....");
        alertDialogBuilder.setMessage("Do You Want Cancel The ride?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Log.d("TAG", "id: "+id);
                        Log.d("TAG", "status: "+status);

                       cancelRequest(id,status);

                    }


                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void cancelRequest(String id, String status) {

      //  if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<statusResponse> call = api.status3(id,status);
                call.enqueue(new Callback<statusResponse>() {
                    @Override
                    public void onResponse(Call<statusResponse> call,
                                           Response<statusResponse> response) {

                        status_response = new statusResponse();
                        status_response = response.body();
                    }

                    @Override
                    public void onFailure(Call<statusResponse> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
     /*   }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }


    }

