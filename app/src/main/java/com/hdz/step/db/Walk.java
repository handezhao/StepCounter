package com.hdz.step.db;

import java.io.Serializable;

/**
 * Description:
 * Created by hdz on 2017/7/4.
 */

public class Walk implements Serializable {

    private static final long serialVersionUID = 1L;

    private String date;

    private int steps;

    public Walk(String date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Walk{" +
                "date='" + date + '\'' +
                ", steps=" + steps +
                '}';
    }
}
