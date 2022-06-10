package com.example.moody777;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.moody777.IncomeTable.*;
import com.example.moody777.ExpenseTable.*;
import com.example.moody777.QuestionTable.*;
import com.example.moody777.SelfTest.QuestionTableInfo;
import com.example.moody777.SleepTableInfo.*;



// we are accessing sqlite db with this layer
public class sqlite_layer extends SQLiteOpenHelper {

    private Context context;


    sqlite_layer(Context c){
        super(c,"moody.db",null,26);
        this.context = c;
    }

    // Income table created
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_INCOME = "CREATE TABLE "
                + IncomeEntry.TABLE_NAME + "(" + IncomeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + IncomeEntry.NAME
                + " TEXT," + IncomeEntry.VALUE + " INTEGER," + IncomeEntry.CREATED_AT
                + " DATETIME" + ");";

        final String CREATE_TABLE_EXPENSE = "CREATE TABLE "
                + ExpenseEntry.TABLE_NAME + "(" + ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ExpenseEntry.NAME
                + " TEXT," + ExpenseEntry.VALUE + " INTEGER," + ExpenseEntry.CREATED_AT
                + " DATETIME" + ");";

        final String CREATE_TABLE_SLEEP = "CREATE TABLE "
                + InstertSleepInfo.TABLE_NAME
                + "(" + InstertSleepInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + InstertSleepInfo.YESTERDAY_DATE + " TEXT UNIQUE NOT NULL,"
                + InstertSleepInfo.TOTAL_HOURS + " REAL ,"
                + InstertSleepInfo.SLEEP_TYPE + " TEXT ,"
                + InstertSleepInfo.AWAKE_NIGHT_TIME + " TEXT,"
                + InstertSleepInfo.SLEEP_AWAKE_TYPE + " TEXT,"
                + InstertSleepInfo.MOOD_YESTERDAY_WAS + " TEXT ,"
                + InstertSleepInfo.EXERCISE_TIME_YESTERDAY_WAS + " TEXT,"
                + InstertSleepInfo.EXERCISE_TYPE_YESTERDAY_WAS + " TEXT);";
        // TABLE FOR QUESTION PART //
        final String CREATE_TABLE_QUESTION_PART = "CREATE TABLE "
                + QuestionTableInfo.InsertQuestionTable.TABLE_NAME
                + "(" + QuestionTableInfo.InsertQuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM1 + " TEXT ,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM2 + " TEXT ,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM3 + " TEXT ,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM4 + " TEXT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM5 + " TEXT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM6 + " TEXT ,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM7 + " TEXT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM8 + " TEXT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM9 + " TEXT,"
                + QuestionTableInfo.InsertQuestionTable.SYMOTOM10 + " TEXT);";





        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_SLEEP);
        db.execSQL(CREATE_TABLE_QUESTION_PART);

        System.out.println("EXPENSEtablesql "+ExpenseTable.ExpenseEntry._ID);

        System.out.println("EXPENSEtablesql "+ExpenseTable.ExpenseEntry.NAME);

    }

    public void onUpgrade(SQLiteDatabase db, int oldv, int newv){
        db.execSQL(" DROP TABLE IF EXISTS "+ IncomeEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+ ExpenseEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+ SleepTableInfo.InstertSleepInfo.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+ QuestionTableInfo.InsertQuestionTable.TABLE_NAME);

        onCreate(db);
    }
    Cursor readAllData(){
        String query=" SELECT * FROM "+IncomeEntry.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursor=null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor readAllDataE(){

        System.out.println("expensetable"+ ExpenseEntry.TABLE_NAME);

        String query=" SELECT * FROM "+ExpenseEntry.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursorE=null;
        if(db != null){
            cursorE = db.rawQuery(query,null);
        }
        return cursorE;
    }
    Cursor readAllDataQ(){


        String query=" SELECT * FROM "+QuestionTableInfo.InsertQuestionTable.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursorE=null;
        if(db != null){
            cursorE = db.rawQuery(query,null);
        }
        return cursorE;
    }
    Cursor readAllDataS(){

        System.out.println("sleeptable"+ ExpenseEntry.TABLE_NAME);

        String query=" SELECT * FROM "+InstertSleepInfo.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursorE=null;
        if(db != null){
            cursorE = db.rawQuery(query,null);
        }
        return cursorE;
    }
    void updateData(String row_id, String name, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IncomeEntry.NAME, name);
        cv.put(IncomeEntry.VALUE, value);
        System.out.println("updated_name "+name);
        System.out.println("row_id "+row_id);


        long result=db.update(IncomeEntry.TABLE_NAME,cv,"_id=?",new String[]{row_id});

        System.out.println("result"+result);

        if(result==-1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context , "Successfully Updated!", Toast.LENGTH_SHORT).show();

        }}
    void DeleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(IncomeEntry.TABLE_NAME,"_id=?",new String[]{row_id});
        if(result==-1) {
            Toast.makeText(context , "Failed to Delete", Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(context , "Successfully Deleted!", Toast.LENGTH_SHORT).show();

        }
    }
    void updateDataE(String row_id, String name, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ExpenseEntry.NAME, name);
        cv.put(ExpenseEntry.VALUE, value);
        System.out.println("updated_name " + name);
        System.out.println("row_id " + row_id);


        long result = db.update(ExpenseEntry.TABLE_NAME, cv, "_id=?", new String[]{row_id});


        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();

        }
    }
    void DeleteOneRowE(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(ExpenseEntry.TABLE_NAME,"_id=?",new String[]{row_id});
        if(result==-1) {
            Toast.makeText(context , "Failed to Delete", Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(context , "Successfully Deleted!", Toast.LENGTH_SHORT).show();

        }
    }
}
