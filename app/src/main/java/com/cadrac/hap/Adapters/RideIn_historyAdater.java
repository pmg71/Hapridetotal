package com.cadrac.hap.Adapters;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cadrac.hap.R;
import com.cadrac.hap.activites.agent_ridein_history;
import com.cadrac.hap.responses.RideInHistoryResponse;

import java.util.ArrayList;

public class RideIn_historyAdater extends RecyclerView.Adapter<RideIn_historyAdater.EMSHolder>implements Filterable {

    int FADE_DURATION = 100;
    ArrayList<RideInHistoryResponse.data> data;
    ArrayList<RideInHistoryResponse.data> dataFilter;
    Context context;

    agent_ridein_history getIn_historyActivity;
    String date,time,time1;

    public RideIn_historyAdater(ArrayList<RideInHistoryResponse.data> data, agent_ridein_history getIn_historyActivity, Context applicationContext) {
        this.context=applicationContext;
        this.data = data;
        this.dataFilter = new ArrayList<RideInHistoryResponse.data>();
        this.dataFilter.addAll(data);
        this.getIn_historyActivity=getIn_historyActivity;
    }

    @Override
    public void onBindViewHolder(@NonNull RideIn_historyAdater.EMSHolder holder, int position) {
        setScaleAnimation(holder.itemView);

        // holder.no_pass.setText(data.get(position).getPassengerCount());

        //mahesh
        //String appPassCount=data.get(position).getPassengerCount();
        //  String addPassCount=data.get(position).getAddonpassengerCount();
        int appPassCount =Integer.parseInt(data.get(position).getPassengerCount());
        int  addPassCount=Integer.parseInt(data.get(position).getAddonpassengerCount());
        int totalPassCount=appPassCount+addPassCount;

        holder.no_pass.setText(String.valueOf(totalPassCount));
        Log.d("TAG", "totalPass: "+totalPassCount);
        //closed

        holder.source.setText(data.get(position).getSource());
        holder.destination.setText(data.get(position).getDestination());
        /*  holder.status.setText(data.get(position).getRideStatus());*/
        holder.date.setText(data.get(position).getDate());

        holder.vechno.setText(data.get(position).getVehicleNumber());
        String string = data.get(position).getTime();
        String[] parts = string.split(":");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        String stime=part1+":"+part2+":"+part3;
        holder.time.setText(stime);
        if(data.get(position).getRideStatus().equals("0")){
            holder.status.setText("pending");
        }else {
            holder.status.setText("received");
        }

        Log.d("TAG", "addonPass: "+data.get(position).getAddonpassengerCount());
        Log.d("TAG", "addonfare: "+data.get(position).getAddonFare());
        if(data.get(position).getAddonpassengerCount().equalsIgnoreCase("0") && data.get(position).getAddonFare().equalsIgnoreCase("0")){
            holder.addon.setVisibility(View.GONE);
        }else{
            holder.addon.setVisibility(View.VISIBLE);
        }


    }
    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                data.clear();

                String charString = charSequence.toString();

                if (charString.isEmpty())
                {
                    data.addAll(dataFilter);
                }
                else
                {
                    ArrayList<RideInHistoryResponse.data> filteredList = new ArrayList<>();
                    for (RideInHistoryResponse.data row : dataFilter)
                    {
                        if (row.getSource().toLowerCase().contains(charString.toLowerCase())||
                                row.getDate().toLowerCase().contains(charString.toLowerCase())||
                                row.getTime().toLowerCase().contains(charString.toLowerCase())||
                                row.getDestination().toUpperCase().contains(charString.toUpperCase()))
                        {
                            filteredList.add(row);
                        }
                    }

                    data = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {

                data = (ArrayList<RideInHistoryResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class EMSHolder extends RecyclerView.ViewHolder
    {

        TextView amount,vechno,no_pass,source,destination,status,date,time,addon;
        LinearLayout linearLayout;

        public EMSHolder(View v)
        {
            super(v);
            source=(TextView)v.findViewById(R.id.source);
            destination=(TextView)v.findViewById(R.id.destination);
            vechno=(TextView)v.findViewById(R.id.Vech_no);
            no_pass=(TextView)v.findViewById(R.id.no_of_pass);
            linearLayout=(LinearLayout)v.findViewById(R.id.hi);
            status=(TextView)v.findViewById(R.id.status);
            date=(TextView)v.findViewById(R.id.date);
            time=(TextView)v.findViewById(R.id.time);
            addon=(TextView)v.findViewById(R.id.addon);

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
    public RideIn_historyAdater.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_agent_ridein_history_adapter,
                parent, false);

        return new RideIn_historyAdater.EMSHolder(itemView);
    }

    public long getItemId(int i) {
        return i;
    }

}