package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


public class PickMeassurementActivity extends Activity {

    private int currentObjectID = -1;



    private double x;
    private double y;
    private double z;
    private boolean distance;
    private boolean area;
    private boolean volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(Constants.SIZEOBJECT_ID)) {
            this.currentObjectID = extras.getInt(Constants.SIZEOBJECT_ID);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_meassurement);


        initCheckBoxes();
        setValues(extras.getDoubleArray(Constants.RAWMEASSUREMENT_KEY));
        initButtons();




    }



    private void initButtons() {

        Button backButton = (Button) findViewById(R.id.backButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        ButtonListener bl = new ButtonListener();
        backButton.setOnClickListener(bl);

        saveButton.setOnClickListener(bl);

    }

    private class ButtonListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.backButton){
                //TODO do stuff
            } else if(view.getId() == R.id.saveButton){
               viewDetails();
            }
        }
    }

    private void prepareDetails() {


    }

    private void setValues(double[] values) {
        //TODO check index out of bounds
        TextView xValView = (TextView) findViewById(R.id.xValue);
        x = values[0];
        xValView.setText(nicefy(x));
        TextView yValView = (TextView) findViewById(R.id.yValue);
        y = values[1];
        yValView.setText(nicefy(y));
        TextView zValView = (TextView) findViewById(R.id.zValue);
        z = +values[2];
        zValView.setText(nicefy(z));

        TextView distValView = (TextView) findViewById(R.id.distValue);
        distValView.setText(nicefy(calcDistance(values[0], values[1], values[2])));
    }

    private String nicefy(double d){
        return String.format("%.3f%n", d);
    }

    private double calcDistance(double... vals){
       return vals[0] + vals[1] + vals[2];
    }

    private void initCheckBoxes() {
        CheckBox cbX = (CheckBox)findViewById(R.id.xCheckBox);
        CheckBox cbY = (CheckBox)findViewById(R.id.yCheckBox);
        CheckBox cbZ = (CheckBox)findViewById(R.id.zCheckBox);
        CBListener cbl = new CBListener();
        cbX.setOnCheckedChangeListener(cbl);
        cbY.setOnCheckedChangeListener(cbl);
        cbZ.setOnCheckedChangeListener(cbl);

        CheckBox cbD = (CheckBox)findViewById(R.id.distCheckBox);
        CheckBox cbA = (CheckBox)findViewById(R.id.areaCheckBox);
        CheckBox cbV = (CheckBox)findViewById(R.id.volCheckBox);

        distance = cbD.isChecked();
        area = cbA.isChecked();
        volume = cbV.isChecked();

        cbX.setOnCheckedChangeListener(cbl);
        cbY.setOnCheckedChangeListener(cbl);
        cbZ.setOnCheckedChangeListener(cbl);



    }

    private class CBListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.volCheckBox : volume = compoundButton.isChecked();
                    break;
                case R.id.areaCheckBox : area = compoundButton.isChecked();
                    break;
                case R.id.distCheckBox : distance = compoundButton.isChecked();
                    break;
            }

        }
    }

    private void viewDetails() {
        Intent activityIntent = new Intent(PickMeassurementActivity.this, BrowseDetailsActivity.class);
        if (currentObjectID != -1) {
            activityIntent.putExtra(Constants.SIZEOBJECT_ID, currentObjectID);

        }

        String[] types = new String[3];
        double[] values = new double[3];
        if(distance){
            types[0] = Constants.DISTANCE;
            values[0] = calcDistance(x,y,z); //TODO check what values to cal on
        }
        if(area){
            types[1] = Constants.AREA;
            values[1] = calcAREA(x,y); //TODO check what values to cal on
        }
        if(volume){
            types[2] = Constants.DISTANCE;
            values[2] = calcVol(x,y,z); //TODO check what values to cal on
        }


        activityIntent.putExtra(Constants.AGG_MEASSURMENT_KEY, values);
        activityIntent.putExtra(Constants.MEASSURMENT_TYPE_KEY, types);
        startActivity(activityIntent);
    }

    private double calcVol(double x, double y, double z) {
        return x*y*z;
    }

    private double calcAREA(double x, double y) {
        return x*y;
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
