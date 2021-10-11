package dte.masteriot.mdp.chartexample;

// Some code snippets have been reused from:
// https://weeklycoding.com/mpandroidchart-documentation/getting-started/
// https://weeklycoding.com/mpandroidchart-documentation/setting-data/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    List<Entry> lightSamples = new ArrayList<>();
    LineChart chart;
    LineData lineData;
    LineDataSet dataSetLight;
    Sensor lightSensor;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup dataset
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSetLight = new LineDataSet(lightSamples, "Light values");
        dataSetLight.setColor(Color.BLACK);
        dataSetLight.setCircleColor(Color.RED);
        dataSets.add(dataSetLight);
        lineData = new LineData(dataSets);

        // Connect dataset to chart
        chart = findViewById(R.id.chart);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.invalidate(); // refresh

        // Setup sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void processNewLightMeasurement(float val) {
        // Process data
        lightSamples.add(new Entry(lightSamples.size(), val));

        // Update UI
        dataSetLight.notifyDataSetChanged();
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        processNewLightMeasurement(sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
