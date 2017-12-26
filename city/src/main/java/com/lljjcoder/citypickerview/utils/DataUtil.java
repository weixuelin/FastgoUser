package com.lljjcoder.citypickerview.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lljjcoder.citypickerview.model.DbInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类
 */

public class DataUtil {

    private static DataUtil dataUtil;
    private DbHelper dbHelper;
    private Context context;

    public static DataUtil getInstance(Context context) {
        if (dataUtil == null) {
            dataUtil = new DataUtil(context);
        }
        return dataUtil;
    }

    private DataUtil(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }


    public void clearOne() {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        sql.delete(DbHelper.ONE_TABLE, null, null);

    }


    public void saveOne(List<DbInfo> list, int type) {
        if (list != null && list.size() != 0) {
            SQLiteDatabase sql = dbHelper.getWritableDatabase();
            for (DbInfo t : list) {
                if (!isExist(sql, DbHelper.ONE_TABLE, DbHelper.ONE_ID, t, type)) {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.ONE_NAME, t.getText());
                    values.put(DbHelper.ONE_ID, t.getId());
                    values.put(DbHelper.TYPE, type);
                    sql.insert(DbHelper.ONE_TABLE, null, values);
                }

            }
        }
    }


    public void saveOne(DbInfo info, int type) {
        if (info != null) {
            SQLiteDatabase sql = dbHelper.getWritableDatabase();
            if (!isExist(sql, DbHelper.ONE_TABLE, DbHelper.ONE_ID, info, type)) {
                ContentValues values = new ContentValues();
                values.put(DbHelper.ONE_NAME, info.getText());
                values.put(DbHelper.ONE_ID, info.getId());
                values.put(DbHelper.TYPE, type);
                sql.insert(DbHelper.ONE_TABLE, null, values);
            }

        }
    }


    /**
     * 清除数据
     */
    public void deleteData(int type) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        sql.delete(DbHelper.ONE_TABLE, DbHelper.TYPE + "=?", new String[]{String.valueOf(type)});
    }


    private boolean isExist(SQLiteDatabase sql, String table, String name, DbInfo t, int type) {
        Cursor cursor = sql.query(table, null, DbHelper.TYPE + "=? AND " + name + "=?",
                new String[]{String.valueOf(type), String.valueOf(t.getId())}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }


    private boolean isTwoExist(SQLiteDatabase sql, String table, String key, String values, String name, DbInfo t, int type) {
        Cursor cursor = sql.query(table, null, DbHelper.TYPE + "=? AND " + key + "=? AND " + name + "=?",
                new String[]{String.valueOf(type), values, String.valueOf(t.getId())}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    private boolean isThreeExist(SQLiteDatabase sql, String threeTable, String oneId, int id, String twoKey, int twoId, String threeId, DbInfo t, int type) {
        Cursor cursor = sql.query(threeTable, null, DbHelper.TYPE + "=? AND " + oneId + "=? AND " + twoKey + "=?  AND " + threeId + "=?",
                new String[]{String.valueOf(type), String.valueOf(id), String.valueOf(twoId), String.valueOf(t.getId())}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } else {
            return false;
        }

    }


    public List<DbInfo> getOne(int type) {
        List<DbInfo> list = new ArrayList<>();
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DbHelper.ONE_TABLE, null, DbHelper.TYPE + "=?", new String[]{String.valueOf(type)}, null, null, null);
        while (cursor.moveToNext()) {
            DbInfo dbInfo = new DbInfo();
            String str = cursor.getString(cursor.getColumnIndex(DbHelper.ONE_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.ONE_ID));
            dbInfo.setText(str);
            dbInfo.setId(id);

            list.add(dbInfo);
        }
        cursor.close();
        return list;

    }

    public int getIdByName(String s, int code) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DbHelper.ONE_TABLE, null, DbHelper.TYPE + "=? AND " + DbHelper.ONE_NAME + "=?", new String[]{String.valueOf(code), s}, null, null, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.ONE_ID));
            return id;

        }
        cursor.close();
        return 0;
    }


    public List<DbInfo> getTwo(int oneId, int type) {
        List<DbInfo> list = new ArrayList<>();
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DbHelper.TWO_TABLE, null, DbHelper.TYPE + "=? AND " + DbHelper.ONE_KEY + "=?", new String[]{String.valueOf(type), String.valueOf(oneId)}, null, null, null);
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(DbHelper.TWO_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.TWO_ID));
            DbInfo dbInfo = new DbInfo();
            dbInfo.setId(id);
            dbInfo.setText(str);
            list.add(dbInfo);

        }

        cursor.close();
        return list;

    }


    public Map<Integer, List<DbInfo>> getTwoAll(int type) {
        Map<Integer, List<DbInfo>> map = new HashMap<>();
        return map;

    }


    /**
     * 获取id
     *
     * @param s    查询的key
     * @param s1   查询的name
     * @param code 类型
     * @return
     */
    public int getIdByNameFromTwo(String s, String s1, int code) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DbHelper.TWO_TABLE, null, DbHelper.TYPE + "=? AND " + DbHelper.ONE_KEY + "=? AND " + DbHelper.TWO_NAME + "=?", new String[]{String.valueOf(code), s, s1}, null, null, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.TWO_ID));
            return id;

        }

        cursor.close();
        return 0;
    }


    public List<DbInfo> getThree(int oneId, int twoId, int type) {
        List<DbInfo> list = new ArrayList<>();
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        Cursor cursor = sql.query(DbHelper.THREE_TABLE, null, DbHelper.TYPE + "=? AND " + DbHelper.ONEID + "=?  AND " + DbHelper.TWO_KEY + "=?",
                new String[]{String.valueOf(type), String.valueOf(oneId), String.valueOf(twoId)}, null, null, null);
        while (cursor.moveToNext()) {
            DbInfo dbInfo = new DbInfo();
            String str = cursor.getString(cursor.getColumnIndex(DbHelper.THREE_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.THREE_ID));
            dbInfo.setId(id);
            dbInfo.setText(str);
            list.add(dbInfo);

        }

        cursor.close();
        return list;
    }


    public void saveTwoData(Key key) {
        int keyId = key.getKey();
        int type = key.getType();
        List<DbInfo> list = key.getList();
        if (list != null) {
            SQLiteDatabase sql = dbHelper.getWritableDatabase();
            for (DbInfo t : list) {
                if (!isTwoExist(sql, DbHelper.TWO_TABLE, DbHelper.ONE_KEY, String.valueOf(keyId), DbHelper.TWO_ID, t, type)) {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.ONE_KEY, keyId);
                    values.put(DbHelper.TWO_NAME, t.getText());
                    values.put(DbHelper.TWO_ID, t.getId());
                    values.put(DbHelper.TYPE, type);

                    sql.insert(DbHelper.TWO_TABLE, null, values);

                }

            }
        }
    }


    public void saveThree(Key key) {
        int keyId = key.getKey();
        int type = key.getType();
        int id = key.getOneId();
        List<DbInfo> list = key.getList();
        if (list != null && list.size() != 0) {
            SQLiteDatabase sql = dbHelper.getWritableDatabase();
            for (DbInfo t : list) {
                if (!isThreeExist(sql, DbHelper.THREE_TABLE, DbHelper.ONEID, id, DbHelper.TWO_KEY, keyId, DbHelper.THREE_ID, t, type)) {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.TWO_KEY, keyId);
                    values.put(DbHelper.THREE_NAME, t.getText());
                    values.put(DbHelper.THREE_ID, t.getId());
                    values.put(DbHelper.TYPE, type);
                    values.put(DbHelper.ONEID, id);

                    sql.insert(DbHelper.THREE_TABLE, null, values);
                }
            }

        }
    }


    public void clearTwo() {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        sql.delete(DbHelper.TWO_TABLE, null, null);

    }


}

