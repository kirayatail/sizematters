package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


public class EditObjectActivity extends Activity {

    private int currentObjectID = -1;
    private SizeObject sizeObject = null;
    private EditText nameText = null;
    private String[] oldKeys;
    private double[] newValues;
    private String[] newKeys;
    private int idCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);

        Bundle extras = getIntent().getExtras();
        nameText = (EditText)findViewById(R.id.editObjectActivity_nameText);
/*        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
            this.sizeObject = SizeObject.load(this, currentObjectID);
            this.oldKeys = new String[sizeObject.getMeasurements().size()];
            Entry<String, Double>[] entryArray = new Entry[0];
            entryArray = sizeObject.getMeasurements().toArray(entryArray);
            for (int i=0; i<oldKeys.length; i++) {
                oldKeys[i] = entryArray[i].getKey();
            }
            nameText.setText(sizeObject.getName());
        } else { */
            this.sizeObject = new SizeObject(this);
            this.currentObjectID = sizeObject.getId();
            this.oldKeys = new String[0];
            nameText.setText("<Set the name of the object>");
//        }


        if(extras != null && extras.containsKey(Constants.AGG_MEASSURMENT_KEY)) {
            newKeys = extras.getStringArray(Constants.MEASSURMENT_TYPE_KEY);
            newValues = extras.getDoubleArray(Constants.AGG_MEASSURMENT_KEY);
        }
        initNavButtons();
        makeMeasurementComponents();
    }

    private void initNavButtons() {
        Button doneButton = (Button)findViewById(R.id.editObjectActivity_DoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditObjectActivity.this.saveObject();
            }
        });
    }

    void saveObject() {
        this.sizeObject.setName(nameText.getText().toString());
        LinearLayout layout = (LinearLayout)findViewById(R.id.editObjectActivity_tupleLayout);
        for(int i=0; i<(idCount/3); i++) {
            EditText keyText = (EditText) layout.findViewById(3*i);
            TextView valueText = (TextView) layout.findViewById((3*i+1));
            this.sizeObject.addMeasurement(keyText.getText().toString(), Double.parseDouble(valueText.getText().toString()));
        }
        this.sizeObject.save(this);

        Intent activityIntent = new Intent(this, BrowseDetailsActivity.class);
        activityIntent.putExtra(Constants.SIZEOBJECT_ID,sizeObject.getId());
        startActivity(activityIntent);
    }

    private void makeMeasurementComponents() {

        int id = 0;
        for (Map.Entry<String, Double> ent : sizeObject.getMeasurements()) {
            createTuple(id++, ent);
        }
        Entry<String, Double> ent;
        for (int i=0; i<newKeys.length; i++) {
            ent = new AbstractMap.SimpleEntry<String, Double>(newKeys[i],newValues[i]);
            createTuple(id++, ent);
        }
        this.idCount = id;
    }

    private void createTuple(int id, Entry<String,Double> ent) {
        LinearLayout outerLayout = (LinearLayout) findViewById(R.id.editObjectActivity_tupleLayout);

        LinearLayout innerLayout;
        EditText measureName;
        TextView measureValue;
        Button eraseButton;
        innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        measureName = new EditText(this);
        measureName.setId((3*id));
        measureName.setText(ent.getKey());
        measureValue = new TextView(this);
        measureValue.setId(3*id+1);
        measureValue.setText((ent.getValue().toString()));
        eraseButton = new Button(this);
        eraseButton.setId(3*id+2);
        eraseButton.setText("X");
        eraseButton.setTextColor(Color.RED);
        innerLayout.addView(measureName);
        innerLayout.addView(measureValue);
        innerLayout.addView(eraseButton);
        outerLayout.addView(innerLayout);
    }


}
