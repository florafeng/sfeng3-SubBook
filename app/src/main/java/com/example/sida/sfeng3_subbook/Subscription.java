/* Subscription
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a Subscription
 *
 * @author Sida Feng
 * @version 1.0
 */
public class Subscription{

    private String name;
    private Calendar dateStarted;
    private Double monthlyCharge;
    private String comment;

    /**
     * Constructs a Subscription object
     *
     * @param name subscription name
     * @param dateStarted subscription start date
     * @param monthlyCharge subscription monthly charge in CAD
     * @param comment subscription comment
     */
    public Subscription(String name, Calendar dateStarted, Double monthlyCharge, String comment){
        this.name = name;
        this.dateStarted = dateStarted;
        this.monthlyCharge= monthlyCharge;
        this.comment = comment;
    }

    /**
     * Converts subscription details to string.
     *
     * @return result (String)
     */
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = "NAME: "+ getName() + ",   DATE: " + dateFormat.format(getDateStarted().getTime()) + ",   MONTHLY CHARGE: $" + getMonthlyChargeString() + ",  COMMENT: " + getComment();
        return result;
    }

    /**
     * Gets subscription name
     *
     * @return name (String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets subscription name
     *
     * @return name (String)
     */
    public Calendar getDateStarted() {
        return this.dateStarted;
    }

    /**
     * Gets subscription monthly charge string
     *
     * @return monthly charge (String)
     */
    public String getMonthlyChargeString() {
        return this.monthlyCharge.toString();
    }

    /**
     * Gets subscription monthly charge number
     *
     * @return monthly charge (Double)
     */
    public Double getMonthlyChargeNumber(){
        return this.monthlyCharge;
    }

    /**
     * Gets subscription comment
     *
     * @return comment (String)
     */
    public String getComment() {
        return this.comment;
    }

}