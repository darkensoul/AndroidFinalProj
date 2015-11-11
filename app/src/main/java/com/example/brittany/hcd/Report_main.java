package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Report_main extends AppCompatActivity {
    ViewPager pager;
    ArrayList<String> types;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main);

        pager = (ViewPager)findViewById(R.id.pager);
        final ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());


        types = new ArrayList<String>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Types");
        query.addAscendingOrder("Type");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject type : parseObjects) {
                        String Type = type.getString("Type");
                        types.add(Type);
                    }
                    pager.setAdapter(adapter);
                }
            }
        });
    }
    // Creates a categorized tabbed for report
    private class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
        public ViewPagerFragmentAdapter(FragmentManager fm){
            super(fm);

        }

        @Override
        public Fragment getItem(int position){
            return PageFragment.create(position+1, types.get(position).toString());
        }


        @Override
        public int getCount(){
            return types.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return types.get(position).toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
