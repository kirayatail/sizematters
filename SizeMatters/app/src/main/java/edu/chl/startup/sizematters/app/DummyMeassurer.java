package edu.chl.startup.sizematters.app;

import android.app.Activity;

/**
 * Created by horv on 03/05/14.
 */
public class DummyMeassurer {

    public static boolean startMeassure(Activity a){
        return true;
    }

    public static double[] stopMeassure(Activity a){
        return new double[] {Math.random() * 100, Math.random() * 100, Math.random() * 100};
    }

}
