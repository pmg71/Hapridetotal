package  com.cadrac.hap.Adapters;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.activites.fragment_agent_drivers_availabilty;
import com.cadrac.hap.responses.Drivers_Availabilty_Response;

import java.util.ArrayList;

public class Driver_avaliabilty_adapter extends RecyclerView.Adapter<Driver_avaliabilty_adapter.EMSHolder> {


    int FADE_DURATION = 100;
    String driver_number;
    ArrayList<Drivers_Availabilty_Response.data> data;
    Context context;
    fragment_agent_drivers_availabilty driverFragment;
    public Driver_avaliabilty_adapter(ArrayList<Drivers_Availabilty_Response.data> data, fragment_agent_drivers_availabilty driverFragment, Context context)
    {
        this.context=context;
        this.data = data;
        this.driverFragment=driverFragment;
    }

    public Driver_avaliabilty_adapter.EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_avaliabilty_adapter,
                parent, false);

        return new Driver_avaliabilty_adapter.EMSHolder(itemView);
    }
    public void onBindViewHolder(final Driver_avaliabilty_adapter.EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

        holder.driver_name.setText(data.get(position).getDriver_name());
        Log.d("TAG", "onBindViewHolder: "+data.get(position).getVechile_no());
        holder.vechno.setText(data.get(position).getVechile_no());
        String vehicleType= data.get(position).getVehicleType();

        if(data.get(position).getVehicleType().equalsIgnoreCase("car")){
            holder.car.setVisibility(View.VISIBLE);
            holder.auto.setVisibility(View.GONE);

        }else if(data.get(position).getVehicleType().equalsIgnoreCase("auto")){
            holder.car.setVisibility(View.GONE);
            holder.auto.setVisibility(View.VISIBLE);

        }

        //   holder.veh_type.setText(data.get(position).getVehicleType());
//             holder.drivermob.setText(data.get(position).getDriver_no());


        holder.drivermob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              String emg = driver_number;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + data.get(position).getDriver_no()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

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


        TextView driver_name,vechno,drivermob,veh_type;
        LinearLayout linearLayout;
        ImageView car,auto;

        public EMSHolder(View v)
        {
            super(v);

            driver_name=(TextView)v.findViewById(R.id.driver_name);
            vechno=(TextView)v.findViewById(R.id.Vech_no);
            drivermob=(TextView) v.findViewById(R.id.driver_no);
            car=(ImageView)v.findViewById(R.id.car);
            auto=(ImageView)v.findViewById(R.id.auto);


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