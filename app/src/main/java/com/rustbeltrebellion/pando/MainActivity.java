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

    public boolean checkIndex() throws FileNotFoundException {
        Log.d("TAG", "In checkIndex.");


        List<String> results = new ArrayList<String>();

        SharedPreferences settings = getSharedPreferences(this.preferencesFile, 0);

        File[] files = new File("/data/user/0/com.rustbeltrebellion.pando/files").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }


        settings.edit().putInt("bookCount",results.size()).commit(); // set the stored value to false
        this.bookCount = settings.getInt("bookCount", 0);

        this.bookCount = 5; // mocking having files...

        LinearLayout buttonsParentView = (LinearLayout) findViewById(R.id.booksList0);


        for (int bookRowCount = 0; bookRowCount < this.bookCount; bookRowCount++) {
            LinearLayout bookRow = new LinearLayout(this);
            bookRow.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.FILL_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout buttonColumns = new LinearLayout(this);
            buttonColumns.setOrientation(LinearLayout.HORIZONTAL);
            for (int buttonColumn = 0; buttonColumn < 2; buttonColumn++) {

                Button bookButton = new Button(this);

                bookButton.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                if (buttonColumn == 0) {
                    String bookTitle = "bookTitle[" + bookRowCount + "]";
                    bookButton.setText(bookTitle);
                    bookButton.setId(bookRowCount + 100); // limits IDs to 100 static predefined app elements
                }
                else {
                    bookButton.setText("Delete");
                    bookButton.setId(bookRowCount + 10000); // limits the library to 9,900 books and same number of bookButton IDs
                }

                buttonColumns.addView(bookButton);

            }
            bookRow.addView(buttonColumns);

            bookRow.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

            buttonsParentView.addView(bookRow);
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
