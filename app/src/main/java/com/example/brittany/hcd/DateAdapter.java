package com.example.brittany.hcd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Britt-Britt on 11/7/2015.
 */
public class DateAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> data = new ArrayList<>();

    public DateAdapter(Context context, int layoutResourceId,
                             ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View row = convertView;
        UserHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.dateName = (TextView) row.findViewById(R.id.dateView);
            row.setTag(holder);
        }
        else{

            holder = (UserHolder)row.getTag();
        }
        String date = data.get(position);
        holder.dateName.setText(date);

       return row;
    }

    static class UserHolder {
        TextView dateName;
    }

}
