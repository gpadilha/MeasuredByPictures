package com.momogui.measuredbypictures;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.momogui.measuredbypictures.database.SettingDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by momo on 2017-07-13.
 */

public class SettingStorage { // connector between database and activity
    private static SettingStorage sSettingStorage;
    private Context myContext;
    private SQLiteDatabase myDatabase;

    public static SettingStorage get(Context context) {
        if (sSettingStorage == null) {
            sSettingStorage = new SettingStorage(context);
        }
        return sSettingStorage;
    }

    private SettingStorage(Context context) {
        myContext = context.getApplicationContext();
        myDatabase = new SettingBaseHelper(myContext).getWritableDatabase();
    }

    public static ContentValues getContentValues(Setting setting) {
        ContentValues values = new ContentValues();
        values.put(SettingDbSchema.SettingTable.Cols.UUID, String.valueOf(setting.getMyUUID()));
        values.put(SettingDbSchema.SettingTable.Cols.TITLE, setting.getMyTitle());
        values.put(SettingDbSchema.SettingTable.Cols.HEIGHT, setting.getMyHeight());

        return values;
    }

    public void insertSetting(Setting setting) {
        ContentValues values = getContentValues(setting);
        myDatabase.insert(SettingDbSchema.SettingTable.NAME, null, values);
    }

    public void updateSetting(Setting setting) {
        String uuidString = setting.getMyUUID().toString();
        ContentValues values = getContentValues(setting);

        myDatabase.update(SettingDbSchema.SettingTable.NAME,
                values,
                SettingDbSchema.SettingTable.Cols.UUID + " = ?",
                new String[]{uuidString}
        );
    }

    private SettingCursorWrapper querySettings(String whereClause, String[] whereArgs) {
        Cursor cursor = myDatabase.query(
                SettingDbSchema.SettingTable.NAME,
                null, // column = null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new SettingCursorWrapper(cursor);
    }

    public void removeSetting(Setting setting) {
        String uuidString = setting.getMyUUID().toString();
        myDatabase.delete(
                SettingDbSchema.SettingTable.NAME,
                SettingDbSchema.SettingTable.Cols.UUID + " = ?",
                new String[]{uuidString}
        );
    }

    public List<Setting> getSettings() {
        List<Setting> settings = new ArrayList<>();
        SettingCursorWrapper cursor = querySettings(null, null);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                settings.add(cursor.getSetting());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return settings;
    }

    public Setting getSetting(UUID id) {
        SettingCursorWrapper cursor = querySettings(
                SettingDbSchema.SettingTable.Cols.UUID + " = ?",
                new String[]{
                        id.toString()
                }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getSetting();
        } finally {
            cursor.close();
        }
    }
}
