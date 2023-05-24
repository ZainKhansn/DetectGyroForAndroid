package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textInputLayout);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> typedSensors = sm.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        if (typedSensors != null && typedSensors.size() > 0) {
            sm.registerListener(this, typedSensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float q[] = new float[16];
        float[] orientationTmps = new float[3];

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ROTATION_VECTOR:
                SensorManager.getRotationMatrixFromVector(q, event.values);

                SensorManager.remapCoordinateSystem(q, SensorManager.AXIS_X, SensorManager.AXIS_Z, q);

                SensorManager.getOrientation(q, orientationTmps);


                String out =
                        " X: " + Float.toString((int) Math.toDegrees(orientationTmps[0])) +
                        " Y: " + Float.toString((int) Math.toDegrees(orientationTmps[1]));
                tv.setText(out);

                break;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Empty implementation
    }
}
