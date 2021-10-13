package dte.masteriot.mdp.chartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AccelActivity extends AppCompatActivity {

    LineChart chart;
    LineData lineData;
    ArrayList<AxisDataset> axes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);

        // Setup dataset
        axes.add(new AxisDataset("X", Color.RED));
        axes.add(new AxisDataset("Y", Color.GREEN));
        axes.add(new AxisDataset("Z", Color.BLUE));

        List<ILineDataSet> dataSets = new ArrayList<>();
        for (AxisDataset i: axes) {
            dataSets.add(i.dataSet);
        }
        lineData = new LineData(dataSets);

        // Connect dataset to chart
        chart = findViewById(R.id.chartGyro);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.invalidate();

        // fake data
        for (int i = 0; i < 3; i++) {
            for (float x = 0; x <= 5; x += 1.0) {
                axes.get(i).samples.add(new Entry(x, x+i+1));
            }
        }
        invalidateChart();
    }

    private void invalidateChart() {
        for (AxisDataset i: axes) {
            i.dataSet.notifyDataSetChanged();
        }
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
    }
}
