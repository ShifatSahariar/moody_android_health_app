package com.example.moody777;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.moody777.Calender.HomeCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SleepTracker extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    static EditText wentTimeinBed,wokeUpTime,awakeSuddenly;
    Context mContext = this;
    ImageView calenderdatapick;
    String userSelectedDate;
    String appChoosenDate;
    static String tempDate = " ";
    sqlite_layer msqliteLayer;
    SQLiteDatabase sleepDatabase;
    SleepTableInfo InstertSleepInfo;
    private sqlite_layer mDbHelper;
    float wokeTime;
    Float totalHours;
    private String sleepType = "Bad";
    private String sleepAwakeType = "Normally ";
    private String AwakeNightTime= " You didn't awake. ";
    private String moodYesterdayWas = " Really Well ";
    private String exerciseTimeYesterdayWas = "  ";
    private String exerciseTypeYesterdayWas= " Normal ";
    private Spinner mAwakeSpinner,mExerciseTypeSpinner,mExerciseTimeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);
        this.setTitle("Sleep Diary");
        wentTimeinBed = findViewById(R.id.wentTimeBedField);
        mDbHelper = new sqlite_layer(this);

        wokeUpTime = findViewById(R.id.wokeUpTimeField);
        awakeSuddenly = findViewById(R.id.awaketimefield);
        calenderdatapick = findViewById(R.id.calenderdatapick);
        mAwakeSpinner = (Spinner) findViewById(R.id.awakereasonselecspinner);
        mExerciseTypeSpinner = (Spinner) findViewById(R.id.exercisespinner);
        mExerciseTimeSpinner = (Spinner) findViewById(R.id.exercisespinnertime);
        setupAwakeSpinner();
        setupExerciseTimeSpinner();
        setupExerciseTypeSpinner();
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DATE, -1);

        userSelectedDate = dateFormat.format(calendar.getTime());


        wentTimeinBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        wentTimeinBed.setText(hourOfDay + ":" + minute);


                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                // test1 = MainActivity.wentTimeinBed.getText().toString();
                timePickerDialog.show();

            }
        });

        wokeUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        wokeUpTime.setText(hourOfDay + ":" + minute);
                        //test = MainActivity.wokeUpTime.getText().toString();



                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));

                timePickerDialog.show();

            }
        });
        calenderdatapick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });




        awakeSuddenly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        awakeSuddenly.setText(hourOfDay + ":" + minute);

                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();


            }
        });
        //wentTime = Float.parseFloat(test);
        //wokeTime = Float.parseFloat(test1);
        //totalHours = wokeTime - wentTime;




    }

    public void onMoodSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.superhappy:
                moodYesterdayWas = "Really Well";
                Toast.makeText(this, "It was a Super Day", Toast.LENGTH_SHORT).show();
                break;
            case R.id.somehowhappy:
                moodYesterdayWas = "Good";
                Toast.makeText(this, "I was Okay", Toast.LENGTH_SHORT).show();
                break;
            case R.id.meamood:
                moodYesterdayWas = "Indifferent";
                Toast.makeText(this, "I was Indifferent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sadmood:
                moodYesterdayWas = "Sad";
                Toast.makeText(this, "I was Sad", Toast.LENGTH_SHORT).show();
                break;
            case R.id.depressedmood:
                moodYesterdayWas = "Depressed";
                Toast.makeText(this, "I was So Depressed !", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void onSleepSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroupSleep);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.sleptSuper:
                sleepType = "Really Well";

                Toast.makeText(this, "I slept Really Well", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sleptGood:
                sleepType = "Decent";
                Toast.makeText(this, "It was decent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sleptBad:
                sleepType = "Bad";
                Toast.makeText(this, "It was Bad night", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sleptPain:
                sleepType = "Painful";
                Toast.makeText(this, "Last night was Painful", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void setupAwakeSpinner() {

        ArrayAdapter mAwakeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.awake_type_arrays, android.R.layout.simple_spinner_item);


        mAwakeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mAwakeSpinner.setAdapter(mAwakeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mAwakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Anxiety))) {
                        sleepAwakeType = getString(R.string.Anxiety);
                    } else if (selection.equals(getString(R.string.Breathing_trouble))) {
                        sleepAwakeType = getString(R.string.Breathing_trouble);
                    }
                    else if (selection.equals(getString(R.string.Digestive_problem))) {
                        sleepAwakeType = getString(R.string.Digestive_problem);
                    }
                    else if (selection.equals(getString(R.string.Pain))) {
                        sleepAwakeType = getString(R.string.Pain);
                    }
                    else if (selection.equals(getString(R.string.Peeing_a_lot))) {
                        sleepAwakeType = getString(R.string.Peeing_a_lot);
                    }
                    else if (selection.equals(getString(R.string.Scared))) {
                        sleepAwakeType = getString(R.string.Scared);
                    }
                    else if (selection.equals(getString(R.string.Stressed))) {
                        sleepAwakeType = getString(R.string.Stressed);
                    }
                    else if (selection.equals(getString(R.string.Hormones))) {
                        sleepAwakeType = getString(R.string.Hormones);
                    }else {
                        sleepAwakeType = " "; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sleepAwakeType = " "; // Unknown
            }
        });
    }
    private void setupExerciseTimeSpinner() {

        ArrayAdapter mExerciseTimeSpinnerSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_time_arrays, android.R.layout.simple_spinner_item);


        mExerciseTimeSpinnerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mExerciseTimeSpinner.setAdapter(mExerciseTimeSpinnerSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mExerciseTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Morning))) {
                        exerciseTimeYesterdayWas = getString(R.string.Morning);
                    } else if (selection.equals(getString(R.string.Noon))) {
                        exerciseTimeYesterdayWas = getString(R.string.Noon);
                    }
                    else if (selection.equals(getString(R.string.Afternoon))) {
                        exerciseTimeYesterdayWas = getString(R.string.Afternoon);
                    }
                    else if (selection.equals(getString(R.string.Evening))) {
                        exerciseTimeYesterdayWas = getString(R.string.Evening);
                    }
                    else if (selection.equals(getString(R.string.Night))) {
                        exerciseTimeYesterdayWas = getString(R.string.Night);
                    }
                    else {
                        exerciseTimeYesterdayWas = " "; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                exerciseTimeYesterdayWas = " "; // Unknown
            }
        });
    }
    private void setupExerciseTypeSpinner() {

        ArrayAdapter mAwakeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_type_arrays, android.R.layout.simple_spinner_item);


        mAwakeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mExerciseTypeSpinner.setAdapter(mAwakeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mExerciseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Extreme_hard))) {
                        exerciseTypeYesterdayWas = getString(R.string.Extreme_hard);
                    } else if (selection.equals(getString(R.string.Light))) {
                        exerciseTypeYesterdayWas = getString(R.string.Light);
                    }
                    else if (selection.equals(getString(R.string.Moderate))) {
                        exerciseTypeYesterdayWas = getString(R.string.Moderate);
                    }
                    else if (selection.equals(getString(R.string.Hard))) {
                        exerciseTypeYesterdayWas = getString(R.string.Hard);
                    }
                    else {
                        exerciseTypeYesterdayWas = "No"; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                exerciseTypeYesterdayWas = " No"; // Unknown
            }
        });
    }
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-24*60*60*1000);
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar userSelectedDateCalendar = Calendar.getInstance();
        userSelectedDateCalendar.set(year, month, dayOfMonth);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        userSelectedDate =  dateFormat.format(userSelectedDateCalendar.getTime());

    }

    public void onSubmit(View view) {


       /* if (userSelectedDate.isEmpty() || userSelectedDate == null){
            userSelectedDate = appChoosenDate;
        }*/


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
        Cursor cursor = db.query(
                SleepTableInfo.InstertSleepInfo.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);

    /*    try{
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


            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                if (cursor.getString(lastDateIndex) == Date) {
                    tempDate = getString(lastDateIndex);
                    break;
                }




                // Display the values from each column of the current row in the cursor in the TextView

            }


            // Display the values from each column of the current row in the cursor in the TextView
            // Toast.makeText(this,  " Date "+ cursor.getFloat(lastDateIndex) + " "+ Date, Toast.LENGTH_SHORT).show();


        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }*/

  if(TextUtils.isEmpty(wentTimeinBed.getText().toString()) || TextUtils.isEmpty(wokeUpTime.getText().toString()) ){
            Toast.makeText(this, "Please Fill the Time !", Toast.LENGTH_SHORT).show();
        }
        else {//


                                  try {
                                      //your query code
                                      /* CALCULATION OF TIME */
                                      String wentBedTime = wentTimeinBed.getText().toString();
                                      //which is from server;
                                      String splitTime[]=wentBedTime.split(":");
                                      //String hourswent=splitTime[0];
                                      int hoursmultiplySIXTY = Integer.parseInt(splitTime[0]) * 60;
                                      int minutesStore = Integer.parseInt(splitTime[1]);
                                      int sumOfHoursMinutes = hoursmultiplySIXTY + minutesStore;
                                      //Float minuteToHours = Float.parseFloat(minutesWent)/60;

                                      //wentTime = Float.parseFloat(wentBedTime);
                                      //wentTime = wentTime * 100;
                                      String wokeUptime = wokeUpTime.getText().toString();
                                      String splitTimeWoke[]=wokeUptime.split(":");
                                      int hoursmultiplySIXTYWoke = Integer.parseInt(splitTimeWoke[0]) * 60;
                                      int minutesStoreWoke = Integer.parseInt(splitTimeWoke[1]);
                                      int sumOfHoursMinutesWoke = hoursmultiplySIXTYWoke + minutesStoreWoke;

                                      int subtractWokeFromWent = sumOfHoursMinutesWoke - sumOfHoursMinutes ;
                                      int finalHour = subtractWokeFromWent / 60;
                                      int minutes = subtractWokeFromWent % 60;

                                      Float minutesToHoursfraction =(float) minutes/60;

                                      totalHours = Math.abs(finalHour + minutesToHoursfraction);
                                      String finalHourInTwoDecimals = String.format("%.2f", totalHours);



                                      /* INSERT TO DATABASE */
                                      //sleepType = awakeSuddenly.getText().toString().trim();
                                      // sleepAwakeType = awakeSuddenly.getText().toString().trim();
                                      AwakeNightTime = awakeSuddenly.getText().toString().trim();
                                      //moodYesterdayWas = awakeSuddenly.getText().toString().trim();
                                      //exerciseTimeYesterdayWas = awakeSuddenly.getText().toString().trim();
                                      //exerciseTypeYesterdayWas = awakeSuddenly.getText().toString().trim();
                                      msqliteLayer = new sqlite_layer(this);
                                      sleepDatabase = msqliteLayer.getWritableDatabase();
                                      // Create a ContentValues object where column names are the keys,
                                      // and Toto's pet attributes are the values.
                                      ContentValues values = new ContentValues();
                                      values.put(SleepTableInfo.InstertSleepInfo.YESTERDAY_DATE, userSelectedDate);
                                      values.put(SleepTableInfo.InstertSleepInfo.TOTAL_HOURS, finalHourInTwoDecimals);
                                      values.put(SleepTableInfo.InstertSleepInfo.SLEEP_TYPE, sleepType);
                                      values.put(SleepTableInfo.InstertSleepInfo.AWAKE_NIGHT_TIME, AwakeNightTime);
                                      values.put(SleepTableInfo.InstertSleepInfo.SLEEP_AWAKE_TYPE, sleepAwakeType);
                                      values.put(SleepTableInfo.InstertSleepInfo.MOOD_YESTERDAY_WAS, moodYesterdayWas);
                                      values.put(SleepTableInfo.InstertSleepInfo.EXERCISE_TIME_YESTERDAY_WAS, exerciseTimeYesterdayWas);
                                      values.put(SleepTableInfo.InstertSleepInfo.EXERCISE_TYPE_YESTERDAY_WAS, exerciseTypeYesterdayWas);

                                      long newRowId = sleepDatabase.insert(SleepTableInfo.InstertSleepInfo.TABLE_NAME, null, values);
                                      if (newRowId == -1) {
                                          LayoutInflater inflater = getLayoutInflater();
                                          View layout = inflater.inflate(R.layout.custom_toast_invalid,
                                                  (ViewGroup) findViewById(R.id.toast_layout_root));

                                          ImageView image = (ImageView) layout.findViewById(R.id.image);
                                          image.setImageResource(R.drawable.sorryicon);
                                          TextView text = (TextView) layout.findViewById(R.id.text);
                                          text.setText("You have made notes for this day !");

                                          Toast toast = new Toast(getApplicationContext());
                                          toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                          toast.setDuration(Toast.LENGTH_LONG);
                                          toast.setView(layout);
                                          toast.show();
                                          // If the row ID is -1, then there was an error with insertion.
                                          //Toast.makeText(this, "Check Information !", Toast.LENGTH_SHORT).show();
                                      }


                                      else {

                                          // Otherwise, the insertion was successful and we can display a toast with the row ID.

                                          //Toast.makeText(this, "Data Successfully Saved" + tempDate+ newRowId, Toast.LENGTH_SHORT).show();
                                          //Toast toast = Toast.makeText(this, "I am custom Toast!", Toast.LENGTH_LONG);


                                          /* And now you can get the TextView of the default View of the Toast. */
                                          LayoutInflater inflater = getLayoutInflater();
                                          View layout = inflater.inflate(R.layout.custom_toast,
                                                  (ViewGroup) findViewById(R.id.toast_layout_root));

                                          ImageView image = (ImageView) layout.findViewById(R.id.image);
                                          image.setImageResource(R.drawable.checkmark);
                                          TextView text = (TextView) layout.findViewById(R.id.text);
                                          text.setText("Note successfully added in Diary.");

                                          Toast toast = new Toast(getApplicationContext());
                                          toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                          toast.setDuration(Toast.LENGTH_LONG);
                                          toast.setView(layout);
                                          toast.show();
                                                  Intent intent = new Intent(SleepTracker.this,Menu.class);
                                                  startActivity(intent);
                                      }

                                  } catch (SQLException e) {
                                      System.err.println("Exception Message");
                                      Toast toast = Toast.makeText(this, "Select Date!", Toast.LENGTH_LONG);

                                  }




        }





    }



}
