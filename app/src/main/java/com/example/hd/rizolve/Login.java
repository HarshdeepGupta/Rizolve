package com.example.hd.rizolve;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;


public class Login extends AppCompatActivity {
    static String serverAddress;
    static RequestQueue myQueue;
    String first_name;
    String last_name;
    String hostel;
    String email;
    int usertype;
    static boolean proceed;
    JSONObject userComplains,hostelComplains,instiComplains,notificationData;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    Globals global;
    private ProgressDialog progressBar;
    EditText user ;
    EditText pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);

        manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        global = ((Globals) this.getApplication());

        global.setServerAddress("http://192.168.122.1:8000/complaint1");


        serverAddress = global.getServerAddress();


        myQueue =  global.getVolleyQueue();
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        user.setText("2013ee10505");
        pass.setText("2013ee10505");


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Click Listener for login button
    public void login_method(View view){

        progressBar = new ProgressDialog(view.getContext());
        progressBar.setCancelable(true);
        progressBar.setIndeterminate(true);
        progressBar.setMessage("Logging In ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.show();

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;

        String user_name = user.getText().toString();
        String password = pass.getText().toString();

        //Prepare URLs for sending the requests to the server
        String url_login = serverAddress.concat("/default/login.json?userid=");
        url_login = url_login.concat(user_name).concat("&password=").concat(password);
        String url_notification = serverAddress.concat("/default/notifications.json");
        String url_user_complaints = serverAddress.concat("/default/user_complaints.json");
        String url_hostel_complaints = serverAddress.concat("/default/hostel_complaints.json");
        String url_insti_complaints = serverAddress.concat("/default/institute_complaints.json");

        final JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET,url_insti_complaints,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                instiComplains = response;
                successCallback();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                progressBar.hide();
                toast.show();
            }
        }) ;



        final JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET,url_hostel_complaints,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                hostelComplains = response;
                myQueue.add(request4 );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                progressBar.hide();
                toast.show();
            }
        }) ;


        final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET,url_user_complaints,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                userComplains = response;
                myQueue.add(request3 );
               }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                progressBar.hide();
                toast.show();
            }
        }) ;

        final JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_notification,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                notificationData = response;
                myQueue.add(request2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                progressBar.hide();
                toast.show();
            }
        }) ;

        JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_login,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    proceed = response.getBoolean("success");
                    Log.i("hagga", "gettinghere");
                    if(!proceed){
                        Toast toast = Toast.makeText(context, "Invalid Username or Password", duration);
                        progressBar.hide();
                        toast.show();
                    }
                    else if(proceed){
                        JSONObject details;
                        //Get the user details from the server response
                        details = response.getJSONObject("user");
                        first_name = details.getString("first_name");
                        last_name = details.getString("last_name");
                        email = details.getString("email");
                        usertype = details.getInt("usertype_");
                        hostel = details.getString("hostel");
                        //After getting the user details, set the global variables in app for this session
                        global.setName(first_name.concat(" ").concat(last_name));
                        global.setEmail(email);
                        global.setHostel(hostel);
                        global.setUser_type(usertype);
                        global.setIs_loggedin(true);
                        //add the next request in the queue
                        myQueue.add(request1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "Network Error", duration);
                progressBar.hide();
                toast.show();
            }
        }) ;
        //Add the first request in the queue
        myQueue.add(request0);
    }


    public void successCallback() {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("FIRST_NAME", first_name);
        bundle.putString("LAST_NAME", last_name);
        bundle.putString("NotificationList", notificationData.toString());
        bundle.putString("UserComplains",userComplains.toString());
        bundle.putString("HostelComplains",hostelComplains.toString());
        bundle.putString("InstiComplains",instiComplains.toString());


        //hide progressBar here
        progressBar.hide();

        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}
