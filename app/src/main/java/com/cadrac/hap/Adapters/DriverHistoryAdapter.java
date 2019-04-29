package com.cadrac.hap.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.activites.Driver_History;
import com.cadrac.hap.responses.DriverHistoryResponse;

import java.util.ArrayList;

public class DriverHistoryAdapter extends RecyclerView.Adapter<DriverHistoryAdapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<DriverHistoryResponse.data> data;
    ArrayList<DriverHistoryResponse.data> dataFilter;
    Context context;
    Driver_History driverHistory;

    String date,time,time1;

    public DriverHistoryAdapter(ArrayList<DriverHistoryResponse.data> data, Driver_History driverHistory, Context applicationContext) {
        this.context=applicationContext;
        this.data = data;
        this.dataFilter = new ArrayList<DriverHistoryResponse.data>();
        this.dataFilter.addAll(data);
        this.driverHistory=driverHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull EMSHolder holder, int position) {
        setScaleAnimation(holder.itemView);

        holder.source.setText(data.get(position).getSource());
        holder.destination.setText(data.get(position).getDestination());
        holder.id.setText(data.get(position).getId());
        holder.giid.setText(data.get(position).getGi_id());
        holder.goid.setText(data.get(position).getGo_id());
        holder.passengercount.setText(data.get(position).getPassengerCount());
        holder.totalfare.setText(data.get(position).getTotalFare());



        Log.d("TAG", "onBindViewHolder: so"+data.get(position).getSource());

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

        TextView id,giid,goid,source,destination,passengercount,totalfare;

        public EMSHolder(View v)
        {
            super(v);
            id= (TextView) v.findViewById(R.id.reqId);
            giid = (TextView) v.findViewById(R.id.agentriid);
            goid = (TextView) v.findViewById(R.id.agentroid);
            source =(TextView) v.findViewById(R.id.source);
            destination=(TextView) v.findViewById(R.id.destination);
            passengercount=(TextView) v.findViewById(R.id.nop);
            totalfare=(TextView) v.findViewById(R.id.fare);

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
    public EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_history_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }

    public long getItemId(int i) {
        return i;
    }


}
