package com.cadrac.hap.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.utils.Config;

public class Drivers_Documents_Upload extends AppCompatActivity implements View.OnClickListener {
    Button rc,rc2,insurance,pollution,aadhar,pan;
    TextView rcc,rccc,insurancetext,pollutiontext,aadhartext,pantext;
    TextView rcuploaded,licenseuploaded,insuranceuploaded,pollutionuploaded,aadharuploaded,panuploaded;
    ImageView load , succ, load1, succ1, load2, load3, load4, load5, succ2, succ3, succ4, succ5;
    int rc1=0, license1=0,insurance1=0,pollution1=0,aadhar1=0,pan1=0, pan2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers__documents__upload);

        load=findViewById( R.id.load );
        succ=findViewById( R.id.success );
        load1=findViewById( R.id.load1 );
        succ1=findViewById( R.id.success1 );

        load2=findViewById( R.id.load2 );
        succ2=findViewById( R.id.success2 );
        load3=findViewById( R.id.load3 );
        succ3=findViewById( R.id.success3 );
        load4=findViewById( R.id.load4 );
        succ4=findViewById( R.id.success4 );
        load5=findViewById( R.id.load5 );
        succ5=findViewById( R.id.success5 );
        rc=(Button)findViewById(R.id.rc);
        rc2=(Button)findViewById(R.id.license);
        insurance=(Button)findViewById(R.id.insurance);
        pollution=(Button)findViewById(R.id.pollution);
        aadhar=(Button)findViewById(R.id.aadhar);
        pan=(Button)findViewById(R.id.pan);
        rcc=(TextView) findViewById(R.id.rct);
        rccc=(TextView) findViewById(R.id.rct2);
        insurancetext=(TextView) findViewById(R.id.insurancetext);
        pollutiontext=(TextView) findViewById(R.id.pollutiontext);
        aadhartext=(TextView) findViewById(R.id.aadhaartext);
        pantext=(TextView) findViewById(R.id.pantext);
        rcuploaded = (TextView)findViewById(R.id.rcuploaded);
        licenseuploaded = (TextView)findViewById(R.id.licenseuploaded);
        panuploaded = (TextView)findViewById(R.id.panuploaded);
        aadharuploaded = (TextView)findViewById(R.id.aadharuploaded);
        insuranceuploaded = (TextView)findViewById(R.id.insuranceuploaded);
        pollutionuploaded = (TextView)findViewById(R.id.pollutionuploaded);

        rcuploaded.setVisibility(View.GONE);
        licenseuploaded.setVisibility(View.GONE);
        panuploaded.setVisibility(View.GONE);
        aadharuploaded.setVisibility(View.GONE);
        insuranceuploaded.setVisibility(View.GONE);
        pollutionuploaded.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Documents Upload");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
                startActivity(i);*/
               finish();

            }
        });


        rc.setOnClickListener(this);
        rc2.setOnClickListener(this);
        insurance.setOnClickListener(this);
        pollution.setOnClickListener(this);
        aadhar.setOnClickListener(this);
        pan.setOnClickListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("TAG", "onCreate: " + sharedPreferences.getString("Rc", ""));
        if (sharedPreferences.getString("lic", "").equalsIgnoreCase("1")) {


            rc2.setVisibility( View.GONE );
            load1.setVisibility( View.GONE );
            succ1.setVisibility( View.VISIBLE );


        }

        if (sharedPreferences.getString("lic", "").equalsIgnoreCase("123")) {

            rc2.setVisibility( View.GONE );
            load1.setVisibility( View.VISIBLE );


        }
        if (sharedPreferences.getString("rc", "").equalsIgnoreCase("1")) {

            rc.setVisibility( View.GONE );
            load.setVisibility( View.GONE );
            succ.setVisibility( View.VISIBLE );


        }if (sharedPreferences.getString("rc", "").equalsIgnoreCase("123")) {

            rc.setVisibility( View.GONE );
            load.setVisibility( View.VISIBLE );

        }

        if (sharedPreferences.getString("insurance", "").equalsIgnoreCase("1")) {

            insurance.setVisibility( View.GONE );
            load2.setVisibility( View.GONE );
            succ2.setVisibility( View.VISIBLE );
            insurance1 = 1;
        } if (sharedPreferences.getString("insurance", "").equalsIgnoreCase("123")) {

            insurance.setVisibility( View.GONE );
            load2.setVisibility( View.VISIBLE );
        }
        if (sharedPreferences.getString("pollution", "").equalsIgnoreCase("1")) {

            pollution.setVisibility( View.GONE );
            load3.setVisibility( View.GONE );
            succ3.setVisibility( View.VISIBLE );

        }
        if (sharedPreferences.getString("pollution", "").equalsIgnoreCase("123")) {
            pollution.setVisibility( View.GONE );
            load3.setVisibility( View.VISIBLE );
        }
        if (sharedPreferences.getString("aadhar", "").equalsIgnoreCase("1")) {
            //aadharuploaded.setVisibility(View.VISIBLE);
            aadhar.setVisibility( View.GONE );
            load4.setVisibility( View.GONE );
            succ4.setVisibility( View.VISIBLE );

        }
        if (sharedPreferences.getString("aadhar", "").equalsIgnoreCase("123")) {
            aadhar.setVisibility( View.GONE );
            load4.setVisibility( View.VISIBLE );
        }
         if (sharedPreferences.getString("pan", "").equalsIgnoreCase("123")) {
            pan.setVisibility( View.GONE );
            load5.setVisibility( View.VISIBLE );


        }if (sharedPreferences.getString("pan", "").equalsIgnoreCase("1")) {

            pan.setVisibility( View.GONE );
            load5.setVisibility( View.GONE );
            succ5.setVisibility( View.VISIBLE );


            String a=Config.getLoginusername(getApplicationContext());
            if ((a.startsWith("A"))||(a.startsWith("a")))
            {
                Intent i = new Intent(getApplicationContext(),Home_agent.class);
                startActivity(i);

            }
            else if ((a.startsWith("S"))||(a.startsWith("s")))
            {
                Intent i = new Intent(getApplicationContext(),home_supervisor.class);
                startActivity(i);

            }
            else if ((a.startsWith("D"))||(a.startsWith("d"))){
                Intent i = new Intent(getApplicationContext(),Home_driver.class);
                startActivity(i);
            }
            else
                finish();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rc)
        {
            rcupload();
        }else  if (v.getId()==R.id.license)
        {
            licenseupload();
        }
        else  if (v.getId()==R.id.insurance)
        {
            insuranceupload();
        }
        else  if (v.getId()==R.id.pollution)
        {
            pollutionupload();
        }
        else  if (v.getId()==R.id.aadhar)
        {
            aadharupload();
        }
        else  if (v.getId()==R.id.pan)
        {
            panupload();
        }

    }

    private void pollutionupload() {
        String b=pollutiontext.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),b);
        startActivity(i);
    }
    private void aadharupload() {
        String b=aadhartext.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),b);
        startActivity(i);
    }
    private void panupload() {
        String b=pantext.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),b);
        startActivity(i);
    }

    private void insuranceupload() {
        String b=insurancetext.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),b);
        startActivity(i);
    }

    public void rcupload()
    {

        String a=rcc.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),a);
        startActivity(i);

    }
    public void licenseupload()
    {

        String b=rccc.getText().toString();
        Intent i=new Intent(getApplicationContext(),Upload_Image_Driver_Registration.class);
        Config.savedriver_registrationvalue(getApplicationContext(),b);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(getApplicationContext(),new_driver_registration.class);
        startActivity(i);
    }
}
