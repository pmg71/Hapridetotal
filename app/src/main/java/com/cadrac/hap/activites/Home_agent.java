package com.cadrac.hap.activites;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap.Adapters.ViewPagerAdapter;
import com.cadrac.hap.MainActivity;
import com.cadrac.hap.R;
import com.cadrac.hap.responses.request_response;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;


public class Home_agent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
ViewPager viewPager;
TextView agent_id, count;
TabLayout tabLayout;
ImageButton imageButton;
static String c;
request_response getResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_agent);


        count = (TextView)findViewById(R.id.count);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        toolbar.setTitleTextColor(Color.WHITE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        agent_id=(TextView)header.findViewById(R.id.agent_id);
        agent_id.setText(Config.getLoginusername(getApplicationContext()));
        navigationView.setNavigationItemSelectedListener(this);

        getMessage();


  //      tabLayout = (TabLayout) findViewById(R.id.tabs1);
    //           tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);

        Log.d( "TAG", "onCreate: 1234567890" );

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        imageButton=findViewById(R.id.home);
        viewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Home_agent.class);
                startActivity(i);
            }
        });


//
//        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        c = sharedPreferences.getString("count","");
//        Log.d("TAG", "onClick:count "+c);
//        count.setText(c);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home)
        {
            Intent i=new Intent(getApplicationContext(),Home_agent.class);
            startActivity(i);


        }
        else if (id == R.id.status)

        {
            Intent i=new Intent(getApplicationContext(),agent_rideout_status.class);
            startActivity(i);



        }

        else if (id == R.id.assignRides)

        {
            Intent i=new Intent(getApplicationContext(), agent_assignedRides.class);
            startActivity(i);



        }
        else if (id == R.id.DriverRegistration)
        {
            Intent intent=new Intent( getApplicationContext(),new_driver_registration.class );
            startActivity( intent );


        }

        else if (id == R.id.rideinhistory)
        {
            Intent i=new Intent(getApplicationContext(),agent_ridein_history.class);
            startActivity(i);


        }
        else if (id == R.id.rideouthistory)
        {
            Intent i=new Intent(getApplicationContext(),agent_rideout_history.class);
            startActivity(i);

        }
        else if (id == R.id.settlement)
        {
            Intent i=new Intent(getApplicationContext(),agent_to_supervisor_settlement.class);
            startActivity(i);

        }

        else if (id == R.id.contactsupervisor)
        {
            Intent i=new Intent(getApplicationContext(),agent_contactsupervisor.class);
            startActivity(i);
        }
        else if (id == R.id.video)
        {
            Intent i=new Intent(getApplicationContext(),Users_Video_Capture.class);
            startActivity(i);

        }
        else if (id == R.id.contactus)
        {
            Intent i=new Intent(getApplicationContext(),contactus_company.class);
            startActivity(i);

        }
        else if (id == R.id.Howtouseapp)
        {

            Intent i=new Intent(getApplicationContext(),agent_howtouseapp.class);
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

    private void setupViewPager(ViewPager viewPager)
    {

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new fragment_agent_drivers_availabilty(),"Drivers" );

            adapter.addFragment( new fragment_agent_ridein(),"Ride In" );

            adapter.addFragment( new fragment_agent_rideout(),"Ride Out" );

            adapter.addFragment( new fragment_agent_req(),"Requests " );

            viewPager.setAdapter( adapter );


    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(this, "Press Back again to Exit from App.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
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
                Intent intent=new Intent(getApplicationContext(),Home_agent.class);
                startActivity(intent);
        }
        return true;
    }


    public void getMessage(){

        try {
            Log.d("TAG", "onResponse:221");
            OkHttpClient okHttpClient = new OkHttpClient();
            Log.d("TAG", "onResponse:223");
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            Log.d("TAG", "onResponse:224");
            API api = RestClient.client.create(API.class);
            String un= Config.getLoginusername(getApplicationContext());
            Call<request_response> call = api.request(un);
            call.enqueue(new Callback<request_response>() {
                @Override
                public void onResponse(Call<request_response> call,
                                       Response<request_response> response) {
                    Log.d("TAG", "onResponse:22");
                    getResponse = new request_response();
                    getResponse = response.body();
                    //  Log.d("TAG", "onResponse:22true"+getResponse.getStatus());

                    try{
                        if (getResponse.getStatus().equalsIgnoreCase("True")){
                            Log.d(TAG, "onResponse: status1"+getResponse.getStatus());
                            c = getResponse.getData1()[0].getCount();
                            Log.d(TAG, "onResponse:kkk1"+c);
                           count.setText(getResponse.getData1()[0].getCount());
                        }

                        else {
                            Log.d("TAG", "onResponse:22true"+getResponse.getStatus());
                            Toast.makeText(getApplicationContext(),"nodata", Toast.LENGTH_LONG).show();
                        }


                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<request_response> call, Throwable t) {


                }

            });

        }catch (Exception e) {
            // System.out.print("Exception e" + e);

            Toast.makeText(getApplicationContext(),"nodata", Toast.LENGTH_LONG).show();

        }

    }





}



