package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by max on 2014-05-03.
 */
public class StartActivity extends Activity {

    private int currentObjectID = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivitylayout);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
        }

        setupControlBindings();
    }

    private void setupControlBindings() {

        Button startButton = (Button) findViewById(R.id.startActivity_startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMeasurement();
            }
        });

        Button showDetailsButton = (Button) findViewById(R.id.startActivity_ShowDetailsButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeasurements();
            }
        });


    }

    private void showMeasurements() {
        Intent activityIntent = new Intent(StartActivity.this, BrowseDetailsActivity.class);
        startActivity(activityIntent);
        finish();
    }

    private void startMeasurement() {
        Intent activityIntent = new Intent(StartActivity.this, MeasureActivity.class);
        if (currentObjectID != -1) {
            activityIntent.putExtra(Constants.SIZEOBJECT_ID, currentObjectID);
        }
        startActivity(activityIntent);
    }


}