package com.momogui.measuredbypictures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private EditText myUserHeight;
    private Button myButton;
    private Setting mCurrentSetting;
    private SettingStorage mStorage;

    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        myUserHeight = (EditText) findViewById(R.id.setting_user_height);
        myButton = (Button) findViewById(R.id.save_button);

        //try to get setting if already exists
        mStorage = SettingStorage.get(this);
        List<Setting> existentSettings = mStorage.getSettings();
        if(existentSettings.size()>0){
            mCurrentSetting = existentSettings.get(0);
            myUserHeight.setText(String.valueOf(mCurrentSetting.getMyHeight()));
        }


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnDatabase(myUserHeight.getText().toString());
                finish();
            }
        });

    }
    // mCurrentHeight will have the height value that user set.
    // get the most recent height from the database onCreate. if there's no height in the database then you can set it to nothing but if there's a data in the database then set that height data to textView text.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void saveOnDatabase(String height) {
        if(mCurrentSetting == null){
            mCurrentSetting = new Setting();
            mCurrentSetting.setMyTitle("user_height");
            mCurrentSetting.setMyHeight(new Double(height));
            mStorage.insertSetting(mCurrentSetting);
        }else{
            mCurrentSetting.setMyHeight(new Double(height));
            mStorage.updateSetting(mCurrentSetting);
        }

        Log.i(TAG, "saveOnDatabase: Saved ");
    }
}
