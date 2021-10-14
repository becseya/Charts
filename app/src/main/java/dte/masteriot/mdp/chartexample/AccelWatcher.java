package dte.masteriot.mdp.chartexample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;

public class AccelWatcher implements Runnable, SensorEventListener {

    final Handler handler;
    final Context context;
    Sensor accelerometer;
    SensorManager sensorManager;

    public AccelWatcher(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        try {
            while (!Thread.interrupted()) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                Thread.sleep(2 * 1000);
            }
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Unsubscribe ASAP
        sensorManager.unregisterListener(this, accelerometer);

        // Prepare message
        Message msg = handler.obtainMessage();
        Bundle msg_data = msg.getData();
        msg_data.putFloat("x", sensorEvent.values[0]);
        msg_data.putFloat("y", sensorEvent.values[1]);
        msg_data.putFloat("z", sensorEvent.values[2]);

        // Send message
        handler.dispatchMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
