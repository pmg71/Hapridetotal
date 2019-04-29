package com.cadrac.hap.activites;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap.Adapters.SettlementAdapter;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.DigCashResponse;
import com.cadrac.hap.responses.SettlementResponse;
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

public class agent_to_supervisor_settlement extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    TextView textview;
    Button enter;
    // EditText search;
    TextView text;
    RecyclerView recyclerView;
    DigCashResponse digCashResponse;
    android.support.v7.widget.SearchView search;
  /*  String s_id="124";
    String gi_id="7";*/

    SwipeRefreshLayout mSwipeRefreshLayout;
    Connection_Detector connection_detector;


    String s_otp;


    SettlementResponse settlementResponse;
    ArrayList<SettlementResponse.data> data = new ArrayList<>();
    SettlementAdapter settlementAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_to_supervisor_settlement);

        //  search=(EditText)findViewById(R.id.search);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settlement");
        toolbar.setTitleTextColor(Color.WHITE);
        connection_detector = new Connection_Detector(this);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        search=(android.support.v7.widget.SearchView) findViewById(R.id.search);
        text=(TextView) findViewById(R.id.text);
        textview=(TextView) findViewById(R.id.textview);
        textview.setVisibility(View.GONE);


        recyclerView = (RecyclerView)findViewById( R.id.recyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        settlement();

        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                settlementAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                try {
                    settlementAdapter.getFilter().filter(query);
                }catch (Exception e) {
                    return false;
                }
                return false;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                settlement();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void settlement(){
        if (connection_detector.isConnectingToInternet()) {
            Config.showLoader(this);
            data = new ArrayList<SettlementResponse.data>();

            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            String s_id = Config.getLoginsupervisorId(getApplicationContext());
            String gi_id = Config.getLoginusername(getApplicationContext());
            Log.d("TAG", "settlement:sid" + Config.getLoginsupervisorId(getApplicationContext()));
            Log.d("TAG", "settlement:gid" + Config.getLoginusername(getApplicationContext()));


            Call<SettlementResponse> call = api.settlement(gi_id, s_id);
            Log.d("TAG", "settlement: mahesh" + gi_id);
            Log.d("TAG", "settlement: mahesh sid" + s_id);

            call.enqueue(new Callback<SettlementResponse>() {
                @Override
                public void onResponse(Call<SettlementResponse> call, Response<SettlementResponse> response) {

                    settlementResponse = response.body();
                    Log.d("TAG", "resbodybg" + settlementResponse);
                    Config.closeLoader();
                    try {
                        Log.d("TAG", "resbody" + settlementResponse.getStatus());
                        if (settlementResponse.getStatus().equalsIgnoreCase("true")) {

                            for (int i = 0; i < settlementResponse.getData().length; i++) {
                                data.add(settlementResponse.getData()[i]);
                            }
                        }
                        Log.d("TAG", "onResponse:true" + data);
                        setListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                        textview.setVisibility(View.VISIBLE);
                        search.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<SettlementResponse> call, Throwable t) {
                    Log.d("TAG", "onResponse:nottrue");
                    Log.d("TAG", "onFailure:t" + t);
                }
            });
        }else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
    public void setListView() {

        Log.d("TAG", "setListView: "+data);
        // getoutAdapter = new GetOutAdapter(data,GetOutActivity2.this,getApplicationContext() );
        settlementAdapter=new SettlementAdapter( data,agent_to_supervisor_settlement.this,getApplicationContext() );
        recyclerView.setAdapter(settlementAdapter);
        settlementAdapter.notifyDataSetChanged();

    }
    //otp validating method
    /*private void sourceSpinner() {

        Log.d("TAG", "sourceSpinner: ");

        try {

            Config.showLoader(this);
            // placesResponse = new PlacesResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<AgentPinResponse> call = api.source1(gi_id,s_id);
            call.enqueue(new Callback<AgentPinResponse>() {
                @Override
                public void onResponse(Call<AgentPinResponse> call,
                                       Response<AgentPinResponse> response) {
                    Utils.closeLoader();
                    agentPinResponse = response.body();
                    // places=new ArrayList<String>();
                    try {
                        Log.d("TAG", "onResponse: 1");
                        f.setText(agentPinResponse.getStatus());
                        if (agentPinResponse.getStatus().equalsIgnoreCase("true")) {
                            Log.d("TAG", "onResponse: 2");

                            s_otp= agentPinResponse.getS_otp();
                            if(s_otp.equals("0")){

                            }else{
                                f.setText(s_otp);
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"sorry",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call <AgentPinResponse> call,Throwable t) {
                    Utils.closeLoader();
                    Log.d("TAG", "onFailure: "+t);
                }

            });
        }
        catch (Exception e){
            System.out.print( "Exception e" + e );

        }

    }


    //otp generation dialog
    public void pindialog(int id) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View v2=getLayoutInflater().inflate(R.layout.pingenerate,null);
        builder.setView(v2);
        final AlertDialog dialog=builder.create();
        dialog.show();




        f =  v2.findViewById(R.id.text1);
        ed1 =  v2.findViewById(R.id.ed1);
        ed2 =  v2.findViewById(R.id.ed2);
        ed3 =  v2.findViewById(R.id.ed3);
        ed4 =  v2.findViewById(R.id.ed4);
        ed5 =  v2.findViewById(R.id.ed5);
        enter =  (Button) v2.findViewById(R.id.enter);

        ed5.setVisibility(View.GONE);
        sourceSpinner();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_LONG).show();

            }
        });

        ed1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed1.getText().toString().length()==1)     //size as per your requirement
                {
                    ed2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed2.getText().toString().length()==1)     //size as per your requirement
                {
                    ed3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed3.getText().toString().length()==1)     //size as per your requirement
                {
                    ed4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed4.getText().toString().length()==1)     //size as per your requirement
                {
                    ed5.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }*/


    //payment dialog box
    public void payment(int id,final String s_id,final String gi_id, final String set_id, final String totalUnpaid,final  String totalRides, final String paidDate) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View v1=getLayoutInflater().inflate(R.layout.payment_dialog,null);



        final EditText cash1=(EditText)v1.findViewById(R.id.cash);
        final EditText digital1=(EditText)v1.findViewById(R.id.digital);
        TextView yes=(TextView)v1.findViewById(R.id.yes);
        TextView no=(TextView)v1.findViewById(R.id.no);
        builder.setView(v1);
        final AlertDialog dialog=builder.create();
        dialog.show();



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connection_detector.isConnectingToInternet()) {

                    try {

                        String cash = cash1.getText().toString();
                        String digital = digital1.getText().toString();

                        int a = Integer.parseInt(cash);
                        int b = Integer.parseInt(digital);

                        int tUnpaid = Integer.parseInt(totalUnpaid);


                        if ((a + b) > tUnpaid || (a + b) == 0) {
                            Toast.makeText(getApplicationContext(), "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        } else {

                            OkHttpClient okHttpClient = new OkHttpClient();
                            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                                    client(okHttpClient).
                                    addConverterFactory(GsonConverterFactory.create()).
                                    build();

                            API api = RestClient.client.create(API.class);
                            Call<DigCashResponse> call = api.digcash(cash, digital, s_id, gi_id, set_id, totalUnpaid, totalRides, paidDate);


                            call.enqueue(new Callback<DigCashResponse>() {
                                @Override
                                public void onResponse(Call<DigCashResponse> call, Response<DigCashResponse> response) {

                                    digCashResponse = response.body();


                                    try {
                                        if (digCashResponse.getStatus().equalsIgnoreCase("true")) {


                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();

                                        } else {

                                            Toast.makeText(getApplicationContext(), "please enter valid details", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }


                                @Override
                                public void onFailure(Call<DigCashResponse> call, Throwable t) {
                                    //dialog.cancel();

                                    Toast.makeText(getApplicationContext(), "Please Enter Correct Amount", Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(agent_to_supervisor_settlement.this, "Please Enter Amount Correctly", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(agent_to_supervisor_settlement.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }

        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}