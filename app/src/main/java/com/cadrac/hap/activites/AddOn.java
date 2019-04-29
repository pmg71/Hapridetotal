package com.cadrac.hap.activites;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.R;
import com.cadrac.hap.responses.AddOnRideInResponse;
import com.cadrac.hap.responses.AgentIdResponse;
import com.cadrac.hap.responses.Drivers_Availabilty_Response;
import com.cadrac.hap.responses.Pass_Driver_Availability_List;
import com.cadrac.hap.responses.RideInCostDetailsResponse;
import com.cadrac.hap.responses.RideInDriverDetailsResponse;
import com.cadrac.hap.responses.Ridein_Response;
import com.cadrac.hap.responses.SeatsResponse;
import com.cadrac.hap.responses.request_response;
import com.cadrac.hap.responses.statusResponse;
import com.cadrac.hap.supporter_classes.Connection_Detector;
import com.cadrac.hap.utils.Config;
import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import java.security.SecureRandom;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class AddOn extends AppCompatActivity {
    TextView lab1, lab2,lab3,lab4,lab5;
    EditText from, to, veh_no, veh_type, driver_num, total_fare, pass_count, total_fare2;
    Spinner driverName, no_of_pass, agent_id;
    Button submit;

    String d_name, d_fName, d_lName, carcost, autocost, no_of_seats, veh_no1, veh_type1, driver_no1, l, agent_id1, source, driver_id, res_seats_count, c;
    String veh_type_pass, agents_id="", supervisor_id, req_id, status1;
    int k,i,fare, u;
String a_id, go_id;
    ArrayList<String> drivername, list, agentid;
    String a[] = new String[10];

    Connection_Detector connection_detector;
    RideInCostDetailsResponse getindetailsresponse;
    Pass_Driver_Availability_List driverResponse;
    RideInDriverDetailsResponse driverDetailsResponse;
    AgentIdResponse agentIdResponse;
    AddOnRideInResponse addonResponse;
    statusResponse status_response;
    request_response request_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on);

        connection_detector = new Connection_Detector(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add-on");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a= Config.getLoginusername(getApplicationContext());
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

        source = Config.getLoginfrom(getApplicationContext());

        lab1=findViewById(R.id.costlab1);
        lab2=findViewById(R.id.costlab2);
        lab3=findViewById(R.id.costlab3);
        lab4=findViewById(R.id.costlab4);
        lab5=findViewById(R.id.costlab5);

        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        veh_no=findViewById(R.id.vech_no);
        veh_type = findViewById(R.id.vehicle_type);
        driver_num=findViewById(R.id.driver_no);
        total_fare = findViewById(R.id.total_fare);
        pass_count = findViewById(R.id.passengers);
        total_fare2 = findViewById(R.id.total_fare2);

        driverName = findViewById(R.id.driver_name);
        no_of_pass = findViewById(R.id.no_of_passengers);
        agent_id = findViewById(R.id.agent_id);

       submit = findViewById(R.id.assign);

        Intent intent = getIntent();
        String fare_pass = intent.getStringExtra("fare");
        String seats_pass = intent.getStringExtra("seats");
        String dest_pass = intent.getStringExtra("destination");
        veh_type_pass = intent.getStringExtra("veh_type");
        Log.d(TAG, "onCreate: vehType"+veh_type_pass);
        req_id = intent.getStringExtra("id");
            from.setText(source);
            to.setText(dest_pass);
            pass_count.setText(seats_pass);
            total_fare2.setText(fare_pass);


// if source and destination is not equals to null then display cost details
        if(!(from.getText().toString().equalsIgnoreCase("") && to.getText().toString().equalsIgnoreCase(""))) {

            getInCostdetails();// fetch cost details from db

            DriverSpinner(); //fetch available drivers list from db

            AgentIdSpinner();  //fetch GetOut Agent Ids from db


        }

        driverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //available drivers list
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                d_name= driverName.getSelectedItem().toString();
                String[] r=d_name.split(" ");
                d_fName=r[0];
                d_lName=r[1];
                Log.d("TAG", "driver name: "+r[0]);
                Log.d("TAG", "driver name: "+r[1]);
              //  Log.d("TAG", "onItemSelected: "+destination);


                if(d_name.equalsIgnoreCase("Select Driver")){
                    //Toast.makeText(getContext(), "select driver name", Toast.LENGTH_SHORT).show();

                    veh_no.getText().clear();
                    driverName.setSelection(0);
                    driver_num.getText().clear();
                    veh_type.getText().clear();


                }
                else
                {
                   getInDriverdetails();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        agent_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //Agent Id SPinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                agents_id = agent_id.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+agents_id);
                go_id = a[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIn();
            }
        });


    }


    public void getInCostdetails(){
        if (connection_detector.isConnectingToInternet()) {
            try {


                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<RideInCostDetailsResponse> call = api.rideInCostDetails(from.getText().toString(),
                        to.getText().toString());
                call.enqueue(new Callback<RideInCostDetailsResponse>() {
                    @Override
                    public void onResponse(Call<RideInCostDetailsResponse> call,
                                           Response<RideInCostDetailsResponse> response) {

                        Log.d(TAG, "onItemSelected: vineetha");
                        getindetailsresponse = new RideInCostDetailsResponse();
                        getindetailsresponse = response.body();
                        Log.d("TAG", "onResponse:1234567890 " + getindetailsresponse);
                        try {
                            lab1.setVisibility(View.VISIBLE);
                            lab2.setVisibility(View.VISIBLE);
                            lab3.setVisibility(View.VISIBLE);
                            lab4.setVisibility(View.VISIBLE);
                            lab5.setVisibility(View.VISIBLE);
                            lab2.setText("Rs." + getindetailsresponse.getData()[0].getCabcost());
                            carcost = getindetailsresponse.getData()[0].getCabcost();
                            Log.d("TAG", "getinres: " + carcost);
                            autocost = getindetailsresponse.getData()[0].getAutocost();
                            Log.d("TAG", "getinres11: " + autocost);
                            lab4.setText("Rs." + getindetailsresponse.getData()[0].getAutocost());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print(e);
                        }

                    }

                    @Override
                    public void onFailure(Call<RideInCostDetailsResponse> call, Throwable t) {
                        Log.d(TAG, "onItemSelected: vineetha failed" + t);
                    }

                });
            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }

    private void DriverSpinner()                     //Driver list spinner
    {
        if (connection_detector.isConnectingToInternet()) {

            Log.d("TAG", "sourceSpinner: ");

            try {

                driverResponse = new Pass_Driver_Availability_List();
                Log.d("TAG", "sourceSpinner:1 ");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<Pass_Driver_Availability_List> call = api.driver_availability(from.getText().toString(),veh_type_pass);
                call.enqueue(new Callback<Pass_Driver_Availability_List>() {
                    @Override
                    public void onResponse(Call<Pass_Driver_Availability_List> call,
                                           Response<Pass_Driver_Availability_List> response) {

                        driverResponse = response.body();
                        drivername = new ArrayList<String>();
                        Log.d("TAG", "onResponse: 1");
                        if (driverResponse.getStatus().equalsIgnoreCase("sucess")) {
                            Log.d("TAG", "onResponse: 2");
                            try {

                                for (int i = 0; i < driverResponse.getData().length; i++) {
                                    drivername.add(driverResponse.getData()[i].getDriver_name().toString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setDriverAdapter(drivername);
                        } else {
                            Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Pass_Driver_Availability_List> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t);
                    }

                });
            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setDriverAdapter(ArrayList<String> drivername) {

        ArrayList<String> fun1;
        fun1 = new ArrayList<String>();

        fun1.add(0,"Select Driver");

        fun1.addAll(drivername);

        driverName.setSelection(0);

        driverName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun1));
    }


    public void getInDriverdetails(){
        if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<RideInDriverDetailsResponse> call = api.rideInDriverdetails(d_fName,d_lName);
                call.enqueue(new Callback<RideInDriverDetailsResponse>() {
                    @Override
                    public void onResponse(Call<RideInDriverDetailsResponse> call,
                                           Response<RideInDriverDetailsResponse> response) {

                        driverDetailsResponse = new RideInDriverDetailsResponse();
                        driverDetailsResponse = response.body();

                        no_of_seats = driverDetailsResponse.getData()[0].getNo_of_pass();
                        Log.d("TAG", "getinres123: " + no_of_seats);
                        Log.d("TAG", "getinres123: " + driverDetailsResponse.getData()[0].getVeh_num());
                        Log.d("TAG", "getinres123: " + driverDetailsResponse.getData()[0].getContact_num());
                        Log.d("TAG", "getinres123: " + driverDetailsResponse.getData()[0].getVeh_type());
                        Log.d("TAG", "getinres123: " + driverDetailsResponse.getData()[0].getDriver_id());
                        //   no_of_passengers.setText(driverDetailsResponse.getData()[0].getNo_of_pass());
                        veh_no.setText(driverDetailsResponse.getData()[0].getVeh_num());
                        driver_num.setText(driverDetailsResponse.getData()[0].getContact_num());
                        veh_type.setText(driverDetailsResponse.getData()[0].getVeh_type());

                        veh_no1 = driverDetailsResponse.getData()[0].getVeh_num();
                        driver_id = driverDetailsResponse.getData()[0].getDriver_id();
                        driver_no1 = driverDetailsResponse.getData()[0].getContact_num();
                        veh_type1 = driverDetailsResponse.getData()[0].getVeh_type();
                        Log.d("TAG", "getinres1234: " + veh_type1);

                        try {

                            //getSeatsCount();
                            res_seats_count = pass_count.getText().toString();
                            fareCal();
                        } catch (Exception e) {
                            Toast.makeText(AddOn.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RideInDriverDetailsResponse> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void fareCal(){

        list = new ArrayList<String>();
    int res_seats = Integer.parseInt(res_seats_count);
        int seats_count =Integer.parseInt(no_of_seats);

        int seatsLeft=seats_count - res_seats;
        Log.d(TAG, "fareCal: "+seatsLeft);
        for(int i=1;i<=(seatsLeft);i++){
            list.add("passenger "+String.valueOf(i));
            System.out.println("seats are "+i);

        }
        if(veh_type1.equalsIgnoreCase("car")){
            fare = Integer.parseInt(carcost);

        }else if(veh_type1.equalsIgnoreCase("auto")) {
            fare = Integer.parseInt(autocost);
        }


        int i1=Integer.parseInt( total_fare2.getText().toString());
        int i2=Integer.parseInt(pass_count.getText().toString()) ;

        Log.d("TAG", "onItemSelected:12i1 " +i1);
        Log.d("TAG", "onItemSelected:12i2 " +i2);
        if(i1 == 0.0){
            Log.d("TAG", "onItemSelected:12...........:c ");
            c = Double.toString(i2 * fare);
            Log.d("TAG", "onItemSelected:12:0000000000000000000" +c);
            total_fare2.setText(c);
        }
        Log.d("TAG", "onItemSelected:12:0000000000000000000" +c);



        list.add(0, "Select passengers");



        no_of_pass.setSelection(0);
        no_of_pass.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list));

        no_of_pass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = no_of_pass.getSelectedItem().toString();


                u = position;

                Log.d("TAG", "onItemSelected:postion " +position);
                Object value = parent.getItemAtPosition(position);

                switch (u) {
                    case 0:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;
                    case 1:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;

                    case 2:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;

                    case 3:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;
                    case 4:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;
                    case 5:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;

                    case 6:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;

                    case 7:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;
                    case 8:
                        k = u * (fare);
                        l = Double.toString(k);
                        total_fare.setText(l);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }
    private void AgentIdSpinner() {                          //Agent list spinner
        if (connection_detector.isConnectingToInternet()) {
            Log.d("TAG", "agentidSpinner: ");

            try {

                agentIdResponse = new AgentIdResponse();
                Log.d("TAG", "agentspinner:1 ");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<AgentIdResponse> call = api.agentIdSpinner(to.getText().toString());
                call.enqueue(new Callback<AgentIdResponse>() {
                    @Override
                    public void onResponse(Call<AgentIdResponse> call,
                                           Response<AgentIdResponse> response) {

                        agentIdResponse = response.body();
                        agentid = new ArrayList<String>();
                        Log.d("TAG", "onResponse: 1");
                        if (agentIdResponse.getStatus().equalsIgnoreCase("sucess")) {
                            try {


                                for (int i = 0; i < agentIdResponse.getData().length; i++) {
                                    Log.d("TAG", "onResponse: 12:"+agentIdResponse.getData()[i].getAgentid().toString());
                                    //Log.d("TAG", "onResponse: 12:"+agentIdResponse.getData()[i].getAgentid().toString());
                                    agentid.add(agentIdResponse.getData()[i].getAgent_name().toString());
                                    a[i+1]=agentIdResponse.getData()[i].getAgentid().toString();
                                   supervisor_id=agentIdResponse.getData()[i].getSupervisor_id().toString();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setAgentIdAdapter(agentid);
                        } else {
                            Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AgentIdResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t);
                    }

                });
            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setAgentIdAdapter(ArrayList<String> agentid) {

        ArrayList<String> fun1;
        fun1 = new ArrayList<String>();

        fun1.add(0,"Select Agent Id");

        fun1.addAll(agentid);

        agent_id.setSelection(0);
        agent_id.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun1));

    }




    public void getIn() {
        if (connection_detector.isConnectingToInternet()) {
            try {
                String hap_passenger_count = String.valueOf(u);


                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Log.d("TAG", "getIn:from" + from);
                Log.d("TAG", "getIn:froml" + l);
                String gi_id = Config.getLoginusername(getApplicationContext());
                Log.d("TAG", "getIn:fromlid" + gi_id);
                Call<AddOnRideInResponse> call = api.addOn_rideIn(from.getText().toString(), to.getText().toString(),gi_id,driver_id, hap_passenger_count,l, pass_count.getText().toString(),total_fare2.getText().toString(), go_id, supervisor_id);
                call.enqueue(new Callback<AddOnRideInResponse>() {
                    @Override
                    public void onResponse(Call<AddOnRideInResponse> call,
                                           Response<AddOnRideInResponse> response) {
                        addonResponse = new AddOnRideInResponse();
                        addonResponse = response.body();
                        Log.d("TAG", "onResponse:getin" + addonResponse);
                        if(addonResponse.getStatus().equalsIgnoreCase("true")){

                            Toast.makeText(AddOn.this, "Ride assigned", Toast.LENGTH_SHORT).show();
                            status1="2";
                            setStaus();
                            setCount();
//                            Intent i = new Intent(getApplicationContext(), Home_agent.class);
//                            startActivity(i);

                        }else{

                            Toast.makeText(AddOn.this, "failed", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<AddOnRideInResponse> call, Throwable t) {
                        t.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Try Again",
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure:t" + t);
                    }

                });

            } catch (Exception e) {
                System.out.println("msg:" + e);
            }
        }else Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setStaus(){
        if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<statusResponse> call = api.status1(req_id,driver_id,status1);
                call.enqueue(new Callback<statusResponse>() {
                    @Override
                    public void onResponse(Call<statusResponse> call,
                                           Response<statusResponse> response) {

                        status_response = new statusResponse();
                        status_response = response.body();

                        if(status_response.getStatus().equalsIgnoreCase("true")){
                            Intent i = new Intent(getApplicationContext(), Home_agent.class);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void onFailure(Call<statusResponse> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void setCount(){
        if (connection_detector.isConnectingToInternet()) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                a_id= Config.getLoginusername(getApplicationContext());
                Call<request_response> call = api.countvalidation(a_id);

                call.enqueue(new Callback<request_response>() {
                    @Override
                    public void onResponse(Call<request_response> call,
                                           Response<request_response> response) {

                        request_response = new request_response();
                        request_response = response.body();
                        if (request_response.getStatus().equalsIgnoreCase("true")) {


                        }
                    }


                    @Override
                    public void onFailure(Call<request_response> call, Throwable t) {

                    }


                });


            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


}
