package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by max on 2014-05-03.
 */
public class SizeObject implements Serializable {

    private static final String FILE_NAME = "SizeObject";
    private static final String LOG_TAG = "SizeObject";

    private int id;
    private String name;
    private String description;
    private Map <String, Double> measurements;



    public SizeObject(Activity act) {
        this.id = DataHandler.getNextID(act);
        name = "";
        description = "";
        measurements = new HashMap<String, Double>();
    }

    public boolean addMeasurement (String name, double value) {
        if(!this.measurements.containsKey(name)) {
            this.measurements.put(name,value); // Very defensive =)
            return true;
        } else {
            return false;
        }
    }

    public boolean measurementExists (String name) {
        return this.measurements.containsKey(name);
    }

    public boolean removeMeasurement (String name) {
        return measurements.remove(name) != null; // map returns null if it didn't remove anything.

    }

    public boolean updateMeasurement (String name, double value) {
        if(this.measurements.containsKey(name)) {
            this.measurements.put(name,value); // Very defensive =)
            return true;
        } else {
            return false;
        }
    }

    public Set<Entry<String, Double>> getMeasurements() {
        return new HashSet<Entry<String, Double>>(measurements.entrySet());
    }

    public double getMeasurement(String name) {
        Double d = measurements.get(name);
        return d == null ? -1 : d;
    }

    public static SizeObject load(Activity a, int id){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = a.openFileInput(FILE_NAME+"_"+id);
            ois = new ObjectInputStream(fis);
            return (SizeObject) ois.readObject();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not read file, IO Exception: "+e.getMessage());
        } catch (ClassNotFoundException e){
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

    public boolean save(Activity a){
        return SizeObject.save(a, this);
    }

    public static boolean save(Activity a, SizeObject so){
        try {
            FileOutputStream fos = a.openFileOutput(FILE_NAME+"_"+so.id, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(so);
            os.close();
            return true;
        } catch (IOException e){
            Log.e(LOG_TAG, e.getMessage());
            return false;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
