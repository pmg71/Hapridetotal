package com.cadrac.hap.activites;





import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.preference.PreferenceManager;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.ScaleAnimation;
        import android.widget.LinearLayout;
        import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.responses.Supervisor_Status_Response;

import java.util.ArrayList;


public class Supervisor_Status_Adapter extends RecyclerView.Adapter<Supervisor_Status_Adapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<Supervisor_Status_Response.data> data;
    Context context;
    fragment_supervisor_status getOutActivity;
    String settlementStatus="";

    public Supervisor_Status_Adapter(ArrayList<Supervisor_Status_Response.data> data, fragment_supervisor_status getOutActivity, Context context)
    {
        this.context=context;
        this.data = data;
        this.getOutActivity=getOutActivity;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_supervisor_status_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }
    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

        Log.d("TAG", "onBindViewHolder: "+data.get(position).getVechile_no());
        holder. agent_id.setText(data.get(position).getAgent_id());
        holder. agent_id2.setText(data.get(position).getAgent_id());
        holder.agent_name.setText(data.get(position).getAgent_name());
        holder.agent_name2.setText(data.get(position).getAgent_name());
        holder.agent_number1.setText(data.get(position).getAgent_number());
        holder.total_rides1.setText(data.get(position).getTotal_rides());
        holder.total_amount1.setText(data.get(position).getTotalFare());
        holder.source2.setText(data.get(position).getArea());
        holder.source.setText(data.get(position).getArea());
        holder.digital1.setText(data.get(position).getTotalPaid());

        // dividing total paid and unpaid amount
        try {
            if (data.get(position).getTotalUnpaid().equals("null"))
            {
                holder.cash1.setText(data.get(position).getTotalUnpaid());
            } else
            {
                holder.cash1.setText(data.get(position).getTotalUnpaid());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            holder.cash1.setText(data.get(position).getTotalFare());
        }


        final String ride_id=data.get(position).getR_id();
        final String driver_id=data.get(position).getD_id();
        final String passenger_count=data.get(position).getPassengerCount();

        final String amount=data.get(position).getTotalFare();
        final String agent_id=data.get(position).getAgent_id();
        final String total_rides=data.get(position).getTotal_rides();
        final String cash=data.get(position).getCash();
        final String digital=data.get(position).getDigital();
        final String paidDate=data.get(position).getPaidDate();
        final String s_id=data.get(position).getS_id();
        final String set_id=data.get(position).getSet_id();
        final String totalPaid=data.get(position).getTotalPaid();
        final String totalUnpaid=data.get(position).getTotalUnpaid();
        final String userName=data.get(position).getAgent_name();
        final String mobileNumber=data.get(position).getMobileNumber();
        final String area=data.get(position).getArea();
        final String pending=data.get(position).getPending();
        settlementStatus=data.get(position).getSettlementStatus();


        holder.agent_id1.setVisibility(View.GONE);
        holder.agent_id2.setVisibility(View.GONE);
        holder.agent_name1.setVisibility(View.GONE);
        holder.agent_name2.setVisibility(View.GONE);
        holder.total_amount.setVisibility(View.GONE);
        holder.total_amount1.setVisibility(View.GONE);
        holder.total_rides.setVisibility(View.GONE);
        holder.total_rides1.setVisibility(View.GONE);
        holder.source1.setVisibility(View.GONE);
        holder.source2.setVisibility(View.GONE);
        holder.close.setVisibility(View.GONE);
        holder.agent_number.setVisibility(View.GONE);
        holder.agent_number1.setVisibility(View.GONE);
        holder.digital.setVisibility(View.GONE);
        holder.digital1.setVisibility(View.GONE);
        holder.cash.setVisibility(View.GONE);
        holder.cash1.setVisibility(View.GONE);
        holder.generateotp.setVisibility(View.GONE);
        //  holder.  assign.setVisibility(View.GONE);
        holder.collect.setVisibility(View.GONE);

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.agent_id1.setVisibility(View.VISIBLE);
                holder.agent_id2.setVisibility(View.VISIBLE);
                holder.agent_name1.setVisibility(View.VISIBLE);
                holder.agent_name2.setVisibility(View.VISIBLE);
                holder. total_amount.setVisibility(View.VISIBLE);
                holder. total_amount1.setVisibility(View.VISIBLE);
                holder. total_rides.setVisibility(View.VISIBLE);
                holder. total_rides1.setVisibility(View.VISIBLE);
                holder.  source1.setVisibility(View.VISIBLE);
                holder.  source2.setVisibility(View.VISIBLE);
                holder.  close.setVisibility(View.VISIBLE);
                // holder.  assign.setVisibility(View.VISIBLE);
                //  holder.  collect.setVisibility(View.VISIBLE);
                holder.  agent_id.setVisibility(View.GONE);
                holder.source.setVisibility(View.GONE);
                holder.agent_name.setVisibility(View.GONE);
               /* holder.  driver_name.setVisibility(View.GONE);
                holder.  total_fare.setVisibility(View.GONE);*/
                holder.  check.setVisibility(View.GONE);
                holder.agent_number.setVisibility(View.VISIBLE);
                holder.agent_number1.setVisibility(View.VISIBLE);
                holder.digital.setVisibility(View.VISIBLE);
                holder.digital1.setVisibility(View.VISIBLE);
                holder.cash.setVisibility(View.VISIBLE);
                holder.cash1.setVisibility(View.VISIBLE);
                holder.generateotp.setVisibility(View.GONE);
                try {

                    if (data.get(position).getSettlementStatus().equals("0")) {
                        holder.collect.setVisibility(View.VISIBLE);
                    } else {
                        holder.collect.setVisibility(View.GONE);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    holder.collect.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.agent_id1.setVisibility(View.GONE);
                holder.agent_id2.setVisibility(View.GONE);
                holder.agent_name1.setVisibility(View.GONE);
                holder.agent_name2.setVisibility(View.GONE);
                holder. total_amount.setVisibility(View.GONE);
                holder. total_amount1.setVisibility(View.GONE);
                holder. total_rides.setVisibility(View.GONE);
                holder. total_rides1.setVisibility(View.GONE);
                holder.  source1.setVisibility(View.GONE);
                holder.  source2.setVisibility(View.GONE);
                holder.  close.setVisibility(View.GONE);
                // holder.  assign.setVisibility(View.GONE);
                holder.  collect.setVisibility(View.GONE);
                holder.  agent_id.setVisibility(View.VISIBLE);
          /*    holder.  driver_name.setVisibility(View.VISIBLE);
                holder.  total_fare.setVisibility(View.VISIBLE);    */
                holder.  check.setVisibility(View.VISIBLE);
                holder.source.setVisibility(View.VISIBLE);
                holder.agent_name.setVisibility(View.VISIBLE);
                holder.agent_number.setVisibility(View.GONE);
                holder.agent_number1.setVisibility(View.GONE);
                holder.digital.setVisibility(View.GONE);
                holder.digital1.setVisibility(View.GONE);
                holder.cash.setVisibility(View.GONE);
                holder.cash1.setVisibility(View.GONE);
                holder.generateotp.setVisibility(View.GONE);


            }
        });

        holder.generateotp.setId(position);
        holder.generateotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getOutActivity.otpGenerate(v.getId(),agent_id,amount,total_rides,cash,digital,paidDate);


            }
        });

        holder.collect.setId(position);
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getOutActivity.supervisor_collect(v.getId(),agent_id,amount,total_rides,cash,digital,paidDate,s_id,set_id,totalPaid,totalUnpaid);


            }
        });



//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.putString("no_of_pass",data.get(position).getNo_of_passengers());
//                editor.commit();
//                //Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(v.getContext(),Passinger.class);
//                context.startActivity(intent);
//
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

    public static class EMSHolder extends RecyclerView.ViewHolder
    {


        TextView agent_id,agent_name,source,agent_id1,agent_id2,agent_name1,agent_name2,agent_number,agent_number1,total_rides,total_rides1,total_amount,total_amount1,source1,source2,digital,digital1,cash,cash1;
        LinearLayout linearLayout;
        TextView check,close,assign,collect,generateotp;

        public EMSHolder(View v)
        {
            super(v);
            agent_id=(TextView)v.findViewById(R.id.agent_id);
            agent_name=(TextView)v.findViewById(R.id.agent_name);
            source=(TextView)v.findViewById(R.id.source);

            agent_id1=(TextView)v.findViewById(R.id.agent_id1);
            agent_id2=(TextView)v.findViewById(R.id.agent_id2);

            agent_name1=(TextView)v.findViewById(R.id.agent_name1);
            agent_name2=(TextView)v.findViewById(R.id.agent_name2);

            agent_number=(TextView)v.findViewById(R.id.agent_number);
            agent_number1=(TextView)v.findViewById(R.id.agent_number1);

            total_rides=(TextView)v.findViewById(R.id.total_rides);
            total_rides1=(TextView)v.findViewById(R.id.total_rides1);

            total_amount=(TextView)v.findViewById(R.id.total_fare);
            total_amount1=(TextView)v.findViewById(R.id.total_fare1);

            source1=(TextView)v.findViewById(R.id.source1);
            source2=(TextView)v.findViewById(R.id.source2);

            digital=(TextView)v.findViewById(R.id.digital);
            digital1=(TextView)v.findViewById(R.id.digital1);

            cash=(TextView)v.findViewById(R.id.cash);
            cash1=(TextView)v.findViewById(R.id.cash1);





            linearLayout=(LinearLayout)v.findViewById(R.id.hi);



            check=(TextView)v.findViewById(R.id.check);
            close=(TextView)v.findViewById(R.id.close);
            collect=(TextView)v.findViewById(R.id.collect);
            generateotp=(TextView)v.findViewById(R.id.otp);



        }
    }

    public int getItemCount() {
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return i;
    }

}
