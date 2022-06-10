package com.example.moody777;

import android.provider.BaseColumns;

public class IncomeTable {

    private IncomeTable() {
    }

    public static final class IncomeEntry implements BaseColumns{

        // Table Names
        public static final String TABLE_NAME = "Income";

        // Income Table - column names
        public static final String NAME = "name";
        public static final String VALUE = "value";
        public static final String CREATED_AT = "date";

    }

}
