package com.example.android.dao.demo0.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by idisfkj on 16/3/30.
 * Email : idisfkj@qq.com.
 */
public class SqliteDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recycleView.db";
    private static final int VERSION = 1;

    public SqliteDbHelper(Context context) {
        super(context, DB_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DataProviderHelper.ItemDBInfo.TABLE.createTab(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
