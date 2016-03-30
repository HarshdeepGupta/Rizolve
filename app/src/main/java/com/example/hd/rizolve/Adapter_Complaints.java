package com.example.hd.rizolve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tarun on 29/3/16.
 */
public class Adapter_Complaints extends RecyclerView.Adapter<Adapter_Complaints.ViewHolder>{


    static String serverAddress;
    static RequestQueue myQueue;
    final int duration = Toast.LENGTH_LONG;
    Globals global;
    Context context;
    //we pass the complaints_data to the next activity
    JSONObject complaint_details;
    Activity parent;

    private ArrayList<Data_Model_Complaints> ComplaintsData ;


    public Adapter_Complaints(JSONObject ndata,Activity a,Context c) {

        JSONArray object1 = null;
        JSONArray object2 = null;
        try {
            object1 = ndata.getJSONArray("complaints");
            object2 = ndata.getJSONArray("users");

        }catch (JSONException e) {
            e.printStackTrace();
        }
        ComplaintsData = Data_Model_Complaints.fromJson(object1, object2);

        context = c;
        parent = a;
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
        public Button upvote;
        public Button downvote;
        public ImageView resolve_photo;
        public ViewHolder(final View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.complaint_title);
            description = (TextView) v.findViewById(R.id.complaint_description);
            postedBy = (TextView) v.findViewById(R.id.complaint_posted_by);
            createdAt = (TextView) v.findViewById(R.id.complaint_created_at);
            upvote = (Button) v.findViewById(R.id.complain_upvote);
            downvote = (Button) v.findViewById(R.id.complain_downvote);
            cardView = (CardView) v.findViewById(R.id.complaint_card_view);
            resolve_photo = (ImageView) v.findViewById(R.id.resolve_photo);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_Complaints.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_complaints, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Data_Model_Complaints item =  ComplaintsData.get(position);
        String a = "At: "+item.date;
        String b = "By: "+item.name;
        String c = String.valueOf(item.up_vote);
        String d = String.valueOf(item.down_vote);
        holder.title.setText(item.title);
        holder.createdAt.setText(a);
        holder.postedBy.setText(b);
        holder.description.setText(item.description);
        holder.upvote.setText(c);
        holder.downvote.setText(d);
        int is_resolved = item.isresolved;
        if(is_resolved==1) {
            Log.i("haggax1","1");
            holder.resolve_photo.setImageResource(R.drawable.right);
        }
        if(is_resolved==0) {
            Log.i("haggax1","0");
            holder.resolve_photo.setImageResource(R.drawable.wrong);
        }
        final int complaint_id = item.complaint_id;
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url_check_upvote = serverAddress.concat("/complaint/check_if_vote.json?complaint_id=").concat(String.valueOf(complaint_id));
                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_check_upvote,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("hagga3","response");
                        boolean if_already_checked=false;
                        try {
                            if_already_checked = response.getBoolean("success");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(if_already_checked==false){
                            item.up_vote = item.up_vote+1;
                            String upvote = String.valueOf(Integer.parseInt(holder.upvote.getText().toString())+1);
                            Log.i("hagga3", upvote);
                            holder.upvote.setText(upvote);
                            //holder.upvote.setClickable(false);

                            String url_upvote = serverAddress.concat("/complaint/up_vote.json?complaint_id=").concat(String.valueOf(complaint_id));

                            JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_upvote,null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i("hagga3","response");
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
                        }

                        else if(if_already_checked==true){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Network Error", duration);
                        toast.show();
                    }
                }) ;
                myQueue.add(request1);

            }
        });

        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_check_upvote = serverAddress.concat("/complaint/check_if_vote.json?complaint_id=").concat(String.valueOf(complaint_id));
                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url_check_upvote,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        boolean if_already_checked=false;
                        try {
                            if_already_checked = response.getBoolean("success");
                            Log.i("haggaxx",String.valueOf(if_already_checked));
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(if_already_checked==false){
                            item.down_vote = item.down_vote+1;
                            String downvote = String.valueOf(Integer.parseInt(holder.downvote.getText().toString()) + 1);
                            holder.downvote.setText(downvote);
                            //holder.downvote.setClickable(false);
                            String url_downvote = serverAddress.concat("/complaint/down_vote.json?complaint_id=").concat(String.valueOf(complaint_id));

                            JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_downvote,null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

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
                        }

                        else if(if_already_checked==true){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Network Error", duration);
                        toast.show();
                    }
                }) ;
                myQueue.add(request1);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Log.i("haggaxx","getting here1");
                String url_complaints_detail = serverAddress.concat("/complaint/complaint_data.json?complaint_id=").
                        concat(String.valueOf(complaint_id));

                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_complaints_detail,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        complaint_details = response;
                        Intent intent = new Intent(v.getContext(),ComplaintsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("complaint_details", complaint_details.toString());
                        bundle.putString("title",item.title);
                        bundle.putString("postedBy",item.name);
                        bundle.putString("created_at",item.date);
                        bundle.putString("description",item.description);
                        bundle.putString("upvote",String.valueOf(item.up_vote));
                        bundle.putString("downvote", String.valueOf(item.down_vote));
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
                Log.i("haggaxx","getting here2");
                myQueue.add(request0);
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ComplaintsData.size();
    }

}