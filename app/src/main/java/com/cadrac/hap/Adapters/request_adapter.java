package com.cadrac.hap.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.R;
import com.cadrac.hap.activites.AddOn;
import com.cadrac.hap.activites.fragment_agent_req;
import com.cadrac.hap.responses.request_response;
import com.cadrac.hap.responses.statusResponse;
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

public class request_adapter extends RecyclerView.Adapter<request_adapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<request_response.data> data;
    Context context;
    fragment_agent_req agent_req;
    Connection_Detector connection_detector;
    String status, det_id, agent_id;
    statusResponse status_response;
    request_response request_response;


    public request_adapter(ArrayList<request_response.data> data, fragment_agent_req agent_req, Context context)
    {
        this.context=context;
        this.data = data;
        this.agent_req=agent_req;
        connection_detector = new Connection_Detector(context);
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_request_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

       // holder.name.setText(data.get(position).getPassengerName());
        holder.number.setText(data.get(position).getPassengerNumber());
        holder.seats.setText(data.get(position).getSeats());
        holder.destination.setText(data.get(position).getDestination());
        holder.veh_type.setText(data.get(position).getVeh_type());
        holder.fare.setText(data.get(position).getFare());
        holder.id.setText(data.get(position).getId());



        agent_id= Config.getLoginusername(context);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.accept.setVisibility(View.GONE);
                holder.reject.setVisibility(View.GONE);
                holder.assign.setVisibility(View.VISIBLE);
                holder.addon.setVisibility(View.VISIBLE);

                status = "1";
                det_id = holder.id.getText().toString();
                setStaus();


            }
        });
        holder.addon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddOn.class);
                i.putExtra("seats", holder.seats.getText().toString());
                i.putExtra("fare", holder.fare.getText().toString());
                i.putExtra("destination", holder.destination.getText().toString());
                i.putExtra("veh_type", holder.veh_type.getText().toString());
                i.putExtra("id", holder.id.getText().toString());
                context.startActivity(i);



            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.ll1.setVisibility(View.GONE);
                status = "3";
                det_id = holder.id.getText().toString();
                holder.ll1.removeView(v);

              data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());

                setStaus();
        setCount();
              //  notifyItemRangeChanged(position, m);

            }
        });

        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddOn.class);
                i.putExtra("seats", holder.seats.getText().toString());
                i.putExtra("fare", holder.fare.getText().toString());
                i.putExtra("destination", holder.destination.getText().toString());
                i.putExtra("veh_type", holder.veh_type.getText().toString());
                i.putExtra("id", holder.id.getText().toString());
                context.startActivity(i);
            }
        });

        // Toast.makeText(context,"hhhhh",Toast.LENGTH_SHORT).show();
//        final String call1 = holder.mobile.getText().toString();
//
//        holder.bcall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                callIntent.setData(Uri.parse("tel:" + call1));
//
//                if (ActivityCompat.checkSelfPermission(context,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//
//
//                    return;
//                }
//                context.startActivity(callIntent);
//            }
//        });

    }

    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    public void setStaus(){
        if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<statusResponse> call = api.status(det_id,status);
                call.enqueue(new Callback<statusResponse>() {
                    @Override
                    public void onResponse(Call<statusResponse> call,
                                           Response<statusResponse> response) {

                        status_response = new statusResponse();
                        status_response = response.body();
                    }

                    @Override
                    public void onFailure(Call<statusResponse> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCount(){
        if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<request_response> call = api.countvalidation(agent_id);

                call.enqueue(new Callback<request_response>() {
                    @Override
                    public void onResponse(Call<request_response> call,
                                           Response<request_response> response) {

                        request_response = new request_response();
                        request_response = response.body();
                        if (request_response.getStatus().equalsIgnoreCase("true")) {


                        }
                    }


                    @Override
                    public void onFailure(Call<request_response> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public static class EMSHolder extends RecyclerView.ViewHolder
    {
        Context context;
LinearLayout ll1;
        TextView number, name, seats,destination, fare, veh_type, id;
        Button accept, reject, assign, addon;

        public EMSHolder(View v)
        {
            super(v);
            Log.d("TAG", "getItemCount: ");
            number =(TextView) v.findViewById(R.id.number);
            name=(TextView) v.findViewById(R.id.name);
            seats=(TextView) v.findViewById(R.id.seats);
            destination=(TextView) v.findViewById(R.id.destination);
            accept = (Button)v.findViewById(R.id.accept);
            fare= (TextView)v.findViewById(R.id.fare);
            veh_type = (TextView)v.findViewById(R.id.veh_type);
            reject = (Button)v.findViewById(R.id.reject);
            assign = (Button)v.findViewById(R.id.assign);
            addon = (Button)v.findViewById(R.id.addon);
            id= (TextView) v.findViewById(R.id.reqId);
            ll1 = (LinearLayout)v.findViewById(R.id.ll1);
//            accept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context, AddOn.class);
//                   // i.putExtra("seats", seats.getText().toString());
//                    context.startActivity(i);
//                }
//            });

        }
    }

    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+data.size());
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return i;
    }

}

