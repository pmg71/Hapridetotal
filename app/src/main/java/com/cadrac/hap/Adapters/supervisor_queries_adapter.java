package com.cadrac.hap.Adapters;



        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.ScaleAnimation;
        import android.widget.Button;
        import android.widget.TextView;

        import com.cadrac.hap.R;
        import com.cadrac.hap.activites.supervisor_queries;
        import com.cadrac.hap.responses.Agent_Status_For_RideOut_Response;

        import java.util.ArrayList;

public class supervisor_queries_adapter extends RecyclerView.Adapter<supervisor_queries_adapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<Agent_Status_For_RideOut_Response.data> data;
    Context context;
    supervisor_queries mainActivity;

    public supervisor_queries_adapter(ArrayList<Agent_Status_For_RideOut_Response.data> data, supervisor_queries mainactivity, Context context)
    {
        this.context=context;
        this.data = data;
        this.mainActivity=mainactivity;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_supervisor_queries_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

        holder.msg.setText(data.get(position).getDescription());
        holder.agentid.setText(data.get(position).getS_id());
        holder.mobile.setText(data.get(position).getMobileNumber());


        final String call1 = holder.mobile.getText().toString();

        holder.bcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + call1));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {



                    return;
                }
                context.startActivity(callIntent);
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

        TextView msg, mobile, agentid;
        Button bcall;

        public EMSHolder(View v)
        {
            super(v);

            msg=(TextView) v.findViewById(R.id.msg);
            mobile=(TextView) v.findViewById(R.id.mobile);
            bcall=(Button) v.findViewById(R.id.callagent);
            agentid=(TextView) v.findViewById(R.id.agentid);


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

