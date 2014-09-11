/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;

/**
 * <p> It is the  DTO class which is used to transfer prize data. The information can be null or can be empty, therefore
 * this check is not present in the setters. It is related with the Prize entity.</p>
 *
 * <p> This class is not thread safe because it's highly mutable</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class CompetitionPrize implements Serializable {
    /**
     * <p> Represents the prize amount</p>
     */
    private double amount = -1.0;

    /**
     * <p> Represents the place</p>
     */
    private int place = -1;

    /**
     * <p> This is the default constructor. It does nothing.</p>
     */
    public CompetitionPrize() {
    }

    /**
     * <p> Return the amount</p>
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * <p> Set the amount</p>
     *
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * <p> Return the place</p>
     *
     * @return the place
     */
    public int getPlace() {
        return place;
    }

    /**
     * <p> Set the place</p>
     *
     * @param place the place to set
     */
    public void setPlace(int place) {
        this.place = place;
    }
}


