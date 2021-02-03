package com.example.sale.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sale.Config.DBHelper;
import com.example.sale.Fragment.CustomerListFragment;
import com.example.sale.Fragment.DashboardFragment;
import com.example.sale.R;
import com.example.sale.font.FontFace;
import com.example.sale.font.OpenSansFontUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends  AppCompatActivity   {
    public static String TAG = MainActivity.class.getName();
    DBHelper dbHelper;
    String id,na,pa;
    Context mContext;
    private FragmentManager mManager;
    Toolbar toolbar;
    SharedPreferences mSharedPreference;
    TextView mTitle;
    ImageView mImgProfile;
    OpenSansFontUtils mFontClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        mImgProfile = findViewById(R.id.imageView);
        setSupportActionBar(toolbar);

        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
       mTitle = toolbar.findViewById(R.id.txt_toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mContext = MainActivity.this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        mFontClass = new OpenSansFontUtils(mContext);
        mFontClass.setTextView(mTitle, FontFace.SEMIBOLD);
//        mSharedPreference = AppSharedPrefrence.getAppPreference(mContext);
//        mUserRepository = new UserRepository(mContext);
        mManager = getSupportFragmentManager();
        final BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        int childCount = menuView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(com.google.android.material.R.id.largeLabel);
            if (activeLabel != null && activeLabel instanceof TextView) {
                mFontClass.setTextView((TextView) activeLabel, FontFace.BOLD);
            }
        }
        mTitle.setText("Dashboard");
        navigationView.setSelectedItemId(R.id.nav_dashboard);
//        mImgProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callProfileActivity();
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_dashboard:
                    CallDashBoardFragment();
                    mTitle.setText("Dashboard");
                    return true;
                case R.id.nav_customer:
                    CallCustomerListFragment();
                    mTitle.setText("Customers");
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                LogoutApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void LogoutApp() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                          dbHelper.deleteRow();
                          Intent i=new Intent(MainActivity.this,Login.class);
                          startActivity(i);
//                                 finish();
                                } catch (Exception e) {
//                                    LogCrashlytics.log(e);
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void callLoginActivity() {
        Intent intent = new Intent(mContext, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

//    public void callProfileActivity() {
//        Intent intent = new Intent(mContext, ProfileActivity.class);
//        mContext.startActivity(intent);
//
//    }

//    public void callOrderActivity(CustomerData obj) {
//        Intent intent = new Intent(mContext, OrderActivity.class);
//        intent.putExtra(BUNDLE_KEY_CUSTOMER_DETAIL, new CustomerDetailObject(obj.getCustomerID(), obj.getCustomername(), obj.getCustomerCityName(), obj.getCustomerMobile(), obj.getCustomerOSAmount()));
//
//        mContext.startActivity(intent);
//
//    }

//    public void callPaymentActivity(CustomerData obj) {
//        Intent intent = new Intent(mContext, ExpenseActivity.class);
//        intent.putExtra(BUNDLE_KEY_CUSTOMER_DETAIL, new CustomerDetailObject(obj.getCustomerID(), obj.getCustomername(), obj.getCustomerCityName(), obj.getCustomerMobile(), obj.getCustomerOSAmount()));
//
//        mContext.startActivity(intent);
//
//    }

    public void CallDashBoardFragment() {

        Fragment fragment = mManager.findFragmentByTag(DashboardFragment.TAG);
        if (fragment == null) {
            fragment = DashboardFragment.createInstance(this);
        }
        FragmentTransaction ft = mManager.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment, DashboardFragment.TAG);
        ft.commit();
    }

    public void CallCustomerListFragment() {

        Fragment fragment = mManager.findFragmentByTag(CustomerListFragment.TAG);
        if (fragment == null) {
            fragment = CustomerListFragment.createInstance(this);
        }
        FragmentTransaction ft = mManager.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment, CustomerListFragment.TAG);
        ft.commit();
        mTitle.setText("Customers");
    }



    @Override
    public void onBackPressed() {
        ExitApp();
        //super.onBackPressed();

    }

    public void ExitApp() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit app?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
