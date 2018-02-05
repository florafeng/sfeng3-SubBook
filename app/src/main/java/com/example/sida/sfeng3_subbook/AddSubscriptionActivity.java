/* AddSubscriptionActivity
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AddSubscriptionActivity extends AppCompatActivity {

    private String name;
    private EditText nameText;
    private Calendar dateStarted;
    private EditText dateStartedText;
    private Double monthlyCharge;
    private EditText monthlyChargeText;
    private String comment;
    private EditText commentText;
    private Button submitButton;
    private Button cancelButton;
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
        setContentView(R.layout.activity_add_subscription);

        nameText = findViewById(R.id.add_subscription_name);
        dateStartedText = findViewById(R.id.add_subscription_date);
        commentText = findViewById(R.id.add_subscription_comment);
        monthlyChargeText = findViewById(R.id.add_subscription_charge);
        submitButton = findViewById(R.id.add_subscription_add);
        cancelButton = findViewById(R.id.add_subscription_cancel);

        dateStartedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameText.getText().toString();

                dateStarted = Calendar.getInstance();
                dateStarted.set(year, month, day, 0, 0);

                monthlyCharge = Double.valueOf( monthlyChargeText.getText().toString() );
                comment = commentText.getText().toString();

                addSubscription(name, dateStarted, monthlyCharge, comment);

//                if (name.isEmpty() || name.trim().isEmpty()) {
//                    Toast.makeText(AddSubscriptionActivity.this, "Init value is required", Toast.LENGTH_SHORT).show();
//                }
//                else if (name.isEmpty() || name.trim().isEmpty()) {
//                    Toast.makeText(AddSubscriptionActivity.this, " Name is required", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    addSubscription(name, dateStarted, monthlyCharge, comment);
//                }
            }
        });

    }

    /**
     * Add a subscription and save in file
     *
     * @param name subscription name
     * @param dateStarted subscription start date
     * @param monthlyCharge subscription monthly charge
     * @param comment subscription comment
     *
     */
    protected void addSubscription(String name, Calendar dateStarted, Double monthlyCharge, String comment) {
        try {
            Subscription subscription = new Subscription(name, dateStarted, monthlyCharge, comment);
            IOUtility ioUtility = new IOUtility(this);
            ioUtility.saveInFile(subscription);
            finish();
            Log.d("list_file", fileList().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Open date picking dialog
     */
    public void openDatePickerDialog() {
        final Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);

        dateStartedText.setHint(year + "-" + month + "-" + day);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearr, int monthOfYear, int dayOfMonth) {
                dateStartedText.setText(yearr + "-" + ( monthOfYear + 1 ) + "-" + dayOfMonth);
                year = yearr;
                month = monthOfYear;
                day = dayOfMonth;
            }
        }, year, month, day);

        System.out.println(year);
        System.out.println(month);
        System.out.println(day);

        datePickerDialog.show();
    }

}
