package com.example.sale.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sale.Config.DBHelper;
import com.example.sale.R;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FullDetails extends AppCompatActivity {
    DBHelper dbHelper;
    String id, na, pa;
    private Uri fileUri;
    String picturePath;
    public static final String KEY_User_Document1 = "doc1";
    private String Document_img1="";
    String ticketid,status,price,qty;
     int total_price=0;
    Toolbar toolbar;
    String imagestring,imagestring1,imagestring2,custamername,custameraddress;
    ArrayList<HashMap<String,String>> hashMap;
    TextView CustomerName,CustomerAddress,total;
    TextInputEditText phoneno;
    String phone_no,goodstatName;
    Button verifibtn;
    List<String> stat = new ArrayList<>();
    MaterialSpinner statuspay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        CustomerName=findViewById(R.id.CustomerName);
        CustomerAddress=findViewById(R.id.CustomerAddress);
        total=findViewById(R.id.total);
        phoneno=findViewById(R.id.phoneno);
        verifibtn=findViewById(R.id.verifibtn);

        statuspay = findViewById(R.id.status);

        stat.add("Online");
        stat.add("Cash On");


        ArrayAdapter<String> _Adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stat);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statuspay.setAdapter(_Adapter1);



        statuspay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

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
        dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getAllData();
        Intent i=getIntent();
        ticketid=i.getStringExtra("ticketid");
        hashMap = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("map");
        imagestring=i.getStringExtra("image1");
        imagestring1=i.getStringExtra("image2");
        imagestring2=i.getStringExtra("image3");
        custamername=i.getStringExtra("custamername");
        custameraddress=i.getStringExtra("custameraddress");


        Log.e("dddddddddd",""+hashMap);
        CustomerName.setText(""+custamername);
        CustomerAddress.setText(""+custameraddress);

        addHeaders();
        addData();

        Log.e("total_price",""+total_price);

        total.setText("Total:"+total_price);

        verifibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_no=phoneno.getText().toString();
                Intent i = new Intent(FullDetails.this, OtpScreen.class);
                startActivity(i);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FullDetails.this, Addimage.class);
                startActivity(i);
            }
        });



    }









    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setMaxLines(3);
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }





     public void addHeaders() {
     TableLayout tl = findViewById(R.id.table);
     TableRow tr = new TableRow(this);
     tr.setLayoutParams(getLayoutParams());
     tr.addView(getTextView(0, "ITEMNAME", Color.BLACK, Typeface.BOLD,  R.drawable.cel_shape));
     tr.addView(getTextView(1, "QTY", Color.BLACK, Typeface.BOLD,  R.drawable.cel_shape));
     tr.addView(getTextView(2, "STATUS", Color.BLACK, Typeface.BOLD,  R.drawable.cel_shape));
     tr.addView(getTextView(3, "PRICE", Color.BLACK, Typeface.BOLD,  R.drawable.cel_shape));
     tl.addView(tr, getTblLayoutParams());
     }


     public void addData() {
     int numCompanies = hashMap.size();
     TableLayout tl = findViewById(R.id.table);
     for (int i = 0; i < numCompanies; i++) {
     TableRow tr = new TableRow(this);
     tr.setLayoutParams(getLayoutParams());
     tr.addView(getTextView(i + 1,hashMap.get(i).get("itemname") , Color.BLACK, Typeface.NORMAL,  R.drawable.cel_shape));
     tr.addView(getTextView(i + numCompanies, hashMap.get(i).get("qty"), Color.BLACK, Typeface.NORMAL,  R.drawable.cel_shape));
     tr.addView(getTextView(i + numCompanies, hashMap.get(i).get("status"), Color.BLACK, Typeface.NORMAL,  R.drawable.cel_shape));
     tr.addView(getTextView(i + numCompanies, hashMap.get(i).get("price"), Color.BLACK, Typeface.NORMAL,  R.drawable.cel_shape));

         total_price= total_price+Integer.parseInt(hashMap.get(i).get("price"));



     tl.addView(tr, getTblLayoutParams());
     }








}



//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        TextView tv = findViewById(id);
//        if (null != tv) {
//            Log.i("onClick", "Clicked on row :: " + id);
//            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
//        }
//    }
}