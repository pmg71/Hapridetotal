package com.cadrac.hap.dialogs;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap.R;
import com.cadrac.hap.activites.Home_agent;
import com.cadrac.hap.responses.Ridein_Response;
import com.cadrac.hap.responses.Rideout_Response;
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


public class agent_assign_dialog extends DialogFragment implements View.OnClickListener {
    View view;
    TextView yes, no,call;
    Rideout_Response getoutResponse;
    Ridein_Response getInResponse;
    ArrayList<Rideout_Response.data> data;






    public static String status = "";
    Spinner agents_nearyou;

    String from="",agents="",ride_id="",other_agent_ids="",mobile="";

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public static agent_assign_dialog newInstance() {
        return new agent_assign_dialog();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Add SubTaks");

        view = inflater.inflate(R.layout.activity_agent_assign_dialog, container, false);
        yes = (TextView)view.findViewById(R.id.yes);
        no = (TextView)view.findViewById(R.id.no);
        call = (TextView)view.findViewById(R.id.call);



        agents_nearyou = (Spinner)view.findViewById(R.id.resources);



        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        call.setOnClickListener(this);

        from = getArguments().getString("from");
        ride_id= getArguments().getString("ride_id");


        agents_nearyou.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //agents= data.get(position).getId();

                if(position==0) {

                    call.setVisibility(View.GONE);

                }else {
                    String other_agent_names=agents_nearyou.getSelectedItem().toString();
                    other_agent_ids= getoutResponse.getData()[position-1].getA_id();


                    mobile = getoutResponse.getData()[position-1].getMob_num();
                    call.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        available_agents();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.no)
        {
            getDialog().cancel();
        }
        if(v.getId() ==R.id.call)
        {
            agent_call();
        }
        if(v.getId() ==R.id.yes)
        {
            if (other_agent_ids.isEmpty())
            {
                Toast.makeText(getActivity(),"Please Select Exact AgentID",Toast.LENGTH_LONG).show();
            }
            else assignAgent();

        }

    }
    public void agent_call()
    {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        startActivity(callIntent);


    }
    public void assignAgent()
    {
        try{


            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<Ridein_Response> call = api.hap_assign1(ride_id,other_agent_ids,Config.getLoginusername(getActivity()));
            call.enqueue(new Callback<Ridein_Response>() {
                @Override
                public void onResponse(Call<Ridein_Response> call,
                                       Response<Ridein_Response> response) {
                    getInResponse=response.body();
                    try
                    {
                        if(getInResponse.getStatus().equalsIgnoreCase("True"))
                        {
                            Intent intent=new Intent(getContext(), Home_agent.class);
                            startActivity(intent);




                        }}catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<Ridein_Response> call, Throwable t) {
                    t.getMessage();
                    Toast.makeText(getContext(),
                            "Try Again",
                            Toast.LENGTH_LONG).show();
                    Log.d("TAG", "onFailure:t"+t);
                }

            });
           /* Intent i=new Intent(getContext(),GenIn_Activity.class);
            startActivity(i);*/
        }
        catch (Exception e){
            System.out.println("msg:"+e);
        }

    }


    public  void available_agents()
    {
        try
        {

            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<Rideout_Response> call = api.hap_assign(Config.getLoginusername(getActivity()),Config.getLoginfrom(getActivity()));

            call.enqueue(new Callback<Rideout_Response>()
            {
                @Override
                public void onResponse(Call<Rideout_Response> call,
                                       Response<Rideout_Response> response)
                {

                    getoutResponse = response.body();

                    try {

                        data = new ArrayList<Rideout_Response.data>();


                        if (getoutResponse.getStatus().equalsIgnoreCase("true")) {
                            System.out.println("User Data---" + getoutResponse.getData().length);
                            if (getoutResponse.getData() != null) {
                                for (int i = 0; i < getoutResponse.getData().length; i++) {

//                                System.out.println("User Details--"+userResponse.getData()[i].getFull_name());
                                    data.add(getoutResponse.getData()[i]);


                                }

                            }


                        }


                        setResourcesAdapter();


                        ArrayList<String> fun =new ArrayList<String>();

                        fun.add(0,"select");

                        for(int i=0; i<getoutResponse.getData().length; i++ )
                        {
                            System.out.println("User Details--"+getoutResponse.getData()[i].getA_id());
                            //fun.add(userResponse.getData()[i]);
                            data.get(i);

                            fun.add(getoutResponse.getData()[i].getFirst_name()+" "+getoutResponse.getData()[i].getLast_name());
                            // getoutResponse.getData()[i].getA_id();







                        }

                        agents_nearyou.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,fun));



                    }catch (Exception e){
                        e.printStackTrace();

                        Toast.makeText(getActivity(), "No Agents are Available For Reassigning", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<Rideout_Response> call, Throwable t)
                {
                    // Config.closeLoader();
                    t.getMessage();
                    Toast.makeText(getActivity(),
                            "Try Again!",
                            Toast.LENGTH_LONG).show();
                }
            });

        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }



    }
    public void setResourcesAdapter()
    {
        // Creating adapter for spinner
            /*userSpinnerAdapter = new UserSpinnerAdapter(getActivity(),data);
            resources.setAdapter(userSpinnerAdapter);
            resources.setSelection(0);*/



    }
}