package com.cadrac.hap.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.MainActivity;
import com.cadrac.hap.R;
import com.cadrac.hap.activites.aboutus_company;
import com.cadrac.hap.activites.contactus_company;
import com.cadrac.hap.activites.driver_howtouseapp;
import com.cadrac.hap.activites.new_driver_registration;
import com.cadrac.hap.responses.Driver_StatusResponse;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_driver extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SeekBar status;
    Driver_StatusResponse statusResponse;
    Integer prog;
    Drawable home,available,driving;
    TextView driver_id;

    //mahesh
    TextView ride,ride_id;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);

//For navigation and drawer layout
// santosh


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Status");
        toolbar.setTitleTextColor(Color.WHITE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        driver_id=(TextView)header.findViewById(R.id.driver_id);
        driver_id.setText(Config.getLoginusername(getApplicationContext()));
        navigationView.setNavigationItemSelectedListener(this);





        home=getResources().getDrawable(R.drawable.homestatus);
        available=getResources().getDrawable(R.drawable.driverstatus);
        driving=getResources().getDrawable(R.drawable.drivingstatus);




        //seekbar
        status = findViewById(R.id.status);

        //addded by mahesh
        ride=findViewById(R.id.ride);
        ride_id=findViewById(R.id.ride_id);
        linearLayout=findViewById(R.id.ll);

// object initialization for response
        statusResponse =new Driver_StatusResponse();
//to fetch the status from database
        status("0","get_status");


//developer credicts:sriram
//seekbar listner

        status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
// status is divided into 3 units
                if (progress ==2){

//disabling driver to set the status to driving

                    status.setProgress(0);

                }
                else
                {
                    if(progress==0){

                        status.setProgress(0);
                        status.setThumb(home);

                    }
                    if(progress==1){
                        status.setThumb(available);
                    }


//to update the status on change

                    status( Integer.toString(progress),"update");

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });




    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home)
        {

            Intent i=new Intent(getApplicationContext(),Home_driver.class);
            startActivity(i);
        }
        else if (id == R.id.video)
        {
            Intent i=new Intent(getApplicationContext(),Users_Video_Capture.class);
            startActivity(i);

        }
        else if (id == R.id.DriverRegistration)

        {
            Intent intent=new Intent( getApplicationContext(),new_driver_registration.class );
            startActivity( intent );


        }
        else if (id == R.id.history)

        {
            Intent intent=new Intent( getApplicationContext(),Driver_History.class );
            startActivity( intent );


        }
        else if (id == R.id.contactus)
        {
            Intent i=new Intent(getApplicationContext(),contactus_company.class);
            startActivity(i);

        }
        else if (id == R.id.Howtouseapp)
        {
            Intent i=new Intent(getApplicationContext(),driver_howtouseapp.class);
            startActivity(i);

        }
        else if (id == R.id.aboutus)
        {
            Intent i=new Intent(getApplicationContext(),aboutus_company.class);
            startActivity(i);

        }
        else if (id == R.id.scanning)
        {
            Intent i=new Intent(getApplicationContext(),My_code.class);
            startActivity(i);

        }
        else if (id == R.id.logout)
        {

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Do you want to logout..!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Config.saveLoginStatus(getApplicationContext(),"0");
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            AlertDialog alertDialog=builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }















    //developer credicts:sriram
//To get the status of the driver and to update also

    public  Void  status(String driver_status,String action) {
        try{

//okhttp clinet object  with json-gson conversion
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();


            API api = RestClient.client.create(API.class);

//api calling
            Call<Driver_StatusResponse> call = api.driverStatus(Config.getLoginusername(getApplicationContext()),driver_status,action);
//if the api is connected suessfully

            call.enqueue(new Callback<Driver_StatusResponse>() {
                @Override
                public void onResponse(Call<Driver_StatusResponse> call,
                                       Response<Driver_StatusResponse> response) {

                    statusResponse=response.body();
                    Log.d("TAG", "onResponse: "+statusResponse);

                    if(statusResponse.getStatus().equalsIgnoreCase("true"))
                    {
//setting the seekbar with respect to response
                        String dstatus = statusResponse.getDriver_status();
                        String ride_id1=statusResponse.getRide_id();
                        Log.d("TAG", "onResponse: " +dstatus);


                        if (dstatus.contains("0")) {

                            status.setProgress(0);
                            linearLayout.setVisibility(View.GONE);
                            status.setThumb(home);


                        } else if (dstatus.contains("1")) {
                            linearLayout.setVisibility(View.GONE);
                            status.setProgress(1);
                            status.setThumb(available);
                        } else {
                            status.setProgress(2);
                            linearLayout.setVisibility(View.VISIBLE);
                            status.setThumb(driving);
                            ride_id.setText(ride_id1);
                            status.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return true;
                                }
                            });
                        }
                        ride_id.setText(ride_id1);



                    }

                    else{
                        Toast.makeText(getApplicationContext(),"sorry",Toast.LENGTH_SHORT);
                    }


                    prog=status.getProgress();

                }
                //if the api does not  connect suessfully
                @Override
                public void onFailure(Call<Driver_StatusResponse> call, Throwable t) {

                    Log.d("TAG", "onFailure: "+t);

                }

            });

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                Intent intent=new Intent(getApplicationContext(),Home_driver.class);
                startActivity(intent);

        }
        return true;
    }
}