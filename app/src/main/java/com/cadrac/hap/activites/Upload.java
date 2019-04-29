package com.cadrac.hap.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cadrac.hap.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Upload {
    public static final String UPLOAD_URL=  "http://toobworks.com/hap/video_uploads.php" ;

    private int serverResponseCode;
    File sourceFile;
    public String uploadVideo(String file) {

        String fileName = file;
        Log.d( TAG, "uploadVideo: 909090" + fileName );
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        try
        {
         sourceFile = new File( file );
        Log.d( TAG, "uploadVideo: source" + sourceFile);// uploadVideo: source/storage/emulated/0/DCIM/Camera/VID_20190228_210331.mp4
       if (!sourceFile.isFile()) {
            Log.e( "Huzza", "Source File Does not exist" );

            return null;
        }}catch (Exception e)
       {
           e.printStackTrace();
           //Toast.makeText(context, "Please Do Select or Record Your Video First", Toast.LENGTH_SHORT).show();
       }

        try {
            FileInputStream fileInputStream = new FileInputStream( sourceFile );
            Log.d( TAG, "uploadVideo: input" + fileInputStream );   // uploadVideo: inputjava.io.FileInputStream@81b9a
            URL url = new URL( UPLOAD_URL );
            conn = (HttpURLConnection) url.openConnection();
            Log.d( TAG, "uploadVideo: connnnnn" + conn );
            conn.setDoInput( true );
            conn.setDoOutput( true );
            conn.setUseCaches( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Connection", "Keep-Alive" );
            conn.setRequestProperty( "ENCTYPE", "multipart/form-data" );
            conn.setRequestProperty( "Content-Type", "multipart/form-data;boundary=" + boundary );
            conn.setRequestProperty( "myFile", fileName );
            dos = new DataOutputStream( conn.getOutputStream() );
            Log.d( TAG, "uploadVideo: data1" + dos );

            dos.writeBytes( twoHyphens + boundary + lineEnd );
            dos.writeBytes( "Content-Disposition: form-data; name=\"myFile\";filename=\"" + fileName + "\"" + lineEnd );
            dos.writeBytes( lineEnd );

            bytesAvailable = fileInputStream.available();
            Log.i( "Huzza", "Initial .available : " + bytesAvailable );
            Log.d( TAG, "uploadVideo: available" + bytesAvailable );

            bufferSize = Math.min( bytesAvailable, maxBufferSize );
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read( buffer, 0, bufferSize );
            Log.d( TAG, "uploadVideo: rachhaa" + bytesRead );

            while (bytesRead > 0) {
                dos.write( buffer, 0, bufferSize );
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min( bytesAvailable, maxBufferSize );
                bytesRead = fileInputStream.read( buffer, 0, bufferSize );
            }

            dos.writeBytes( lineEnd );
            dos.writeBytes( twoHyphens + boundary + twoHyphens + lineEnd );

            serverResponseCode = conn.getResponseCode();
            Log.d( TAG, "uploadVideo: server"+serverResponseCode );

            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (serverResponseCode == 200) {
            StringBuilder sb = new StringBuilder();


            try {
                BufferedReader rd = new BufferedReader( new InputStreamReader( conn
                        .getInputStream() ) );
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append( line );
                }
                rd.close();
            } catch (IOException ioex) {
            }
            return sb.toString();
        } else {
            return "Could not upload,Please try Again";
            //  return "Failed";

        }
    }
}