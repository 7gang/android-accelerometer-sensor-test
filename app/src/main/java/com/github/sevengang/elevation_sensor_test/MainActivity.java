package com.github.sevengang.elevation_sensor_test;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView = null;
    private String accuracy = "Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout lView = new LinearLayout(this);
        textView = new TextView(this);

        // start listening if the desired sensor is supported
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            textView.setText("This device does not support the desired sensor functionality!");
        }

        // display textView
        lView.addView(textView);
        setContentView(lView);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        switch (accuracy) {
            case 0:
                this.accuracy = "Unreliable";
                break;
            case 1:
                this.accuracy = "Low";
                break;
            case 2:
                this.accuracy = "Medium";
                break;
            case 3:
                this.accuracy = "High";
                break;
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            textView.setText(
                              "X-axis acceleration: " + event.values[0] + "\n"
                            + "Y-axis acceleration: " + event.values[1] + "\n"
                            + "Z-axis acceleration: " + event.values[2] + "\n"
                            + "Accuracy: " + accuracy
            );
        }
    }

}
