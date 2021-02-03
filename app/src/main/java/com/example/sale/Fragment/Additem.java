package com.example.sale.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sale.Activity.Login;
import com.example.sale.Api.Api;
import com.example.sale.Config.DBHelper;
import com.example.sale.Model.ItemData;
import com.example.sale.R;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class Additem extends Fragment {
    MaterialSpinner goodSpinner,taxSpinner,Unit,stack,serial;
    View view;
    TextInputEditText Printname,Hsncode,Sellingprice,Remarks,itemcode,itemname;
    List<String> good = new ArrayList<>();
    List<String> stackid = new ArrayList<>();
    List<String> stackname = new ArrayList<>();
    List<String> serialid = new ArrayList<>();
    List<String> serialname = new ArrayList<>();
    String selectedtaxidID;
    String selectedtaxName;
    String selectedunitidID;
    String selectedunitidname;
    String goodSpinnerID;
    String goodSpinnerName;
    String stackID;
    String stackName;
    String serialId;
    String serialName;
    Api api;
    String message;
    List<String> taxid = new ArrayList<>();
    List<String> taxname = new ArrayList<>();
    List<String> unitid = new ArrayList<>();
    List<String> unitname = new ArrayList<>();
    DBHelper dbHelper;
    String id,na,pa;
    Button login_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_additem, container, false);
        dbHelper=new DBHelper(getActivity());
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        Printname=view.findViewById(R.id.Printname);
        Hsncode=view.findViewById(R.id.Hsncode);
        Sellingprice=view.findViewById(R.id.Sellingprice);
        Remarks=view.findViewById(R.id.Remarks);
        itemcode=view.findViewById(R.id.itemcode);
        itemname=view.findViewById(R.id.itemname);
        login_btn=view.findViewById(R.id.login_btn);

      taxSpinner = view.findViewById(R.id.GST);
        Unit = view.findViewById(R.id.Unit);
        goodSpinner = view.findViewById(R.id.goodservice);
        stack = view.findViewById(R.id.stack);
        serial = view.findViewById(R.id.serial);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });


        good.add("Good");
        good.add("Service");
        stackname.add("Yes");
        stackname.add("No");
        stackid.add("1");
        stackid.add("0");
        serialname.add("Yes");
        serialname.add("No");
        serialid.add("1");
        serialid.add("0");

        gettax();
        GetUnits();

        ArrayAdapter<String> _Adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, good);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goodSpinner.setAdapter(_Adapter1);


        ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stackname);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stack.setAdapter(_Adapter2);


        ArrayAdapter<String> _Adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, serialname);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serial.setAdapter(_Adapter3);




        goodSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    goodSpinnerID = good.get(i).toString();
                    goodSpinnerName = good.get(i).toString();
                } else {
                    goodSpinnerID = "";
                    goodSpinnerName = "";
                }
            }


        });

        stack.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    stackID = stackid.get(i).toString();
                    stackName = stackname.get(i).toString();
                } else {
                    stackID = "";
                    stackName = "";
                }
            }


        });
        serial.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    serialId = serialid.get(i).toString();
                    serialName = serialname.get(i).toString();
                } else {
                    serialId = "";
                    serialName = "";
                }
            }
        });

        taxSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    selectedtaxidID = taxid.get(i).toString();
                    selectedtaxName = taxname.get(i).toString();
                } else {
                   selectedtaxidID = "";
                   selectedtaxName = "";
                }
            }


        });
        Unit.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    selectedunitidID = unitid.get(i).toString();
                    selectedunitidname = unitname.get(i).toString();
                } else {
                    selectedunitidID = "";
                    selectedunitidname = "";
                }
            }


        });

        return view;
    }


    public void gettax() {
        api=new Api();

        String url =""+Api.gettax; ;
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest ExampleRequest = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jObject1 = null;
                try {
                    jObject1 = response.getJSONArray("data");
                    for(int i=0;i<jObject1.length();i++){
                        JSONObject jsonObject = jObject1.getJSONObject(i);
                        taxid.add(jsonObject.getString("ID"));
                        taxname.add(jsonObject.getString("TaxName"));
                    }
                    ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, taxname);
                    _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    taxSpinner.setAdapter(_Adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        });
        ExampleRequestQueue.add(ExampleRequest);



    }

    public void GetUnits() {
        api=new Api();

        String url =""+Api.getunit; ;
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest ExampleRequest = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jObject1 = null;
                try {
                    jObject1 = response.getJSONArray("data");
                    for(int i=0;i<jObject1.length();i++){
                        JSONObject jsonObject = jObject1.getJSONObject(i);
                        unitid.add(jsonObject.getString("ID"));
                        unitname.add(jsonObject.getString("UnitName"));
                    }
                    ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, unitname);
                    _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Unit.setAdapter(_Adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        });
        ExampleRequestQueue.add(ExampleRequest);



    }


    public void postData() {
        api=new Api();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject object = new JSONObject();
        try {
String ItemCode=""+itemcode.getText().toString();
String ItemName=""+itemname.getText().toString();
String PrintName=""+Printname.getText().toString();
String HsnSac=""+Hsncode.getText().toString();
String SellingRate=""+Sellingprice.getText().toString();
String Description=""+Remarks.getText().toString();
//            Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
//            object.put("ItemID",""+checkusername);
            object.put("ItemCode",ItemCode);
            object.put("ItemName",""+ItemName);
            object.put("PrintName",""+PrintName);
            object.put("ItemType",""+goodSpinnerName);
            object.put("HsnSac",""+HsnSac);
            object.put("SellingRate",""+SellingRate);
            object.put("GstID",""+selectedtaxidID);
            object.put("UnitID",""+selectedunitidID);
            object.put("Stock",""+stackID);
            object.put("Serial",""+serialId);
            object.put("Description",""+Description);
            object.put("UserID",""+na);
            object.put("OrganizationID",""+pa);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url =Api.additem;

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




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}