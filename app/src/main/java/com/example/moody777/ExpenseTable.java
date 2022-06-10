package com.example.moody777;
import android.provider.BaseColumns;

public class ExpenseTable {

    private ExpenseTable() {
    }

    public static final class ExpenseEntry implements BaseColumns{

        // Table Names
        public static final String TABLE_NAME = "Expense";

        // Income Table - column names
        public static final String NAME = "name";
        public static final String VALUE = "value";
        public static final String CREATED_AT = "date";

    }

}
