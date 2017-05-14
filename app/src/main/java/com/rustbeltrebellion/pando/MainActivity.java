package com.rustbeltrebellion.pando;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity {

    public Boolean reindexBooks = false;

    public final String PREFERENCES_FILE = "preferences";
    
    //public String pandoFilesDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(this.PREFERENCES_FILE, 0);
        //this.pandoFilesDir = toString(valueOf(getFilesDir()));

        Log.d("TAG", "this.reindexBooks");
        Log.d("TAG", valueOf(this.reindexBooks));
        Log.d("TAG", "trying user settings first");
        if(settings.contains(this.PREFERENCES_FILE)) {
            this.reindexBooks = settings.getBoolean("reindexBooks", true);
        }
        else {
            settings.edit().putBoolean("reindexBooks", true).commit();

            this.reindexBooks = settings.getBoolean("reindexBooks", true);


            Log.d("TAG", "setting defaults");
        }
        Log.d("TAG", "this.reindexBooks:");
        Log.d("TAG", valueOf(this.reindexBooks));


        if (this.reindexBooks) {

            Log.d("TAG", "In reindexBooks");

            try {
                checkIndex();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            settings.edit().putBoolean("reindexBooks", false).commit();
            this.reindexBooks = settings.getBoolean("reindexBooks", true);

        }
        Log.d("TAG", "this.reindexBooks:");
        Log.d("TAG", valueOf(this.reindexBooks));
        Log.d("TAG", "Init Finished.");


        // create selectable list (with trash icon... [[{{fileObject}}.delete()]] ) from book index


    }

    public boolean checkIndex() throws FileNotFoundException {
        Log.d("TAG", "In checkIndex.");


        List<String> results = new ArrayList<String>();

        File[] files = new File("/data/user/0/com.rustbeltrebellion.pando/files").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }

        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.FILL_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 4; j++) {
                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                btnTag.setText("Button " + (j + 1 + (i * 4 )));
                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
            }

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

            layout.addView(row);
        }
        //setContentView(layout);
        //setContentView(R.layout.main);


        Log.d("TAG", "checkIndex End.");
        if(results.size()>0) {
            return true;
        }else {
            return false;
        }



    }

    public void LoadStory(View view) {
        Intent intent = new Intent(this, LoadActivity.class);
        startActivity(intent);
    }

    public void LoadSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        // And put the SharedPreferences test here
    }

}
