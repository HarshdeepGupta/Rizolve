package com.example.hd.rizolve;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplaintsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JSONObject complaintDetails;
    String  postedBy, createdAt, upvote, downvote,title,description,id;
    Globals global;
    static String serverAddress;
    static RequestQueue myQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent  = getIntent();
        //Get data from the intent
        global = ((Globals) this.getApplication());
        myQueue = global.getVolleyQueue();
        serverAddress = global.getServerAddress();

        try{

            Log.i("hagga","here1");

            complaintDetails = new JSONObject(intent.getStringExtra("complaint_details"));
            postedBy = intent.getStringExtra("postedBy");
            title = intent.getStringExtra("title");
            createdAt = intent.getStringExtra("created_at");
            description =  intent.getStringExtra("description");
            upvote = intent.getStringExtra("upvote");
            downvote = intent.getStringExtra("downvote");
            id = intent.getStringExtra("id");

        }
        catch (JSONException e){

        }


        setContentView(R.layout.activity_complaints);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        mAdapter = new Adapter_Comment(complaintDetails);
        //Log.i("hagga", complaints_data.toString());

        mRecyclerView.setAdapter(mAdapter);

        TextView Title;
        TextView description_;
        TextView posted_By;
        TextView created_At;
        final Button upvote_;
        final Button downvote_;

        Title = (TextView) findViewById(R.id.complain_detail_title);
        description_ = (TextView) findViewById(R.id.complain_detail_description);
        posted_By = (TextView) findViewById(R.id.complain_detail_posted_by);
        created_At = (TextView) findViewById(R.id.complain_detail_created_at);
        upvote_ = (Button) findViewById(R.id.complain_detail_upvote);
        downvote_ = (Button) findViewById(R.id.complain_detail_downvote);


        String a = createdAt;
        String b = "By: " + postedBy;
        String c = String.valueOf(upvote);
        String d = String.valueOf(downvote);
        Title.setText(title);
        created_At.setText(a);
        posted_By.setText(b);
        description_.setText(description);
        upvote_.setText(c);
        downvote_.setText(d);
        final String complaint_id = id;
        final int duration = Toast.LENGTH_LONG;


        upvote_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String upvote = String.valueOf(Integer.parseInt(upvote_.getText().toString())+1);
                Log.i("hagga3",upvote);
                upvote_.setText(upvote);
                upvote_.setClickable(false);

                String url_upvote = serverAddress.concat("/complaint/up_vote.json?complaint_id=").concat(String.valueOf(complaint_id));

                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_upvote,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("hagga3","response");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Network Error", duration);
                        toast.show();
                    }
                }) ;
                //Add the first request in the queue
                myQueue.add(request0);
            }
        });

        downvote_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String downvote = String.valueOf(Integer.parseInt(downvote_.getText().toString()) + 1);
                downvote_.setText(downvote);
                downvote_.setClickable(false);
                String url_downvote = serverAddress.concat("/complaint/down_vote.json?complaint_id=").concat(String.valueOf(complaint_id));

                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url_downvote,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Network Error", duration);
                        toast.show();
                    }
                }) ;
                //Add the first request in the queue
                myQueue.add(request0);
            }
        });


    }
}
