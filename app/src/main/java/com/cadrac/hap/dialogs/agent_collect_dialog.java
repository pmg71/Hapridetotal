package com.cadrac.hap.dialogs;
import android.app.Activity;
        import android.app.Dialog;
        import android.content.Intent;
        import android.os.Bundle;
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
import com.cadrac.hap.activites.agent_driver_feedback;
import com.cadrac.hap.responses.Agent_DriverFeedBack_Response;
import com.cadrac.hap.responses.Ridein_Response;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;
        import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;


public class agent_collect_dialog extends DialogFragment implements View.OnClickListener {
    View view;
    TextView yes, no;
    EditText cash, digital;
    Ridein_Response getInResponse;

    public static String status = "";


    String ride_id = "" , driver_id="",passenger_count="",amount="",agent_id="",s_id="",cas,dig;
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

    public static agent_collect_dialog newInstance() {
        return new agent_collect_dialog();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Add SubTaks");

        view = inflater.inflate(R.layout.activity_agent_collect_dialog, container, false);
        yes = (TextView)view.findViewById(R.id.yes);
        no = (TextView)view.findViewById(R.id.no);
        cash = (EditText)view.findViewById(R.id.cash);
        digital = (EditText)view.findViewById(R.id.digital);



        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        ride_id= getArguments().getString("ride_id");
        driver_id= getArguments().getString("driver_id");
        passenger_count= getArguments().getString("passenger_count");
        amount= getArguments().getString("amount");
        agent_id= getArguments().getString("agent_id");
        s_id= getArguments().getString("s_id");











        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.no) {
            getDialog().cancel();
        } else if (v.getId() == R.id.yes) {


            try {


                cas = cash.getText().toString();
                dig = digital.getText().toString();
                amount = getArguments().getString("amount");

                int a = Integer.parseInt(cas);
                int b = Integer.parseInt(dig);


                int tot_amount = a + b;
                String str = new String(String.valueOf(tot_amount));
                if (!str.equals(amount)) {
                    Toast.makeText(getActivity(), "enter valid amount", Toast.LENGTH_SHORT).show();
                } else {


                    collect_details();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "enter valid input", Toast.LENGTH_SHORT).show();


            }
        }

    }
    public void collect_details()
    {
        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<Ridein_Response> call = api.getCollect(ride_id,driver_id,passenger_count,amount,dig,
                    cas,s_id,agent_id,Config.getLoginusername(getActivity()));
            call.enqueue(new Callback<Ridein_Response>() {
                @Override
                public void onResponse(Call<Ridein_Response> call,
                                       Response<Ridein_Response> response) {
                    getInResponse=response.body();
                    if(getInResponse.getStatus().equalsIgnoreCase("True"))
                    {
                        Intent intent=new Intent(getContext(), agent_driver_feedback.class);
                        intent.putExtra("rid",ride_id);
                        intent.putExtra("goid",agent_id);
                        intent.putExtra("did",driver_id);
                        startActivity(intent);
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
}
