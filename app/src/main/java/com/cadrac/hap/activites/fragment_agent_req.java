package com.cadrac.hap.activites;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cadrac.hap.Adapters.request_adapter;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.request_response;
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

import static android.support.constraint.Constraints.TAG;


public class fragment_agent_req extends Fragment {

    View view;
    String username,from;
    RecyclerView listview;
    request_response getResponse;
    ArrayList data = new ArrayList<>();
    request_adapter message_adapter;


    public static Context context;
String c="a";

    public fragment_agent_req() {

    }


    public static fragment_agent_req newInstance(String param1, String param2) {
        fragment_agent_req fragment = new fragment_agent_req();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_agent_req, container, false );
        listview = (RecyclerView)view.findViewById( R.id.listview );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext());

        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        getMessage();


        return view;
    }



    public void getMessage(){

        //    String s_id= Config.getLoginusername(getApplicationContext());
        // Log.d("TAG", "onResponse:22");
        try {
            Log.d("TAG", "onResponse:221");
            OkHttpClient okHttpClient = new OkHttpClient();
            Log.d("TAG", "onResponse:223");
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            Log.d("TAG", "onResponse:224");
            API api = RestClient.client.create(API.class);
            String un= Config.getLoginusername(getActivity());
            Call<request_response> call = api.request(un);
            call.enqueue(new Callback<request_response>() {
                @Override
                public void onResponse(Call<request_response> call,
                                       Response<request_response> response) {
                    Log.d("TAG", "onResponse:22");
                    getResponse = new request_response();
                    getResponse = response.body();
                  //  Log.d("TAG", "onResponse:22true"+getResponse.getStatus());

                    try
                    {
                        data = new ArrayList<>();
                        if (getResponse.getStatus().equalsIgnoreCase("True")){
                            Log.d(TAG, "onResponse: status"+getResponse.getStatus());
                           c = getResponse.getData1()[0].getCount();
                            Log.d(TAG, "onResponse:kkk"+c);
                         // Config.saveCountValue(context, getResponse.getData1()[0].getCount());
//                          final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putString("count",c);
//                            editor.apply();
//
//                            Log.d(TAG, "onResponse:kkkcount"+c);


                            Log.d(TAG, "onResponse: count"+getResponse.getData1()[0].getCount());
                           // count = Integer.parseInt(c);
                            for (int i = 0; i < getResponse.getData().length; i++) {

                                data.add( getResponse.getData()[i]);
                                Log.d("TAG", "onResponse:22true1289"+getResponse.getData()[i].getPassengerName());
                                Log.d("TAG", "onResponse:22true1289"+i);
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
                            Log.d("TAG", "onResponse:22true"+getResponse.getStatus());
                            Toast.makeText(context,"nodata", Toast.LENGTH_LONG).show();

                        }
                        setListView();

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<request_response> call, Throwable t) {


                }

            });

        }catch (Exception e) {
            // System.out.print("Exception e" + e);

            Toast.makeText(context,"nodata", Toast.LENGTH_LONG).show();

        }

    }





    public void setListView() {

        Log.d("TAG", "setListView: "+data.size());
        message_adapter = new request_adapter(data, fragment_agent_req.this, getContext() );
        listview.setAdapter( message_adapter );

    }






}
