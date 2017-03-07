package com.example.marrenmatias.trynavdrawer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartGraph extends AppCompatActivity {
    BarChart barChart;
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private RelativeLayout mainBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_chart_graph);
        mydb = new DatabaseHelper(this);
        openDatabase();


        mainBarLayout = (RelativeLayout)findViewById(R.id.mainBarLayout);
        barChart = new BarChart(this);
        mainBarLayout.addView(barChart, 500, 800);

        //barChart.setDescription("The Expenses Chart");
        barChart.setNoDataText("");
        barChart.animateY(2000);
        //barChart.setVisibleXRangeMaximum(5);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);

        // Restarting chart views
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.notifyDataSetChanged();
        barChart.invalidate();

        setCategoriesBarChart();

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setEnabled(true);
        l.setXEntrySpace(17);
        l.setYEntrySpace(15);


    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void setCategoriesBarChart(){
        String SQL = "SELECT SUM(ExpenseAmount), CategoryName FROM EXPENSE WHERE ACTIVE = 1 GROUP BY CategoryName ORDER BY ExpenseDate ASC";
        Cursor ce = db.rawQuery(SQL, null);
        int count = ce.getCount();

        float[] values = new float[count];
        String[] categoryNames = new String[count];

        for (int i = 0; i < count; i++) {
            ce.moveToNext();
            categoryNames[i] = ce.getString(1);
            values[i] = ce.getFloat(0);
        }

       ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int j = 0; j < count; j++){
            xVals.add(categoryNames[j]);
            yVals1.add(new BarEntry(values[j],(count-1)-j));}


        BarDataSet dataSet = new BarDataSet(yVals1, "Expenses Category");
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(xVals, dataSet);
        barChart.setData(barData);
        barChart.invalidate();

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

    }
}
