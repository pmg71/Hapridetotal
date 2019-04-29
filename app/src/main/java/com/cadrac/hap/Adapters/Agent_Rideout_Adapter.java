package com.cadrac.hap.Adapters;



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
import com.cadrac.hap.activites.fragment_agent_rideout;
import com.cadrac.hap.responses.Rideout_Response;

import java.util.ArrayList;


public class Agent_Rideout_Adapter extends RecyclerView.Adapter<Agent_Rideout_Adapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<Rideout_Response.data> data;
    Context context;
    fragment_agent_rideout getOutActivity;

    public Agent_Rideout_Adapter(ArrayList<Rideout_Response.data> data, fragment_agent_rideout getOutActivity, Context context)
    {
        this.context=context;
        this.data = data;
        this.getOutActivity=getOutActivity;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_rideout_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }
    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

        Log.d("TAG", "onBindViewHolder: "+data.get(position).getVechile_no());
        holder. agent_id.setText(data.get(position).getGi_id());
        holder. driver_id1.setText(data.get(position).getD_id());
        holder.total_fare.setText(data.get(position).getTotalFare());
        holder.total_fare2.setText(data.get(position).getTotalFare());
        holder.no_pass.setText(data.get(position).getPassengerCount());
        holder.driver_name.setText(data.get(position).getDriverName());
        holder.driver_name2.setText(data.get(position).getDriverName());
        holder.vechno.setText(data.get(position).getVehicleNumber());
       holder.time.setText(data.get(position).getEstimatedTime());

        /*String a= data.get(position).getEstimatedTime();
        String [] separtor = a.split(" ");
//        separtor[1] = separtor[1];
        Log.d("TAG", "onBindViewHolder: t"+separtor);
        try {
            holder.time.setText(separtor[1]);
        }catch (Exception e){
            System.out.print(e);
        }
        Log.d("TAG", "onBindViewHolder: tulasi"+a);
*/

        final String ride_id=data.get(position).getR_id();
        final String driver_id=data.get(position).getD_id();
        final String amount=data.get(position).getTotalFare();
        final String passenger_count=data.get(position).getPassengerCount();
        final String agent_id=data.get(position).getGi_id();
        final String s_id=data.get(position).getS_id();



        holder. agent_id2.setText(data.get(position).getGi_id());


        holder.agent_id1.setVisibility(View.GONE);
        holder.agent_id2.setVisibility(View.GONE);
        holder.driver_id.setVisibility(View.GONE);
        holder.driver_id1.setVisibility(View.GONE);
        holder. driver_name1.setVisibility(View.GONE);
        holder. driver_name2.setVisibility(View.GONE);
        holder. vechno.setVisibility(View.GONE);
        holder. vechno1.setVisibility(View.GONE);
        holder.  no_pass.setVisibility(View.GONE);
        holder.  no_pass1.setVisibility(View.GONE);
        holder.  total_fare1.setVisibility(View.GONE);
        holder.  total_fare2.setVisibility(View.GONE);
        holder.  time1.setVisibility(View.GONE);
        holder.  time.setVisibility(View.GONE);
        holder.  close.setVisibility(View.GONE);
        holder.  assign.setVisibility(View.GONE);
        holder.  collect.setVisibility(View.GONE);



        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.agent_id1.setVisibility(View.VISIBLE);
                holder.agent_id2.setVisibility(View.VISIBLE);
                holder.driver_id.setVisibility(View.VISIBLE);
                holder.driver_id1.setVisibility(View.VISIBLE);
                holder. driver_name1.setVisibility(View.VISIBLE);
                holder. driver_name2.setVisibility(View.VISIBLE);
                holder. vechno.setVisibility(View.VISIBLE);
                holder. vechno1.setVisibility(View.VISIBLE);
                holder.  no_pass.setVisibility(View.VISIBLE);
                holder.  no_pass1.setVisibility(View.VISIBLE);
                holder.  total_fare1.setVisibility(View.VISIBLE);
                holder.  total_fare2.setVisibility(View.VISIBLE);
                holder.  time1.setVisibility(View.VISIBLE);
                holder.  time.setVisibility(View.VISIBLE);
                holder.  close.setVisibility(View.VISIBLE);
                holder.  assign.setVisibility(View.VISIBLE);
                holder.  collect.setVisibility(View.VISIBLE);
                holder.  agent_id.setVisibility(View.GONE);
                holder.  driver_name.setVisibility(View.GONE);
                holder.  total_fare.setVisibility(View.GONE);
                holder.  check.setVisibility(View.GONE);







            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.agent_id1.setVisibility(View.GONE);
                holder.agent_id2.setVisibility(View.GONE);
                holder.driver_id.setVisibility(View.GONE);
                holder.driver_id1.setVisibility(View.GONE);
                holder. driver_name1.setVisibility(View.GONE);
                holder. driver_name2.setVisibility(View.GONE);
                holder. vechno.setVisibility(View.GONE);
                holder. vechno1.setVisibility(View.GONE);
                holder.  no_pass.setVisibility(View.GONE);
                holder.  no_pass1.setVisibility(View.GONE);
                holder.  total_fare1.setVisibility(View.GONE);
                holder.  total_fare2.setVisibility(View.GONE);
                holder.  time1.setVisibility(View.GONE);
                holder.  time.setVisibility(View.GONE);
                holder.  close.setVisibility(View.GONE);
                holder.  assign.setVisibility(View.GONE);
                holder.  collect.setVisibility(View.GONE);
                holder.  agent_id.setVisibility(View.VISIBLE);
                holder.  driver_name.setVisibility(View.VISIBLE);
                holder.  total_fare.setVisibility(View.VISIBLE);
                holder.  check.setVisibility(View.VISIBLE);

            }
        });
        holder.collect.setId(position);
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getOutActivity.collectGetout(v.getId(),ride_id,amount,driver_id,passenger_count,agent_id,s_id);


            }
        });
        holder.assign.setId(position);
        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getOutActivity.assignGetout(v.getId(),ride_id);


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


        TextView vechno,vechno1,no_pass,no_pass1,agent_id,agent_id1,agent_id2,driver_id,driver_id1,driver_name,driver_name1,driver_name2,total_fare,total_fare1,total_fare2,time,time1;
        LinearLayout linearLayout;
        TextView check,close,assign,collect;

        public EMSHolder(View v)
        {
            super(v);
            agent_id=(TextView)v.findViewById(R.id.agent_id1);
            driver_name=(TextView)v.findViewById(R.id.driver_name1);
            total_fare=(TextView)v.findViewById(R.id.total_fare1);

            agent_id1=(TextView)v.findViewById(R.id.agent_id2);
            agent_id2=(TextView)v.findViewById(R.id.agent_id);

            driver_id=(TextView)v.findViewById(R.id.driver_id1);
            driver_id1=(TextView)v.findViewById(R.id.driver_id);

            driver_name1=(TextView)v.findViewById(R.id.driver_name2);
            driver_name2=(TextView)v.findViewById(R.id.driver_name);







            vechno=(TextView)v.findViewById(R.id.Vech_no);
            vechno1=(TextView)v.findViewById(R.id.Vech_no1);


            no_pass=(TextView)v.findViewById(R.id.no_of_pass);
            no_pass1=(TextView)v.findViewById(R.id.no_of_pass1);

            total_fare1=(TextView)v.findViewById(R.id.total_fare2);
            total_fare2=(TextView)v.findViewById(R.id.total_fare);

            time=(TextView)v.findViewById(R.id.est_time);
            time1=(TextView)v.findViewById(R.id.est_time1);




            linearLayout=(LinearLayout)v.findViewById(R.id.hi);



            check=(TextView)v.findViewById(R.id.check);
            close=(TextView)v.findViewById(R.id.close);
            assign=(TextView)v.findViewById(R.id.assign);
            collect=(TextView)v.findViewById(R.id.collect);



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
