package com.example.brittany.hcd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class Specific_Symptom_click extends AppCompatActivity {
    Symptom incoming;
    String roll;
    String date;

    TextView symptomName;
    EditText editName;
    TextView painText;
    SeekBar painBar;
    EditText editDuration;
    EditText editDescription;


    //========================TESTING NEEDED===================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific__symptom_click);

        Intent intent = this.getIntent();
        roll = intent.getStringExtra("Symptom");
        String[] unroll = roll.split(",");
        incoming = new Symptom(unroll[0],unroll[1],Integer.parseInt(unroll[2]),Integer.parseInt(unroll[3]),unroll[4],unroll[5]);
        date = incoming.getDate();
            //if incoming is not null, initialize everything.
            //initial symptom title
            symptomName = (TextView)findViewById(R.id.symptom_title);
            symptomName.setText(incoming.getName());
//                                        Toast.makeText(Specific_Symptom_click.this,
//                                    incoming.getName(), Toast.LENGTH_LONG)
//                                    .show();

            editName = (EditText) findViewById(R.id.edit_name);
            editName.setText(incoming.getName(), TextView.BufferType.EDITABLE);

            //initial painLevels
            painText = (TextView) findViewById(R.id.pain_view);
            painText.setText(Integer.toString(incoming.getPainLevel()));
            painBar = (SeekBar) findViewById(R.id.seekBar);
            painBar.setProgress(incoming.getPainLevel());

        painBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress = incoming.getPainLevel();

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                        progress = progressValue;
                        painText.setText(Integer.toString(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

            //initialize duration
            editDuration = (EditText) findViewById(R.id.edit_duration);
            editDuration.setText(Integer.toString(incoming.getDuration()));

            //initialize description
            editDescription = (EditText) findViewById(R.id.symptomDescription);
            editDescription.setText(incoming.getDescription());

    }

    public void edit_symptom(View v){

        ParseObject point = ParseObject.createWithoutData("Symptom_Diary", incoming.getId());
        point.put("SymptomName", editName.getText().toString());
        point.put("PainLevel", painText.getText().toString());
        point.put("Duration", editDuration.getText().toString());
        point.put("SymptomDescription", editDescription.getText().toString());

        point.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.d("thing", "Saved Successfully");
                                    Toast.makeText(Specific_Symptom_click.this,
                                    "Update Successful", Toast.LENGTH_SHORT)
                                    .show();
                }
                else{
                    Log.d("thing", "Save Failed");
                }

            }
        });

    }

    public void delete_symptom(View v){

        ParseQuery q = ParseQuery.getQuery("Symptom_Diary");
        q.getInBackground(incoming.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e == null){
                    object.deleteInBackground();
                    Log.d("thing", "Annihilation");
                    Toast.makeText(Specific_Symptom_click.this,
                            "Delete Successful", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(Specific_Symptom_click.this, Symptom_entry_list.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
                else{
                    Log.d("thing", "Destruction unsuccessful");
                }
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_specific__symptom_click, menu);
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
