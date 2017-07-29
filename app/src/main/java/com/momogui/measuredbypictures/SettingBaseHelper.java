package com.momogui.measuredbypictures;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.momogui.measuredbypictures.database.SettingDbSchema;

/**
 * Created by momo on 2017-07-13.
 */

public class SettingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "settingBase.db";


    public SettingBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SettingDbSchema.SettingTable.NAME
                + "(" + " _id INTEGER primary key autoincrement, "
                + SettingDbSchema.SettingTable.Cols.UUID + ", "
                + SettingDbSchema.SettingTable.Cols.TITLE + ", "
                + SettingDbSchema.SettingTable.Cols.HEIGHT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
