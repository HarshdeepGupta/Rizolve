package com.example.hd.rizolve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hd on 28/3/16.
 */
public class Adapter_Notifications extends RecyclerView.Adapter<Adapter_Notifications.ViewHolder> {
    private ArrayList<Data_Model_Notifications> notificationsData;
    Context context;

    public Adapter_Notifications(JSONArray ndata) {
        notificationsData = Data_Model_Notifications.fromJson(ndata);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView description;
        public TextView postedBy;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.notification_title);
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
        holder.title.setText(item.title);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationsData.size();
    }


}
