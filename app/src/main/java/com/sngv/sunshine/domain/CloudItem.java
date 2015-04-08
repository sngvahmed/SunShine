package com.sngv.sunshine.domain;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sngv on 08/04/15.
 */
public class CloudItem{
    private String day;
    private int minGrade;
    private int maxGrade;
    private ImageView cloudImage;

    public CloudItem(String Day ,int MinGrade , int MaxGrade){
        day = Day;
        minGrade = MinGrade;
        maxGrade = MaxGrade;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public ImageView getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(ImageView cloudImage) {
        this.cloudImage = cloudImage;
    }
}
