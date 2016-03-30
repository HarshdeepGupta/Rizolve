package com.example.hd.rizolve;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    static String serverAddress;
    static RequestQueue volleyQueue;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Globals global;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private JSONObject userComplains,hostelComplains,instiComplains,notificationData;

    public JSONObject getUserComplains(){
        return userComplains;
    }
    public JSONObject getHostelComplains(){
        return hostelComplains;
    }
    public JSONObject getInstiComplains(){
        return instiComplains;
    }
    public JSONObject getNotificationData(){
        return notificationData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get the data from the intent
        Intent intent = getIntent();
        try{
            userComplains = new JSONObject(intent.getStringExtra("UserComplains"));
            hostelComplains = new JSONObject(intent.getStringExtra("HostelComplains"));
            instiComplains = new JSONObject(intent.getStringExtra("InstiComplains"));
            notificationData = new JSONObject(intent.getStringExtra("NotificationList"));
        }
        catch (JSONException e){

        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        global = ((Globals) this.getApplication());
        serverAddress = global.getServerAddress();
        volleyQueue = global.getVolleyQueue();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        final Context context = getApplicationContext();
//        final int duration = Toast.LENGTH_LONG;
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.Logout) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //Click Listener for logout
    public void logout_method(MenuItem item) {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    //Click Listener for logout
    public void logout(MenuItem item) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;


        String url_logout = serverAddress.concat("/default/logout.json");
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_logout,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast toast = Toast.makeText(context, "Logged Out", duration);
                toast.show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                toast.show();
            }
        }) ;

        volleyQueue.add(request1);

    }

    public void call_resolve(MenuItem item) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;

        Log.i("hagga1","getting here200");
        String url_resolving = serverAddress.concat("/default/all_complaints_to_be_resolved.json");
        Log.i("hagga1","getting here100");
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_resolving,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("hagga1", "getting here");
                success_callback(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                toast.show();
            }
        }) ;

        volleyQueue.add(request1);

    }

    public void success_callback(JSONObject response){
        Log.i("hagga1","getting here");
        Intent intent = new Intent(this,resolveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("complaints", response.toString());
        intent.putExtras(bundle);
        startActivity(intent);
        Log.i("hagga1", "getting here000");
    }

    public void add_complaint(MenuItem item){
        Intent intent = new Intent(this,add_complaint.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new FragmentNotifications();
                    break;
                case 1:
                    fragment = new FragmentIndividual();
                    break;
                case 2:
                    fragment = new FragmentHostel();
                    break;
                case 3:
                    fragment = new FragmentInstitute();
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Notifications";
                case 1:
                    return "My Complains";
                case 2:
                    return "Hostel Complains";
                case 3:
                    return "Institute Complains";
            }
            return null;
        }
    }
}
