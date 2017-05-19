package com.rustbeltrebellion.pando;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity {

    public Boolean reindexBooks = false;

    public final String preferencesFile = "preferences";

    public int bookCount = 0;
    
    //public String pandoFilesDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(this.preferencesFile, 0);
        //this.pandoFilesDir = toString(valueOf(getFilesDir()));

        Log.d("TAG", "this.reindexBooks");
        Log.d("TAG", valueOf(this.reindexBooks));
        Log.d("TAG", "trying user settings first");
        if(settings.contains(this.preferencesFile)) {
            this.reindexBooks = settings.getBoolean("reindexBooks", true);
            this.bookCount = settings.getInt("bookCount",0);
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

            settings.edit().putBoolean("reindexBooks", false).commit(); // set the stored value to false
            this.reindexBooks = settings.getBoolean("reindexBooks", true); // update the values in the debugger and where I have access to them

        }
        Log.d("TAG", "this.reindexBooks:");
        Log.d("TAG", valueOf(this.reindexBooks));
        Log.d("TAG", "Init Finished.");


        // create selectable list (with trash icon... [[{{fileObject}}.delete()]] ) from book index


    }


    public void checkIndex() throws FileNotFoundException {
        Log.d("TAG", "In checkIndex.");

        List<String> results = new ArrayList<String>();

        SharedPreferences settings = getSharedPreferences(this.preferencesFile, 0);

        File[] files = new File("/data/user/0/com.rustbeltrebellion.pando/files").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        if (files != null && files.length > 0) {
//              for (File file : files) {
//                  //Log.d("TAG", "Creating results, an array of filenames.");
//                  if (file.isFile()) {
//                      //results.add(file.getName());
//                  } else {
//
//                  }
//              }
        }

        results.add(0, "First Book"); // ToDo: remove these place holders
        results.add(1, "Second Book");
        results.add(2, "Third Book");
        results.add(3, "Fourth Book");
        results.add(4, "Fifth Book");
//        results.add(5, "First Book"); // ToDo: remove these place holders
//        results.add(6, "Second Book");
//        results.add(7, "Third Book");
//        results.add(8, "Fourth Book");
//        results.add(9, "Fifth Book");
//        results.add(10, "First Book"); // ToDo: remove these place holders
//        results.add(11, "Second Book");
//        results.add(12, "Third Book");
//        results.add(13, "Fourth Book");
//        results.add(14, "Fifth Book");

        Log.d("TAG", "Generating results finished.");

        settings.edit().putInt("bookCount", results.size()).commit(); // set the stored value to false
        this.bookCount = settings.getInt("bookCount", 0);


        //This is the Layout that is intended to contain the button pairs representing a book to choose or delete
        LinearLayout buttonsParentView = (LinearLayout) findViewById(R.id.booksList0);
        buttonsParentView.setOrientation(LinearLayout.VERTICAL);
        buttonsParentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonsParentView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        buttonsParentView.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        buttonsParentView.setGravity(Gravity.CENTER);
        //buttonsParentView.setGravity(Gravity.FILL_VERTICAL);
        Log.d("TAG", "Creating buttonsParentView.");

        ArrayList<Button> bookButtonList = new ArrayList<Button>();
        int buttonIdCount = 0;
        Log.d("TAG", "Creating bookButtonList array list & explicit counter for buttons.");


        ArrayList<LinearLayout> bookRowColumnList = new ArrayList<LinearLayout>();
        Log.d("TAG", "Creating bookRowColumnList array list.");

        for (int bookRowCount = 0; bookRowCount < this.bookCount; bookRowCount++) {
            Log.d("TAG", "Creating button row layout.");
            // These variable quantity layouts are to contain the side by side book and book delete choices.
            // These layouts containing two buttons will be stacked vertically
            bookRowColumnList.add(bookRowCount, new LinearLayout(this));
            bookRowColumnList.get(bookRowCount).setOrientation(LinearLayout.HORIZONTAL);
            bookRowColumnList.get(bookRowCount).setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.FILL_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            bookRowColumnList.get(bookRowCount).setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
            bookRowColumnList.get(bookRowCount).setVerticalGravity(Gravity.CENTER_VERTICAL);

            Log.d("TAG", "Creating buttons for 2 columns layout.");
            for (int buttonColumn = 0; buttonColumn < 2; buttonColumn++) {
                Log.d("TAG", "In the button creation loop.");


                bookButtonList.add(buttonIdCount, new Button(this));

                bookButtonList.get(buttonIdCount).setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                if (buttonColumn == 0) {
                    String bookTitle = results.get(bookRowCount);
                    bookButtonList.get(buttonIdCount).setText(bookTitle);
                    bookButtonList.get(buttonIdCount).setId(buttonIdCount + 50);
                } else {
                    bookButtonList.get(buttonIdCount).setText("Delete");
                    bookButtonList.get(buttonIdCount).setId(buttonIdCount + 51);
                }

//                btn.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(view v) {
//                        // TODO Whatever you want onclick to do
//                    }
//                });

                bookRowColumnList.get(bookRowCount).addView(bookButtonList.get(buttonIdCount));
                buttonIdCount++;
            }
            bookRowColumnList.get(bookRowCount).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

            buttonsParentView.addView(bookRowColumnList.get(bookRowCount));
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
