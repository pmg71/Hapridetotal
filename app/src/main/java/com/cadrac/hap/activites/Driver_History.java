package com.cadrac.hap.activites;

import android.graphics.Color;
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

import com.cadrac.hap.Adapters.DriverHistoryAdapter;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.DriverHistoryResponse;
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


public class Driver_History extends AppCompatActivity {
    RecyclerView listview;
    DriverHistoryResponse getInHistoryResponse;
    ArrayList<DriverHistoryResponse.data> data = new ArrayList<>();
    DriverHistoryAdapter driverHistoryAdapter;
    String gid;
    EditText search;
    Connection_Detector connection_detector;
    String driverid;
    TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        listview = (RecyclerView)findViewById( R.id.listview );
        search = (EditText)findViewById(R.id.search);
        nodata=findViewById( R.id.nodata);
        nodata.setVisibility( View.GONE );
        connection_detector = new Connection_Detector(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        data = new ArrayList<DriverHistoryResponse.data>();
        getout();

    }

    public void getout(){

        if (connection_detector.isConnectingToInternet()) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                driverid = Config.getLoginusername(getApplicationContext());

                Log.d("TAG", "number: "+driverid);

                Call<DriverHistoryResponse> call = api.driver_history(driverid);

                call.enqueue(new Callback<DriverHistoryResponse>() {
                    @Override
                    public void onResponse(Call<DriverHistoryResponse> call, Response<DriverHistoryResponse> response) {
                        getInHistoryResponse = response.body();
                        Log.d("TAG", "onResponse: status"+getInHistoryResponse.getStatus());
                        try {
                            if (getInHistoryResponse.getStatus().equalsIgnoreCase("true")) {
                                Log.d("TAG", "onResponse: status1"+getInHistoryResponse.getData().length);
                                for (int i = 0; i < getInHistoryResponse.getData().length; i++) {
                                    data.add(getInHistoryResponse.getData()[i]);
                                    Log.d("TAG", "onResponse: source"+getInHistoryResponse.getData()[i].getSource());
                                    Log.d("TAG", "onResponse: dest"+getInHistoryResponse.getData()[i].getDestination());
                                }
                            }
                            setListView();
                        } catch (Exception e) {
                            Log.d("TAG", "onResponse: aaaa");
                            nodata.setVisibility( View.VISIBLE );
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(Call<DriverHistoryResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure:t" + t);
                    }
                });
            }catch (Exception e){
                Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show();
            }
        }else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void setListView() {
        driverHistoryAdapter=new DriverHistoryAdapter( data,Driver_History.this,getApplicationContext() );
        listview.setAdapter(driverHistoryAdapter);

    }

}

