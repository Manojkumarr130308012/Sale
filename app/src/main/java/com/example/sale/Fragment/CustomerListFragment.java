package com.example.sale.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sale.Adapter.CustomerListRecyclerViewAdapter;
import com.example.sale.Api.Api;
import com.example.sale.Config.DBHelper;
import com.example.sale.Model.CustomerData;
import com.example.sale.Model.ItemData;
import com.example.sale.R;
import com.example.sale.Util.AsyncTaskNetCheck;
import com.example.sale.Util.INetworkCallBack;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerListFragment extends Fragment implements INetworkCallBack {
    DBHelper dbHelper;
    String id,na,pa;
    private OnListFragmentInteractionListener mListener;
    List<CustomerData> mCustomerList = new ArrayList<>();
    CustomerListRecyclerViewAdapter mRcvAdapter;
    //private ProgressDialog pDialog = null;
    private AVLoadingIndicatorView pNewDialog = null;
    Context mContext;
//    APIInterface apiInterface;
    SharedPreferences mSharedPreference;
    RelativeLayout mLytNoNetwork;
    LinearLayout mLytDetails;
    Button mTryAgain;
    TextView mTxtNoNetText;
    RecyclerView mRcyCustomerList;
    RelativeLayout rootLayout;
    SearchView searchView;
    Api api;
    public CustomerListFragment() {
    }


    public static final String TAG = CustomerListFragment.class.getName();

    public static Fragment createInstance(Context context) {
        return Fragment.instantiate(context, CustomerListFragment.class.getName(), null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
//        apiInterface = APIClient.getClient().create(APIInterface.class);
        mRcvAdapter = new CustomerListRecyclerViewAdapter(mCustomerList, mListener, mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        mLytNoNetwork = view.findViewById(R.id.lyt_network_fail);
        mLytDetails = view.findViewById(R.id.lyt_details);

        dbHelper=new DBHelper(getActivity());
        Cursor res = dbHelper.getAllData();
api=new Api();
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        mTryAgain = view.findViewById(R.id.btn_try_again);
        mTxtNoNetText = view.findViewById(R.id.txt_display);
        mRcyCustomerList = view.findViewById(R.id.list);
        rootLayout = view.findViewById(R.id.root_layout);
        pNewDialog = view.findViewById(R.id.loading_progress);
        pNewDialog.setVisibility(View.GONE);
        mRcyCustomerList.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyCustomerList.setAdapter(mRcvAdapter);
        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetandLoadData();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkNetandLoadData();
    }

    public void checkNetandLoadData() {
        new AsyncTaskNetCheck(mContext, this, false).execute();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListPaymentInteraction(CustomerData item);

        void onListOrderInteraction(CustomerData item);
    }

    public void loadCustomer() {



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


        String url =Api.Customerlisturl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd",""+ response);
                            JSONArray jObject1 = response.getJSONArray("data");
                            for(int i=0;i<jObject1.length();i++){
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                CustomerData addobj = new CustomerData();
                                addobj.setCustomerID(jsonObject.optString("ID"));
                                addobj.setCustomername(jsonObject.optString("CustomerName"));
                                addobj.setCustomerMobile(jsonObject.optString("Mobile"));
                                addobj.setCustomerCityName(jsonObject.optString("City"));
                                addobj.setCustomerOSAmount(jsonObject.optString("Outstanding"));
                                mCustomerList.add(addobj);

                            }
                            SetCustomerListAdapter(mCustomerList);
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

        dismissDialog();


    }

    public void SetCustomerListAdapter(List<CustomerData> _ArrayList) {
        mRcvAdapter.updateList(_ArrayList);
        if(searchView != null){
            if(!searchView.getQuery().toString().trim().isEmpty())
                mRcvAdapter.getFilter().filter(searchView.getQuery().toString().trim());
        }

    }

    private void dismissDialog() {
        try {
            if (pNewDialog != null && pNewDialog.isShown()) {
                pNewDialog.hide();
                pNewDialog.setVisibility(View.GONE);
            }
            //pNewDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchViewMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mRcvAdapter != null) {
                    mRcvAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

    }

    @Override
    public void NetWorkCallBackResultTryAgain(boolean result) {

    }

    @Override
    public void NetWorkCallBackResultDismiss(boolean result) {
        if (result) {
            mLytNoNetwork.setVisibility(View.GONE);
            mLytDetails.setVisibility(View.VISIBLE);
            loadCustomer();
        } else {
            mLytNoNetwork.setVisibility(View.VISIBLE);
            mLytDetails.setVisibility(View.GONE);
        }
    }

    public void showResponseFailSnackBar(View root, String snackTitle, final int clickactiontype) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT).setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickactiontype == 1) {
                    loadCustomer();
                }
            }
        });
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_VERTICAL);
    }
//    public void callAddCustomerActivity() {
//        Intent intent = new Intent(mContext, AddCustomerActivity.class);
//        mContext.startActivity(intent);
//    }
}
