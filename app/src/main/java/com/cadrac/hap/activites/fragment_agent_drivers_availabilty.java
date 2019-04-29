package com.cadrac.hap.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.Adapters.Driver_avaliabilty_adapter;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.Drivers_Availabilty_Response;
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

public class fragment_agent_drivers_availabilty extends Fragment {

    View view;
    String username,from;
    RecyclerView listview;

    TextView nodrivers,name,vehicle,vehicleType;
    Drivers_Availabilty_Response driverResponse;
    ArrayList<Drivers_Availabilty_Response.data> data = new ArrayList<>();
    Driver_avaliabilty_adapter driver_avaliabilty_adapter;
    Context context;
    Connection_Detector connection_detector;
    SwipeRefreshLayout swipeRefreshLayout;


    public fragment_agent_drivers_availabilty() {

    }

    public static fragment_agent_drivers_availabilty newInstance(String param1, String param2) {
        fragment_agent_drivers_availabilty fragment = new fragment_agent_drivers_availabilty();

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.activity_fragment_agent_drivers_availabilty, container, false );
        listview = (RecyclerView)view.findViewById( R.id.listview );
        nodrivers=(TextView)view.findViewById(R.id.nodrivers);
        name=(TextView)view.findViewById(R.id.name);
        vehicle=(TextView)view.findViewById(R.id.vehicle);
        vehicleType=(TextView)view.findViewById(R.id.vehicleType);

        connection_detector = new Connection_Detector(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        listview.setHasFixedSize( true );
        driverAvailable();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh Data");
                swipeRefreshLayout.setRefreshing(false);

                driverAvailable();

            }
        });
        setHasOptionsMenu(true);
        return view;


    }

    public void driverAvailable(){
        if (connection_detector.isConnectingToInternet()) {

            nodrivers.setVisibility(View.GONE);

            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
      /*  SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        // username= sharedPreferences.getString("username","");
        from=sharedPreferences.getString("from", "");
        Log.d("TAG", "onCreateView:from"+from);
        Log.d("TAG", "getout:user"+username);*/
            username = Config.getLoginusername(getActivity());
            Log.d("TAG", "onCreateView:from" + username);
            from = Config.getLoginfrom(getActivity());
            Log.d("TAG", "onCreateView:from" + from);
            Call<Drivers_Availabilty_Response> call = api.source_driver_availabilty(from);

            call.enqueue(new Callback<Drivers_Availabilty_Response>() {
                @Override
                public void onResponse(Call<Drivers_Availabilty_Response> call, Response<Drivers_Availabilty_Response> response) {

                    driverResponse = response.body();
                    Log.d("TAG", "resbody" + response.body());
                    try {
//                    if (driverResponse.getStatus().equalsIgnoreCase( "true" )){

                        data = new ArrayList<Drivers_Availabilty_Response.data>();

                        Log.d("TAG", "onResponse:length" + driverResponse.getData().length);
                        for (int i = 0; i < driverResponse.getData().length; i++) {

                            data.add(driverResponse.getData()[i]);
                            Log.d("TAG", "onResponse:22true" + driverResponse.getData()[i].getVechile_no());
                            Log.d("TAG", "onResponse:22trueid" + driverResponse.getData()[i].getDriver_name());
                            Log.d("TAG", "onResponse:111true" + driverResponse.getData()[i].getDriver_no());

                        }
                        Log.d("TAG", "onResponse:true");
                        setListView();
                    } catch (Exception e) {

                        nodrivers.setVisibility(View.VISIBLE);
                        name.setVisibility(View.GONE);
                        vehicle.setVisibility(View.GONE);
                        vehicleType.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Drivers_Availabilty_Response> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setListView() {
        driver_avaliabilty_adapter = new Driver_avaliabilty_adapter(data, fragment_agent_drivers_availabilty.this, getContext() );
        listview.setAdapter( driver_avaliabilty_adapter );

    }
}