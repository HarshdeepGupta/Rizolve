package com.example.hd.rizolve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tarun on 29/3/16.
 */
public class Adapter_Comment extends RecyclerView.Adapter<Adapter_Comment.ViewHolder>{

    private ArrayList<Data_Model_Comment> CommentsData;
    Context context;

    public Adapter_Comment(JSONObject ndata) {
        CommentsData = Data_Model_Comment.fromJson(ndata);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView postedBy;
        public TextView description;
        public TextView time_elapsed;
        public ViewHolder(View v) {
            super(v);
            postedBy = (TextView) v.findViewById(R.id.comment_posted_by);
            description = (TextView) v.findViewById(R.id.comment_description);

            time_elapsed = (TextView) v.findViewById(R.id.comment_time_elapsed);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_Comment.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_comment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Data_Model_Comment item =  CommentsData.get(position);
        holder.time_elapsed.setText(item.time_elapsed);
        holder.description.setText(item.description);
        holder.postedBy.setText(item.posted_by);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return CommentsData.size();
    }

}