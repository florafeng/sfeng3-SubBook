/* EditSubscriptionActivity
 *
 * Version 1.0
 *
 * February 5, 2018
 *
 * Copyright (c) 2018 Sida CMPUT 301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and condition of the Code of Student Behaviour at University of Alberta.
 * You can find a copy of licence in this project. Otherwise please contact contact sfeng3@ualberta.ca.
 */

package com.example.sida.sfeng3_subbook;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This activity enables user to edit subscription details
 *
 * @author Sida Feng
 * @version 1.0
 */
public class EditSubscriptionActivity extends AppCompatActivity {
    private String name;
    private EditText nameText;
    private Calendar dateStarted;
    private EditText dateStartedText;
    private Double monthlyCharge;
    private EditText monthlyChargeText;
    private String comment;
    private EditText commentText;
    private Button saveButton;
    private Button cancelButton;
    private String subscriptionDesc;
    private Subscription subscription;
    private int year;
    private int month;
    private int day;

    /**
     * Triggered when activity starts
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        nameText = findViewById(R.id.edit_subscription_name);
        dateStartedText = findViewById(R.id.edit_subscription_date);
        commentText = findViewById(R.id.edit_subscription_comment);
        monthlyChargeText = findViewById(R.id.edit_subscription_charge);
        cancelButton = findViewById(R.id.edit_subscription_cancel);
        saveButton = findViewById(R.id.edit_subscription_save);

        subscriptionDesc = getIntent().getExtras().getString("SubscriptionSearchText");

        System.out.println(subscriptionDesc);

        IOUtility ioUtil = new IOUtility(EditSubscriptionActivity.this);
        ArrayList<Subscription> subscriptionList = ioUtil.loadFromAllFiles();
        if (!subscriptionList.isEmpty()) {
            for (Subscription subscriptionObj : subscriptionList) {
                if ( subscriptionObj.toString().equals(subscriptionDesc) ){
                    subscription = subscriptionObj;
                    break;
                }
            }
        }

        nameText.setText(subscription.getName());
        monthlyChargeText.setText(subscription.getMonthlyChargeString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateStartedText.setText(dateFormat.format(subscription.getDateStarted().getTime()));

        commentText.setText(subscription.getComment());

        dateStartedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();

                dateStarted = Calendar.getInstance();
                dateStarted.set(year, month, day, 0, 0);

                comment = commentText.getText().toString();
                monthlyCharge = Double.valueOf( monthlyChargeText.getText().toString() );

//                if (initvalueString.isEmpty() || initvalueString.trim().isEmpty()) {
//                    Toast.makeText(EditCounterActivity.this, "init value is required", Toast.LENGTH_SHORT).show();
//                } else { }

                IOUtility ioUtility = new IOUtility(EditSubscriptionActivity.this);
                ioUtility.deleteFile(subscription);
                Subscription newSubscription = new Subscription(name, dateStarted, monthlyCharge, comment);
                ioUtility.saveInFile(newSubscription);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Open date picking dialog
     */
    private void openDatePickerDialog() {
        final Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        dateStartedText.setHint(year + "-" + month + "-" + day);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearr, int monthOfYear, int dayOfMonth) {
                dateStartedText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                year = yearr;
                month = monthOfYear;
                day = dayOfMonth;
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
