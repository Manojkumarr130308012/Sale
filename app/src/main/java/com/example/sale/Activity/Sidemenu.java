package com.example.sale.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.sale.Config.DBHelper;
import com.example.sale.Fragment.DashboardFragment;
import com.example.sale.Fragment.TicketListFragment;
import com.example.sale.Model.TicketData;
import com.example.sale.R;
import com.google.android.material.navigation.NavigationView;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class Sidemenu extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    DBHelper dbHelper;
    String id,na,pa;
    Fragment fragment;
    FragmentTransaction ft;
    DashboardFragment dashboardFragment;
    TicketListFragment customerListFragment;
    ActionBarDrawerToggle drawerToggle;
    MaterialSpinner spinner;
    List<String> orgid = new ArrayList<>();
    List<String> orgname = new ArrayList<>();
    String selectedorgID;
    String selectedorgName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        View headerView = nvDrawer.inflateHeaderView(R.layout.nav_header_main);
        spinner=headerView.findViewById(R.id.spinner);

        orgid.add("1");
        orgid.add("2");
        orgname.add("test1");
        orgname.add("test2");
        ArrayAdapter<String> _Adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, orgname);
        _Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(_Adapter1);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    selectedorgID = orgid.get(i).toString();
                    selectedorgName = orgname.get(i).toString();
                } else {
                    selectedorgID = "";
                    selectedorgName = "";
                }
            }


        });
        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        loadDbHomefragment();
    }


    private void loadDbHomefragment() {
        Class fragmentClass;
        fragmentClass = TicketListFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
        // Set action bar title
        setTitle("Dashboard");
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = TicketData.class;
                break;
//            case R.id.nav_second_fragment:
//                fragmentClass = CustomerListFragment.class;
//                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = Allitems.class;
//                break;
            case R.id.action_logout:
           dbHelper.deleteRow();
          Intent i=new Intent(Sidemenu.this,Login.class);
          startActivity(i);
                break;
            default:
                fragmentClass = DashboardFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // ...
}