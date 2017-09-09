package com.momogui.measuredbypictures;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_MEASURE1 = "measure1";
    private static final String EXTRA_MEASURE2 = "measure2";

    private TextView mTargetHeight;
    private ImageButton mHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mHomeButton = (ImageButton) findViewById(R.id.home_button);

        SettingStorage storage = SettingStorage.get(this);

        double parameter = storage.getSettings().get(0).getMyHeight();
        double reference = getIntent().getFloatExtra(EXTRA_MEASURE1, 0);
        double target = getIntent().getFloatExtra(EXTRA_MEASURE2, 0);

        // reference = parameter
        // target = height
        double height = target * parameter / reference;

        mTargetHeight = (TextView) findViewById(R.id.target_height);
        mTargetHeight.setText(String.valueOf(Math.round(height))+ " cm");

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public static Intent newInstance(Context context, List<Path> pathList){
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_MEASURE1, new PathMeasure(pathList.get(0), false).getLength());
        intent.putExtra(EXTRA_MEASURE2, new PathMeasure(pathList.get(1), false).getLength());
        return intent;
    }
}
