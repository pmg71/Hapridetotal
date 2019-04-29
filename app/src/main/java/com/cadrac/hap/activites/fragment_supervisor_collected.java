package com.cadrac.hap.activites;
import android.content.Context;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.cadrac.hap.Adapters.Supervisor_Collected_Adapter;
import com.cadrac.hap.R;
import com.cadrac.hap.dialogs.agent_assign_dialog;
import com.cadrac.hap.dialogs.agent_collect_dialog;
import com.cadrac.hap.responses.Supervisor_Collected_Response;
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


public class fragment_supervisor_collected extends Fragment {

    View view;
    String username,from;
    RecyclerView listview;
    Supervisor_Collected_Response getoutResponse;
    ArrayList data = new ArrayList<>();
    Supervisor_Collected_Adapter getoutAdapter;
    Connection_Detector connection_detector;
    public static final int DIALOG_FRAGMENT_TARGET = 1;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;

    public fragment_supervisor_collected() {

    }


    public static fragment_supervisor_collected newInstance(String param1, String param2) {
        fragment_supervisor_collected fragment = new fragment_supervisor_collected();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.activity_fragment_supervisor_collected, container, false );
        listview = (RecyclerView)view.findViewById( R.id.listview );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext());
        connection_detector = new Connection_Detector(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        getout();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh Data");
                swipeRefreshLayout.setRefreshing(false);

                getout();

            }
        });

        return view;
    }



    public void getout(){
        if (connection_detector.isConnectingToInternet()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<Supervisor_Collected_Response> call = api.hap_rideout1(Config.getLoginusername(getActivity()));
            call.enqueue(new Callback<Supervisor_Collected_Response>() {
                @Override
                public void onResponse(Call<Supervisor_Collected_Response> call, Response<Supervisor_Collected_Response> response) {
                    getoutResponse = response.body();
                    Log.d("TAG", "onResponse:resp" + getoutResponse);
                    try {
                        data = new ArrayList<>();
                        if (getoutResponse.getStatus().equalsIgnoreCase("true")) {

                            Log.d("TAG", "onResponse:length" + getoutResponse.getData().length);
                            for (int i = 0; i < getoutResponse.getData().length; i++) {

                                data.add(getoutResponse.getData()[i]);


                            }
                        }
                        Log.d("TAG", "onResponse:true");
                        setListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Supervisor_Collected_Response> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setListView() {
        getoutAdapter = new Supervisor_Collected_Adapter(data, fragment_supervisor_collected.this, getContext() );
        listview.setAdapter( getoutAdapter );

    }






}
