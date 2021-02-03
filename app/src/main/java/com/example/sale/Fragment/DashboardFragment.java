package com.example.sale.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sale.Activity.Login;
import com.example.sale.Api.Api;
import com.example.sale.Config.DBHelper;
import com.example.sale.R;
import com.example.sale.Util.AsyncTaskNetCheck;
import com.example.sale.font.FontFace;
import com.example.sale.font.OpenSansFontUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
View view;
    public static final String TAG = DashboardFragment.class.getName();
    Context mContext;
    String message;
    SharedPreferences mSharedPreference;
    TextView mTxtSales, mTxtCollection,mTxtExpense;
    TextView mTxtSalesHeader, mTxtCollectionHeader,mTxtExpenseHeader;
    TextView mTxtSaleslbl, mTxtCollectionlbl,mTxtExpenselbl;
    OpenSansFontUtils mFontClass;
    RelativeLayout mLytNoNetwork;
    LinearLayout mLytDetails;
    Button mTryAgain;
    TextView mTxtNoNetText;
    DBHelper dbHelper;
    String id,na,pa;
    //private ProgressDialog pDialog = null;
    private AVLoadingIndicatorView pNewDialog = null;
    Api api;

    public DashboardFragment() {
        // Required empty public constructor
    }


    public static Fragment createInstance(Context context) {
        return Fragment.instantiate(context, DashboardFragment.class.getName(), null);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_dashboard, container, false);

        dbHelper=new DBHelper(getActivity());
        Cursor res = dbHelper.getAllData();
api=new Api();
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }


        mLytNoNetwork = view.findViewById(R.id.lyt_network_fail);
        mLytDetails = view.findViewById(R.id.lyt_details);
        mTryAgain = view.findViewById(R.id.btn_try_again);
        mTxtNoNetText = view.findViewById(R.id.txt_display);
        mTxtSales = view.findViewById(R.id.txt_day_sale);
        mTxtCollection = view.findViewById(R.id.txt_today_collection);
        mTxtExpense = view.findViewById(R.id.txt_today_expense);

        mTxtSalesHeader = view.findViewById(R.id.txt_day_title);
        mTxtCollectionHeader = view.findViewById(R.id.txt_collection_title);
        mTxtExpenseHeader = view.findViewById(R.id.txt_expense_title);
        mTxtSaleslbl = view.findViewById(R.id.txt_day_lbl);
        mTxtCollectionlbl = view.findViewById(R.id.txt_collection_lbl);
        mTxtExpenselbl = view.findViewById(R.id.txt_expense_lbl);

        pNewDialog = view.findViewById(R.id.loading_progress);
        pNewDialog.setVisibility(View.GONE);

//        mFontClass.setTextView(mTxtSales, FontFace.SEMIBOLD);
//        mFontClass.setTextView(mTxtCollection, FontFace.SEMIBOLD);
//        mFontClass.setTextView(mTxtExpense, FontFace.SEMIBOLD);
//
//        mFontClass.setTextView(mTxtSalesHeader, FontFace.BOLD);
//        mFontClass.setTextView(mTxtCollectionHeader, FontFace.BOLD);
//        mFontClass.setTextView(mTxtExpenseHeader, FontFace.BOLD);
//
//        mFontClass.setTextView(mTxtSaleslbl, FontFace.SEMIBOLD);
//        mFontClass.setTextView(mTxtCollectionlbl, FontFace.SEMIBOLD);
//        mFontClass.setTextView(mTxtExpenselbl, FontFace.SEMIBOLD);
//        mFontClass.setTextView(mTxtNoNetText, FontFace.SEMIBOLD);
//        mFontClass.setButton(mTryAgain, FontFace.SEMIBOLD);

        loadDashBoard();
        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDashBoard();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDashBoard();
//        checkNetandLoadData();
    }

//    public void checkNetandLoadData() {
//        new AsyncTaskNetCheck(mContext, this, false).execute();
//    }

    public void loadDashBoard() {
        pNewDialog.setVisibility(View.VISIBLE);
        pNewDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject object = new JSONObject();
        try {
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date todayDate = new Date();
//            String thisDate = currentDate.format(todayDate);
//            Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
            object.put("UserID",""+na);
            object.put("OrganizationID",""+pa);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url =Api.Dashboardurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd",""+ response);
                            mTxtSales.setText( (String) response.get("CustomerBalance")+ " Rs");
                    mTxtCollection.setText( (String) response.get("ActiveCustomers"));
//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);

        pNewDialog.setVisibility(View.GONE);

    }




}
