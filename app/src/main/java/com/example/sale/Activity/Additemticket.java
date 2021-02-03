package com.example.sale.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.example.sale.Model.ItemData;
import com.example.sale.R;
import com.example.sale.Util.ToastUtils;
import com.example.sale.Util.adderView;
import com.google.android.material.textfield.TextInputEditText;

import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Additemticket extends AppCompatActivity{
    TextView Accesriesinfobtn, digitalsbtn, Officialbtn;
    @Bind(R.id.adderview)
    adderView adderview;
    Toolbar toolbar;
    int itempriceint;
    int totalprice;
    int qty1=1;
    List<String> stat = new ArrayList<>();
    List<String> itemid = new ArrayList<>();
    List<String> itemname = new ArrayList<>();
    List<String> itemprice = new ArrayList<>();
    DBHelper dbHelper;
    String id, na, pa;
    TextInputEditText qty;
    MaterialSpinner status, itemadd, Unit, stack, serial;
    String goodstatName, selecteditemaddID, selecteditemaddName, selecteditemaddprice;
    Api api;
    int priceper;
    ImageView IDProf, IDProf1, IDProf2;
    private String Document_img1 = "";
    public static final String KEY_User_Document1 = "doc1";
    String mCurrentPhotoPath;
    TextView tv_multi_selection;
    Bitmap bitmap, bitmap1, bitmap2;
    String imagestring, imagestring1, imagestring2;
    List<ItemData> mCustomerList = new ArrayList<>();
Button login_btn,add;
String ticketid,custamername,custameraddress;
     TextInputEditText price;
    ArrayList<HashMap<String,String>> arrayList;
    ListView simpleListView;
    RelativeLayout rel;
    TextView CustomerName,CustomerAddress,total;
    int Totalamount=0;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additemticket);


    Accesriesinfobtn = findViewById(R.id.personalinfobtn);
    digitalsbtn = findViewById(R.id.Bussinessbtn);
    rel = findViewById(R.id.rel);
    price = findViewById(R.id.price);


    Accesriesinfobtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rel.setVisibility(View.VISIBLE);
            Accesriesinfobtn.setVisibility(View.VISIBLE);

            Accesriesinfobtn.setTextColor(getResources().getColor(R.color.white));
            digitalsbtn.setTextColor(getResources().getColor(R.color.primary_dark));


        }
    });

    digitalsbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            Accesriesinfobtn.setVisibility(View.INVISIBLE);
            rel.setVisibility(View.GONE);
//            office.setVisibility(View.GONE);
            Accesriesinfobtn.setTextColor(getResources().getColor(R.color.primary_dark));
            digitalsbtn.setTextColor(getResources().getColor(R.color.white));
//            Officialbtn.setTextColor(getResources().getColor(R.color.grey));

        }
    });




    simpleListView=(ListView)findViewById(R.id.simpleListView);
          ButterKnife.bind(this);
   arrayList=new ArrayList<>();

        dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        Intent i=getIntent();
        ticketid=i.getStringExtra("ticketid");
        custamername=i.getStringExtra("custamername");
        custameraddress=i.getStringExtra("custameraddress");

    CustomerName=findViewById(R.id.CustomerName);
    CustomerAddress=findViewById(R.id.CustomerAddress);
    total=findViewById(R.id.total);

    CustomerName.setText(""+custamername);
    CustomerAddress.setText(""+custameraddress);



        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        status = findViewById(R.id.status);
        itemadd = findViewById(R.id.itemadd);
    add = findViewById(R.id.add);
    adderview=findViewById(R.id.adderview);

        login_btn = findViewById(R.id.login_btn);

    add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Totalamount=Totalamount+totalprice;
                total.setText(""+Totalamount);
                    HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
                    hashMap.put("itemname",selecteditemaddName);
                     hashMap.put("itemid",selecteditemaddID);
                    hashMap.put("status",goodstatName);
                    hashMap.put("price",""+totalprice);
                    hashMap.put("qty",""+qty1);
                    arrayList.add(hashMap);//add the hashmap into arrayList

                loadCustomer();
//                Intent i=new Intent(Additemticket.this,Addimage.class);
//                i.putExtra("ticketid",""+ticketid);
//                i.putExtra("status",""+goodstatName);
//                i.putExtra("price",""+totalprice);
//                i.putExtra("qty",""+qty1);
//                startActivity(i);
            }
        });

    qty1=1;
    //monitor
    adderview.setOnValueChangeListene(new adderView.OnValueChangeListener() {
        @Override
        public void onValueChange(int value) {
            ToastUtils.show(Additemticket.this, value + "");
            qty1=value;
            totalprice=qty1*itempriceint;
            price.setText(""+totalprice);
        }
    });
    login_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


//            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
//            hashMap.put("itemname",selecteditemaddName);
//            hashMap.put("status",goodstatName);
//            hashMap.put("price",""+totalprice);
//            hashMap.put("qty",""+qty1);
//            arrayList.add(hashMap);//add the hashmap into arrayList
//
//            loadCustomer();
                Intent i=new Intent(Additemticket.this,Addimage.class);
                i.putExtra("ticketid",""+ticketid);
                i.putExtra("custamername",""+custamername);
                i.putExtra("custameraddress",""+custameraddress);
                 i.putExtra("map", arrayList);
                startActivity(i);
        }
    });
//        tv_multi_selection = findViewById(R.id.tv_multi_selection);
//        IDProf = (ImageView) findViewById(R.id.IdProf);
//        IDProf1 = (ImageView) findViewById(R.id.IdProf1);
//        IDProf2 = (ImageView) findViewById(R.id.IdProf2);
//        SimpleImageSelect.Config(this, "Choose Picture from", "FolderName");
//        tv_multi_selection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.example.ticket.fileprovider")
////                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
////                        .isMultiSelect()
////                        .setMinimumMultiSelectCount(3)
////                        .setMaximumMultiSelectCount(6)
////                        .build();
////                pickerDialog.show(getSupportFragmentManager(), "picker");
//            }
//        });


        stat.add("Completed");
        stat.add("Pending");

        getitem();
        ArrayAdapter<String> _Adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stat);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(_Adapter1);


//        qty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String qtyper = String.valueOf(s);
////                int i= Integer.parseInt(qtyper);
////                int totalprice=(i*priceper);
//                try {
//                    qty1=NumberFormat.getInstance().parse(qtyper).intValue();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        status.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    goodstatName = stat.get(i).toString();
                } else {
                    goodstatName = "";
                }
            }
        });

        itemadd.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    selecteditemaddID = itemid.get(i).toString();
                    selecteditemaddName = itemname.get(i).toString();
                    selecteditemaddprice = itemprice.get(i).toString();
                    try {
                        itempriceint= NumberFormat.getInstance().parse(selecteditemaddprice).intValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
              price.setText("" + itempriceint);

                } else {
                    selecteditemaddID = "";
                    selecteditemaddName = "";
                    selecteditemaddprice = "";

                }
            }


        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Additemticket.this, Sidemenu.class);
                startActivity(i);
            }
        });

    }

    public void getitem() {
        api = new Api();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();
        try {
          // SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
          // Date todayDate = new Date();
          // String thisDate = currentDate.format(todayDate);
          // Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
            object.put("UserID", "" + na);
            object.put("OrganizationID", "" + pa);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = Api.getitem;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);
                            JSONArray jObject1 = response.getJSONArray("data");
                            for (int i = 0; i < jObject1.length(); i++) {

                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                itemid.add(jsonObject.getString("ID"));
                                itemname.add(jsonObject.getString("ItemName"));
                                itemprice.add(jsonObject.getString("SellingRate"));

                            }
                            ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Additemticket.this, android.R.layout.simple_spinner_item, itemname);
                            _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            itemadd.setAdapter(_Adapter);
//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public void loadCustomer() {
        String[] from={"itemname","qty","price"};//string array
        int[] to={R.id.itemname,R.id.qty,R.id.price};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.allitemlist,from,to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);

        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                ImageView del=view.findViewById(R.id.del);




                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(i);
                        loadCustomer();

                    }
                });


//                Toast.makeText(getApplicationContext(),fruitsNames[i],Toast.LENGTH_LONG).show();//show the selected image in toast according to position
            }
        });

    }

}