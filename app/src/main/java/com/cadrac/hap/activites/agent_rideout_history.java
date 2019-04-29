package com.cadrac.hap.activites;

import android.graphics.Color;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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
        import com.cadrac.hap.Adapters.Rideout_historyAdapter;
        import com.cadrac.hap.R;
        import com.cadrac.hap.responses.Rideout_HistoryResponse;
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

public class agent_rideout_history extends AppCompatActivity {

    RecyclerView listview;
    Rideout_HistoryResponse getoutHistoryResponse;
    ArrayList<Rideout_HistoryResponse.data> data = new ArrayList<>();
    Rideout_historyAdapter getout_historyAdapter;
    EditText search;
    Connection_Detector connection_detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_agent_rideout_history );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ride Out History");
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
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        connection_detector = new Connection_Detector(this);
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
                    getout_historyAdapter.getFilter().filter(s.toString());

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
        data = new ArrayList<Rideout_HistoryResponse.data>();
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
            Call<Rideout_HistoryResponse> call = api.hap_rideout_history(Config.getLoginusername(getApplicationContext()));

            call.enqueue(new Callback<Rideout_HistoryResponse>() {
                @Override
                public void onResponse(Call<Rideout_HistoryResponse> call, Response<Rideout_HistoryResponse> response) {

                    getoutHistoryResponse = response.body();
                    Log.d("TAG", "resbody" + getoutHistoryResponse.getStatus());
                    if (getoutHistoryResponse.getStatus().equalsIgnoreCase("true")) {
                        try {


                            for (int i = 0; i < getoutHistoryResponse.getData().length; i++) {
                                data.add(getoutHistoryResponse.getData()[i]);
                                Log.d("TAG", "onResponse:22true" + getoutHistoryResponse.getData()[i].getAgent_id());
                                Log.d("TAG", "onResponse:pass count" + getoutHistoryResponse.getData()[i].getPassengerCount());
                                Log.d("TAG", "onResponse:name" + getoutHistoryResponse.getData()[i].getDriver_name());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    setListView();
                }

                @Override
                public void onFailure(Call<Rideout_HistoryResponse> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setListView() {

        Log.d("TAG", "setListView: "+data);
        getout_historyAdapter=new Rideout_historyAdapter( data,agent_rideout_history.this,getApplicationContext());
        listview.setAdapter(getout_historyAdapter);

    }
}
