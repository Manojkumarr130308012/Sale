package com.example.sale.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sale.Api.Api;
import com.example.sale.Config.DBHelper;
import com.example.sale.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Login extends AppCompatActivity {
    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    TextInputEditText username1, password1;
    DBHelper dbHelper;
    String Storeuser;
    String Storemob;
    String message;
    String checkusername,checkpassword;
    ProgressBar pbar;
    MaterialSpinner spn_branch;
    List<String> orgid = new ArrayList<>();
    List<String> orgname = new ArrayList<>();
    String selectedorgID;
    String selectedorgName;
    String userid;
    String DisplayName;
Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        dbHelper=new DBHelper(this);
        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.password1);
        login_btn = findViewById(R.id.login_btn);
        pbar = (ProgressBar) findViewById(R.id.log_progress);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkusername = username1.getText().toString();
                checkpassword = password1.getText().toString();
                //validate form
                if(validateLogin(checkusername, checkpassword)){
                    //do loginhj

                    Log.e("ffffffffffffffff",""+checkusername);
                    Log.e("ffffffffffffffff",""+checkpassword);

                    postData(checkusername,checkpassword);

                }

            }
        });


    }


    public boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


//    public class fetchData extends AsyncTask<Void,Void,Void> {
//        String data = "";
//        String dataParsed = "";
//        String singleParsed = "";
//
//        @Override
//        protected void onPreExecute() {
//            pbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            if (checkusername == null || checkpassword == null) {
//
////                Toast.makeText(LoginActivity.this, " Fill the Fields", Toast.LENGTH_SHORT).show();
//
//            } else {
//
//                try {
//
//                    URL url = new URL("https://testbackapi.herokuapp.com/patient/login?adthar="+checkusername+"&password="+checkpassword);
//                    message="error";
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line = "";
//                    while (line != null) {
//                        line = bufferedReader.readLine();
//                        data = data + line;
//                    }
//
//                    Log.e("dddddddddddddd", "" + data);
//                    JSONObject jsonobj = new JSONObject(data);
//                    JSONObject jObject = jsonobj.getJSONObject("data");
//                    message = (String) jsonobj.get("status");
//                    singleParsed = (String) jObject.get("phone");
//                    Storeuser = (String) jObject.get("adthar");
//                    Storemob = (String) jObject.get("email");
//
//
//
//
//                    Log.e("ddddddddd", "" + singleParsed);
//                    Log.e("ddddddddd", "" + Storeuser);
//                    Log.e("ddddddddd", "" + message);
//
//                    dataParsed = dataParsed + singleParsed + "\n";
//
//
//
//              /*      if (message != "Welcome !!") {
//                        dbHelper.insertData(Storeuser,singleParsed);
////                        pbar.setVisibility(View.INVISIBLE);
//
//                        Intent i=new Intent(ActivitySignin.this,Student_details.class);
//                        startActivity(i);
//                    }else{
//                        Toast.makeText(ActivitySignin.this, ""+message, Toast.LENGTH_SHORT).show();
////                        pbar.setVisibility(View.INVISIBLE);
//                    }*/
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                } catch (IOException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                } catch (JSONException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                }
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (!message.equals("error")) {
////                        pbar.setVisibility(View.INVISIBLE);
//                dbHelper.insertData(Storeuser,singleParsed);
//                pbar.setVisibility(View.GONE);
//                Intent i=new Intent(Login.this,MainActivity.class);
//                i.putExtra("user",""+Storeuser);
//                startActivity(i);
//
//            }else{
//                pbar.setVisibility(View.GONE);
//                Toast.makeText(Login.this, ""+message, Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }



    public void postData(String usernamestr, String passstr) {
        api=new Api();
        orgname=new ArrayList<>();
        orgid=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date todayDate = new Date();
//            String thisDate = currentDate.format(todayDate);
            Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
            object.put("UserName",""+checkusername);
            object.put("Password",""+checkpassword);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url =Api.Loginurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            message = (String) response.get("status");
                            Log.e("xdddddddddddd",""+ response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(message.equals("success")){
                            try {
                                JSONObject jObject = response.getJSONObject("UserDetails");
                                userid=(String) jObject.get("UserID");
                                DisplayName=(String) jObject.get("DisplayName");
                                Log.e("xddddd",""+userid);
                                JSONArray jObject1 = jObject.getJSONArray("OrganizationDetails");
                                for(int i=0;i<jObject1.length();i++){
                                    JSONObject jsonObject = jObject1.getJSONObject(i);
                                    orgid.add(i,""+jsonObject.optString("ID"));
                                    orgname.add(i,""+jsonObject.optString("OrganizationName"));
                                }
                                showAlertDialogButtonClicked();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(Login.this, "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void showAlertDialogButtonClicked()
    {

        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);


        // set the custom layout
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.custom_layout_org,
                        null);
        builder.setView(customLayout);
        spn_branch = customLayout.findViewById(R.id.spinner);

        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, orgname);
        _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_branch.setAdapter(_Adapter);



        spn_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > -1) {
                    selectedorgID = orgid.get(position).toString();
                    selectedorgName = orgname.get(position).toString();
                } else {
                    selectedorgID = "";
                    selectedorgName = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {



                                dbHelper.insertData(userid,selectedorgID);
                                pbar.setVisibility(View.GONE);
                                Intent i=new Intent(Login.this,Sidemenu.class);
                                startActivity(i);

                                // send data from the
                                // AlertDialog to the Activity

                            }
                        });

        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }

    // Do something with the data
    // coming from the AlertDialog
    private void sendDialogDataToActivity(String data)
    {
        Toast.makeText(this,
                data,
                Toast.LENGTH_SHORT)
                .show();
    }

}