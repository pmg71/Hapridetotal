package com.cadrac.hap.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.activites.agent_assignedRides;
import com.cadrac.hap.activites.agent_ridein_history;
import com.cadrac.hap.activites.agent_to_supervisor_settlement;
import com.cadrac.hap.responses.Agent_Assigned_Ride_Response;
import com.cadrac.hap.responses.RideInHistoryResponse;
import com.cadrac.hap.responses.SettlementResponse;
import com.cadrac.hap.utils.Config;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Agent_AssignedRides_Adapter extends  RecyclerView.Adapter<Agent_AssignedRides_Adapter.EMSHolder>{

    int FADE_DURATION = 100;
    ArrayList<Agent_Assigned_Ride_Response.data> data;
    ArrayList<Agent_Assigned_Ride_Response.data> search;
    Context context;
    agent_assignedRides agentAssignedRides;
    Agent_Assigned_Ride_Response agent_assigned_ride_response;


    public Agent_AssignedRides_Adapter(ArrayList<Agent_Assigned_Ride_Response.data> data, agent_assignedRides agentAssignedRides, Context context) {
        this.context=context;
        this.data = data;
        this.agentAssignedRides=agentAssignedRides;

        this.search= new ArrayList<Agent_Assigned_Ride_Response.data>();
        this.search.addAll(data);
        this.notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull final Agent_AssignedRides_Adapter.EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);

        holder.rideid.setText(data.get(position).getRide_id());
        holder.passname.setText(data.get(position).getPassenger_Name());


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PassengerNumber=data.get(position).getPass_Number();

                Log.d(TAG, "onnumber "+PassengerNumber);

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts( "tel", PassengerNumber, null ));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity( callIntent );

            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=data.get(position).getRide_id();
                String status="6";
                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());
                agentAssignedRides.canceldialog(id,status);

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
      TextView rideidtxt,rideid,passname,cancel;
      ImageView call;


        public EMSHolder(View v)
        {
            super(v);

            rideidtxt=v.findViewById(R.id.rideidtxt);
            rideid=v.findViewById(R.id.rideid);
            passname=v.findViewById(R.id.PassengerName);
            cancel=v.findViewById(R.id.cancel);
            call=v.findViewById(R.id.call);



        }
    }

    public int getItemCount() {
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }


    @NonNull
    @Override
    public Agent_AssignedRides_Adapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_assignedrides_adapter,
                parent, false);

        return new Agent_AssignedRides_Adapter.EMSHolder(itemView);
    }
    public long getItemId(int i) {
        return i;
    }

}