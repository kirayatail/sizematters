package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by horv on 03/05/14.
 */
public class DataHandler {
    private static final String PREFS_NAME = "DH_PREFS";
    private static final String ID = "ID";


    public static int getNextID(Activity a){
        SharedPreferences sp = a.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int id = sp.getInt(ID, 0);

        SharedPreferences.Editor editor = sp.edit();
        id++;
        editor.putInt(ID, id);
        editor.apply();

        return id;
    }
}
