package com.scorp.sodfuhelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.scorp.sodfuhelper.data.Contract.Entry.*;


public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_NTE_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
            + CULUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CULUMN_REGION + " TEXT NOT NULL, "
            + CULUMN_PERIOD1 + " INTEGER, "
            + CULUMN_PERIOD2 + " INTEGER, "
            + CULUMN_PERIOD3 + " INTEGER,"
            + CULUMN_PERIOD4 + " INTEGER,"
            + CULUMN_PERIOD5 + " INTEGER"
            + ");";

    private static final String SQL_DELETE_NTE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_NTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_NTE_TABLE);
        //onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}