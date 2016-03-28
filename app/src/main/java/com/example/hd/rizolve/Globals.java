package com.example.hd.rizolve;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hd on 27/3/16.
 */
public class Globals extends Application {

    private String serverAddress;
    private RequestQueue volleyQueue;

    private String name;
    private String email;

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    private String hostel;
    private int user_type;

    public boolean is_loggedin() {
        return is_loggedin;
    }

    public void setIs_loggedin(boolean is_loggedin) {
        this.is_loggedin = is_loggedin;
    }

    private boolean is_loggedin;


    public String getServerAddress() {
        return serverAddress;
    }


    public RequestQueue getVolleyQueue(){
        if(volleyQueue == null){
            volleyQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return volleyQueue;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}