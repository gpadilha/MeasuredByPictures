package com.momogui.measuredbypictures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private EditText myUserHeight;
    private Button myButton;
    private String mCurrentHeight;
    private TextView mCurrentHeightTextView;

    private static final String TAG = "SettingActivity";
    private static final String CURRENT_HEIGHT = "height";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mCurrentHeight = data from database recent;

        setContentView(R.layout.activity_setting);
        mCurrentHeightTextView = (TextView) findViewById(R.id.current_height);
        mCurrentHeightTextView.setText(mCurrentHeight);
        myUserHeight = (EditText) findViewById(R.id.setting_user_height);
        myButton = (Button) findViewById(R.id.save_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentHeight = myUserHeight.getText().toString();
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
        Setting setting = new Setting();
        setting.setMyTitle("user_height");
        setting.setMyHeight(new Double(height));
        mCurrentHeightTextView.setText(height);
        SettingStorage storage = SettingStorage.get(this);
        storage.insertSetting(setting);
        Log.i(TAG, "saveOnDatabase: Saved ");
    }
}
