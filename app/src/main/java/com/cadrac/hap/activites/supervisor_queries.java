package com.cadrac.hap.activites;

import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.net.Uri;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.cadrac.hap.Adapters.supervisor_queries_adapter;
        import com.cadrac.hap.R;
        import com.cadrac.hap.responses.Agent_Status_For_RideOut_Response;
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

public class supervisor_queries extends AppCompatActivity {

    Agent_Status_For_RideOut_Response getResponse;

    Connection_Detector connection_detector;


    RecyclerView listview;

    ArrayList<Agent_Status_For_RideOut_Response.data> data = new ArrayList<>();
    supervisor_queries_adapter message_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_queries);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Queries");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        listview = (RecyclerView)findViewById( R.id.listview );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        connection_detector = new Connection_Detector(this);
        data = new ArrayList<Agent_Status_For_RideOut_Response.data>();

if (connection_detector.isConnectingToInternet()) {
    getMessage();
}else{ Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();}
        //  getMobile();
    }




    public void getMessage(){

        String s_id= Config.getLoginusername(getApplicationContext());

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<Agent_Status_For_RideOut_Response> call = api.hap_msg(s_id);
            call.enqueue(new Callback<Agent_Status_For_RideOut_Response>() {
                @Override
                public void onResponse(Call<Agent_Status_For_RideOut_Response> call,
                                       Response<Agent_Status_For_RideOut_Response> response) {

                    getResponse = new Agent_Status_For_RideOut_Response();
                    getResponse = response.body();


                    try
                    {

                    if (getResponse.getStatus().equalsIgnoreCase("True")){

                        for (int i = 0; i < getResponse.getData().length; i++) {
                            //data = new ArrayList<GetoutResponse.data>();
                            data.add( getResponse.getData()[i] );
//                            Log.d("TAG", "onResponse:111true");
//                            Log.d("TAG", "onResponse:22true"+getoutResponse.getData()[i].getVechile_no());
                        }
//                       try {
//                           msg.setText(getResponse.getData()[0].getDescription());
//                       }catch (Exception e){
//
//                           Toast.makeText(getApplicationContext(),"no data", Toast.LENGTH_LONG).show();
//                       }



                    }

                    else {
                        Toast.makeText(getApplicationContext(),"nodata", Toast.LENGTH_LONG).show();
                    }
                    setListView();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<Agent_Status_For_RideOut_Response> call, Throwable t) {


                }

            });

        }catch (Exception e) {
            // System.out.print("Exception e" + e);

            Toast.makeText(getApplicationContext(),"nodata", Toast.LENGTH_LONG).show();

        }
    }





    public void setListView() {

        Log.d("TAG", "setListView: "+data);
        message_adapter = new supervisor_queries_adapter(data, supervisor_queries.this, getApplicationContext() );
        listview.setAdapter( message_adapter );

    }
}

