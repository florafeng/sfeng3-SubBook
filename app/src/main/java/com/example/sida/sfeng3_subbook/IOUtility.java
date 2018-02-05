/* IOUtility
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

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Handles subscription operations(input & output) using Gson
 *
 * @author Sida Feng
 * @version 1.0
 */
public class IOUtility {

    private Context context;

    /**
     * Constructs a IOUtility object
     *
     * @param context
     */
    public IOUtility(Context context) {
        this.context = context;
    }

    /**
     * Load subscriptions saved in file
     */
    protected ArrayList<Subscription> loadFromAllFiles() {
        ArrayList<Subscription> subscriptions = new ArrayList<>();

        Subscription subscription;
        String[] fileList = this.context.fileList();
        for (int i = 0; i < fileList.length; i++) {
            try {
                FileInputStream fis = this.context.openFileInput(fileList[i]);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                subscription = gson.fromJson(in, Subscription.class);
                subscriptions.add(subscription);
            } catch (FileNotFoundException e) {
                System.out.println("Error " + e.getMessage());
                throw new RuntimeException();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
                throw new RuntimeException();
            }
        }

        return subscriptions;
    }

    /**
     * Generate json file name
     *
     * @param  subscription subscription object
     * @return json file name (String)
     */
    protected String jsonFileName(Subscription subscription) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return dateFormat.format(subscription.getDateStarted().getTime()) + ".json";
    }

    /**
     * Delete file containing a subscription
     *
     * @param  subscription subscription object
     */
    protected void deleteFile(Subscription subscription) {
        String fileName = jsonFileName(subscription);
        this.context.deleteFile(fileName);
    }

    /**
     * Save subscription in file
     *
     * @param  subscription subscription object
     */
    protected void saveInFile(Subscription subscription) {
        String fileName = jsonFileName(subscription);
        try {
            FileOutputStream fos = this.context.openFileOutput(fileName, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(subscription, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException();
        }
    }

}
