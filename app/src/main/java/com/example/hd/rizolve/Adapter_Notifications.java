package com.example.hd.rizolve;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hd on 28/3/16.
 */
public class Adapter_Notifications extends RecyclerView.Adapter<Adapter_Notifications.ViewHolder> {
    private ArrayList<Data_Model_Notifications> notificationsData;

    static String serverAddress;
    static RequestQueue myQueue;
    final int duration = Toast.LENGTH_LONG;
    JSONObject complaint_details;

    Globals global;
    Context context;

    public Adapter_Notifications(JSONObject ndata,Activity a,Context c) {
        JSONArray ndata1 = null;
        try{
            ndata1 = ndata.getJSONArray("notifications");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notificationsData = Data_Model_Notifications.fromJson(ndata1);
        context = c;
        global = ((Globals) a.getApplication());
        serverAddress = global.getServerAddress();
        myQueue = global.getVolleyQueue();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView title;
        public TextView description;
        public TextView postedBy;
        public TextView createdAt;
        public CheckBox isseen;
        public ViewHolder(final View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.notification_title);
            description = (TextView) v.findViewById(R.id.notification_description);
            postedBy = (TextView) v.findViewById(R.id.notification_posted_by);
            createdAt = (TextView) v.findViewById(R.id.notification_created_at);
            isseen = (CheckBox) v.findViewById(R.id.is_seen_box);
            cardView = (CardView) v.findViewById(R.id.notification_card_view);

        }


    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_Notifications.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_notification, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Data_Model_Notifications item =  notificationsData.get(position);
        int is_seen = item.is_seen;
        String a = "At: "+item.created_at;
        String b = "By: "+item.postedBy;
        holder.title.setText(item.title);
        holder.createdAt.setText(a);
        holder.postedBy.setText(b);
        holder.description.setText(item.description);
        if(is_seen==1) {
            holder.isseen.setChecked(true);
        }
        else if(is_seen==0){
            holder.isseen.setChecked(false);
        }

        holder.isseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.is_seen = 1;
                holder.isseen.setChecked(true);
                int notification_id = Integer.parseInt(item.notification_id);
                String url_upvote = serverAddress.concat("/default/is_seen.json?notification_id=").concat(String.valueOf(notification_id));
                Log.i("haggax",url_upvote);
                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET, url_upvote, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("hagga3", "response");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Network Error", duration);
                        toast.show();
                    }
                });
                //Add the first request in the queue
                myQueue.add(request0);

            }

        });

        final String complaint_id = item.complaint_id;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Log.i("haggaxx","getting here3");
                String url_complaints_detail = serverAddress.concat("/complaint/complaint_data.json?complaint_id=").
                        concat(String.valueOf(complaint_id));

                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_complaints_detail,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        complaint_details = response;

                        JSONObject temp;
                        temp = null;
                        String up = null;
                        String down = null;
                        try {
                            temp = complaint_details.getJSONObject("complaint");
                            up = String.valueOf(temp.getInt("up_vote_"));
                            down = String.valueOf(temp.getInt("down_vote_"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        Intent intent = new Intent(v.getContext(),ComplaintsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("complaint_details", complaint_details.toString());
                        bundle.putString("title",item.title);
                        bundle.putString("postedBy",item.postedBy);
                        bundle.putString("created_at",item.created_at);
                        bundle.putString("description",item.description);
                        bundle.putString("upvote",String.valueOf(up));
                        bundle.putString("downvote", String.valueOf(down));
                        bundle.putString("id", String.valueOf(item.complaint_id));
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Network Error", duration);
                        toast.show();
                    }
                }) ;
                //Add the first request in the queue
                myQueue.add(request0);
                Log.i("haggaxx", "getting here4");
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationsData.size();
    }


}
