package com.example.hd.rizolve;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hd on 28/3/16.
 */
public class Data_Model_Notifications {


    public String title;
    public String postedBy;
    public String description;



    public Data_Model_Notifications(String title, String postedBy,String description) {
        this.title = title;
        this.postedBy = postedBy;
        this.description = description;

    }



    public Data_Model_Notifications(JSONObject object) {

        Log.i("hagga", "Datamodel Constructor Called");
        try {
            this.title = object.getString("title");
            this.description = object.getString("description");
            this.postedBy = object.getString("postedBy");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("hagga", "Datamodel Constructor Finished");
    }


    public static ArrayList<Data_Model_Notifications> fromJson(JSONArray jsonObjects) {

        Log.i("hagga", "FromJSONcalled" );
        ArrayList<Data_Model_Notifications> gradesData = new ArrayList<Data_Model_Notifications>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                gradesData.add(new Data_Model_Notifications(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("hagga", "FromJSONFinished");
        return gradesData;
    }
}
