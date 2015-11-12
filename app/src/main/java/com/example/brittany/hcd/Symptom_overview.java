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
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Symptom_overview extends AppCompatActivity {
    ListView dateList;
    DateAdapter dateAdapter;
    ArrayList<String> dateArray = new ArrayList<>();

    ParseQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_overview);

        //grab data from database.
        query = new ParseQuery("Symptom_Diary");
        query.orderByDescending("createdAt");
        query.addDescendingOrder("SymptomName");
        //add items to arraylist
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("date", "Retrieved " + scoreList.size() + " dates");
                    String previous = "date";
                    for (ParseObject dealsObject : scoreList) {
                        //how to display the string things
                        //Log.d("thing", "Description: "+ dealsObject.getString("ImageDescription")+"\n");

                        //format
                        DateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy");
                        String output = outputFormatter.format(dealsObject.getCreatedAt());

                        if (!previous.equals(output)) {

                            dateArray.add(output);
                            Log.d("thing", "Date: " + previous);
                            previous = output;
                        }


                    }

                    dateAdapter = new DateAdapter(Symptom_overview.this, R.layout.activity_symptom_overview_row, dateArray);
                    dateList = (ListView) findViewById(R.id.listView);
                    dateList.setItemsCanFocus(false);
                    dateList.setAdapter(dateAdapter);

                    dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                final int position, long id) {
//                            Log.i("List View Clicked", "**********");
//                            Toast.makeText(Symptom_overview.this,
//                                    "List View Clicked:" + position, Toast.LENGTH_LONG)
//                                    .show();
                            String date = ((TextView) v.findViewById(R.id.dateView)).getText().toString();
                            Intent intent = new Intent(getApplicationContext(),Symptom_entry_list.class );
                            intent.putExtra("date", date);
                            startActivity(intent);

                        }
                    });


//                   for (int i = 0; i < dateArray.size(); i++) {
//                        Log.d("array", "Array " + dateArray.get(i));
//                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_symptom_overview, menu);
        return true;
    }

    public void Edit_clicked(View view)
    {
        Intent intent_edit_1 = new Intent(this, Specific_Symptom_click.class);
        startActivity(intent_edit_1);
    }
    public void delete_clicked(View view)
    {
        Intent intent_delete_1 = new Intent(this, Specific_Symptom_click.class);
        startActivity(intent_delete_1);
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
