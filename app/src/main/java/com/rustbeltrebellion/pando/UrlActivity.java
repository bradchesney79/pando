package com.rustbeltrebellion.pando;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
    }

    public boolean writeStory(String storyText) throws FileNotFoundException {
        Date date = null;
        long unixTime = date.getTime() /1000;
        //System.out.println(unixTime );//<- prints 1352504418

        String FILENAME = "storyfile" + Long.toString(unixTime);


        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

        try {
            fos.write(storyText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }




    }
}
