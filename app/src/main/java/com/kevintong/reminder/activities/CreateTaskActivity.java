package com.kevintong.reminder.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kevintong.reminder.MyApp;
import com.kevintong.reminder.UtilMethods;
import com.kevintong.reminder.views.CustomFontButton;
import com.kevintong.reminder.R;
import com.kevintong.reminder.database.TaskDbUtilMethods;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kevintong on 2017-03-29.
 */

public class CreateTaskActivity extends Activity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    CustomFontButton backButton;
    CustomFontButton saveButton;

    EditText taskNameInput;
    EditText taskDetailsInput;

    Calendar selectedTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        initViews();

        setTopButtons();
    }

    public void initViews(){
        backButton = (CustomFontButton) findViewById(R.id.back_button);
        saveButton = (CustomFontButton) findViewById(R.id.save_button);
        taskNameInput = (EditText) findViewById(R.id.task_name_input);
        taskDetailsInput = (EditText) findViewById(R.id.task_details_input);

        taskNameInput.requestFocus();

        Button button = (Button) findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateTaskActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMinDate(now);
                dpd.setAccentColor(getResources().getColor(R.color.orange));
                dpd.show(getFragmentManager(), "DatePicker");
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        selectedTime.set(Calendar.YEAR, year);
        selectedTime.set(Calendar.MONTH, monthOfYear);
        selectedTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CreateTaskActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        if (isTimePickerConstraintRequired()) {
            tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), 0);
        }
        tpd.setAccentColor(getResources().getColor(R.color.orange));
        tpd.show(getFragmentManager(), "TimePicker");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedTime.set(Calendar.MINUTE, minute);
        selectedTime.set(Calendar.SECOND, 0);

        TextView textView = (TextView) findViewById(R.id.test_txt);
        textView.setText(UtilMethods.timeToString(selectedTime.getTimeInMillis()));

    }

    private boolean isTimePickerConstraintRequired(){
        // since already set the minimum for date picker, only need to see if it's same day
        Calendar now = Calendar.getInstance();
        return
                selectedTime.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                selectedTime.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                selectedTime.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH);
    }

    public void setTopButtons(){

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation backButtonAnim = AnimationUtils.loadAnimation(CreateTaskActivity.this, R.anim.button_clicked);
                backButton.startAnimation(backButtonAnim);

                backButtonAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation saveButtonAnim = AnimationUtils.loadAnimation(CreateTaskActivity.this, R.anim.button_clicked);
                saveButton.startAnimation(saveButtonAnim);

                saveButtonAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        String taskTitle = taskNameInput.getText().toString();

                        if (taskTitle.trim().isEmpty()){
                            showEmptyTaskNameDialog();
                            return;
                        }

                        String taskDesc = taskDetailsInput.getText().toString();
                        long taskTime = selectedTime.getTimeInMillis();
                        TaskDbUtilMethods.writeAnItemToTaskTable(MyApp.dbHelper, taskTitle, taskDesc, taskTime);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    private void showEmptyTaskNameDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(CreateTaskActivity.this)
                .content(getString(R.string.empty_task_name))
                .contentColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .positiveText("OK")
                .positiveColorRes(R.color.blue);

        builder.build().show();
    }

}
