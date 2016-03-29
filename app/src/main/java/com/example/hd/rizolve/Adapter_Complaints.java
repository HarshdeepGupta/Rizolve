package com.example.hd.rizolve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tarun on 29/3/16.
 */
public class Adapter_Complaints extends RecyclerView.Adapter<Adapter_Complaints.ViewHolder>{

    private ArrayList<Data_Model_Complaints> ComplaintsData ;
    Context context;

    public Adapter_Complaints(JSONObject ndata) {
        JSONArray object1 = null;
        JSONArray object2 = null;
        try {
            object1 = ndata.getJSONArray("complaints");
            object2 = ndata.getJSONArray("users");

        }catch (JSONException e) {
            e.printStackTrace();
        }
        ComplaintsData = Data_Model_Complaints.fromJson(object1,object2);
            //Log.i("hagga1",ndata.toString());
        //Log.i("hagga1",ComplaintsData.get(1).date);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView description;
        public TextView postedBy;
        public TextView createdAt;
        public Button upvote;
        public Button downvote;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.complaint_title);
            description = (TextView) v.findViewById(R.id.complaint_description);
            postedBy = (TextView) v.findViewById(R.id.complaint_posted_by);
            createdAt = (TextView) v.findViewById(R.id.complaint_created_at);
            upvote = (Button) v.findViewById(R.id.complain_upvote);
            downvote = (Button) v.findViewById(R.id.complain_downvote);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Data_Model_Complaints item =  ComplaintsData.get(position);
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
        Button up_vote = holder.upvote;
        Button down_vote = holder.downvote;
        int upvote = Integer.parseInt(up_vote.getText().toString());
        int downvote = Integer.parseInt(down_vote.getText().toString());
        up_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        down_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ComplaintsData.size();
    }

}