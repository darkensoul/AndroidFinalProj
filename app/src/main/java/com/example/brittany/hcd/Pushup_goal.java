package com.example.brittany.hcd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;

public class Pushup_goal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_goal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pushup_goal, menu);
        return true;
    }
    public void Goal_entered(View view)
    {
        EditText goal = (EditText) findViewById(R.id.editText_goal);


        try {
            int number = Integer.parseInt(goal.getText().toString());
            if( number == 0 )
            {
                Toast t = Toast.makeText(this, "You can do more than that. Come on, push it!", Toast.LENGTH_SHORT);
                t.show();
                return;

            }

            // Go to the position screen
            Intent intent5 = new Intent(this,Position_pushup.class);
            intent5.putExtra("parameter_name", number);

            startActivity(intent5);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast t = Toast.makeText(this, "You have to put something!", Toast.LENGTH_SHORT);
            t.show();
        }

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
