package com.example.brittany.hcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Food_Save_details extends AppCompatActivity {

    ImageView PhotoCapturedImageView;
    ParseFile photoFile;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__save_details);

        bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
/*
            //For parsefile
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] scaledData = bos.toByteArray();

            photoFile = new ParseFile("meal_photo.jpg", scaledData);
            photoFile.saveInBackground();
*/

            PhotoCapturedImageView = (ImageView)findViewById(R.id.foodView);
            PhotoCapturedImageView.setImageBitmap(bmp);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void saveFoodEntry(View view){

        EditText name = (EditText)findViewById(R.id.nameText);
        EditText description = (EditText)findViewById(R.id.dText);

        String n = check(name.getText().toString());

        if(n != null && description != null){
            // Create a New Class called "ImageUpload" in Parse
            ParseObject imgupload = new ParseObject("Food_Diary");

            // Create a column named "ImageName" and set the string
            //imgupload.put("ImageName", name.getText().toString());
            imgupload.put("ImageDescription", description.getText().toString());

            //For parsefile
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] scaledData = bos.toByteArray();

            //insert parse function here

            photoFile = new ParseFile(n+".jpg", scaledData);
            photoFile.saveInBackground();

            // Create a column named "ImageFile" and insert the image
            imgupload.put("ImageFile", photoFile);

            // Create the class and the columns
            imgupload.saveInBackground();


            // Get the date made, turn into nice string
            // http://stackoverflow.com/questions/9629636/get-todays-date-in-java-at-midnight-time
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

            // PREF
            SharedPreferences prefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String _username = prefs.getString("username","");

            // Master Table
            Temp_Table item = new Temp_Table();
            item.setPhoto(photoFile); // Photo
            item.setName(description.getText().toString().trim()); // Description
            item.setName2(sdf.format(date).trim()); // date
            item.setUsername(_username); // username
            item.setType("Food"); // Category
            item.saveInBackground();

            // Show a simple toast message
            Toast.makeText(Food_Save_details.this, "Image Uploaded",
                    Toast.LENGTH_SHORT).show();}

    }

    public String check(String fileName){

        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(fileName);
        boolean b = m.find();
        if(b){
            Toast.makeText(Food_Save_details.this, "DO NOT INPUT SPECIAL CHARACTERS",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        fileName.replaceAll("\\s+", "_");
        Log.d("new", fileName);
        return fileName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food__save_details, menu);
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
