package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewSymptom extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView painView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_symptom);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        painView = (TextView)findViewById(R.id.pain_view);

        painView.setText(Integer.toString(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress=0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                        progress=progressValue;
                        painView.setText(Integer.toString(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

    }


    public void save_symptom(View view){

        EditText name = (EditText)findViewById(R.id.edit_name);
        EditText description = (EditText)findViewById(R.id.edit_description);
        EditText duration = (EditText)findViewById(R.id.edit_duration);

        if(name != null && description != null){
            // Create a New Class called "ImageUpload" in Parse
            ParseObject symptom_entry = new ParseObject("Symptom_Diary");

            // Create a column named "ImageName" and set the string
            symptom_entry.put("SymptomName", name.getText().toString());
            symptom_entry.put("SymptomDescription", description.getText().toString());
            symptom_entry.put("PainLevel", painView.getText().toString());
            symptom_entry.put("Duration",duration.getText().toString());

            // Create the class and the columns
            symptom_entry.saveInBackground();


            // PREF
            SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String _username = prefs.getString("username","");

            // Master Table
            Temp_Table item = new Temp_Table();
            item.setName("Issue: " + name.getText().toString()); // Symp name
            item.setName2("Deets: " + description.getText().toString()); // Description
            item.setUsername(_username); // username
            item.setType("Symptom"); // Category
            item.saveInBackground();

            // Show a simple toast message
            Toast.makeText(NewSymptom.this, "Symptom Uploaded!",
                    Toast.LENGTH_SHORT).show();}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_symptom, menu);
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
