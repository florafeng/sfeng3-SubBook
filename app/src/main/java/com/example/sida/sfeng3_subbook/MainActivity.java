/* MainActivity
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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Represents the app's main page
 *
 * @author Sida Feng
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Subscription> subscriptionList = new ArrayList<>();
    private ArrayAdapter<Subscription> subscriptionAdapter;
    private ListView subscriptionListView;
    private TextView totalChargeTextView;

    /**
     * Triggers when app starts
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subscriptionListView = findViewById(R.id.subscription_list);
        subscriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subscriptionName = parent.getItemAtPosition(position).toString();

                for (Subscription subscription : subscriptionList) {
                    if (subscription.toString().equals(subscriptionName)) {
                        openEditSubscriptionDialog(subscription);
                    }
                }
            }
        });

        loadAllSubscription();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllSubscription();
        subscriptionAdapter = new ArrayAdapter<>(this, R.layout.subscription_list_item, subscriptionList);
        subscriptionListView.setAdapter(subscriptionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscriptionList.clear();
        loadAllSubscription();
        subscriptionAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_subscription_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if ( itemId == R.id.action_add_subscription ){
            Intent intentView = new Intent(this, AddSubscriptionActivity.class);
            startActivity(intentView);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Load saved subscriptions from local file
     *
     * @see IOUtility
     */
    private void loadAllSubscription() {
        IOUtility ioUtility = new IOUtility(this);
        ArrayList<Subscription> subscriptions = ioUtility.loadFromAllFiles();
        Double totalCharge = 0.0;
        if (!subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                subscriptionList.add(subscription);
                totalCharge += subscription.getMonthlyChargeNumber();
            }
        }
        totalChargeTextView = findViewById(R.id.total_charge);
        totalChargeTextView.setText(String.format("$%.2f", totalCharge));
    }

    /**
     * Open a dialog for user to edit subscription
     *
     * @param subscription Subscription object
     */
    private void openEditSubscriptionDialog(final Subscription subscription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Do you want edit this subscription?")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentEdit = new Intent(MainActivity.this, EditSubscriptionActivity.class);
                        intentEdit.putExtra("SubscriptionSearchText", subscription.toString());
                        startActivity(intentEdit);
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSubscription(subscription);
                        loadAllSubscription();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Delete a subscription
     *
     * @param subscription Subscription object to delete
     */
    private void deleteSubscription(Subscription subscription) {
        IOUtility ioUtility = new IOUtility(this);
        ioUtility.deleteFile(subscription);
        subscriptionList.remove(subscription);
        subscriptionAdapter.clear();
        subscriptionAdapter.addAll(subscriptionList);
        subscriptionAdapter.notifyDataSetChanged();
    }

}
