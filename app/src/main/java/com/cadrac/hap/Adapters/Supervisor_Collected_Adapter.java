package com.cadrac.hap.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cadrac.hap.R;
import com.cadrac.hap.activites.fragment_supervisor_collected;
import com.cadrac.hap.responses.Supervisor_Collected_Response;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Supervisor_Collected_Adapter extends RecyclerView.Adapter<Supervisor_Collected_Adapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<Supervisor_Collected_Response.data> data;
    Context context;
    fragment_supervisor_collected getOutActivity;

    public Supervisor_Collected_Adapter(ArrayList<Supervisor_Collected_Response.data> data, fragment_supervisor_collected getOutActivity, Context context)
    {
        this.context=context;
        this.data = data;
        this.getOutActivity=getOutActivity;
    }



    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_supervisor_collected_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }
    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);





        holder.rides.setText(data.get(position).getTotalRides());
        // Log.d("amith",holder.rides.getText().toString());
        holder.amount.setText(data.get(position).getTotalAmount());
        holder.totalpaid.setText(data.get(position).getTotalPaid());
        holder.cash.setText(data.get(position).getCash());
        holder.digital.setText(data.get(position).getDigital());
        holder.time2.setText(data.get(position).getTime());
        //   holder.status.setText(data.get(position).getVehicleNumber());
        holder.date.setText(data.get(position).getDate());

        holder.agent_id.setText(data.get(position).getGo_id());


        holder.downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.paid.setVisibility(View.VISIBLE);
                holder.cash1.setVisibility(View.VISIBLE);
                holder.digital1.setVisibility(View.VISIBLE);
                holder.totalpaid.setVisibility(View.VISIBLE);
                holder.timeV.setVisibility(View.VISIBLE);
                holder.cash.setVisibility(View.VISIBLE);
                holder.digital.setVisibility(View.VISIBLE);
                holder.time2.setVisibility(View.VISIBLE);


                holder.downarrow.setVisibility(View.GONE);
                holder.uparrow.setVisibility(View.VISIBLE);


            }
        });


        holder.uparrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.uparrow.setVisibility(View.GONE);
                holder.downarrow.setVisibility(View.VISIBLE);

                holder.totalpaid.setVisibility(View.GONE);
                holder.cash.setVisibility(View.GONE);
                holder.digital.setVisibility(View.GONE);
                holder.time2.setVisibility(View.GONE);

                holder.paid.setVisibility(View.GONE);
                holder.cash1.setVisibility(View.GONE);
                holder.digital1.setVisibility(View.GONE);
                holder.totalpaid.setVisibility(View.GONE);
                holder.timeV.setVisibility(View.GONE);
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


        LinearLayout linearLayout;

        TextView date,rides,amount,totalpaid,cash,digital,time2,status, agent_id, paid, cash1, digital1, timeV ;

        ImageView uparrow, downarrow;

        public EMSHolder(View v)
        {
            super(v);
            date=(TextView)v.findViewById(R.id.date);
            rides=(TextView)v.findViewById(R.id.rides);
            amount=(TextView)v.findViewById(R.id.amount);

            totalpaid=(TextView)v.findViewById(R.id.totalpaid);
            cash=(TextView)v.findViewById(R.id.cash);

            digital=(TextView)v.findViewById(R.id.digital);
            time2=(TextView)v.findViewById(R.id.time1);

            status=(TextView)v.findViewById(R.id.status);

            agent_id = (TextView)v.findViewById(R.id.agent_id);

            paid = (TextView)v.findViewById(R.id.paid);
            cash1 = (TextView)v.findViewById(R.id.cash1);
            digital1 = (TextView)v.findViewById(R.id.digital1);
            timeV =(TextView)v.findViewById(R.id.timeView);




            uparrow = (ImageView)v.findViewById(R.id.arrowup);

            downarrow = (ImageView)v.findViewById(R.id.arrowdown);



            linearLayout=(LinearLayout)v.findViewById(R.id.hi);






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