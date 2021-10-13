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
import android.widget.TextView;

import com.bendaschel.sevensegmentview.SevenSegmentView;
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
    TextView txtAvg;
    TextView txtMin;
    TextView txtMax;
    float statSum = 0;
    float statMin = -1;
    float statMax = 100000;

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

        // Connect UI
        txtAvg = findViewById(R.id.txtAvgVal);
        txtMax = findViewById(R.id.txtMaxVal);
        txtMin = findViewById(R.id.txtMinVal);
    }

    private void processNewLightMeasurement(float val) {
        // Process data
        lightSamples.add(new Entry(lightSamples.size(), val));

        if (val < statMin)
            statMin = val;
        if (val > statMin)
            statMax = val;
        statSum += val;

        // Update UI
        updateSegmentDisplay((int) val);
        dataSetLight.notifyDataSetChanged();
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
        txtAvg.setText(String.format("%.1f", statSum / lightSamples.size()));
        txtMax.setText(String.format("%.1f", statMax));
        txtMin.setText(String.format("%.1f", statMin));
    }

    private void updateSegmentDisplay(int val) {
        final int[] segmentIds = {R.id.seg0, R.id.seg1, R.id.seg2, R.id.seg3, R.id.seg4};
        int pow10 = 1;

        for (int id : segmentIds) {
            SevenSegmentView segment = findViewById(id);

            int digit = (val % (pow10 * 10)) / pow10;
            segment.setCurrentValue(digit);
            pow10 *= 10;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        processNewLightMeasurement(sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
