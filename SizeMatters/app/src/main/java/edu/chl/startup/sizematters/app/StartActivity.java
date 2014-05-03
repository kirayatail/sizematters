package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by max on 2014-05-03.
 */
public class StartActivity extends Activity {

    private int currentObjectID = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("StartActivity", "Reached StartActivity");
        setContentView(R.layout.startactivitylayout);

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
        }
        Log.d("StartActivity", "Abou to setup control bindings");
        setupControlBindings();
    }

    private void setupControlBindings() {

        Button startButton = (Button) findViewById(R.id.startActivity_startButton);
        Log.d("StartActivity", "Binding start button");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StartActivity", "Clicked startButton");
                startMeasurement();
            }
        });
        Log.d("StartActivity", "start button bound");

        Button showDetailsButton = (Button) findViewById(R.id.startActivity_ShowDetailsButton);
        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StartActivity", "Clicked showDetailsButton");
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
        Log.d("StartActivity", "Start of startMeassurement");
        Intent activityIntent = new Intent(StartActivity.this, MeasureActivity.class);
        if (currentObjectID != -1) {
            activityIntent.putExtra(Constants.SIZEOBJECT_ID, currentObjectID);
        } else {
            activityIntent.putExtra(Constants.SIZEOBJECT_ID, "42");
        }
        Log.d("StartActivity", "Going to meassure activity");
        startActivity(activityIntent);
    }


}