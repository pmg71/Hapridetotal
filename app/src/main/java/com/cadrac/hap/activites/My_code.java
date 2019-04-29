package com.cadrac.hap.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


  import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cadrac.hap.R;
import com.cadrac.hap.utils.Config;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class My_code extends AppCompatActivity{
//        EditText editText;
        Button share;
        ImageView imageView;
        TextView textView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_code);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("My Refferal Code");
            toolbar.setTitleTextColor(Color.WHITE);


            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();

                }
            });

//            editText=findViewById(R.id.edit);
            imageView=findViewById(R.id.image);
            textView=findViewById(R.id.value);
//            button=findViewById(R.id.button);
            share=findViewById(R.id.share);
       /*     button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {*/


            String a=Config.getcode(getApplicationContext());
            textView.setText(a);

                    MultiFormatWriter multiFormatWriter= new MultiFormatWriter();
                    try {
//                        a=
                        BitMatrix bitMatrix= multiFormatWriter.encode(a, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder= new BarcodeEncoder();
                        Bitmap bitmap= barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
           /*     }
            });*/
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String sharebody = "Hap Refferal Code";
                    String sub = "Join me on Hap ride with my refferal code : " +Config.getcode(getApplicationContext());
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
                    myIntent.putExtra(Intent.EXTRA_TEXT,sub);
                    startActivity(Intent.createChooser(myIntent,"Share"));

                }
            });

        }
}
