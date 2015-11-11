package com.example.brittany.hcd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class Food_Diary_Takeimage extends AppCompatActivity {

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView PhotoCapturedImageView;
    private Bitmap rotatedScaledMealImage;
    private ParseFile photoFile;
    View b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__diary__takeimage);

        b = findViewById(R.id.saveButton);
        b.setVisibility(View.GONE);
        PhotoCapturedImageView = (ImageView)findViewById(R.id.imageView);

    }

    public void takePhoto(View view){
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            //Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");

            //attempt to scale and rotate image.
            /*Bitmap mealImageScaled = Bitmap.createScaledBitmap(photoCaptureBitmap, 200, 200
            * photoCaptureBitmap.getHeight()/photoCaptureBitmap.getWidth(), false);*/

            // Override Android default landscape orientation and save portrait
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            rotatedScaledMealImage = Bitmap.createBitmap(photoCaptureBitmap, 0,
                    0, photoCaptureBitmap.getWidth(), photoCaptureBitmap.getHeight(),
                    matrix, true);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            PhotoCapturedImageView.setImageBitmap(rotatedScaledMealImage);
            //end attempt

            //on save method
            byte[] scaledData = bos.toByteArray();

            // Save the scaled image to Parse
            photoFile = new ParseFile("meal_photo.jpg", scaledData);

            photoFile.saveInBackground();
            /*
            // Create a New Class called "ImageUpload" in Parse
            ParseObject imgupload = new ParseObject("ImageUpload");

            // Create a column named "ImageName" and set the string
            imgupload.put("ImageName", "AndroidBegin Logo");

            // Create a column named "ImageFile" and insert the image
            imgupload.put("ImageFile", photoFile);

            // Create the class and the columns
            imgupload.saveInBackground();*/

            // Show a simple toast message
            Toast.makeText(Food_Diary_Takeimage.this, "Image Uploaded",
                    Toast.LENGTH_SHORT).show();
            b.setVisibility(View.VISIBLE);
            //PhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food__diary__takeimage, menu);
        return true;
    }
    public void Save_food_details(View view)
    {
       /* Intent intent_food = new Intent(this, Food_Save_details.class);
        startActivity(intent_food);*/

        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            rotatedScaledMealImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            rotatedScaledMealImage.recycle();

            //Pop intent
            Intent in1 = new Intent(this, Food_Save_details.class);
            in1.putExtra("image", filename);
            startActivity(in1);
        } catch (Exception e) {
            e.printStackTrace();
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
