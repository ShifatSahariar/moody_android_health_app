package com.example.moody777;

import android.provider.BaseColumns;

public class QuestionTable {

    private QuestionTable() {
    }

    public static final class QuestionEntry implements BaseColumns{

        // Table Names
        public static final String TABLE_NAME = "Question";

        // Income Table - column names
        public static final String NAME = "name";
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";


    }

}
