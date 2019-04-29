package com.cadrac.hap.activites;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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
        import com.cadrac.hap.responses.PlacesResponse;
        import com.cadrac.hap.responses.driver_Login_Response;
        import com.cadrac.hap.supporter_classes.Connection_Detector;
        import com.cadrac.hap.utils.Config;
        import com.cadrac.hap.webservices.API;
        import com.cadrac.hap.webservices.RestClient;

        import java.util.ArrayList;

        import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;


public class driver_login extends AppCompatActivity implements View.OnClickListener{

    EditText driver_username, driver_password;
    Spinner driver_from;
    Button driver_login;
    driver_Login_Response loginResponse;
    PlacesResponse placesResponse;
    ArrayList<String> places;
    String driver_source;
    TextView driver_changepassword;
    Connection_Detector connection_detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        driver_username = (EditText) findViewById(R.id.driver_username);
        driver_password = (EditText) findViewById(R.id.driver_password);
        driver_from = (Spinner) findViewById(R.id.driver_sourcespinner);
        driver_login = (Button) findViewById(R.id.driver_login);
        driver_changepassword = (TextView) findViewById(R.id.driver_changepassword);
        driver_login.setOnClickListener(this);
        connection_detector = new Connection_Detector( this);
        driver_changepassword.setOnClickListener(this);

        driver_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                driver_source= driver_from.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+driver_source);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sourceSpinner();

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.driver_login) {
            if (driver_username.getText().length() == 0) {
                driver_username.setError("Enter Username");
            } else if (driver_password.getText().length() == 0) {
                driver_password.setError("Enter Password");
            }
            else if(driver_source.equalsIgnoreCase("Please Select Your source")){
                Toast.makeText(getApplicationContext(),
                        "Please enter valid source",
                        Toast.LENGTH_LONG).show();
            }
             else{
                Log.d( "TAG", "onClick: "+driver_login );
                userLogin();
            }
        }
        if (v.getId()==R.id.driver_changepassword)
        {
            changePassword();
        }
    }

    private void changePassword() {
        Intent i=new Intent(getApplicationContext(),ChangePassword_driver.class);
        startActivity(i);
    }

    private void userLogin() {
        if (connection_detector.isConnectingToInternet())
        {
        try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d("TAG", "userLogin:resp");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString("from",source);
                editor.putString("username", driver_username.getText().toString());

                editor.commit();
                Log.d("TAG", "userLogin:pass" + driver_password.getText().toString());
                Log.d("TAG", "userLogin:user" + driver_username.getText().toString());
                Call<driver_Login_Response> call = api.driverlogin(driver_username.getText().toString(),
                        driver_password.getText().toString(), "driver", driver_source);
                call.enqueue(new Callback<driver_Login_Response>() {
                    @Override
                    public void onResponse(Call<driver_Login_Response> call,
                                           Response<driver_Login_Response> response) {

                        loginResponse = response.body();
                        Log.d("TAG", "onResponse: login" + loginResponse);
                        Log.d("TAG", "onResponse: s" + loginResponse.getStatus());

                        if (loginResponse.getStatus().equalsIgnoreCase("True")) {
                            Config.saveLoginusername(getApplicationContext(), driver_username.getText().toString());
                            Config.saveLoginpassword(getApplicationContext(), driver_password.getText().toString());
                            Config.saveLoginfrom(getApplicationContext(), driver_source);
                            Config.saveLoginStatus(getApplicationContext(), "3");
                            Config.savecode(getApplicationContext(),loginResponse.getCode());
                            Intent i1 = new Intent(getApplicationContext(), Home_driver.class);
                            i1.putExtra("username", loginResponse.getUsername());
                            startActivity(i1);
                            finish();
                        } else {


                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid username& password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<driver_Login_Response> call, Throwable t) {
                        t.getMessage();
                        Log.d(" TAG", "onFailure:fail" + t);
                        Toast.makeText(getApplicationContext(),
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }

                });

        }catch (Exception e) {
            System.out.print("Exception e" + e);

        }}else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }



    private void sourceSpinner() {

        Log.d("TAG", "sourceSpinner: ");

        try {

            placesResponse=new PlacesResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<PlacesResponse> call = api.source();
            call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(Call<PlacesResponse> call,
                                       Response<PlacesResponse> response) {

                    placesResponse = response.body();
                    places=new ArrayList<String>();
                    Log.d("TAG", "onResponse: 1");
                    if (placesResponse.getStatus().equalsIgnoreCase("sucess")) {
                        Log.d("TAG", "onResponse: 2");
                        for(int i=0;i<placesResponse.getData().length;i++) {
                            places.add(placesResponse.getData()[i].getPlaces().toString());
                        }
                        setPlacesAdapter(places);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"sorry",Toast.LENGTH_SHORT).show();
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

        fun.add(0,"Please Select Your source");

        fun.addAll(place);



        driver_from.setSelection(0);
        driver_from.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }
 /*   public void onBackPressed() {

        Intent intent=new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );

    }*/
}


