package edu.chl.startup.sizematters.app;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


public class EditObjectActivity extends Activity {

    private int currentObjectID = -1;
    private SizeObject sizeObject = null;
    private String[] oldKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
            this.sizeObject = SizeObject.load(this, currentObjectID);
            this.oldKeys = new String[sizeObject.getMeasurements().size()];
            Entry<String, Double>[] entryArray = new Entry[0];
            entryArray = sizeObject.getMeasurements().toArray(entryArray);
            for (int i=0; i<oldKeys.length; i++) {
                oldKeys[i] = entryArray[i].getKey();
            }
        } else {
            this.sizeObject = new SizeObject(this);
            this.currentObjectID = sizeObject.getId();
            this.oldKeys = new String[0];
        }


        makeMeasurementComponents();
    }

    private void makeMeasurementComponents() {
        LinearLayout innerLayout;

        EditText measureName;
        TextView measureValue;
        Button eraseButton;
        int id = 0;
        for (Map.Entry<String, Double> ent : sizeObject.getMeasurements()) {
            innerLayout = new LinearLayout(this);
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            measureName = new EditText(this);
            measureName.setId((3*id));
            measureName.setText(ent.getKey());
            measureName.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    return false;
                }
            });
            measureValue = new TextView(this);
            measureValue.setId(3*id+1);
            measureValue.setText((ent.getValue().toString()));
            eraseButton = new Button(this);
            eraseButton.setId(3*id+2);
            id++;
        }
    }


}
