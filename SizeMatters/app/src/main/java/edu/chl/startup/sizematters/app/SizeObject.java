package edu.chl.startup.sizematters.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by max on 2014-05-03.
 */
public class SizeObject {
    private String name;
    private String description;
    private Map <String, Double> measurements;

    public SizeObject() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
