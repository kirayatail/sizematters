package edu.chl.startup.sizematters.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horv on 03/05/14.
 */
public class DummyMeassurer implements SensorEventListener {


    private static SensorManager mSensorManager;
    private static Sensor mLinearAccelerator;
    private static double[] oldLinear_acceleration = new double[3];
    private static double[] linear_acceleration = new double[3];
    private static double[] oldspeed = new double[3];
    private static double[] speed = new double[3];
    private static double[] distance = new double[3];

    double startTimeStamp=0;




    private double startUpTime;

    private long oldTimeStamp = 0;

    private ArrayList<Double> listTime = new ArrayList<Double>();

    private ArrayList<Double> listAccX = new ArrayList<Double>();

    private ArrayList<Double> listAccY = new ArrayList<Double>();

    private ArrayList<Double> listAccZ = new ArrayList<Double>();

    private ArrayList<Double> accuresityArray = new ArrayList<Double>();
    static DummyMeassurer  db;

    double timeTaken = 0;

    static double magicalNummber= 1.2;




    public static boolean startMeassure(Activity a){
        db=new DummyMeassurer();
        mSensorManager = (SensorManager)a.getSystemService(a.SENSOR_SERVICE);
        mLinearAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        mSensorManager.registerListener(db, mLinearAccelerator, SensorManager.SENSOR_DELAY_NORMAL);

       oldLinear_acceleration = new double[3];
      linear_acceleration = new double[3];
      oldspeed = new double[3];
      speed = new double[3];
      distance = new double[3];

        double startTimeStamp=0;


        return true;
    }

    public static double[] stopMeassure(Activity a){
        mSensorManager.unregisterListener(db);
        return new double[] {distance[0]*100*magicalNummber, distance[1]*100*magicalNummber, distance[2]*100*magicalNummber};
    }



    public void onSensorChanged(SensorEvent event) {

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        if (oldTimeStamp == 0) {
            oldTimeStamp = event.timestamp;
        }
        if (startTimeStamp == 0) {
            startTimeStamp = event.timestamp;
        }
        //  Log.d("messuer",""+event.);


        /*
        final double alpha = 0.8;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        */
        timeTaken = event.timestamp - startTimeStamp;
        // Log.d("Messuer",Double.toString(timeTaken));
        if (timeTaken > (double) (100000000.0)) {



/*
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];
  */

            oldLinear_acceleration[0] = linear_acceleration[0];
            oldLinear_acceleration[1] = linear_acceleration[1];
            oldLinear_acceleration[2] = linear_acceleration[2];

            linear_acceleration[0] = event.values[0];
            linear_acceleration[1] = event.values[1];
            linear_acceleration[2] = event.values[2];


            if (linear_acceleration[0] < 0.04) {
                linear_acceleration[0] = 0;
            }

            if (linear_acceleration[1] < 0.04) {
                linear_acceleration[1] = 0;
            }
            if (linear_acceleration[2] < 0.04) {
                linear_acceleration[2] = 0;
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
            oldspeed[0] = speed[0];
            oldspeed[1] = speed[1];
            oldspeed[2] = speed[2];
            if (linear_acceleration[0] != 0) {
                speed[0] = speed[0] + ((linear_acceleration[0] + oldLinear_acceleration[0]) / 2) * sec;
            } else {
                speed[0] = 0;
            }

            if (linear_acceleration[1] != 0) {
                speed[1] = speed[1] + ((linear_acceleration[1] + oldLinear_acceleration[1]) / 2) * sec;
            } else {
                speed[1] = 0;
            }
            if (linear_acceleration[2] != 0) {
                speed[2] = speed[2] + ((linear_acceleration[2] + oldLinear_acceleration[2]) / 2) * sec;
            } else {
                speed[2] = 0;
            }


//            speed[0] = avgX * avgSec;
//            speed[1] = avgY * avgSec;
//            speed[2] = avgZ * avgSec;


            //         double totalTime = avgSec * listTime.size();
            //   double totalTime2 = totalTime * totalTime;

            distance[0] = distance[0] + ((speed[0] + oldspeed[0]) / 2) * sec;
            distance[1] = distance[1] + ((speed[1] + oldspeed[1]) / 2) * sec;
            distance[2] = distance[2] + ((speed[2] + oldspeed[2]) / 2) * sec;
            //distance[0] = (avgX * totalTime2) ;
            //distance[1] = (avgY * totalTime2) ;
            //distance[2] = (avgZ * totalTime2) ;

            timeTaken = (event.timestamp - oldTimeStamp) / (double) (1000000000);
            if (listTime.size() % 5 == 0) {

                //            accX.setText("acclaration x:" + String.format("%.3f%n", avgX));
                //          accY.setText("acclaration y:" + String.format("%.3f%n", avgY));
                //          accZ.setText("acclaration z:" + String.format("%.3f%n", avgZ));
                double accuresityInt = linear_acceleration[0] + linear_acceleration[1] + linear_acceleration[2];

                accuresityArray.add(accuresityInt);

                double accuresityAvr = calculateAverageEnd(accuresityArray, 8);


          

            }
            double temp = linear_acceleration[0];

            //   Log.d("ACCX", Double.toString(linear_acceleration[0]));
            //   Log.d("ACCY", Double.toString(linear_acceleration[1]));
            //   Log.d("ACCZ", Double.toString(linear_acceleration[2]));


        } else {
            // Log.d("before ACCX", Double.toString(event.values[0]));
            // Log.d("before ACCY", Double.toString(event.values[1]));
            // Log.d("before ACCZ", Double.toString(event.values[2]));


        }


        oldTimeStamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private double calculateAverageEnd(List<Double> marks,int i) {
        double returnValure=0;
        if(marks.size()>i){
            for (int j = 1; j < i; j++) {
                returnValure+=marks.get(marks.size()-j);
            }

            returnValure/=i;
        }
        return returnValure;
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

}
