package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by max on 2014-05-03.
 */
public class StartActivity extends Activity {

    private String currentObjectID = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivitylayout);
        Bundle extras = getIntent().getExtras();
        if(extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getString(Constants.SIZEOBJECT_ID);
        }

        setupControlBindings();
    }

    private void setupControlBindings() {

        Button startButton = (Button) findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMeasurement();
            }
        });

    }

    private void startMeasurement() {
        
    }


}