package com.cadrac.hap.activites;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cadrac.hap.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
public class Users_Video_Capture extends AppCompatActivity {
    private static int serverResponseCode;
    private Button btn, uploadtoserver;
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String upurl;
    TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users__video__capture);

        btn = (Button) findViewById( R.id.btn );
        uploadtoserver = (Button) findViewById( R.id.upload );
        videoView = (VideoView) findViewById( R.id.vv );
        textViewResponse=(TextView)findViewById( R.id.textViewRespone );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Record video");
        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        } );
        uploadtoserver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//Toast.makeText(getApplicationContext(), "Please Do Select or Record Your Video First", Toast.LENGTH_SHORT).show();
                uploadVideo();

            }
        } );

    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder( this );
        pictureDialog.setTitle( "Select Action" );
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera"};
        pictureDialog.setItems( pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();

                                break;
                        }
                    }
                } );
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( galleryIntent, GALLERY );
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE );
        startActivityForResult( intent, CAMERA );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d( "result", "" + resultCode );
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == this.RESULT_CANCELED) {
            Log.d( "what", "cancle" );
            return;
        }
        if (requestCode == GALLERY) {

            if (data != null) {
                Uri contentURI = data.getData();

                String selectedVideoPath = getPath( contentURI );
                Log.d( "TAG", "onActivityResult:5555 "+selectedVideoPath );// /storage/emulated/0/DCIM/Camera/VID_20190228_091519.mp4
                saveVideoToInternalStorage( selectedVideoPath );
                videoView.setVideoURI( contentURI );
                videoView.requestFocus();
                videoView.start();

            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath( contentURI );
            Log.d( "TAG", "onActivityResult:6666 "+recordedVideoPath );///storage/emulated/0/DCIM/Camera/VID_20190228_094810.mp4
            saveVideoToInternalStorage( recordedVideoPath );
            videoView.setVideoURI( contentURI );
            videoView.requestFocus();
            videoView.start();
        }
    }

    private void saveVideoToInternalStorage(String filePath) {

        File newfile ;
        upurl=filePath;
        Log.d( "TAG", "saveVideoToInternalStorage:path "+upurl );

        try {

            File currentFile = new File( filePath );
            Log.d( "TAG", "saveVideoToInternalStorage: 8888"+currentFile );
            File wallpaperDirectory = new File( Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY );
            Log.d( "TAG", "saveVideoToInternalStorage:1111" + wallpaperDirectory );
            newfile = new File( wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4" );
            Log.d( "TAG", "saveVideoToInternalStorage:2222" + newfile );
            // saveVideoToInternalStorage=newfile;

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }


            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                Log.d( "TAG", "saveVideoToInternalStorage: " );
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v( "vii", "Video file saved successfully." );
            }
            else {
                Log.v( "vii", "Video saving failed. Source file missing." );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Log.d( "TAG", "saveVideoToInternalStorage: 12121212" + filePath );
    }


    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query( uri, projection, null, null, null );
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow( MediaStore.Video.Media.DATA );
            cursor.moveToFirst();
            return cursor.getString( column_index );
        } else
            return null;
    };
    private void uploadVideo() {

        class UploadVideo extends AsyncTask<Void, Void, String> {


            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(Users_Video_Capture.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                textViewResponse.setText( Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textViewResponse.setMovementMethod( LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                Log.d( "TAG", "doInBackground:10101 "+u );
                String msg = u.uploadVideo(upurl);
                Log.d( "TAG", "doInBackground:66666 "+upurl );
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        Log.d( "TAG", "uploadVideo:33333 "+uv );
        uv.execute();

    }
}
