package com.momogui.measuredbypictures;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.momogui.measuredbypictures.database.SettingDbSchema;
import java.util.UUID;

/**
 * Created by momo on 2017-07-13.
 */

public class SettingCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SettingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Setting getSetting(){
        String uuidString = getString(getColumnIndex(SettingDbSchema.SettingTable.Cols.UUID));
        String title = getString(getColumnIndex(SettingDbSchema.SettingTable.Cols.TITLE));
        double height = getDouble(getColumnIndex(SettingDbSchema.SettingTable.Cols.HEIGHT));

        Setting setting = new Setting(UUID.fromString(uuidString));
        setting.setMyTitle(title);
        setting.setMyHeight(height);

        return setting;
    }
}
