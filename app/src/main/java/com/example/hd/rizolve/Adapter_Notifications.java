package com.example.hd.rizolve;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hd on 28/3/16.
 */
public class Adapter_Notifications extends RecyclerView.Adapter<Adapter_Notifications.ViewHolder> {
    private ArrayList<Data_Model_Notifications> notificationsData;


    public Adapter_Notifications(JSONObject ndata) {
        JSONArray ndata1 = null;
        try{
          ndata1 = ndata.getJSONArray("notifications");
        } catch (JSONException e) {
          e.printStackTrace();
        }
        notificationsData = Data_Model_Notifications.fromJson(ndata1);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView description;
        public TextView postedBy;
        public TextView createdAt;
        public CardView cardView;
        public ViewHolder(final View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.notification_title);
            description = (TextView) v.findViewById(R.id.notification_description);
            postedBy = (TextView) v.findViewById(R.id.notification_posted_by);
            createdAt = (TextView) v.findViewById(R.id.notification_created_at);
            cardView = (CardView) v.findViewById(R.id.notification_card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(v.getContext(),ComplaintsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Data_Model_Notifications item =  notificationsData.get(position);

        String a = "At: "+item.created_at;
        String b = "By: "+item.postedBy;
        holder.title.setText(item.title);
        holder.createdAt.setText(a);
        holder.postedBy.setText(b);
        holder.description.setText(item.description);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationsData.size();
    }


}
