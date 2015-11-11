package com.example.brittany.hcd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.SignUpCallback;
public class Create_user extends AppCompatActivity {

    private EditText usernameview;
    private EditText passwordview;
    private EditText PasswordAgainview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // get value from edit text
        usernameview = (EditText) findViewById(R.id.name);
        passwordview = (EditText)findViewById(R.id.password_1);
        PasswordAgainview = (EditText)findViewById(R.id.password_2);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

    public void Create_user_clicked(View view)
    {
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
        if(!isMatching(passwordview,PasswordAgainview)) {
            if(validationerror) {
                validationerrormessage.append(" and ");
            }
            validationerror = true;
            validationerrormessage.append("enter the same password twice");
        }
        validationerrormessage.append(".");

        if(validationerror){
            Toast.makeText(this,validationerrormessage.toString(),Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog dig = new ProgressDialog(this);
        dig.setTitle("Please wait.");
        dig.setMessage("Signing up. Please wait.");
        dig.show();

        ParseUser user = new ParseUser();
        user.setUsername(usernameview.getText().toString());
        user.setPassword(passwordview.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dig.dismiss();
                if (e != null) {
                    Toast.makeText(Create_user.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent7 = new Intent(Create_user.this, UserMainPage.class);
                    intent7.putExtra("usernameintent", usernameview.getText().toString());
                    intent7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    // PREFERENCES FILE and SUCCESSFUL LOGIN
                    SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", usernameview.getText().toString().trim());
                    editor.commit();

                    startActivity(intent7);
                }
            }
        });
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
