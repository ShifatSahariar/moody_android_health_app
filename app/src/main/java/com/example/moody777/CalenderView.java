package com.example.moody777;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.moody777.Calender.HomeCollection;
import com.example.moody777.Calender.HwAdapter;

import java.util.ArrayList;

import java.util.GregorianCalendar;


public class CalenderView extends AppCompatActivity {
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    private SleepTableInfo sleepTableInfo;
    private sqlite_layer mDbHelper;
    //ArrayList<HomeCollection> mArrayList;

    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> lableName;
    BarDataSet barDataSet;
    Description description;
    BarData barData;
    XAxis xAxis;

    ArrayList<HomeCollection> modelClassCollection = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mDbHelper = new sqlite_layer(this);
        this.setTitle("Track Sleep");
        HomeCollection.date_collection_arr =new ArrayList<>();
        barChart =findViewById(R.id.sleepchart);
        barEntriesArrayList = new ArrayList<>();
        lableName= new ArrayList<>();
        xAxis = barChart.getXAxis();


        String[] projection = {
                SleepTableInfo.InstertSleepInfo._ID,
                SleepTableInfo.InstertSleepInfo.YESTERDAY_DATE,
                SleepTableInfo.InstertSleepInfo.TOTAL_HOURS,
                SleepTableInfo.InstertSleepInfo.SLEEP_TYPE,
                SleepTableInfo.InstertSleepInfo.AWAKE_NIGHT_TIME,
                SleepTableInfo.InstertSleepInfo.SLEEP_AWAKE_TYPE,
                SleepTableInfo.InstertSleepInfo.MOOD_YESTERDAY_WAS,
                SleepTableInfo.InstertSleepInfo.EXERCISE_TIME_YESTERDAY_WAS,
                SleepTableInfo.InstertSleepInfo.EXERCISE_TYPE_YESTERDAY_WAS
                };

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        mDbHelper = new sqlite_layer(this);
        Cursor cursor = db.query(
                SleepTableInfo.InstertSleepInfo.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        try{
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo._ID);
            int lastDateIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.YESTERDAY_DATE);
            int totalHoursIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.TOTAL_HOURS);
            int sleepTypeIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.SLEEP_TYPE);
            int awakeNightTimeIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.AWAKE_NIGHT_TIME);
            int sleepAwakeTypeIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.SLEEP_AWAKE_TYPE);
            int moodYesterdayIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.MOOD_YESTERDAY_WAS);
            int exerciseTimeYesterdayWasIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.EXERCISE_TIME_YESTERDAY_WAS);
            int exerciseTypeYesterdayWasIndex = cursor.getColumnIndex( SleepTableInfo.InstertSleepInfo.EXERCISE_TYPE_YESTERDAY_WAS);


            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.

                HomeCollection.date_collection_arr.add(new HomeCollection(
                        cursor.getString(lastDateIndex),cursor.getString(totalHoursIndex),
                        cursor.getString(sleepTypeIndex),cursor.getString(awakeNightTimeIndex),
                        cursor.getString(sleepAwakeTypeIndex),cursor.getString(moodYesterdayIndex),
                        cursor.getString(exerciseTimeYesterdayWasIndex),cursor.getString(exerciseTypeYesterdayWasIndex)));

                modelClassCollection.add(new HomeCollection(
                        cursor.getString(lastDateIndex),cursor.getString(totalHoursIndex),
                        cursor.getString(sleepTypeIndex),cursor.getString(awakeNightTimeIndex),
                        cursor.getString(sleepAwakeTypeIndex),cursor.getString(moodYesterdayIndex),
                        cursor.getString(exerciseTimeYesterdayWasIndex),cursor.getString(exerciseTypeYesterdayWasIndex)));

                // Display the values from each column of the current row in the cursor in the TextView

            }

        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


        // GRAPH START

        for (int i = 0; i < modelClassCollection.size(); i++){
            String getValue = modelClassCollection.get(i).getTOTAL_HOURS();;

           Float totalHours = Float.parseFloat(getValue);

            String date = modelClassCollection.get(i).getDate();
            barEntriesArrayList.add(new BarEntry(i, totalHours));
            lableName.add(date);
        }

        barDataSet = new BarDataSet(barEntriesArrayList,"Daily Sleep");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        description = new Description();
        description.setText("Daily");
        barChart.setDescription(description);

        barData = new BarData(barDataSet);
        barChart.setData(barData);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(lableName.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();

        // GRAPH END


//        HomeCollection.date_collection_arr.add(new HomeCollection("2020-07-10","4.5","bad",
//                "5:15","stressed","good","morning","fuck"));


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month,HomeCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(CalenderView.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2021) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(CalenderView.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalenderView.this);
            }

        });



    }
    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
    }
