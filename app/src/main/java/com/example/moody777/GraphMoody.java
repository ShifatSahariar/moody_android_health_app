package com.example.moody777;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphMoody extends AppCompatActivity {
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> lableName;
    BarDataSet barDataSet;
    Description description;
    BarData barData;
    XAxis xAxis;

    ArrayList<ModelClassExpenses> modelClassExpenses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dash_board);

        barChart =findViewById(R.id.barChart);
        barEntriesArrayList = new ArrayList<>();
        lableName= new ArrayList<>();
        xAxis = barChart.getXAxis();



        for (int i = 0; i < modelClassExpenses.size(); i++){
            int getValue = modelClassExpenses.get(i).getValue();

            String date = modelClassExpenses.get(i).getTime();
            barEntriesArrayList.add(new BarEntry(i, getValue));
            lableName.add(date);
        }

        barDataSet = new BarDataSet(barEntriesArrayList,"Daily Sleep");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(30);

        description = new Description();
        description.setText("Daily");
        barChart.setDescription(description);

        barData = new BarData(barDataSet);
        barChart.setData(barData);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);

        xAxis.setLabelCount(lableName.size());
        xAxis.setLabelRotationAngle(360);
        barChart.animateY(2000);
        barChart.invalidate();
    }


    }
