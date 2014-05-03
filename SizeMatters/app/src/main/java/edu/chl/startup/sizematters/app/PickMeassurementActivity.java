package edu.chl.startup.sizematters.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class PickMeassurementActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_meassurement);

        initButtons();
        Bundle extras = getIntent().getExtras();
        setValues(extras.getDoubleArray(Constants.RAWMEASSUREMENT_KEY));






    }

    private void setValues(double[] values) {
        //TODO check index out of bounds
        TextView xValView = (TextView) findViewById(R.id.xValue);
        xValView.setText(""+values[0]);
        TextView yValView = (TextView) findViewById(R.id.yValue);
        yValView.setText(""+values[1]);
        TextView zValView = (TextView) findViewById(R.id.zValue);
        zValView.setText(""+values[2]);

        TextView distValView = (TextView) findViewById(R.id.distValue);
        distValView.setText("Dummy dist: "+(values[0] + values[1] + values[2]));
    }

    private void initButtons() {
        CheckBox cbX = (CheckBox)findViewById(R.id.xCheckBox);
        CheckBox cbY = (CheckBox)findViewById(R.id.yCheckBox);
        CheckBox cbZ = (CheckBox)findViewById(R.id.zCheckBox);
        CBListener cbl = new CBListener();
        cbX.setOnCheckedChangeListener(cbl);
        cbY.setOnCheckedChangeListener(cbl);
        cbZ.setOnCheckedChangeListener(cbl);

    }

    private class CBListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(compoundButton.getId() == R.id.xCheckBox){
                //Fix stuff
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pick_meassurement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
