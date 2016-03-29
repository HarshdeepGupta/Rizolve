package com.example.hd.rizolve;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by hd on 28/3/16.
 */
public class Data_Model_Notifications {


    public String title;
    public String postedBy;
    public String description;
    public String created_at;
    public String complaint_id;
    public String notification_id;





    public Data_Model_Notifications(String title, String postedBy,String description) {
        this.title = title;
        this.postedBy = postedBy;
        this.description = description;

    }



    public Data_Model_Notifications(JSONObject object) {


        try {
            String created = object.getString("created_at");
            String description = object.getString("description");
            String complaint_id = object.getString("complaint_id");
            String notification_id = object.getString("id");
            String title = object.getString("title");
            Document doc = Jsoup.parse(description);
            org.jsoup.select.Elements links = doc.select("a");
            String name = links.get(0).text();
            this.postedBy = name;
            this.description = doc.text();
            this.created_at = created;
            this.complaint_id = complaint_id;
            this.notification_id = notification_id;
            this.title = title;
            } catch (JSONException e) {
                e.printStackTrace();
            }


    }


    public static ArrayList<Data_Model_Notifications> fromJson(JSONArray jsonObjects) {

        ArrayList<Data_Model_Notifications> gradesData = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                gradesData.add(new Data_Model_Notifications(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return gradesData;
    }
}
