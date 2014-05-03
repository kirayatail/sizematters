package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by max on 2014-05-03.
 */
public class BrowseDetailsActivity extends Activity {

    private int currentObjectID = -1;
    private SizeObject obj;

    private TextView objectName;
    private TextView valueWidth;
    private TextView valueHeight;
    private TextView valueDiagonal;

    private TextView objectWidth;
    private TextView objectHeight;
    private TextView objectDiagonal;

    private Set<Map.Entry<String, Double>> measures;

    String one;
    Double oneone;
    /*
     public Set<Entry<String, Double>> getMeasurements() {
        return new HashSet<Entry<String, Double>>(measurements.entrySet());
    }
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browsedetailsactivitylayout);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
        }

        objectWidth = (TextView) findViewById(R.id.objectWidth);
        objectDiagonal = (TextView) findViewById(R.id.objectDiagonal);
        objectHeight = (TextView) findViewById(R.id.objectHeight);

        valueWidth = (TextView) findViewById(R.id.valueWidth);
        valueDiagonal = (TextView) findViewById(R.id.valueDiagonal);
        valueHeight = (TextView) findViewById(R.id.valueHeight);


        obj = SizeObject.load(this, currentObjectID);

        if(obj != null){
            measures = obj.getMeasurements();

            objectName.setText(obj.getName());

            int i = 0;
            Iterator<Map.Entry<String,Double>> it = measures.iterator();
            for(Map.Entry<String,Double> en:measures){
                one = en.getKey();
                oneone = en.getValue();
                if(i == 0){
                    objectWidth.setText(one);
                    valueWidth.setText(Double.toString(oneone));
                } else if (i == 1){
                    objectHeight.setText(one);
                    valueHeight.setText(Double.toString(oneone));
                }
                i += 1;
            }

        }

    }

    
}