package edu.chl.startup.sizematters.app;

import edu.chl.startup.sizematters.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity implements SensorEventListener{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;


    private  SensorManager mSensorManager;
    private  Sensor mLinearAccelerator;
    private double[] gravity = new double[]{0,0,0};
    private double[] oldLinear_acceleration = new double[3];
    private double[] linear_acceleration = new double[3];
    private double[] oldspeed = new double[3];
    private double[] speed = new double[3];
    private double[] distance = new double[3];

    double startTimeStamp=0;


    private TextView accX;
    private TextView accY;
    private TextView accZ;

    private TextView speedX;
    private TextView speedY;
    private TextView speedZ;

    private TextView distanceX;
    private TextView distanceY;
    private TextView distanceZ;

    private double startUpTime;

    private long oldTimeStamp = 0;

    private ArrayList<Double> listTime = new ArrayList<Double>();

    private ArrayList<Double> listAccX = new ArrayList<Double>();

    private ArrayList<Double> listAccY = new ArrayList<Double>();

    private ArrayList<Double> listAccZ = new ArrayList<Double>();

    double timeTaken = 0;

    public void onSensorChanged(SensorEvent event) {

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        if(oldTimeStamp==0){
            oldTimeStamp = event.timestamp;
        }
        if(startTimeStamp==0){
            startTimeStamp=event.timestamp;
        }
      //  Log.d("messuer",""+event.);


        /*
        final double alpha = 0.8;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        */
        timeTaken = event.timestamp-startTimeStamp;
       // Log.d("Messuer",Double.toString(timeTaken));
        if(timeTaken > (double)(100000000.0)) {



/*
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];
  */

            oldLinear_acceleration[0] =linear_acceleration[0];
            oldLinear_acceleration[1] =linear_acceleration[1];
            oldLinear_acceleration[2] =linear_acceleration[2];

            linear_acceleration[0] = event.values[0];
            linear_acceleration[1] = event.values[1];
            linear_acceleration[2] = event.values[2];


            if(linear_acceleration[0]<0.04){
                linear_acceleration[0]=0;
            }

            if(linear_acceleration[1]<0.04){
                linear_acceleration[1]=0;
            }
            if(linear_acceleration[2]<0.04){
                linear_acceleration[2]=0;
            }

            listAccX.add(linear_acceleration[0]);
            listAccY.add(linear_acceleration[1]);
            listAccZ.add(linear_acceleration[2]);

            long time = event.timestamp - oldTimeStamp;


            double sec = (time / (double) (1000000000));

            listTime.add(sec);

//            double avgSec = calculateAverage(listTime);
//            double avgX = calculateAverage(listAccX);
//            double avgY = calculateAverage(listAccY);
//            double avgZ = calculateAverage(listAccZ);
            oldspeed[0] =speed[0];
            oldspeed[1] =speed[1];
            oldspeed[2] =speed[2];
            if(linear_acceleration[0]!=0){
                speed[0] = speed[0]+ ((linear_acceleration[0]+oldLinear_acceleration[0])/2) * sec;
            }else{
                speed[0] =0;
            }

            if(linear_acceleration[1]!=0){
                speed[1] = speed[1]+ ((linear_acceleration[1]+oldLinear_acceleration[1])/2) * sec;
            }else{
                speed[1] =0;
            }
            if(linear_acceleration[2]!=0){
                speed[2] = speed[2]+ ((linear_acceleration[2]+oldLinear_acceleration[2])/2) * sec;
            }else{
                speed[2] =0;
            }



//            speed[0] = avgX * avgSec;
//            speed[1] = avgY * avgSec;
//            speed[2] = avgZ * avgSec;



   //         double totalTime = avgSec * listTime.size();
         //   double totalTime2 = totalTime * totalTime;

            distance[0] = distance[0] + ((speed[0]+oldspeed[0])/2) * sec;
            distance[1] = distance[1] + ((speed[1]+oldspeed[1])/2) * sec;
            distance[2] = distance[2] + ((speed[2]+oldspeed[2])/2) * sec;
            //distance[0] = (avgX * totalTime2) ;
            //distance[1] = (avgY * totalTime2) ;
            //distance[2] = (avgZ * totalTime2) ;

            timeTaken = (event.timestamp - oldTimeStamp)/(double)(1000000000);
            if(listTime.size()%20==0) {

    //            accX.setText("acclaration x:" + String.format("%.3f%n", avgX));
      //          accY.setText("acclaration y:" + String.format("%.3f%n", avgY));
      //          accZ.setText("acclaration z:" + String.format("%.3f%n", avgZ));


                speedX.setText("speed x:" + String.format("%.3f%n", speed[0]));
                speedY.setText("speed y:" + String.format("%.3f%n", speed[1]));
                speedZ.setText("speed z:" + String.format("%.3f%n", speed[2]));

                distanceX.setText("distance x:" + String.format("%.3f%n", distance[0]*100));
                distanceY.setText("distance y:" + String.format("%.3f%n", distance[1]*100));
                distanceZ.setText("distance z:" + String.format("%.3f%n", distance[2]*100));



            }
            double temp = linear_acceleration[0];

         //   Log.d("ACCX", Double.toString(linear_acceleration[0]));
         //   Log.d("ACCY", Double.toString(linear_acceleration[1]));
         //   Log.d("ACCZ", Double.toString(linear_acceleration[2]));



        }else{
           // Log.d("before ACCX", Double.toString(event.values[0]));
           // Log.d("before ACCY", Double.toString(event.values[1]));
           // Log.d("before ACCZ", Double.toString(event.values[2]));


        }


        oldTimeStamp = event.timestamp;

    }

    private double calculateAverage(List <Double> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);



        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

                    public void onVisibilityChange(boolean visible) {
                        /*
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    */
                    }

                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        accX = (TextView) findViewById(R.id.accX);
        accY = (TextView) findViewById(R.id.accY);
        accZ = (TextView) findViewById(R.id.accZ);

        speedX = (TextView) findViewById(R.id.speedX);
        speedY = (TextView) findViewById(R.id.speedY);
        speedZ = (TextView) findViewById(R.id.speedZ);

        distanceX = (TextView) findViewById(R.id.distanceX);
        distanceY = (TextView) findViewById(R.id.distanceY);
        distanceZ = (TextView) findViewById(R.id.distanceZ);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mLinearAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        mSensorManager.registerListener(this, mLinearAccelerator, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLinearAccelerator, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
