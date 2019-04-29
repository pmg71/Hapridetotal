package com.cadrac.hap.activites;

import android.content.Context;
        

        import android.content.Intent;
        import android.content.SharedPreferences;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.Toast;


import com.cadrac.hap.Adapters.Agent_Rideout_Adapter;
import com.cadrac.hap.R;
import com.cadrac.hap.dialogs.agent_assign_dialog;
import com.cadrac.hap.dialogs.agent_collect_dialog;
import com.cadrac.hap.responses.Rideout_Response;
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


public class fragment_agent_rideout extends Fragment {

    View view;

    String username,from;
    RecyclerView listview;
    Rideout_Response getoutResponse;
    ArrayList data = new ArrayList<>();
    Agent_Rideout_Adapter getoutAdapter;
    public static final int DIALOG_FRAGMENT_TARGET = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    Connection_Detector connection_detector;
    Context context;

    public fragment_agent_rideout() {

    }


    public static fragment_agent_rideout newInstance(String param1, String param2) {
        fragment_agent_rideout fragment = new fragment_agent_rideout();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.activity_fragment_agent_rideout, container, false );
        listview = (RecyclerView)view.findViewById( R.id.listview );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);

        connection_detector = new Connection_Detector(getContext());
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

        setHasOptionsMenu(true);
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
            Call<Rideout_Response> call = api.hap_rideout(Config.getLoginusername(getActivity()));
            call.enqueue(new Callback<Rideout_Response>() {
                @Override
                public void onResponse(Call<Rideout_Response> call, Response<Rideout_Response> response) {
                    getoutResponse = response.body();
                    Log.d("TAG", "onResponse:resp" + getoutResponse);
                    try {

                        data = new ArrayList<>();
                        if (getoutResponse.getStatus().equalsIgnoreCase("true")) {

                            Log.d("TAG", "onResponse:length" + getoutResponse.getData().length);
                            for (int i = 0; i < getoutResponse.getData().length; i++) {

                                data.add(getoutResponse.getData()[i]);
                                Log.d("TAG", "onResponse:data" + getoutResponse.getData()[i].getGi_id());

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", getoutResponse.getData()[i].getId());
                                editor.commit();
                            }
                        }
                        Log.d("TAG", "onResponse:true");
                        setListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Rideout_Response> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void setListView() {
        getoutAdapter = new Agent_Rideout_Adapter(data, fragment_agent_rideout.this, getContext() );
        listview.setAdapter( getoutAdapter );

    }



    public  void assignGetout(int position, String ride_id) {
        Bundle b = new Bundle();
        b.putString("from",from);
        b.putString("ride_id",ride_id);

        DialogFragment newFragment = new agent_assign_dialog();
        newFragment.setTargetFragment(this, DIALOG_FRAGMENT_TARGET);
        newFragment.setArguments(b);

        FragmentManager fm = getFragmentManager();
        newFragment.show(fm, "Add Subtask Fragment");






    }
    public  void collectGetout(int position,String ride_id, String amount, String driver_id, String passenger_count,String agent_id,String s_id) {
        Bundle b = new Bundle();
       // b.putString("from",from);
        b.putString("ride_id",ride_id);
        b.putString("amount",amount);
        b.putString("driver_id",driver_id);
        b.putString("passenger_count",passenger_count);
        b.putString("agent_id",agent_id);
        b.putString("s_id",s_id);







        DialogFragment newFragment = new agent_collect_dialog();
        newFragment.setTargetFragment(this, DIALOG_FRAGMENT_TARGET);
        newFragment.setArguments(b);

        FragmentManager fm = getFragmentManager();
        newFragment.show(fm, "Add Subtask Fragment");


    }



}
