package com.example.brittany.hcd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Symptom_entry_list extends AppCompatActivity {
    ListView symptomTitles;
    TitleAdapter titleAdapter;
    ArrayList<String> titlesArray = new ArrayList<>();
    Symptom s;

    String date;
    ParseQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_entry_list);

       Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date");
        query = new ParseQuery("Symptom_Diary");
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> entryList, ParseException e) {

                if (e == null) {
                    Log.d("score", "Retrieved " + entryList.size() + " scores");

                    for (ParseObject dealsObject : entryList) {
                        //how to display the string things
                        //Log.d("thing", "Description: "+ dealsObject.getString("ImageDescription")+"\n");

                        //format
                        DateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy");
                        String output = outputFormatter.format(dealsObject.getCreatedAt());
                        //Log.d("thing", "Date: " + output);

                        if (date.equals(output)) {
                            String n = (String)dealsObject.get("SymptomName");
                            String d = (String)dealsObject.get("SymptomDescription");
                            String i = (String)dealsObject.get("PainLevel");
                            Integer p = Integer.parseInt(i);
                            String dur = (String)dealsObject.get("Duration");
                            Integer delta = Integer.parseInt(dur);

                            s = new Symptom(n,d,p,delta);

                            //Log.d("poop","Symptom Name: "+ n );
                            titlesArray.add(n);

                        }

                    }
//might have to change layout thing. But let's check if it goes to the same screen.
                    titleAdapter = new TitleAdapter(Symptom_entry_list.this, R.layout.activity_symptom_entry_list_row, titlesArray);
                    symptomTitles = (ListView) findViewById(R.id.symptom_entryView);
                    symptomTitles.setItemsCanFocus(false);
                    symptomTitles.setAdapter(titleAdapter);

                    symptomTitles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                final int position, long id) {

                            //lead to add specific symptom activity. (update at every edit.)
//                            Log.i("List View Clicked", "**********");
//                            Toast.makeText(Symptom_entry_list.this,
//                                    "List View Clicked:" + position, Toast.LENGTH_LONG)
//                                    .show();
                            Intent intent = new Intent(getApplicationContext(), Specific_Symptom_click.class);
                            intent.putExtra("Symptom",s);

                            startActivity(intent);


                        }
                    });


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_symptom_entry_list, menu);
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
