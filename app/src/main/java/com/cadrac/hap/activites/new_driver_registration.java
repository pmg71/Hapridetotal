package com.cadrac.hap.activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.R;

import com.cadrac.hap.responses.Driver_Registration;
import com.cadrac.hap.responses.HapResponse;
import com.cadrac.hap.supporter_classes.Connection_Detector;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class new_driver_registration extends AppCompatActivity {

    private static final String TAG = "1233";
    EditText firstname,lastname,mobilenumber,vehiclenumber,license,pollutioncheck,insurance,voterid,createdby;
    Button  pollutionbutton,insurancebutton,next;
    Spinner noofseats,vehicletype;
    String mfirstname,mlastname,mmobilenumber,mvehiclenumber,mlicense,mpollutioncheck,mpollutioncheckexpiry,minsurance,minsuranceexpiry,mvoterid,mcreatedby,mnoofseats,mvehicletype;
    ProgressDialog progressDialog = null;
    Connection_Detector connection_detector;
    Driver_Registration driverResponse;
    TextView pollutioncheckexpiry,insuranceexpiry;
    int mYear,mMonth,mDay;
    ArrayList<String> veh_type, seats_count;

    HapResponse hapResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_driver_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Driver Registration");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submiting Details...");
        progressDialog.setCancelable(false);

        firstname=(EditText)findViewById(R.id.firstname);
        connection_detector = new Connection_Detector( this);
        lastname=(EditText)findViewById(R.id.lastname);
        mobilenumber=(EditText)findViewById(R.id.mobilenumber);
        vehicletype=(Spinner) findViewById(R.id.vehicletype);
        noofseats=(Spinner) findViewById(R.id.noofseats);
        vehiclenumber=(EditText)findViewById(R.id.vehiclenumber);
//        rcnumber=(EditText)findViewById(R.id.rcnumber);
        license=(EditText)findViewById(R.id.license);
        pollutioncheck=(EditText)findViewById(R.id.pollutioncheck);
        pollutioncheckexpiry=(TextView) findViewById(R.id.pollutioncheckexpiry);
        insurance=(EditText)findViewById(R.id.insurance);
        insuranceexpiry=(TextView) findViewById(R.id.insuranceexpiry);
//        voterid=(EditText) findViewById(R.id.voterid);
        createdby=(EditText) findViewById(R.id.createdby);
        pollutionbutton=(Button)findViewById(R.id.pollutioncalendar);
        insurancebutton=(Button)findViewById(R.id.insurancecalendar);
        next=(Button)findViewById(R.id.next);


        dispVehType();

        dispSeatsCount();

        pollutionbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(new_driver_registration.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "yyyy/MM/dd"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        pollutioncheckexpiry.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        insurancebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(new_driver_registration.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "yyyy/MM/dd"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        insuranceexpiry.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

//        final ArrayList<String> seats1=new ArrayList<String>();
//        seats1.add("SEATS");
//        seats1.add("1");
//        seats1.add("2");
//        seats1.add("3");
//        seats1.add("4");
//        seats1.add("5");
//        seats1.add("6");
//        seats1.add("7");
//        noofseats.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, seats1));
//
//        final ArrayList<String> type=new ArrayList<String>();
//        type.add("Vehicle Type");
//        type.add("Car");
//        type.add("Auto");
//
//        vehicletype.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, type));

        noofseats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mnoofseats = noofseats.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: " + mnoofseats);



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        vehicletype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mvehicletype = vehicletype.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: " + mnoofseats);




            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick:next::::::::::");
                mfirstname=firstname.getText().toString();
                mlastname=lastname.getText().toString();
                mmobilenumber=mobilenumber.getText().toString();
                mvehiclenumber=vehiclenumber.getText().toString();
//                mrcnumber=rcnumber.getText().toString();
                mlicense=license.getText().toString();
                mpollutioncheck=pollutioncheck.getText().toString();
                minsurance=insurance.getText().toString();
                mpollutioncheckexpiry=pollutioncheckexpiry.getText().toString();
                minsuranceexpiry=insuranceexpiry.getText().toString();
//                mvoterid=voterid.getText().toString();
                mnoofseats=noofseats.getSelectedItem().toString();
                mvehicletype=vehicletype.getSelectedItem().toString();
                mcreatedby=createdby.getText().toString();
                System.out.println("hiiiiiiiiii");
                upload_details();


                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor.putString("rc","");
                editor.commit();

                SharedPreferences sharedPreferences1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor1=sharedPreferences1.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor1.putString("lic","");
                editor1.commit();

                SharedPreferences sharedPreferences2=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor2=sharedPreferences2.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor2.putString("aadhar","");
                editor2.commit();

                SharedPreferences sharedPreferences3=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor3=sharedPreferences3.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor3.putString("pan","");
                editor3.commit();

                SharedPreferences sharedPreferences4=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor4=sharedPreferences4.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor4.putString("pollution","");
                editor4.commit();

                SharedPreferences sharedPreferences5=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor5=sharedPreferences5.edit();
                // Log.d("TAG", "onPostExecute:123 "+aa);
                editor5.putString("insurance","");
                editor5.commit();


                //checking validations

                if (mfirstname.isEmpty()) {
                    firstname.setError("Please enter your First name");
                }
                else if (mlastname.isEmpty()) {
                    lastname.setError("Please enter your Last name");
                }

                else if (mmobilenumber.length() != 10) {
                    mobilenumber.setError("please enter valid phone number");
                }

                else if (mvehiclenumber.isEmpty()) {
                    vehiclenumber.setError("please enter Vehicle type");
                }

                else if (noofseats.getSelectedItem().toString().equalsIgnoreCase("SEATS")) {
                    Toast.makeText(getApplicationContext(),"please select number of seats",Toast.LENGTH_LONG).show();

                }
                else if (vehicletype.getSelectedItem().toString().equalsIgnoreCase("Vehicle Type")) {
                    Toast.makeText(getApplicationContext(),"please select Your vehicle type",Toast.LENGTH_LONG).show();

                }

//                else if (mrcnumber.isEmpty()) {
//                    rcnumber.setError("please enter RC number");
//                }
                else if (mpollutioncheckexpiry.isEmpty()) {
                    pollutioncheckexpiry.setError("please enter Pollution Check Expiry Date");
                }
                else if (mlicense.isEmpty()) {
                    license.setError("please enter License number");
                }

                else if (minsuranceexpiry.isEmpty()) {
                    insuranceexpiry.setError("please enter Insurance Expiry Date");
                }

                else if (mpollutioncheck.isEmpty()) {
                    pollutioncheck.setError("please enter pollution number");
                }
                else if (minsurance.isEmpty()) {
                    insurance.setError("please enter Insurance nymber");
                }


                else if (mcreatedby.isEmpty())
                {
                    createdby.setError("please enter Who Created This Driver");
                }




                else if(mmobilenumber.startsWith("9")||mmobilenumber.startsWith("8")||mmobilenumber.startsWith("7")||mmobilenumber.startsWith("6")){
                    progressDialog.show();
                    upload_details();
                }
                else {
                    mobilenumber.setError("please enter valid number");

                }
            }
        });
    }

    private void upload_details() {

if (connection_detector.isConnectingToInternet()) {


    SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString("Rc","0");
    editor.putString("License","0");
    editor.putString("Insurance","0");
    editor.putString("Pollution","0");
    editor.putString("Aadhar","0");
    editor.putString("Pan","0");
    editor.apply();


    System.out.println("hi5");

    OkHttpClient okHttpClient = new OkHttpClient();
    RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
            client(okHttpClient).
            addConverterFactory(GsonConverterFactory.create()).
            build();
    System.out.println("hi6");
    API api = RestClient.client.create(API.class);
    Log.d("TAG", "upload_details: ");
    Config.savedriver_mobilenumber(getApplicationContext(), mmobilenumber);
    Call<Driver_Registration> call = api.driverregistration(mfirstname, mlastname, mmobilenumber, mvehicletype, mnoofseats,
            mvehiclenumber, mlicense, mpollutioncheck, mpollutioncheckexpiry, minsurance, minsuranceexpiry, mcreatedby);

    System.out.println("hi7");

    call.enqueue(new Callback<Driver_Registration>() {
        @Override
        public void onResponse(Call<Driver_Registration> call, Response<Driver_Registration> response) {
            System.out.println("hi8");
            driverResponse = response.body();
            Log.d("TAG", "onResponse:dri" + driverResponse);

            progressDialog.dismiss();

            Intent i = new Intent(getApplicationContext(), Drivers_Documents_Upload.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "successfully uploaded", Toast.LENGTH_LONG).show();


        }

        @Override
        public void onFailure(Call<Driver_Registration> call, Throwable t) {

           // Toast.makeText(getApplicationContext(), " fail upload", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

        }
    });
}else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }


    public void dispVehType(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d(TAG, "onResponse:12 ");
            Call<HapResponse> call = api.vehicleType();
            Log.d(TAG, "onResponse:123 ");
            call.enqueue(new Callback<HapResponse>() {

                @Override
                public void onResponse(Call<HapResponse> call,
                                       Response<HapResponse> response) {
                    Log.d(TAG, "onResponse:1234 ");
                    veh_type = new ArrayList<String>();
                    hapResponse = new HapResponse();
                    Log.d(TAG, "onResponse:type "+hapResponse.getStatus());
                    hapResponse = response.body();

                    if (hapResponse.getStatus().equalsIgnoreCase("True")) {
                        Log.d("TAG", "onResponse: 2");
                        try {


                            for (int i = 0; i < hapResponse.getData().length; i++) {
                                veh_type.add(hapResponse.getData()[i].getVeh_names().toString());
                                Log.d(TAG, "onResponse: type"+hapResponse.getData()[i].getVeh_names().toString());
                            }
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        setVehAdapter(veh_type);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<HapResponse> call, Throwable t) {

                }


            });

        }catch (Exception e) {
            System.out.print("Exception e" + e);

        }
    }
    public void setVehAdapter(ArrayList<String> rolelist) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Vehicle Type");

        fun.addAll(rolelist);

        vehicletype.setSelection(0);
        vehicletype.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }


    public void dispSeatsCount(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d(TAG, "onResponse:12 ");
            Call<HapResponse> call = api.seatsCount();
            Log.d(TAG, "onResponse:123 ");
            call.enqueue(new Callback<HapResponse>() {

                @Override
                public void onResponse(Call<HapResponse> call,
                                       Response<HapResponse> response) {
                    Log.d(TAG, "onResponse:1234 ");
                    seats_count = new ArrayList<String>();
                    hapResponse = new HapResponse();
                    Log.d(TAG, "onResponse:type "+hapResponse.getStatus());
                    hapResponse = response.body();

                    if (hapResponse.getStatus().equalsIgnoreCase("True")) {
                        Log.d("TAG", "onResponse: 2");
                        try {


                            for (int i = 0; i < hapResponse.getData().length; i++) {
                                seats_count.add(hapResponse.getData()[i].getCount().toString());
                                Log.d(TAG, "onResponse: type"+hapResponse.getData()[i].getCount().toString());
                            }
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        setSeatsAdapter(seats_count);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<HapResponse> call, Throwable t) {

                }


            });

        }catch (Exception e) {
            System.out.print("Exception e" + e);

        }
    }
    public void setSeatsAdapter(ArrayList<String> rolelist) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Seats");

        fun.addAll(rolelist);

        noofseats.setSelection(0);
        noofseats.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }
}