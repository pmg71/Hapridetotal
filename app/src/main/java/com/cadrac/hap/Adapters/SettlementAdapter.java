package com.cadrac.hap.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.activites.agent_to_supervisor_settlement;
import com.cadrac.hap.responses.SettlementResponse;
import com.cadrac.hap.utils.Config;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class SettlementAdapter extends  RecyclerView.Adapter<SettlementAdapter.EMSHolder>{

    int FADE_DURATION = 100;
    ArrayList<SettlementResponse.data> data;
    ArrayList<SettlementResponse.data> search;
    Context context;
    agent_to_supervisor_settlement Settlement;
    String charString;
    SettlementResponse settlementResponse;
   /* String s_id="124";
    String  gi_id="7";*/


    String totalRides;

    public SettlementAdapter(ArrayList<SettlementResponse.data> data, agent_to_supervisor_settlement Settlement, Context context) {
        this.context=context;
        this.data = data;
        this.Settlement=Settlement;

        this.search= new ArrayList<SettlementResponse.data>();
        this.search.addAll(data);
        this.notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull final SettlementAdapter.EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);


        Log.d(TAG, "onBindViewHolder: "+data.get(position).getDate());
        holder.date.setText(data.get(position).getDate());
        holder.totalRides.setText(data.get(position).getTotalRides());
        holder.totalAmount.setText(data.get(position).getTotalAmount());
        holder.totalPaid.setText(data.get(position).getTotalPaid());
        //   holder.totalUnpaid.setText(data.get(position).getTotalUnpaid());
        final String set_id=data.get(position).getSet_id();
        final String totalRides=data.get(position).getTotalRides();
        final String paidDate=data.get(position).getDate();
        final String Status= data.get(position).getStatus();
        //  final String totalAmount=data.get(position).getTotalAmount();
        final String totalUnpaid=data.get(position).getTotalAmount();

        /*  totalUnpaid=data.get(position).getTotalUnpaid();*/
        //setting amount to unpaid

        final  String totalpaid=data.get(position).getTotalPaid();
        Log.d("TAG","status2213221"+data.get(position).getTotalPaid());
        try{
            if(data.get(position).getTotalPaid().equalsIgnoreCase("0")){
                holder.totalUnpaid.setText(data.get(position).getTotalAmount());
            }else{
                holder.totalUnpaid.setText(data.get(position).getTotalUnpaid());
            }
        }catch (Exception e){
            holder.totalUnpaid.setText(data.get(position).getTotalAmount());
            e.printStackTrace();
        }

        try{
            Log.d("TAG","status2213221"+data.get(position).getStatus());
            if(data.get(position).getStatus().equalsIgnoreCase("1")){
                holder.pending.setVisibility(View.GONE);

                holder.l1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.h==0) {
                            holder.totalRides1.setVisibility(View.VISIBLE);
                            holder.totalAmount1.setVisibility(View.VISIBLE);
                            holder.totalPaid1.setVisibility(View.VISIBLE);
                            holder.totalUnpaid1.setVisibility(View.VISIBLE);

                            holder.totalRides.setVisibility(View.VISIBLE);
                            holder.totalAmount.setVisibility(View.VISIBLE);
                            holder.totalPaid.setVisibility(View.VISIBLE);
                            holder.totalUnpaid.setVisibility(View.VISIBLE);

                            holder.expandless.setVisibility(View.VISIBLE);
                            holder.expandmore.setVisibility(View.GONE);
                            holder.pay.setVisibility(View.GONE);

                            holder.h=holder.h+1;
                        }else{

                            holder.totalPaid.setVisibility(View.GONE);
                            holder.totalUnpaid.setVisibility(View.GONE);

                            holder.totalPaid1.setVisibility(View.GONE);
                            holder.totalUnpaid1.setVisibility(View.GONE);
                            // holder.pay.setVisibility(View.GONE);
                            //       holder.g_pin.setVisibility(View.GONE);

                            holder.expandless.setVisibility(View.GONE);
                            holder.expandmore.setVisibility(View.VISIBLE);


                            holder.h=holder.h-1;


                        }

                    }
                });
            }else if(data.get(position).getStatus().equalsIgnoreCase("0")){
                holder.completed.setVisibility(View.GONE);

                holder.l1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.h==0) {
                            holder.totalRides1.setVisibility(View.VISIBLE);
                            holder.totalAmount1.setVisibility(View.VISIBLE);
                            holder.totalPaid1.setVisibility(View.VISIBLE);
                            holder.totalUnpaid1.setVisibility(View.VISIBLE);

                            holder.totalRides.setVisibility(View.VISIBLE);
                            holder.totalAmount.setVisibility(View.VISIBLE);
                            holder.totalPaid.setVisibility(View.VISIBLE);
                            holder.totalUnpaid.setVisibility(View.VISIBLE);

                            holder.pay.setVisibility(View.VISIBLE);
                            //  holder.g_pin.setVisibility(View.VISIBLE);
                            holder.pending.setVisibility(View.GONE);

                            holder.expandless.setVisibility(View.VISIBLE);
                            holder.expandmore.setVisibility(View.GONE);

                            holder.h=holder.h+1;
                        }else{

                            holder.totalPaid.setVisibility(View.GONE);
                            holder.totalUnpaid.setVisibility(View.GONE);

                            holder.totalPaid1.setVisibility(View.GONE);
                            holder.totalUnpaid1.setVisibility(View.GONE);
                            // holder.pay.setVisibility(View.GONE);
                            //     holder.g_pin.setVisibility(View.GONE);
                            holder.pending.setVisibility(View.VISIBLE);

                            holder.expandless.setVisibility(View.GONE);
                            holder.expandmore.setVisibility(View.VISIBLE);

                            holder.h=holder.h-1;


                        }

                    }
                });
            }}catch (Exception e){
            e.printStackTrace();
        }



//        holder.g_pin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Settlement.pindialog(v.getId());
//
//            }
//        });

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_id=Config.getLoginsupervisorId(context);
                String gi_id=Config.getLoginusername(context);
                //metod name in settlements class
//                String s_id= Config.getLoginsupervisorId(context);
//                String  gi_id=Config.getLoginusername(context);
                Settlement.payment(v.getId(),s_id,gi_id,set_id,totalUnpaid,totalRides,paidDate);
            }
        });

    }

    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public static class EMSHolder extends RecyclerView.ViewHolder
    {

        TextView date,totalRides,totalAmount,totalPaid,totalUnpaid,pay,completed,pending;
        TextView totalRides1,totalAmount1,totalPaid1,totalUnpaid1;
        ImageView expandmore,expandless;
        LinearLayout l1;
        int h=0;

        public EMSHolder(View v)
        {
            super(v);

            date=(TextView)v.findViewById(R.id.date);
            totalRides=(TextView)v.findViewById(R.id.t_rides);
            totalAmount=(TextView)v.findViewById(R.id.t_amount);
            totalPaid=(TextView)v.findViewById(R.id.t_paid);
            totalUnpaid=(TextView)v.findViewById(R.id.t_unpaid);
            l1=(LinearLayout)v.findViewById(R.id.l1);


            totalRides1=(TextView)v.findViewById(R.id.t_rides1);
            totalAmount1=(TextView)v.findViewById(R.id.t_amount1);
            totalPaid1=(TextView)v.findViewById(R.id.t_paid1);
            totalUnpaid1=(TextView)v.findViewById(R.id.t_unpaid1);
            pay=(TextView) v.findViewById(R.id.pay);
            //   g_pin=(TextView)v.findViewById(R.id.g_pin);
            completed=(TextView) v.findViewById(R.id.completed);
            pending=(TextView) v.findViewById(R.id.pending);

            expandmore=(ImageView)v.findViewById(R.id.expandmore);
            expandless=(ImageView)v.findViewById(R.id.expandless);

            totalPaid.setVisibility(View.GONE);
            totalUnpaid.setVisibility(View.GONE);

            totalPaid1.setVisibility(View.GONE);
            totalUnpaid1.setVisibility(View.GONE);

            //  g_pin.setVisibility(View.GONE);
            pay.setVisibility(View.GONE);

            expandless.setVisibility(View.GONE);




        }
    }

    public int getItemCount() {
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                data.clear();

                charString = charSequence.toString();

                if (charString.isEmpty())
                { Log.d("TAG", "onTextChanged: "+charString);
                    data.addAll(search);
                }
                else
                {
                    Log.d("TAG", "onTextChanged: "+charString);
                    //dataFilter.clear();
                    ArrayList<SettlementResponse.data> filteredList = new ArrayList<>();
                    for (SettlementResponse.data row : search)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getDate().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/
                        {
                            filteredList.add(row);
                        }
                    }

                    data = filteredList;
                }

                //System.out.println("Data Filter Size--"+data.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void  publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                data = (ArrayList<SettlementResponse.data>) filterResults.values;
                notifyDataSetChanged();

            }


        };
    }

    @NonNull
    @Override
    public SettlementAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_settlement_adapter,
                parent, false);

        return new SettlementAdapter.EMSHolder(itemView);
    }
    public long getItemId(int i) {
        return i;
    }

}