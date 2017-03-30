package com.kevintong.reminder.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.kevintong.reminder.FontLoader;
import com.kevintong.reminder.R;

/**
 * Created by kevintong on 2017-03-29.
 */

public class CreateTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        TextView testTextView = (TextView) findViewById(R.id.test_text);
        FontLoader.setTextViewASMAN(CreateTaskActivity.this, testTextView);
    }
}
