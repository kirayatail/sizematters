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
public class MeasureActivity extends Activity {

    private int currentObjectID = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measureactivitylayout);
        Log.d("MeassureActivity", "Reached onCreate");

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
        }

        setupControls();
        Log.d("MeassureActivity", "SetupConrols finished");

        if(!DummyMeassurer.startMeassure(this)) {
            Log.e("sizematters", "Error starting measurer!");
        }

    }

    private void setupControls() {
        Button stopButton = (Button) findViewById(R.id.measureActivity_StopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[] measurements = DummyMeassurer.stopMeassure(MeasureActivity.this);
                Intent activityIntent = new Intent(MeasureActivity.this, PickMeassurementActivity.class);
                if (currentObjectID != -1) {
                    activityIntent.putExtra(Constants.SIZEOBJECT_ID, currentObjectID);
                }
                activityIntent.putExtra(Constants.RAWMEASSUREMENT_KEY, measurements);
                startActivity(activityIntent);
            }
        });

    }


}