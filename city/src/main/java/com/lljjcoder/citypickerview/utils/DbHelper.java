package com.lljjcoder.citypickerview.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库对象
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TABLE = "data.db";
    private static final int VERSION = 1;

    public static final String ONE_TABLE = "one";
    public static final String ONE_NAME = "onename";
    public static final String ONE_ID = "oneid";
    public static final String TYPE = "type";

    public static final String TWO_TABLE = "two";
    public static final String ONE_KEY = "onekey";
    public static final String TWO_NAME = "twoname";
    public static final String TWO_ID = "twoid";

    public static final String THREE_TABLE = "three";
    public static final String ONEID="onekey";
    public static final String TWO_KEY = "twokey";
    public static final String THREE_NAME = "threename";
    public static final String THREE_ID = "threeid";

    private static final String CREATE_ONE_DATA = "CREATE TABLE IF NOT EXISTS " + ONE_TABLE + "(id integer PRIMARY KEY, " + ONE_ID + " integer, " + TYPE + " integer, " + ONE_NAME + " text)";
    private static final String CREATE_TWO_DATA = "CREATE TABLE IF NOT EXISTS " + TWO_TABLE + "(id integer PRIMARY KEY, " + ONE_KEY + " integer, " + TWO_ID + " integer, " + TYPE + " integer, " + TWO_NAME + " text)";
    private static final String CREATE_THREE_DATA = "CREATE TABLE IF NOT EXISTS " + THREE_TABLE + "(id integer PRIMARY KEY, " + ONEID+" integer, "+TWO_KEY + " integer, " + THREE_ID + " integer, " + TYPE + " integer, " + THREE_NAME + " text)";


    public DbHelper(Context context) {
        super(context, TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ONE_DATA);
        sqLiteDatabase.execSQL(CREATE_TWO_DATA);
        sqLiteDatabase.execSQL(CREATE_THREE_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
