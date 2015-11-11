package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.LogInCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends ActionBarActivity {

    private EditText usernameview;
    private EditText passwordview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameview = (EditText) findViewById(R.id.username);
        passwordview = (EditText) findViewById(R.id.password);

    }
    @Override
    protected void onResume() {
        super.onResume();
        initParse(); // another way without using class
    }
    private void initParse() {
        try
        {
            // Enable Local Datastore.
            Parse.enableLocalDatastore(this);
            ParseObject.registerSubclass(Temp_Table.class); // Have to register a subclass before using it!!!
            Parse.initialize(this, "9lsXvAhxazTezFl8oTEhCnGr3p9S0qNetMNgmmgR", "ZYWsX8HmLCoEBFkfwZuPljn2VNiaqDomcMbkFIrk");
            ParseInstallation.getCurrentInstallation().saveInBackground();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void Login_clicked(View view)
    {
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        boolean validationerror =false;
        StringBuilder validationerrormessage = new StringBuilder("Please ");

        if(isEmpty(usernameview)) {
            validationerror = true;
            validationerrormessage.append("enter a username");
        }
        if(isEmpty(passwordview)) {
            if(validationerror) {
                validationerrormessage.append(" and ");
            }
            validationerror = true;
            validationerrormessage.append("enter a password");
        }
        validationerrormessage.append(".");

        if(validationerror){
            Toast.makeText(MainActivity.this,validationerrormessage.toString(),Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog dig = new ProgressDialog(this);
        dig.setTitle("Please wait.");
        dig.setMessage("Signing in. Please wait.");
        dig.show();

        final ParseUser user = new ParseUser();
        user.setUsername(usernameview.getText().toString());
        user.setPassword(passwordview.getText().toString());

        user.logInInBackground(usernameview.getText().toString(), passwordview.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        dig.dismiss();
                        if (e != null) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent_login = new Intent(MainActivity.this, UserMainPage.class);
                            // intent_login.putExtra("usernameintent", usernameview.getText().toString());
                            intent_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            // PREFERENCES FILE and SUCCESSFUL LOGIN
                            SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", usernameview.getText().toString().trim());
                            editor.commit();

                            startActivity(intent_login);
                        }
                    }
                });
    }

    private boolean isEmpty(EditText ettext)
    {
        if(ettext.getText().toString().trim().length() > 0){
            return false;
        }else {
            return true;
        }
    }

    private boolean isMatching(EditText text1 , EditText text2)
    {
        if(text1.getText().toString().equals(text2.getText().toString())){
            return true;
        }else{
            return false;
        }
    }
    public void Create_account_click(View view)
    {
        Intent intent_caccount = new Intent(this, Create_user.class);
        startActivity(intent_caccount);
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
