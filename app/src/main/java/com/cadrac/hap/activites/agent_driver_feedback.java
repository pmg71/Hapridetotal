package com.cadrac.hap.activites;



        import android.content.Intent;
        import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.widget.Toast;


import com.cadrac.hap.R;
import com.cadrac.hap.responses.Agent_DriverFeedBack_Response;
        import com.cadrac.hap.supporter_classes.Connection_Detector;
        import com.cadrac.hap.webservices.API;
import com.cadrac.hap.webservices.RestClient;

import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class agent_driver_feedback extends AppCompatActivity {

    RatingBar rb;
    TextView value;
    EditText others;
    Button  b1;
    Connection_Detector connection_detector;


    Agent_DriverFeedBack_Response feedbackResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_driver_feedback);
        rb=(RatingBar)findViewById(R.id.rb);
        value=(TextView)findViewById(R.id.value);
        others=(EditText)findViewById(R.id.others);
        b1=(Button)findViewById(R.id.b1);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        connection_detector = new Connection_Detector(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });




        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value.setText("" + rating);
         }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((value.getText().toString().length()==0)&&(others.getText().toString().length()==0)) {
                    value.setError("Give Rating");
                    others.setError("give feedback");
                }

                else {
                    feedbackform();



                }

            }



            Intent intent= getIntent();
            String rid=intent.getStringExtra("rid");
            String did=intent.getStringExtra("did");
            String goid=intent.getStringExtra("goid");


            private void feedbackform() {
                if (connection_detector.isConnectingToInternet()) {
                    try {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                                client(okHttpClient).
                                addConverterFactory(GsonConverterFactory
                                        .create()).build();
                        API api = RestClient.client.create(API.class);
                        Call<Agent_DriverFeedBack_Response> call = api.feedbackform(rid, goid, did, value.getText().toString(),
                                others.getText().toString());
                        call.enqueue(new Callback<Agent_DriverFeedBack_Response>() {
                            @Override
                            public void onResponse(Call<Agent_DriverFeedBack_Response> call, Response<Agent_DriverFeedBack_Response> response) {

                                feedbackResponse = response.body();
                                if (feedbackResponse.getStatus().equalsIgnoreCase("True")) {
                                    Intent i = new Intent(getApplicationContext(), Home_agent.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<Agent_DriverFeedBack_Response> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();

                            }
                        });

                    } catch (Exception e) {
                        System.out.print("Exception e" + e);

                    }
                }else
                    Toast.makeText(agent_driver_feedback.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
