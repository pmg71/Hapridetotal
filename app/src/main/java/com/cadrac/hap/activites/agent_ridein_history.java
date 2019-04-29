package com.cadrac.hap.activites;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.cadrac.hap.Adapters.RideIn_historyAdater;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.RideInHistoryResponse;
import com.cadrac.hap.supporter_classes.Connection_Detector;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class agent_ridein_history extends AppCompatActivity {

    RecyclerView listview;
    RideInHistoryResponse getInHistoryResponse;
    ArrayList<RideInHistoryResponse.data> data = new ArrayList<>();
    RideIn_historyAdater getIn_historyAdapter;
    String gid;
    EditText search;
    Connection_Detector connection_detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_agent_ridein_history );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ride In History");
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
        connection_detector = new Connection_Detector(this);
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try{
                    getIn_historyAdapter.getFilter().filter(s.toString());

                }catch (NullPointerException e){
                    e.printStackTrace();}
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                System.out.println("Search String---"+s.toString());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        data = new ArrayList<RideInHistoryResponse.data>();
        getout();
    }

    public void getout(){
        if (connection_detector.isConnectingToInternet()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<RideInHistoryResponse> call = api.hap_ridein_hist(Config.getLoginusername(getApplicationContext()));

            call.enqueue(new Callback<RideInHistoryResponse>() {
                @Override
                public void onResponse(Call<RideInHistoryResponse> call, Response<RideInHistoryResponse> response) {
                    getInHistoryResponse = response.body();
                    try {
                        if (getInHistoryResponse.getStatus().equalsIgnoreCase("true")) {
                            for (int i = 0; i < getInHistoryResponse.getData().length; i++) {
                                data.add(getInHistoryResponse.getData()[i]);
                            }
                        }
                        setListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<RideInHistoryResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void setListView() {
        getIn_historyAdapter=new RideIn_historyAdater( data,agent_ridein_history.this,getApplicationContext() );
        listview.setAdapter(getIn_historyAdapter);

    }
}