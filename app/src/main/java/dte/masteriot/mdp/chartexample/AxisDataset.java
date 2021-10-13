package dte.masteriot.mdp.chartexample;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AxisDataset {

    public List<Entry> samples = new ArrayList<>();
    public LineDataSet dataSet;

    public AxisDataset(String label, int color)
    {
        dataSet = new LineDataSet(samples, label);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
    }
}
