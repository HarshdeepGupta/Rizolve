package com.example.hd.rizolve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by tarun on 29/3/16.
 */
public class Adapter_Complaints extends RecyclerView.Adapter<Adapter_Complaints.ViewHolder>{

    static String serverAddress;
    static RequestQueue myQueue;
    final int duration = Toast.LENGTH_LONG;

    Globals global;
    Context context;

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
        global = ((Globals) a.getApplication());
        serverAddress = global.getServerAddress();
        myQueue = global.getVolleyQueue();

        //Log.i("hagga1",ndata.toString());
        //Log.i("hagga1",ComplaintsData.get(1).date);
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
        public ViewHolder(final View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.complaint_title);
            description = (TextView) v.findViewById(R.id.complaint_description);
            postedBy = (TextView) v.findViewById(R.id.complaint_posted_by);
            createdAt = (TextView) v.findViewById(R.id.complaint_created_at);
            upvote = (Button) v.findViewById(R.id.complain_upvote);
            downvote = (Button) v.findViewById(R.id.complain_downvote);
            cardView = (CardView) v.findViewById(R.id.complaint_card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("hagga4","here1");
                    Intent intent = new Intent(v.getContext(),ComplaintsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
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
        final int complaint_id = item.complaint_id;
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.up_vote = item.up_vote+1;
                String upvote = String.valueOf(Integer.parseInt(holder.upvote.getText().toString())+1);
                Log.i("hagga3",upvote);
                holder.upvote.setText(upvote);
                holder.upvote.setClickable(false);

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
        });

        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.down_vote = item.down_vote+1;
                String downvote = String.valueOf(Integer.parseInt(holder.downvote.getText().toString()) + 1);
                holder.downvote.setText(downvote);
                holder.downvote.setClickable(false);
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
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ComplaintsData.size();
    }

}