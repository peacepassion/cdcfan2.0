package com.linuxclub.cdcfan.model;

/**
 * Created by peace_da on 2015/5/26.
 */
public class OrderResult {

    public int succeed_count = 0;
    public int exceed_count = 0;
    public int rejected_count = 0;
    public int failure_count = 0;

    @Override
    public String toString() {
        return "succeed_count: " + succeed_count + ", exceed_count: " + exceed_count + ", rejected_count: " + rejected_count + ", failure_count: " + failure_count;
    }
}
