package com.scorp.sodfuhelper.data;

import android.provider.BaseColumns;

public final class Contract {

    private Contract(){}

    public static abstract class Entry implements BaseColumns {

        public static String DATABASE_PATH;
        public static final String DATABASE_NAME = "sfhelper.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "nte";

        public static final String CULUMN_ID = BaseColumns._ID;
        public static final String CULUMN_REGION = "region";
        public static final String CULUMN_PERIOD1 = "period1";
        public static final String CULUMN_PERIOD2 = "period2";
        public static final String CULUMN_PERIOD3 = "period3";
        public static final String CULUMN_PERIOD4 = "period4";
        public static final String CULUMN_PERIOD5 = "period5";

    }

}
