package com.cadrac.hap.activites;
        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.net.Uri;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.cadrac.hap.R;
        import com.cadrac.hap.responses.Agent_Status_For_RideOut_Response;
        import com.cadrac.hap.supporter_classes.Connection_Detector;
        import com.cadrac.hap.utils.Config;
        import com.cadrac.hap.webservices.API;
        import com.cadrac.hap.webservices.RestClient;

        import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class agent_contactsupervisor extends AppCompatActivity {


    EditText eMsg;
    TextView sName, s_Id, mobile, tvmsg;
    Button b_msg, b_submit, b_call, b_clean;
    LinearLayout llmsg, llmsg2, llsend, llclean;

    Agent_Status_For_RideOut_Response getResponse;
    String message, mobileNumber;
//    Connection_Detector connection_detector;


   String s_id,a_id;

//String s_id="5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_contactsupervisor);

        a_id = Config.getLoginsupervisorId(getApplicationContext());
        s_id= Config.getLoginusername(getApplicationContext());
        getSupervisordetails();
        getAgentMsg();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contact Supervisor");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        eMsg = (EditText) findViewById(R.id.etmsg);
//        connection_detector = new Connection_Detector(this);
        sName = (TextView) findViewById(R.id.sname);
        s_Id = (TextView) findViewById(R.id.sid);
        mobile = (TextView) findViewById(R.id.smobile);
        b_msg = (Button) findViewById(R.id.bmsg);
        b_call = (Button) findViewById(R.id.bcall);
        b_submit = (Button) findViewById(R.id.bsubmit);
        b_clean=(Button)findViewById(R.id.bclean);
        tvmsg=(TextView)findViewById(R.id.tvMsg);
        llmsg = (LinearLayout) findViewById(R.id.llmsg);
        llmsg2 = (LinearLayout) findViewById(R.id.llmsg2);
        llsend = (LinearLayout) findViewById(R.id.llsend);
        llclean = (LinearLayout) findViewById(R.id.llclean);

        s_Id.setText(Config.getLoginsupervisorId(getApplication()));

        b_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String call = mobileNumber;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                //  callIntent.setData(Uri.parse("tel:"+number));
                callIntent.setData(Uri.parse("tel:" + call));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(callIntent);
            }
        });



        b_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llmsg.setVisibility(View.VISIBLE);
                llsend.setVisibility(View.VISIBLE);
                b_msg.setVisibility(View.GONE);
            }
        });

        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (eMsg.length() == 0){
                    eMsg.setError("message");
                }else {

//                    if(connection_detector.isConnectingToInternet()) {

                        getmsg();
                    /*}
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }*/
                    tvmsg.setText(message);
                    llmsg.setVisibility(View.GONE);
                    llsend.setVisibility(View.GONE);
                    llmsg2.setVisibility(View.VISIBLE);
                    llclean.setVisibility(View.VISIBLE);
                }

            }
        });

        b_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMsg();

                llmsg2.setVisibility(View.GONE);
                llclean.setVisibility(View.GONE);
                b_msg.setVisibility(View.VISIBLE);

            }
        });

    }

    public void getmsg() {


            message = eMsg.getText().toString();

            String queryStatus = "1";

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<Agent_Status_For_RideOut_Response> call = api.hap_super(s_id, message, queryStatus);
                call.enqueue(new Callback<Agent_Status_For_RideOut_Response>() {
                    @Override
                    public void onResponse(Call<Agent_Status_For_RideOut_Response> call,
                                           Response<Agent_Status_For_RideOut_Response> response) {
                        getResponse = response.body();
                        //  Log.d("TAG", "onResponse: "+getResponse.getMessage());

                    }

                    @Override
                    public void onFailure(Call<Agent_Status_For_RideOut_Response> call, Throwable t) {
                        t.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Try Again",
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure:t" + t);
                    }

                });
            } catch (Exception e) {
                // System.out.println("msg:" + e);
            }
        }


    public void deleteMsg() {

//        if (connection_detector.isConnectingToInternet()) {
            String queryStatus = "0";

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<Agent_Status_For_RideOut_Response> call = api.hap_delete(s_id, queryStatus);
                call.enqueue(new Callback<Agent_Status_For_RideOut_Response>() {
                    @Override
                    public void onResponse(Call<Agent_Status_For_RideOut_Response> call,
                                           Response<Agent_Status_For_RideOut_Response> response) {
                        getResponse = response.body();
                        // Log.d("TAG", "onResponse: "+getResponse.getMessage());


                    }

                    @Override
                    public void onFailure(Call<Agent_Status_For_RideOut_Response> call, Throwable t) {
                        t.getMessage();

                    }

                });
            } catch (Exception e) {
                System.out.println("msg:" + e);
            }
        /*}else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }

    // getting supervisor details

    public void getSupervisordetails(){

//        if (connection_detector.isConnectingToInternet()) {


//        String sid= "964312587";
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<Agent_Status_For_RideOut_Response> call = api.getSupervisordetails(s_id);
                call.enqueue(new Callback<Agent_Status_For_RideOut_Response>() {
                    @Override
                    public void onResponse(Call<Agent_Status_For_RideOut_Response> call,
                                           Response<Agent_Status_For_RideOut_Response> response) {

                        getResponse = new Agent_Status_For_RideOut_Response();
                        getResponse = response.body();

                        try {


                            mobileNumber = getResponse.getData()[0].getMobileNumber();
                            String sname = getResponse.getData()[0].getFirstName();
                            sName.setText(sname);
                            mobile.setText(mobileNumber);
                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), " No Data", Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Agent_Status_For_RideOut_Response> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        /*}else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void getAgentMsg(){
//        if (connection_detector.isConnectingToInternet()) {


            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<Agent_Status_For_RideOut_Response> call = api.getAgentMsg(s_id, a_id);
                call.enqueue(new Callback<Agent_Status_For_RideOut_Response>() {
                    @Override
                    public void onResponse(Call<Agent_Status_For_RideOut_Response> call,
                                           Response<Agent_Status_For_RideOut_Response> response) {

                        getResponse = new Agent_Status_For_RideOut_Response();
                        getResponse = response.body();
                        try {

                            if (getResponse.getStatus().equalsIgnoreCase("True")) {
                                String msg = getResponse.getData()[0].getDescription();
                                tvmsg.setText(msg);

                                if (!msg.isEmpty()) {
                                    tvmsg.setVisibility(View.VISIBLE);
                                    llmsg2.setVisibility(View.VISIBLE);
                                    llclean.setVisibility(View.VISIBLE);
                                    b_clean.setVisibility(View.VISIBLE);
                                }
                            } else {

                            }

                        } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(),"asdfghj", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Agent_Status_For_RideOut_Response> call, Throwable t) {

                    }
                });

            } catch (Exception e) {
                System.out.print("Exception e" + e);
            }
        /*}else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }
}
