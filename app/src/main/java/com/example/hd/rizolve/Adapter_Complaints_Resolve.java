package com.example.hd.rizolve;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by tarun on 29/3/16.
 */
public class Adapter_Complaints_Resolve extends RecyclerView.Adapter<Adapter_Complaints_Resolve.ViewHolder>{

    static String serverAddress;
    static RequestQueue myQueue;
    final int duration = Toast.LENGTH_LONG;
    JSONObject complaint_details;
    Globals global;
    Context context;
    Activity parent;

    private ArrayList<Data_Model_Complaints> ComplaintsData ;


    public Adapter_Complaints_Resolve(JSONObject ndata,Activity a,Context c) {
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
        parent = a;

        //Log.i("hagga1",ndata.toString());
        //Log.i("hagga1",ComplaintsData.get(1).date);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public CheckBox isresolved;
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
            isresolved = (CheckBox) v.findViewById(R.id.is_seen_box);
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
    public Adapter_Complaints_Resolve.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_complaints_resolve, parent, false);
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
        if(is_resolved==0){
            holder.isresolved.setChecked(false);
        }

        else if(is_resolved==1){
            holder.isresolved.setChecked(true);
        }

        holder.isresolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.isresolved = 1;
                holder.isresolved.setChecked(true);
                int complaint_id = item.complaint_id;
                String url_upvote = serverAddress.concat("/complaint/complaint_resolve.json?complaint_id=").concat(String.valueOf(complaint_id));

                FragmentManager fm = parent.getFragmentManager();
                ResolveFragment dialogFragment = new ResolveFragment();
                dialogFragment.show(fm, "conformation");


                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET, url_upvote, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

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


        final int complaint_id = item.complaint_id;
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.up_vote = item.up_vote+1;
                String upvote = String.valueOf(Integer.parseInt(holder.upvote.getText().toString())+1);

                holder.upvote.setText(upvote);
                holder.upvote.setClickable(false);

                String url_upvote = serverAddress.concat("/complaint/up_vote.json?complaint_id=").concat(String.valueOf(complaint_id));

                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_upvote,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                  ;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


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
                Log.i("haggaxx","getting here3");
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