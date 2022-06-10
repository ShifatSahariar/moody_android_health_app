package com.example.moody777;

import android.provider.BaseColumns;

public final class SleepTableInfo {


    public static final class InstertSleepInfo implements BaseColumns {


        public static final String TABLE_NAME = "SleepTrack";

        public static final String _ID = BaseColumns._ID;
        public static final String YESTERDAY_DATE = "date";
        public static final String TOTAL_HOURS = "total_hour";
        public static final String SLEEP_TYPE = "sleep_type";
        public static final String AWAKE_NIGHT_TIME = "awake_night_time";
        public static final String SLEEP_AWAKE_TYPE = "sleep_awake_type";
        public static final String MOOD_YESTERDAY_WAS = "mood_yesterday_was";
        public static final String EXERCISE_TIME_YESTERDAY_WAS = "exercise_time__was";
        public static final String EXERCISE_TYPE_YESTERDAY_WAS = "exercise_type_was";


    }
}
