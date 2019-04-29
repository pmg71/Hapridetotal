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
import com.cadrac.hap.Adapters.ViewPagerAdapter_supervisior;
import com.cadrac.hap.MainActivity;
import com.cadrac.hap.R;
import com.cadrac.hap.utils.Config;


public class home_supervisor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
ViewPager viewPager;
TextView supervisior_id;
TabLayout tabLayout;
ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_supervisor);
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
        supervisior_id=(TextView)header.findViewById(R.id.supervisior_id);
        supervisior_id.setText(Config.getLoginusername(getApplicationContext()));
        navigationView.setNavigationItemSelectedListener(this);

        //      tabLayout = (TabLayout) findViewById(R.id.tabs1);
        //           tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);

        Log.d( "TAG", "onCreate: 1234567890" );


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        imageButton=findViewById(R.id.home);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),home_supervisor.class);
                startActivity(i);
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

            Intent intent=new Intent( this,home_supervisor.class );
            startActivity( intent );
        }
        else if (id == R.id.queries)

        {
            Intent intent=new Intent( this,supervisor_queries.class );
            startActivity( intent );


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

            Intent i=new Intent(getApplicationContext(),supervisor_howtouseapp.class);
            startActivity(i);
        }
        else if (id == R.id.aboutus)
        {

            Intent i=new Intent(getApplicationContext(),aboutus_company.class);
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
        ViewPagerAdapter_supervisior adapter = new ViewPagerAdapter_supervisior(getSupportFragmentManager());

        adapter.addFragment( new fragment_supervisor_status(),"Status" );

        adapter.addFragment( new fragment_supervisor_collected(),"Collected" );
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
                Intent intent=new Intent(getApplicationContext(),home_supervisor.class);
                startActivity(intent);

        }
        return true;
    }


}




