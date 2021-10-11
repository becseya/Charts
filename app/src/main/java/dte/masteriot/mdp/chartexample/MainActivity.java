package dte.masteriot.mdp.chartexample;

// Some code snippets have been reused from:
// https://weeklycoding.com/mpandroidchart-documentation/getting-started/
// https://weeklycoding.com/mpandroidchart-documentation/setting-data/

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // in this example, a LineChart is initialized from xml
        LineChart chart = findViewById(R.id.chart);

        List<Entry> entriesSine = new ArrayList<Entry>();
        List<Entry> entriesCosine = new ArrayList<Entry>();
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        // add entries to the two lists (sine and cosine):
        for (double x = 0; x <= Math.PI * 2; x += 0.01) {
            entriesSine.add(new Entry((float) x, (float) sin(x)));
            entriesCosine.add(new Entry((float) x, (float) cos(x)));
        }

        // add entries to datasets:
        LineDataSet dataSetSine = new LineDataSet(entriesSine, "Sine");
        LineDataSet dataSetCosine = new LineDataSet(entriesCosine, "Cosine");
        // configure datasets colors:
        dataSetSine.setColor(Color.RED);
        dataSetSine.setCircleColor(Color.RED);
        dataSetCosine.setColor(Color.BLUE);
        dataSetCosine.setCircleColor(Color.BLUE);
        // add datasets to the list of datasets:
        dataSets.add(dataSetSine);
        dataSets.add(dataSetCosine);
        // create line data:
        LineData lineDataSineAndCosine = new LineData(dataSets);
        // set data to chart:
        chart.setData(lineDataSineAndCosine);
        // configure chart:
        chart.getDescription().setEnabled(false);
        chart.animateX(3000);
        chart.invalidate(); // refresh

    }
}