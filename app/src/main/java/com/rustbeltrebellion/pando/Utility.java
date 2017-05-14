package com.rustbeltrebellion.pando;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.Array;


/**
 * Created by brad on 5/5/17.
 */

public class Utility {

    public Utility() {
        Log.d("TAG", "Constructor ran...");
    }

    public static final String PREFERENCES_FILE = "@strings/preferences";

    public static void checkSettings(Context activity_activity) {

        SharedPreferences settings = activity_activity.getSharedPreferences(PREFERENCES_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();

        if (!settings.contains("REINDEX")) {
            // set the default settings

            // reindex if by the odd chance there are things to find
            editor.putString("REINDEX", "yes");

            Log.d("TAG", "SharedPreferences REINDEX : did not exist");

            editor.commit();

        }
    }

    public static void checkIndex(Context activity_activity) {

        SharedPreferences settings = activity_activity.getSharedPreferences(PREFERENCES_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();

        if (settings.getString("key", "REINDEX").equals("yes")) {

            // read the file list and generate a book index
            String[] book_files = activity_activity.fileList();

            for (String book_file : book_files) {

                Log.d("TAG", book_file);
            }



            // put the index in the settings

            //editor.putStringSet();


            editor.putString("REINDEX", "no");

            editor.commit();

        }
    }

    public static void saveBookJson(String json) {

        //Save a book
        //File f = new File("/data/data/com.rustbeltrebellion.pando/shared_prefs/Name_of_your_preference.xml");
        //if (f.exists())
        //Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
        //else
        //Log.d("TAG", "Setup default preferences");

    }


}
