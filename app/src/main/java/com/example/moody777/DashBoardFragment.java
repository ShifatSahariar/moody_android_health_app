package com.example.moody777;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moody777.Calender.HomeCollection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;



public class DashBoardFragment extends Fragment {
    BarChart barChart,barChartIncome;
    ArrayList<BarEntry> barEntriesArrayList,barEntriesArrayListIncome;
    ArrayList<String> lableName,lableNameIncome;
    BarDataSet barDataSet,barDataSetIncome;
    Description description,descriptionIncome;
    BarData barData,barDataIncome;
    XAxis xAxis,xAxixIncome;
    private sqlite_layer mDbHelper,mDbHelperIncome;

    ArrayList<ModelClassExpenses> modelClassExpenses = new ArrayList<>();
    ArrayList<ModelClassIncome> modelClassIncome = new ArrayList<>();

    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;



    private TextView fab_income_txt;
    private TextView fab_expense_txt;


    private SQLiteDatabase mdb ;
    private IncomeAdapter mAdapter;
    private RecyclerView recyclerView;




    private boolean isOpen=false;

    private Animation FadOpen,FadeClose;

    private TextView IncomeResult;


    private TextView totalExpenseResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlite_layer dbHelper = new sqlite_layer(this.getActivity());
        mdb=dbHelper.getWritableDatabase();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_dash_board, container, false);



        barChart = myview.findViewById(R.id.barChart);
        barChartIncome = myview.findViewById(R.id.barChartincome);
        barEntriesArrayList = new ArrayList<>();
        barEntriesArrayListIncome = new ArrayList<>();
        lableName= new ArrayList<>();
        lableNameIncome= new ArrayList<>();
        xAxis = barChart.getXAxis();
        xAxixIncome= barChartIncome.getXAxis();

        //fillSleepList();


        // DB CALL //
        mDbHelper = new sqlite_layer(this.getActivity());
        mDbHelperIncome= new sqlite_layer(this.getActivity());
        String[] projection = {
                ExpenseTable.ExpenseEntry.NAME,
                ExpenseTable.ExpenseEntry.VALUE,
                ExpenseTable.ExpenseEntry.CREATED_AT,


        };
        String[] projectionincome = {
                IncomeTable.IncomeEntry.NAME,
                IncomeTable.IncomeEntry.VALUE,
                IncomeTable.IncomeEntry.CREATED_AT,


        };

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase dbIncome = mDbHelperIncome.getReadableDatabase();

        Cursor cursor = db.query(
                ExpenseTable.ExpenseEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);


        Cursor cursorIncome = dbIncome.query(
                IncomeTable.IncomeEntry.TABLE_NAME,   // The table to query
                projectionincome,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);


        try{
            // Figure out the index of each column
            int nameIndex = cursor.getColumnIndex( ExpenseTable.ExpenseEntry.NAME);
            int valueIndex = cursor.getColumnIndex( ExpenseTable.ExpenseEntry.VALUE);
            int dateIndex = cursor.getColumnIndex( ExpenseTable.ExpenseEntry.CREATED_AT);

            //////FOR EXPENSE

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.


                modelClassExpenses.add(new ModelClassExpenses(
                        cursor.getString(nameIndex),cursor.getInt(valueIndex),
                        cursor.getString(dateIndex)));



                // Display the values from each column of the current row in the cursor in the TextView

            }


            //////FOR INCOME
            int incomeNameIndex = cursorIncome.getColumnIndex(  IncomeTable.IncomeEntry.NAME);
            int incomeValueIndex = cursorIncome.getColumnIndex(  IncomeTable.IncomeEntry.VALUE);
            int incomeDateIndex = cursorIncome.getColumnIndex(  IncomeTable.IncomeEntry.CREATED_AT);




            for(cursorIncome.moveToFirst(); !cursorIncome.isAfterLast(); cursorIncome.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.

                modelClassIncome.add(new ModelClassIncome(
                        cursorIncome.getString(incomeNameIndex),cursorIncome.getInt(incomeValueIndex),
                        cursorIncome.getString(incomeDateIndex)));

                // Display the values from each column of the current row in the cursor in the TextView

            }

        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
        // DB FINISH //



        fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn = myview.findViewById(R.id.income_Ft_btn);
        fab_expense_btn = myview.findViewById(R.id.expense_Ft_btn);



        fab_income_txt = myview.findViewById(R.id.income_ft_text);
        fab_expense_txt = myview.findViewById(R.id.expense_ft_text);



        FadOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.button);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.button_close);

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addData();
                if (isOpen) {

                    fab_income_btn.startAnimation(FadeClose);
                    fab_expense_btn.startAnimation(FadeClose);


                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);



                    fab_income_txt.startAnimation(FadeClose);
                    fab_expense_txt.startAnimation(FadeClose);


                    fab_income_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);


                    isOpen = false;

                } else {
                    fab_income_btn.startAnimation(FadOpen);
                    fab_expense_btn.startAnimation(FadOpen);


                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);



                    fab_income_txt.startAnimation(FadOpen);
                    fab_expense_txt.startAnimation(FadOpen);


                    fab_income_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);


                    isOpen = true;

                }

            }
        });

        // GRAPH

        for (int i = 0; i < modelClassExpenses.size(); i++){
            int getValue = modelClassExpenses.get(i).getValue();

            String date = modelClassExpenses.get(i).getTime();
            barEntriesArrayList.add(new BarEntry(i, getValue));
            lableName.add(date);
        }

        barDataSet = new BarDataSet(barEntriesArrayList,"Daily Expenses");
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
//// INCOME
        for (int i = 0; i < modelClassIncome.size(); i++){
            int getValueIncome = modelClassIncome.get(i).getValue();

            String dateIncome = modelClassIncome.get(i).getTime();
            barEntriesArrayListIncome.add(new BarEntry(i, getValueIncome));
            lableNameIncome.add(dateIncome);
        }

        barDataSetIncome = new BarDataSet(barEntriesArrayListIncome,"Daily Income");
        barDataSetIncome.setColors(ColorTemplate.COLORFUL_COLORS);

        descriptionIncome = new Description();
        descriptionIncome.setText("Daily");
        barChartIncome.setDescription(descriptionIncome);

        barDataIncome = new BarData(barDataSetIncome);
        barChartIncome.setData(barDataIncome);

        xAxixIncome.setValueFormatter(new IndexAxisValueFormatter(lableNameIncome));
        xAxixIncome.setDrawGridLines(false);
        xAxixIncome.setDrawAxisLine(false);
        xAxixIncome.setGranularity(1f);
        xAxixIncome.setLabelCount(lableNameIncome.size());
        xAxixIncome.setLabelRotationAngle(270);
        barChartIncome.animateY(2000);
        barChartIncome.invalidate();



        //GRAPH FINISH
        return myview;
    }

 /*   private void fillSleepList(){

        modelClassExpenses.add(new ModelClassExpenses(1,"",350,"2020-07-12"));
        modelClassExpenses.add(new ModelClassExpenses(1,"",240,"2020-07-14"));

    }*/
    private void ftAnimation(){
        if (isOpen){

            fab_income_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_txt.startAnimation(FadeClose);
            fab_expense_txt.startAnimation(FadeClose);
            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            isOpen=false;

        }else {
            fab_income_btn.startAnimation(FadOpen);
            fab_expense_btn.startAnimation(FadOpen);
            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_txt.startAnimation(FadOpen);
            fab_expense_txt.startAnimation(FadOpen);
            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            isOpen=true;

        }
    }
    private void addData(){
        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { expenseDataInsert();
            }


        });

    }
    public void incomeDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myviewm=inflater.inflate(R.layout.custom_insert_data,null);
        mydialog.setView(myviewm);
        final AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);


        final EditText edtValue=myviewm.findViewById(R.id.ammount_edt);
        final EditText edtName=myviewm.findViewById(R.id.type_edt);
        //final EditText edtNote=myviewm.findViewById(R.id.note_edt);

        Button btnSave=myviewm.findViewById(R.id.btnSave);
        Button btnCancel=myviewm.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtName.getText().toString();
                String ammount=edtValue.getText().toString();
                String mDate= DateFormat.getDateInstance().format(new Date());

                if (TextUtils.isEmpty(ammount)){
                    edtValue.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    edtName.setError("Required Field..");
                    return;
                }
                int ourammontint=Integer.parseInt(ammount);
                ContentValues val= new ContentValues();
                val.put(IncomeTable.IncomeEntry.NAME,name);
                val.put(IncomeTable.IncomeEntry.VALUE,ourammontint);
                val.put(IncomeTable.IncomeEntry.CREATED_AT,mDate);
                long id =mdb.insert(IncomeTable.IncomeEntry.TABLE_NAME,null,val);

                //  mAdapter.swapCursor(getAllItems());

                edtName.getText().clear();
               /* Income i = new Income(edtName.getText().toString(),ourammontint,mDate);
                long id = dt.Income_create(i);*/

                System.out.println("Dashid"+ id);
                if(id==-1)
                    Toast.makeText(getActivity(),"Data did not ADD", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Data ADDED", Toast.LENGTH_SHORT).show();


                ftAnimation();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void expenseDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.custom_insert_data,null);
        mydialog.setView(myview);
        final AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);


        final EditText edtValue=myview.findViewById(R.id.ammount_edt);
        final EditText edtName=myview.findViewById(R.id.type_edt);
        //final EditText edtNote=myviewm.findViewById(R.id.note_edt);

        Button btnSave=myview.findViewById(R.id.btnSave);
        Button btnCancel=myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtName.getText().toString();
                String ammount=edtValue.getText().toString();
                String mDate= DateFormat.getDateInstance().format(new Date());
                System.out.println("EXPENSEADD "+name);


                if (TextUtils.isEmpty(ammount)){
                    edtValue.setError("Required Field..");
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    edtName.setError("Required Field..");
                    return;
                }
                int ourammontint=Integer.parseInt(ammount);
                ContentValues vale= new ContentValues();
                System.out.println("EXPENSEtabledash "+ExpenseTable.ExpenseEntry.TABLE_NAME);
                System.out.println("EXPENSEtableDASH "+ExpenseTable.ExpenseEntry._ID);

                System.out.println("EXPENSEtableDASH "+name);

                vale.put(ExpenseTable.ExpenseEntry.NAME,name);
                vale.put(ExpenseTable.ExpenseEntry.VALUE,ourammontint);
                vale.put(ExpenseTable.ExpenseEntry.CREATED_AT,mDate);
                System.out.println("EXPENSEtableDASHP "+name);
                System.out.println("EXPENSEtableDASHP "+ExpenseTable.ExpenseEntry.NAME);


                long id =mdb.insert(ExpenseTable.ExpenseEntry.TABLE_NAME,null,vale);

                //  mAdapter.swapCursor(getAllItems());

                edtName.getText().clear();
               /* Income i = new Income(edtName.getText().toString(),ourammontint,mDate);
                long id = dt.Income_create(i);*/

                System.out.println("Dashid"+ id);
                if(id==-1)
                    Toast.makeText(getActivity(),"Data did not ADD", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Data ADDED", Toast.LENGTH_SHORT).show();


                ftAnimation();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}