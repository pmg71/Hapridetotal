package com.cadrac.hap.activites;
        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.content.pm.ResolveInfo;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Parcelable;
        import android.preference.PreferenceManager;
        import android.provider.MediaStore;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Base64;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.cadrac.hap.R;
        import com.cadrac.hap.utils.Config;

        import org.apache.http.NameValuePair;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.ResponseHandler;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.BasicResponseHandler;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;



        import static android.Manifest.permission.CAMERA;
        import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
        import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Upload_Image_Driver_Registration extends AppCompatActivity {



    Uri picUri;
    Button uploadtoserver,retake;
    TextView textView;

    String amith,fin;
    ProgressDialog progressDialog ;
    ByteArrayOutputStream byteArrayOutputStream ;
    byte[] byteArray ;
    Bitmap selectedImage;
    String filepath,aa;
    Context context;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__image__driver__registration);
        context=getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Driver Registration");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        aa=Config.getdriverregistrationvalue(getApplicationContext());
        Log.d("TAG", "onCreate: rc"+aa);
        if(aa.equalsIgnoreCase("rc"))
        {
            amith="http://toobworks.com/hap/driver_rc_upload.php";
        }else if(aa.equalsIgnoreCase("license")) {
            amith="http://toobworks.com/hap/driver_lic_upload.php";
        }else if(aa.equalsIgnoreCase("insurance")) {
            amith="http://toobworks.com/hap/driver_insurance_upload.php";
        }else if(aa.equalsIgnoreCase("Pollution")) {
            amith="http://toobworks.com/hap/driver_pollution_upload.php";
        }else if(aa.equalsIgnoreCase("aadhar")) {
            amith="http://toobworks.com/hap/driver_aadhar_upload.php";
        }else if(aa.equalsIgnoreCase("pan")) {
            amith="http://toobworks.com/hap/driver_pan_upload.php";
        }

        retake=findViewById(R.id.retake);

        byteArrayOutputStream = new ByteArrayOutputStream();

        uploadtoserver=(Button)findViewById( R.id.button );

        textView=(TextView)findViewById( R.id. text);


        startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);


        uploadtoserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(selectedImage==null){
                    Toast.makeText(getApplicationContext(),"Please Capture Image",Toast.LENGTH_LONG).show();
                }

                else {
                    //  progressDialog = ProgressDialog.show(Upload_Image_Driver_Registration.this, "Image is Uploading", "Please Wait", false, false);
                    UploadImageToServer();

                    if(aa.equalsIgnoreCase( "rc" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("rc","123");
                        editor.commit();

                    }
                    else if(aa.equalsIgnoreCase( "license" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("lic","123");
                        editor.commit();

                    }
                    else if(aa.equalsIgnoreCase( "insurance" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("insurance","123");
                        editor.commit();

                    }else if(aa.equalsIgnoreCase( "pollution" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("pollution","123");
                        editor.commit();

                    }else if(aa.equalsIgnoreCase( "aadhar" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("aadhar","123");
                        editor.commit();

                    }else if(aa.equalsIgnoreCase( "pan" )){
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        Log.d("TAG", "onPostExecute:123 "+aa);
                        editor.putString("pan","123");
                        editor.commit();

                    }





                    Toast.makeText(getApplicationContext(),"Uploading Background",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Drivers_Documents_Upload.class);
                    startActivity(i);


                }


            }
        });
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);


            }
        });

        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

    }



    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
            Log.d( "TAG", "getCaptureImageOutputUri: "+getImage.getPath() );
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (resultCode == Activity.RESULT_OK) {

                ImageView imageView = findViewById(R.id.imageView);

                if (requestCode == IMAGE_RESULT) {

                    String filePath = getImageFilePath(data);
                    if (filePath != null) {
                        selectedImage = BitmapFactory.decodeFile(filePath);
                        imageView.setImageBitmap(selectedImage);
                    }
                }

            }
        }catch (Exception e)
        {
            Toast.makeText(context, "Please Check Your Internet Connection and Upload Your Files.d", Toast.LENGTH_SHORT).show();
        }

    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    public void UploadImageToServer()
    {
        selectedImage.compress( Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream );

        byteArray = byteArrayOutputStream.toByteArray();

        filepath     = Base64.encodeToString(byteArray, Base64.DEFAULT);


        Log.d("TAG", "UploadImageToServer:"+filepath);



        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                //       progressDialog = ProgressDialog.show(getApplicationContext(), "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                if(string1.contains("sucessrc")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    // Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("rc","1");
                    editor.commit();
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+" Uploaded", Toast.LENGTH_SHORT).show();

                }else if(string1.contains("sucesslic")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //  Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("lic","1");
                    editor.commit();
                    //progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+" Uploaded", Toast.LENGTH_SHORT).show();

                }else if(string1.contains("sucessinsurance")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //  Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("insurance","1");
                    editor.commit();
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+"  Uploaded", Toast.LENGTH_SHORT).show();

                }else if(string1.contains("sucessaadhar")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //  Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("aadhar","1");
                    editor.commit();
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+"  Uploaded", Toast.LENGTH_SHORT).show();

                }else if(string1.contains("sucesspollution")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //  Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("pollution","1");
                    editor.commit();
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+"  Uploaded", Toast.LENGTH_SHORT).show();

                }else if(string1.contains("successpan")){


                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //  Log.d("TAG", "onPostExecute:123 "+aa);
                    editor.putString("pan","1");
                    editor.commit();
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), aa+"  Uploaded", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Drivers_Documents_Upload.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Something went wrong please try again later", Toast.LENGTH_LONG).show();}


            }

            @Override
            protected String doInBackground(Void... params) {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(amith);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);

                fin=Config.getdriver_mobile(getApplicationContext());
                Log.d("TAG", "doInBackground: fin number"+fin);

                nameValuePairs.add(new BasicNameValuePair("image_tag", fin));
                nameValuePairs.add(new BasicNameValuePair("image_data", filepath));

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = null;
                try {
                    response = httpClient.execute(httppost, responseHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "doInBackground: 12345567643" + response);



                return response;
            }
        }
        //progressDialog = ProgressDialog.show(Upload_Image_Driver_Registration.this, "Image is Uploading", "Please Wait", false, false);
        AsyncTaskUploadClass asyncTaskUploadClass;
        asyncTaskUploadClass=new AsyncTaskUploadClass();
        asyncTaskUploadClass.execute();



    }
}



