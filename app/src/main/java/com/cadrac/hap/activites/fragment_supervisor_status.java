package com.cadrac.hap.activites;





import android.content.Context;

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


import com.cadrac.hap.R;
import com.cadrac.hap.dialogs.DialogSupervisor_Collect;
import com.cadrac.hap.responses.Supervisor_Status_Response;
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


public class fragment_supervisor_status extends Fragment {

    View view;
    String username,from,s_id;
    RecyclerView listview;
    Supervisor_Status_Response getoutResponse;
    ArrayList data = new ArrayList<>();
    Supervisor_Status_Adapter getoutAdapter;
    public static final int DIALOG_FRAGMENT_TARGET = 1;
    Connection_Detector connection_detector;
    SwipeRefreshLayout swipeRefreshLayout;



    Context context;

    public fragment_supervisor_status() {

    }


    public static fragment_supervisor_status newInstance(String param1, String param2) {
        fragment_supervisor_status fragment = new fragment_supervisor_status();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.activity_fragment_supervisor_status, container, false );
        listview = (RecyclerView)view.findViewById( R.id.listview );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext());
        connection_detector = new Connection_Detector( getActivity());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
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
            //getting supervisor_id
            s_id = Config.getLoginusername(getActivity());
            Call<Supervisor_Status_Response> call = api.hap_getout(s_id);
            call.enqueue(new Callback<Supervisor_Status_Response>() {
                @Override
                public void onResponse(Call<Supervisor_Status_Response> call, Response<Supervisor_Status_Response> response) {
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
                public void onFailure(Call<Supervisor_Status_Response> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setListView() {
        getoutAdapter = new Supervisor_Status_Adapter(data, fragment_supervisor_status.this, getContext() );
        listview.setAdapter( getoutAdapter );

    }



    /* public  void assignGetout(int position, String ride_id) {
         Bundle b = new Bundle();
         b.putString("from",from);
         b.putString("ride_id",ride_id);
 
         DialogFragment newFragment = new DialoaGetoutAssign();
         newFragment.setTargetFragment(this, DIALOG_FRAGMENT_TARGET);
         newFragment.setArguments(b);
 
         FragmentManager fm = getFragmentManager();
         newFragment.show(fm, "Add Subtask Fragment");
 
 
 
 
 
 
     }*/
    public  void otpGenerate(int position,String agent_id, String amount, String total_rides, String cash,String digital,String paidDate) {
     /*   Bundle b = new Bundle();
        b.putString("from",from);
        b.putString("s_id",s_id);
        b.putString("agent_id",agent_id);
        b.putString("amount",amount);
        b.putString("total_rides",total_rides);
        b.putString("cash",cash);
        b.putString("digital",digital);
        b.putString("paidDate",paidDate);



        DialogFragment newFragment = new DialogGenerateOtp();
        newFragment.setTargetFragment(this, DIALOG_FRAGMENT_TARGET);
        newFragment.setArguments(b);

        FragmentManager fm = getFragmentManager();
        newFragment.show(fm, "Add Subtask Fragment");*/


    }
    public void supervisor_collect(int position,String agent_id, String amount, String total_rides, String cash,String digital,String paidDate,String s_id,String set_id,String totalPaid,String totalUnpaid) {
        Bundle b = new Bundle();
        b.putString("from",from);
        b.putString("s_id",s_id);
        b.putString("agent_id",agent_id);
        b.putString("amount",amount);
        b.putString("total_rides",total_rides);
        b.putString("cash",cash);
        b.putString("digital",digital);
        b.putString("paidDate",paidDate);
        b.putString("set_id",set_id);
        b.putString("totalPaid",totalPaid);
        b.putString("totalUnpaid",totalUnpaid);



        DialogFragment newFragment1 = new DialogSupervisor_Collect();
        newFragment1.setTargetFragment(this, DIALOG_FRAGMENT_TARGET);
        newFragment1.setArguments(b);

        FragmentManager fm1 = getFragmentManager();
        newFragment1.show(fm1, "Add Subtask Fragment");


    }


}
