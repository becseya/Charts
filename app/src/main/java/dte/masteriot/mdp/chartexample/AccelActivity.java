package dte.masteriot.mdp.chartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccelActivity extends AppCompatActivity {

    LineChart chart;
    LineData lineData;
    ArrayList<AxisDataset> axes = new ArrayList<>();
    Handler handler;
    ExecutorService es;

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

        // Setup handler
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int idx = axes.get(0).samples.size();

                axes.get(0).samples.add(new Entry(idx, msg.getData().getFloat("x", 0)));
                axes.get(1).samples.add(new Entry(idx, msg.getData().getFloat("y", 0)));
                axes.get(2).samples.add(new Entry(idx, msg.getData().getFloat("z", 0)));

                invalidateChart();
            }
        };

        // Start thread
        es = Executors.newSingleThreadExecutor();
        es.execute(new AccelWatcher(handler, this));
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
