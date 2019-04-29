package com.cadrac.hap.activites;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap.R;
import com.cadrac.hap.responses.AgentIdResponse;
import com.cadrac.hap.responses.Drivers_Availabilty_Response;
import com.cadrac.hap.responses.PlacesResponse;
import com.cadrac.hap.responses.RideInCostDetailsResponse;
import com.cadrac.hap.responses.RideInDriverDetailsResponse;
import com.cadrac.hap.responses.Ridein_Response;
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

public class fragment_agent_ridein extends Fragment {

    View view;
    EditText vech_no,driver_no,from11, veh_type, total_fare;

    Spinner to, driver_name, agent_id, no_of_passengers;
    Button submit;
    Ridein_Response ridein_response;
    RideInCostDetailsResponse getindetailsresponse;
    AgentIdResponse agentIdResponse;
    String username;
    int u;
    String from, l, no_of_seats, veh_type1, autocost, carcost, veh_no1,driver_no1,driver_id,agent_id1,d_fName,d_lName;
    int k,i,fare;
    PlacesResponse placesResponse;
    Drivers_Availabilty_Response driverResponse;
    RideInDriverDetailsResponse driverDetailsResponse;
    ArrayList<String> places, drivername, agentid, list;
    String destination, d_name, agents_id="",supervisor_id, a_id;
    TextView lab1, lab2,lab3,lab4,lab5;
    Connection_Detector connection_detector;
//    SwipeRefreshLayout swipeRefreshLayout;

    String a[] = new String[10];

    public fragment_agent_ridein() {

    }

    public static fragment_agent_ridein newInstance(String param1, String param2) {
        fragment_agent_ridein fragment = new fragment_agent_ridein();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        view=   inflater.inflate( R.layout.activity_fragment_agent_ridein, container, false );

        to = (Spinner) view.findViewById(R.id.to);
        no_of_passengers = (Spinner) view.findViewById(R.id.no_of_passengers);
        total_fare = (EditText)view.findViewById(R.id.total_fare);
        vech_no = (EditText) view.findViewById(R.id.vech_no);

        driver_no = (EditText) view.findViewById(R.id.driver_no);
        driver_name = (Spinner) view.findViewById(R.id.driver_name);
        connection_detector = new Connection_Detector(getContext());
        agent_id = (Spinner) view.findViewById(R.id.agent_id);
        submit = (Button) view.findViewById(R.id.submit);
        from11=(EditText) view.findViewById( R.id.from );
        veh_type = (EditText) view.findViewById(R.id.vehicle_type);
//        swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipe);
       from=Config.getLoginfrom(getActivity());
        Log.d("TAG", "onCreateView:from"+from);
        from11.setText(from);

        lab1 = (TextView) view.findViewById(R.id.costlab1);
        lab2 = (TextView) view.findViewById(R.id.costlab2);
        lab3 = (TextView) view.findViewById(R.id.costlab3);
        lab4 = (TextView) view.findViewById(R.id.costlab4);
        lab5 = (TextView) view.findViewById(R.id.costlab5);
        sourceSpinner();                       //Source and destination spinner



        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try {


                    sourceSpinner();
                    getInDriverdetails();
                    getIn();
                    getInCostdetails();
                    AgentIdSpinner();
                    DriverSpinner();
                    fareCal();//Driver name list spinner
                    swipeRefreshLayout.setRefreshing(false);
                }catch (Exception e)
                {
                    sourceSpinner();
                    getInDriverdetails();
                    getIn();
                    getInCostdetails();
                    AgentIdSpinner();
                    DriverSpinner();
                    fareCal();//Driver name list spinner
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });*/

        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                destination= to.getSelectedItem().toString();


                Log.d("TAG", "onItemSelected: "+destination);
                if(destination.equalsIgnoreCase("select destination") || destination.equals(from)){
                   // Toast.makeText(getContext(), "Enter valid destination", Toast.LENGTH_SHORT).show();
                    lab1.setVisibility(View.GONE);
                    lab2.setVisibility(View.GONE);
                    lab3.setVisibility(View.GONE);
                    lab4.setVisibility(View.GONE);
                    lab5.setVisibility(View.GONE);


                    to.setSelection(0);

                    no_of_passengers.setSelection(0);
                    vech_no.getText().clear();
                    driver_name.setSelection(0);
                    driver_no.getText().clear();
                    veh_type.getText().clear();
                    total_fare.getText().clear();
                    agent_id.setSelection(0);



                }else{

                    DriverSpinner();                        //Driver name list spinner
                    getInCostdetails();

                    AgentIdSpinner();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        driver_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                d_name= driver_name.getSelectedItem().toString();
                String[] r=d_name.split(" ");
                d_fName=r[0];
                d_lName=r[1];
                Log.d("TAG", "driver name: "+r[0]);
                Log.d("TAG", "driver name: "+r[1]);
                Log.d("TAG", "onItemSelected: "+destination);


                if(d_name.equalsIgnoreCase("Select Driver")){
                    //Toast.makeText(getContext(), "select driver name", Toast.LENGTH_SHORT).show();

                    vech_no.getText().clear();
                    driver_name.setSelection(0);
                    driver_no.getText().clear();
                    veh_type.getText().clear();


                }
                else
                    {

                    getInDriverdetails();

                    //selecting seats
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        agent_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //Agent Id SPinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int p = position;
                agents_id = agent_id.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+agents_id);
                Log.d("TAG", "onItemSelected:pos "+a[p]);
               // Log.d("TAG", "onItemSelected: val"+a[position-1]);
                a_id = a[p];

               // getAgentId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (destination.equalsIgnoreCase("Select Your destination") || destination.equals(from)) {
                    Toast.makeText(getContext(),
                            "Enter valid destination1",
                            Toast.LENGTH_LONG).show();
                }
                else if(u==0){
                    Toast.makeText(getContext(),
                            "Select Passenger",
                            Toast.LENGTH_LONG).show();
                }
                else if(agents_id.equalsIgnoreCase("select agent id")){
                    Toast.makeText(getContext(),
                            "Select agent",
                            Toast.LENGTH_LONG).show();
                }

                else if(vech_no.getText().length()==0){
                    vech_no.setError( "Enter Source" );
                }else if(driver_no.getText().length()==0){
                    driver_no.setError( "Enter Source" );
                }
                else{
                    getIn();

                    Toast.makeText(getContext(), "Ride Opened", Toast.LENGTH_SHORT).show();

                    to.setSelection(0);

                     no_of_passengers.setSelection(0);
                     vech_no.getText().clear();
                      driver_name.setSelection(0);
                     driver_no.getText().clear();
                     veh_type.getText().clear();
                    total_fare.getText().clear();
                    agent_id.setSelection(0);

                }
            }
        } );
        setHasOptionsMenu(true);
        return view;
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
                Call<RideInCostDetailsResponse> call = api.rideInCostDetails(from,
                        destination);
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
        }else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
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
                        //   no_of_passengers.setText(driverDetailsResponse.getData()[0].getNo_of_pass());
                        vech_no.setText(driverDetailsResponse.getData()[0].getVeh_num());
                        driver_no.setText(driverDetailsResponse.getData()[0].getContact_num());
                        veh_type.setText(driverDetailsResponse.getData()[0].getVeh_type());

                        veh_no1 = driverDetailsResponse.getData()[0].getVeh_num();
                        driver_id = driverDetailsResponse.getData()[0].getDriver_id();
                        driver_no1 = driverDetailsResponse.getData()[0].getContact_num();
                        veh_type1 = driverDetailsResponse.getData()[0].getVeh_type();
                        Log.d("TAG", "getinres1234: " + veh_type1);

                        try {
                            fareCal();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Please Do Select The Destination First! ", Toast.LENGTH_SHORT).show();
                            driver_name.setSelection(0);
                            vech_no.getText().clear();
                            veh_type.getText().clear();
                            driver_no.getText().clear();
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
            Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void getIn() {
        if (connection_detector.isConnectingToInternet()) {
            try {
                String passenger_count = String.valueOf(u);
                String trans_id = getAlphaNumeric(10);
                Log.d("TAG", "getIn:id" + trans_id);

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                username = Config.getLoginusername(getContext());
                Log.d("TAG", "getIn:from" + from);
                Call<Ridein_Response> call = api.rideIn(from, destination, d_name, driver_no1, veh_no1, veh_type1,
                        passenger_count.toString(), l, username, a_id, driver_id, supervisor_id,getindetailsresponse.getData()[i].getEstimatedTime());
                call.enqueue(new Callback<Ridein_Response>() {
                    @Override
                    public void onResponse(Call<Ridein_Response> call,
                                           Response<Ridein_Response> response) {
                        ridein_response = response.body();
                        Log.d("TAG", "onResponse:getin" + ridein_response);

                   /* Intent i1=new Intent(getContext(),GetOutActivity.class);
                    startActivity(i1);*/
                    }

                    @Override
                    public void onFailure(Call<Ridein_Response> call, Throwable t) {
                        t.getMessage();
                        Toast.makeText(getContext(),
                                "Try Again",
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure:t" + t);
                    }

                });
           /* Intent i=new Intent(getContext(),GenIn_Activity.class);
            startActivity(i);*/
            } catch (Exception e) {
                System.out.println("msg:" + e);
            }
        }else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public String getAlphaNumeric(int len) {

        char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        char[] c = new char[len];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }

    private void sourceSpinner() {

        Log.d("TAG", " sourceSpinner: ");

        try {

            placesResponse = new PlacesResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<PlacesResponse> call = api.destination(from);
            call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(Call<PlacesResponse> call,
                                       Response<PlacesResponse> response) {

                    placesResponse = response.body();
                    Log.d("TAG", "onResponse: 21" +placesResponse);
                    places=new ArrayList<String>();
                    Log.d("TAG", "onResponse: 1");
                    try {
                        if (placesResponse.getStatus().equalsIgnoreCase("sucess")) {
                            Log.d("TAG", "onResponse: 2");
                            for (int i = 0; i < placesResponse.getData().length; i++) {
                                places.add(placesResponse.getData()[i].getPlaces().toString());
                            }
                            setPlacesAdapter(places);
                        } else {
                            Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call <PlacesResponse> call,Throwable t) {
                    Log.d("TAG", "onFailure: "+t);
                }

            });
        }
        catch (Exception e){
            System.out.print( "Exception e" + e );

        }

    }



    public void setPlacesAdapter(ArrayList<String> place) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Select Your destination");

        fun.addAll(place);



        to.setSelection(0);
        to.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }

    private void DriverSpinner()                     //Driver list spinner
    {
        if (connection_detector.isConnectingToInternet()) {

            Log.d("TAG", "sourceSpinner: ");

            try {

                driverResponse = new Drivers_Availabilty_Response();
                Log.d("TAG", "sourceSpinner:1 ");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<Drivers_Availabilty_Response> call = api.DriverSpinner(from);
                call.enqueue(new Callback<Drivers_Availabilty_Response>() {
                    @Override
                    public void onResponse(Call<Drivers_Availabilty_Response> call,
                                           Response<Drivers_Availabilty_Response> response) {

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
                            Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Drivers_Availabilty_Response> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t);
                    }

                });
            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
        }else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setDriverAdapter(ArrayList<String> drivername) {

        ArrayList<String> fun1;
        fun1 = new ArrayList<String>();

        fun1.add(0,"Select Driver");

        fun1.addAll(drivername);

        driver_name.setSelection(0);

        driver_name.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, fun1));
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

        Call<AgentIdResponse> call = api.agentIdSpinner(destination);
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
                            agentid.add(agentIdResponse.getData()[i].getAgent_name().toString());
                            Log.d(TAG, "onResponse: a_name"+agentIdResponse.getData()[i].getAgent_name().toString());
                            a[i+1] = agentIdResponse.getData()[i].getAgentid();
                            Log.d(TAG, "onResponse: a_id"+agentIdResponse.getData()[i].getAgentid());
                            Log.d(TAG, "onResponse: a_i"+a[i+1]);
                            supervisor_id=agentIdResponse.getData()[i].getSupervisor_id().toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setAgentIdAdapter(agentid);
                } else {
                    Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
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
}else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }
    public void setAgentIdAdapter(ArrayList<String> agentid) {

        ArrayList<String> fun1;
        fun1 = new ArrayList<String>();

        fun1.add(0,"Select Agent Id");

        fun1.addAll(agentid);

        agent_id.setSelection(0);
        agent_id.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, fun1));

    }

    private void getAgentId() {                          //Agent list spinner
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

                Call<AgentIdResponse> call = api.agentId(agents_id);
                call.enqueue(new Callback<AgentIdResponse>() {
                    @Override
                    public void onResponse(Call<AgentIdResponse> call,
                                           Response<AgentIdResponse> response) {

                        agentIdResponse = response.body();
                        agentid = new ArrayList<String>();
                        Log.d("TAG", "onResponse: 1");
                        if (agentIdResponse.getStatus().equalsIgnoreCase("sucess")) {
                            try {


                                    agent_id1=agentIdResponse.getData()[i].getAgentid().toString();
                                    supervisor_id=agentIdResponse.getData()[i].getSupervisor_id().toString();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setAgentIdAdapter(agentid);
                        } else {
                            Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
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
        }else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }

    public void fareCal(){

        list = new ArrayList<String>();

        int seatsLeft =Integer.parseInt(no_of_seats);


        for(int i=1;i<=(seatsLeft);i++){
            list.add("passenger "+String.valueOf(i));
            System.out.println("seats are "+i);

        }
        if(veh_type1.equalsIgnoreCase("car")){
            fare = Integer.parseInt(carcost);

        }else if(veh_type1.equalsIgnoreCase("auto")) {
            fare = Integer.parseInt(autocost);
        }



        list.add(0, "Select passengers");



        no_of_passengers.setSelection(0);
        no_of_passengers.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,list));

        no_of_passengers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = no_of_passengers.getSelectedItem().toString();



                u = position;

                Log.d("TAG", "onItemSelected: " +position);
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
    public void setFields()
    {

    }


}






