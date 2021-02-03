package com.example.sale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sale.Api.Api;
import com.example.sale.Config.DBHelper;
import com.example.sale.Fragment.Additem;
import com.example.sale.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Addimage extends AppCompatActivity {
    Toolbar toolbar;
    ImageView IDProf, IDProf1, IDProf2;
    String imagestring,imagestring1,imagestring2;
    public  static final int RequestPermissionCode  = 1 ;
    DBHelper dbHelper;
    String id, na, pa;
    private Uri fileUri;
    String picturePath;
    public static final String KEY_User_Document1 = "doc1";
    private String Document_img1="";
    String ticketid,status,price,qty,custamername,custameraddress;
    ArrayList<HashMap<String,String>> hashMap;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        login_btn=findViewById(R.id.login_btn);
        Intent i=getIntent();
        ticketid=i.getStringExtra("ticketid");
        custamername=i.getStringExtra("custamername");
        custameraddress=i.getStringExtra("custameraddress");
         hashMap = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("map");

//        Toast.makeText(this, ""+hashMap.toString(), Toast.LENGTH_LONG).show();
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Addimage.this,FullDetails.class);
                i.putExtra("ticketid",""+ticketid);
                i.putExtra("map", hashMap);
                i.putExtra("image1", imagestring);
                i.putExtra("image2", imagestring1);
                i.putExtra("image3", imagestring2);
                i.putExtra("custamername",""+custamername);
                i.putExtra("custameraddress",""+custameraddress);
                startActivity(i);
            }
        });



//        if (ContextCompat.checkSelfPermission(Addimage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(Addimage.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
////                takePicture();
//            } else {
//                //changed here
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
//                }
//            }
//        } else {
//            //changed here
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//            }
//        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        IDProf = (ImageView) findViewById(R.id.IdProf);
        IDProf1 = (ImageView) findViewById(R.id.IdProf1);
        IDProf2 = (ImageView) findViewById(R.id.IdProf2);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Addimage.this, Additem.class);
                startActivity(i);
            }
        });
        EnableRuntimePermissionToAccessCamera();
        IDProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult( intent,0);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        IDProf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult( intent,1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        IDProf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult( intent,2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==0 && resultCode==RESULT_OK) {
           Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf.setImageBitmap(bitmap);
            imagestring=BitMapToString(bitmap);
            Log.e("imagestring",""+imagestring);

        }else if (requestCode == 1 && resultCode == RESULT_OK ) {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf1.setImageBitmap(bitmap);
            imagestring1=BitMapToString(bitmap);
            Log.e("imagestring1",""+imagestring1);
        } else if (requestCode == 2 && resultCode == RESULT_OK ) {

            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf2.setImageBitmap(bitmap);
            imagestring2=BitMapToString(bitmap);
            Log.e("imagestring2",""+imagestring2);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(Addimage.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
//            Toast.makeText(Addimage.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Addimage.this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(Addimage.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(Addimage.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void addticket() {
//        api = new Api();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();
        try {
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date todayDate = new Date();
//            String thisDate = currentDate.format(todayDate);
//            Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
            object.put("UserID", "" + na);
            object.put("OrganizationID", "" + pa);
            object.put("ticketid", "" + ticketid);
            object.put("status", "" + status);
            object.put("price", "" + price);
            object.put("qty", "" + pa);
            object.put("image1", "" + imagestring);
            object.put("image2", "" + imagestring1);
            object.put("image3", "" + imagestring2);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = Api.ticketcrt;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("xdddddddddddd", "" + response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}