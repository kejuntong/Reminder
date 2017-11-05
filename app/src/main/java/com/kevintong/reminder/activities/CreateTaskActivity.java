package com.kevintong.reminder.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kevintong.reminder.MyApp;
import com.kevintong.reminder.views.CustomFontButton;
import com.kevintong.reminder.R;
import com.kevintong.reminder.database.TaskDbUtilMethods;

/**
 * Created by kevintong on 2017-03-29.
 */

public class CreateTaskActivity extends Activity {

    CustomFontButton backButton;
    CustomFontButton saveButton;

    EditText taskNameInput;
    EditText taskDetailsInput;

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
                        String taskTime = "hold on";
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
