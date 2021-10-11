package dte.masteriot.mdp.chartexample;

// Some code snippets have been reused from:
// https://weeklycoding.com/mpandroidchart-documentation/getting-started/
// https://weeklycoding.com/mpandroidchart-documentation/setting-data/

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Entry> lightSamples = new ArrayList<>();
    LineChart chart;
    LineData lineData;
    LineDataSet dataSetLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (float x = 0; x <= 5; x += 1.0) {
            lightSamples.add(new Entry(x, x+2));
        }

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
    }
}
