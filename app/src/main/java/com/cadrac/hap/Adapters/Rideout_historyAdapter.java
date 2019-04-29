package com.cadrac.hap.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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
import android.widget.Toast;


import com.cadrac.hap.R;
import com.cadrac.hap.activites.agent_rideout_history;
import com.cadrac.hap.responses.Rideout_HistoryResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



    public class Rideout_historyAdapter extends RecyclerView.Adapter<Rideout_historyAdapter.EMSHolder> implements Filterable {

        int FADE_DURATION = 100;
        ArrayList<Rideout_HistoryResponse.data> data;
        ArrayList<Rideout_HistoryResponse.data> dataFilter;
        Context context;
        agent_rideout_history getoutHistoryActivity;

        public Rideout_historyAdapter(ArrayList<Rideout_HistoryResponse.data> data, agent_rideout_history getoutHistoryActivity, Context applicationContext) {
            this.context = applicationContext;
            this.data = data;
            this.dataFilter = new ArrayList<Rideout_HistoryResponse.data>();
            this.dataFilter.addAll(data);
            this.getoutHistoryActivity = getoutHistoryActivity;
        }

        @Override
        public void onBindViewHolder(@NonNull Rideout_historyAdapter.EMSHolder holder, int position) {
            setScaleAnimation(holder.itemView);
            holder.source.setText(data.get(position).getSource());
            holder.destination.setText(data.get(position).getDestination());
            holder.date.setText(data.get(position).getDate());
            String string = data.get(position).getTime();
            String[] parts = string.split(":");
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];
            String stime = part1 + ":" + part2 + ":" + part3;
            holder.time.setText(stime);
            holder.Agent_id.setText(data.get(position).getAgent_id());
            holder.Driver_Id.setText(data.get(position).getDriver_id());
            holder.no_pass.setText(data.get(position).getPassengerCount());
            holder.drivername.setText(data.get(position).getDriver_name());
            holder.amount.setText(data.get(position).getTotalFare());
            holder.vechno.setText(data.get(position).getVehicleNumber());

        }

        private void setScaleAnimation(View view) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    data.clear();

                    String charString = charSequence.toString();

                    if (charString.isEmpty()) {
                        data.addAll(dataFilter);
                    } else {
                        ArrayList<Rideout_HistoryResponse.data> filteredList = new ArrayList<>();
                        for (Rideout_HistoryResponse.data row : dataFilter) {
                            if (row.getAgent_id().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getDate().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getDestination().toUpperCase().contains(charString.toUpperCase())) {
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
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                    data = (ArrayList<Rideout_HistoryResponse.data>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public static class EMSHolder extends RecyclerView.ViewHolder {

            TextView amount, vechno, no_pass, source, destination, Agent_id, Driver_Id, date, time, drivername;
            LinearLayout linearLayout;

            public EMSHolder(View v) {
                super(v);

                amount = (TextView) v.findViewById(R.id.amount);
                vechno = (TextView) v.findViewById(R.id.Vech_no);
                no_pass = (TextView) v.findViewById(R.id.no_of_pass);
                linearLayout = (LinearLayout) v.findViewById(R.id.hi);
                source = (TextView) v.findViewById(R.id.source);
                destination = (TextView) v.findViewById(R.id.destination);
                Agent_id = (TextView) v.findViewById(R.id.ag_id);
                Driver_Id = (TextView) v.findViewById(R.id.dri_id);
                date = (TextView) v.findViewById(R.id.date);
                time = (TextView) v.findViewById(R.id.time);
                drivername = (TextView) v.findViewById(R.id.driv_name);
            }
        }

        public int getItemCount() {
            return data.size();
        }

        public Object getItem(int i) {
            return i;
        }

        @NonNull
        @Override
        public Rideout_historyAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_rideout_history_adapter,
                    parent, false);

            return new Rideout_historyAdapter.EMSHolder(itemView);
        }

        public long getItemId(int i) {
            return i;
        }
    }
