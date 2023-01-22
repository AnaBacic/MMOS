package com.example.ana_senzoric;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView sensorStatusTV;
    SensorManager sensorManager;
    Sensor proximitySensor;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorStatusTV = findViewById(R.id.sensorStatusTV);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (proximitySensor == null) {
            Toast.makeText(this, "Uređaj nema senzor aproksimacije.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            sensorManager.registerListener(proximitySensorEventListener,
                    proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the sensor type is proximity sensor.
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {

                    sensorStatusTV.setText("Ooo, preblizu si!");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else{vibrator.vibrate(1000);}}
                    else {
                    sensorStatusTV.setText("Dođi bliže!");

                }
            }
        }
    };
}
