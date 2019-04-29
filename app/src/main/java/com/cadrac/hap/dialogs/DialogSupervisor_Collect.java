package com.cadrac.hap.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap.R;
import com.cadrac.hap.responses.Supervisor_Status_Response;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogSupervisor_Collect extends DialogFragment {

    View view;

    EditText cash,digital,pending;
    Button submit;
    Supervisor_Status_Response getoutResponse;
    String go_otp;
    Button ok;
    String cash1,digital1,pending1;



    public static String status = "";

    String from="",s_id="",total_rides="",agent_id="",amount="",cash2="",digital2="",paidDate="",set_id="",totalPaid="",totalUnpaid="";

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

    public static DialogSupervisor_Collect newInstance() {
        return new DialogSupervisor_Collect();
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Add SubTaks");

        view = inflater.inflate(R.layout.activity_dialog_supervisor__collect, container, false);

        cash=(EditText) view.findViewById(R.id.cash);
        digital=(EditText) view.findViewById(R.id.digital);

        ok=(Button) view.findViewById(R.id.OK);




        agent_id = getArguments().getString("agent_id");
        amount = getArguments().getString("amount");
        cash2 = getArguments().getString("cash");
        digital2 = getArguments().getString("digital");

        s_id = getArguments().getString("s_id");
        set_id=getArguments().getString("set_id");
        totalPaid=getArguments().getString("totalPaid");
        totalUnpaid=getArguments().getString("totalUnpaid");
        total_rides = getArguments().getString("total_rides");
        paidDate=getArguments().getString("paidDate");


     /*   cash1=cash.getText().toString();
        System.out.println("cashiwqrew"+cash.getText().toString());
        digital1=digital.getText().toString();
        System.out.println("cashiwqrew"+digital.getText().toString());*/
        //submit otp and validating otp
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // supervisor_collect();

                cash1=cash.getText().toString();
                System.out.println("cashiwqrew"+cash.getText().toString());
                digital1=digital.getText().toString();
                System.out.println("cashiwqrew"+digital.getText().toString());
                //submit otp and validating otp
try {

    int a = Integer.parseInt(cash1);
    System.out.println(" adkshfyu"+a);
    int b = Integer.parseInt(digital1);
    System.out.println("bashfshajf" +b);


    int tot_amount = a + b;
    System.out.println("a+bhjwhgurw" +tot_amount);
    String str = new String(String.valueOf(tot_amount));
    if (tot_amount>Integer.parseInt(amount)|| (a==0 && b==0) ){
        Toast.makeText(getActivity(), "enter valid amount", Toast.LENGTH_SHORT).show();
    } else {

        supervisor_collect();

    }
}catch (Exception e)
{
    System.out.println("error call :"+e);
    Toast.makeText(getContext(), "Please Enter Valid Amount", Toast.LENGTH_SHORT).show();
}





            }
        });

        return view;

    }

    //for submitting and validate otp
    public void supervisor_collect()
    {
        try{


       /*   int a = Integer.parseInt(cash1);
            int b = Integer.parseInt(digital1);

            int tUnpaid = Integer.parseInt(totalUnpaid);


            if ((a + b) > tUnpaid || (a+b) == 0) {
                Toast.makeText(getContext(), "Enter Correct Amount", Toast.LENGTH_SHORT).show();
            }else {*/

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                String agent_pin = go_otp;


                Call<Supervisor_Status_Response> call = api.hap_supervisor_collect(s_id, agent_id, cash.getText().toString(), digital.getText().toString(), paidDate, set_id, total_rides, totalPaid, totalUnpaid, cash2, digital2, amount);
                call.enqueue(new Callback<Supervisor_Status_Response>() {
                    @Override
                    public void onResponse(Call<Supervisor_Status_Response> call,
                                           Response<Supervisor_Status_Response> response) {
                        getoutResponse = response.body();
                        Log.d("TAG", "onResponse:amith " + getoutResponse);
                        try {
                            if (getoutResponse.getStatus().equalsIgnoreCase("true")) {


                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                getDialog().dismiss();

                            } else {

                                Toast.makeText(getContext(), "please enter valid details", Toast.LENGTH_SHORT).show();
                                getDialog().dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(Call<Supervisor_Status_Response> call, Throwable t) {
                        t.getMessage();
                        Toast.makeText(getActivity(),
                                "Try Again",
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure:t" + t);
                    }

                });
//            }

        }
        catch (Exception e){
            System.out.println("msg:"+e);
        }

    }

}

