package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PageFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PAGE = "ARG_PAGE";
    private String type;

    private CustomParseQueryAdapter mAdapter;
    String _username;


    public static PageFragment create(int page, String type){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("type", type);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        _username = prefs.getString("username","");

        type = getArguments().getString("type");
        this.setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
                             View rootView = inflater.inflate(R.layout.page_fragment, container, false);
    mAdapter = new CustomParseQueryAdapter(getActivity());
    setListAdapter(mAdapter);
    mAdapter.loadObjects();

    return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    // Temp_Table Custom
    public class CustomParseQueryAdapter extends ParseQueryAdapter<Temp_Table> {
        public CustomParseQueryAdapter(Context context){
            super(context, new QueryFactory<Temp_Table>(){
                public ParseQuery create(){

                    ParseQuery query = new ParseQuery("Temp_Table");
                    query.whereEqualTo("Type", type); // Make sure inserts in correct category
                    query.whereEqualTo("username",_username); // search login user

                    query.orderByAscending("createdAt");

                    // Today - Midnight; 00:00 is the first minute of the next day
                    Calendar date = new GregorianCalendar();
                    // reset hour, minutes, seconds and millis
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);
                    Date midnight = date.getTime();

                    // Today - Before Midnight; 23:59 is the last minute of the day
                    date.set(Calendar.HOUR_OF_DAY, 23);
                    date.set(Calendar.MINUTE, 59);
                    date.set(Calendar.SECOND, 59);
                    date.set(Calendar.MILLISECOND, 59);
                    Date beforemidnight = date.getTime();
                    query.whereGreaterThan("createdAt", midnight);
                    query.whereLessThan("createdAt", beforemidnight);


//            USE FOR LAST MONTH DATA - IF want to change up report
//
//            // Today - Midnight; 00:00 is the first minute of the next day
//            Calendar date = new GregorianCalendar();
//
//            // Get Last month - FirstDay
//            date.set(Calendar.MONTH,date.get(Calendar.MONTH)-1); // Last Month
//            date.set(Calendar.DAY_OF_MONTH,1); // first day
//            // reset hour, minutes, seconds and millis
//            date.set(Calendar.HOUR_OF_DAY, 0);
//            date.set(Calendar.MINUTE, 0);
//            date.set(Calendar.SECOND, 0);
//            date.set(Calendar.MILLISECOND, 0);
//            Date firstdayMonth = date.getTime();
//
//            // Get Last month - LastDay
//            date.set(Calendar.MONTH,date.get(Calendar.MONTH)-1); // Last Month
//            date.set(Calendar.DAY_OF_MONTH,31); // Last day
//            // reset hour, minutes, seconds and millis
//            date.set(Calendar.HOUR_OF_DAY, 23);
//            date.set(Calendar.MINUTE, 59);
//            date.set(Calendar.SECOND, 59);
//            date.set(Calendar.MILLISECOND, 59);
//            Date lastdayMonth = date.getTime();
//            query.whereGreaterThan("createdAt", firstdayMonth);
//            query.whereLessThan("createdAt", lastdayMonth);


                    return query;
                }

            });
        }


        @Override
        public View getItemView(Temp_Table item, View v, ViewGroup parent){

            if (v == null){
                v = View.inflate(getContext(), R.layout.health_description_tags, null);
            }

            super.getItemView(item, v, parent);

            ParseImageView Image = (ParseImageView)v.findViewById(R.id.Image);
            ParseFile imageFile = item.getPhoto();
            if (imageFile != null){
                Image.setParseFile(imageFile);
                Image.loadInBackground();
            }
            else{
                // Default Picture if no image is found
                ImageView iv = (ImageView)v.findViewById(R.id.Image);
                if(type.contains("Symptom")) {
                    iv.setImageResource(R.drawable.symp);
                }
                if(type.contains("Exercise")) {
                    iv.setImageResource(R.drawable.exercise);
                }
                if(type.contains("Push Up")) {
                    iv.setImageResource(R.drawable.exercisepushup);
                }
            }

            TextView field = (TextView)v.findViewById(R.id.Name);
            field.setText(item.getName());


            TextView field2 = (TextView)v.findViewById(R.id.Name2);
            field2.setText(String.valueOf(item.getName2()));

            return v;
        }

    }



}
