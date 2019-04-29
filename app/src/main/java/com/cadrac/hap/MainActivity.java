package com.cadrac.hap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cadrac.hap.activites.agent_login;
import com.cadrac.hap.activites.driver_login;
import com.cadrac.hap.activites.supervisior_login;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton agent,driver,supervisior;
    String utype="";

    private static final int MULTIPLE_PERMISSIONS = 7;
    String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        agent=(RadioButton)findViewById(R.id.agents);
        driver=(RadioButton)findViewById(R.id.driver);
        supervisior=(RadioButton)findViewById(R.id.supervisior);

        agent.setOnClickListener( this );
        driver.setOnClickListener( this );
        supervisior.setOnClickListener( this );
//        EnableRuntimePermissionToAccessCall();
        checkPermissions();



    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.agents){
            utype="agent";

        }
        else if(v.getId()==R.id.driver){
            utype="driver";
        }
        else if(v.getId()==R.id.supervisior){
            utype="supervisior";
        }
        else  {

            Toast.makeText(MainActivity.this,
                    "please select anyone",
                    Toast.LENGTH_LONG).show();
        }

        if (utype.equalsIgnoreCase("Agent")) {
            Intent i=new Intent(getApplicationContext(),agent_login.class);
            startActivity(i);


        }
        else if (utype.equalsIgnoreCase("Driver")) {
            Intent i=new Intent(getApplicationContext(),driver_login.class);
            startActivity(i);


        }
        else if (utype.equalsIgnoreCase("Supervisior")) {
            Intent i=new Intent(getApplicationContext(),supervisior_login.class);
            startActivity(i);
            }


    }

    /*public void EnableRuntimePermissionToAccessCall () {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
            // Printing toast message after enabling runtime permission.
            // Toast.makeText(Passenger_login.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, RequestPermissionCode);
        }
    }*/
    /*@Override
    public void onRequestPermissionsResult ( int RC, String per[],int[] PResult){
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(Passenger_ride_status.this, "Permission Granted!!!", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(Passenger_ride_status.this, "Permission Denied!!!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }*/

    public void onBackPressed() {

        Intent intent=new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

// all permissions are granted.
                } else {

//permissions missing

                }
                return;
            }
        }
    }

}