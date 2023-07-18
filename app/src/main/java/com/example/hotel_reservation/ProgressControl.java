package com.example.hotel_reservation;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ProgressControl {
    private ProgressBar progressBar;
    private ImageView progress20, progress40, progress60, progress80;
    private int currentProgress = 20;

    public ProgressControl(ProgressBar progressBar, ImageView progress20, ImageView progress40,
                           ImageView progress60, ImageView progress80) {
        this.progressBar = progressBar;
        this.progress20 = progress20;
        this.progress40 = progress40;
        this.progress60 = progress60;
        this.progress80 = progress80;
    }

    public void setProgress(int progress) {
        currentProgress = progress;
        progressBar.setProgress(currentProgress);

        if (progress >= 40) {
            changeCircleColor(progress20, "#02ba18");
            progressBar.setProgress(currentProgress);
        } else {
            changeCircleColor(progress20, "#FFFFFF");
            changeCircleColor(progress40, "#FFFFFF");
        }

        if (progress >= 60) {
            changeCircleColor(progress40, "#02ba18");
            progressBar.setProgress(currentProgress);

        } else {
            changeCircleColor(progress60, "#FFFFFF");
        }

        if (progress >= 80) {
            changeCircleColor(progress60, "#02ba18");
            progressBar.setProgress(currentProgress);

        } else {
            changeCircleColor(progress80, "#FFFFFF");
        }
        if (progress>=100){
            changeCircleColor(progress80, "#02ba18");
            progressBar.setProgress(currentProgress);

        }
    }

    private void changeCircleColor(ImageView view, String colorCode) {
        int color = Color.parseColor(colorCode);
        GradientDrawable circleShape = (GradientDrawable) view.getDrawable();
        circleShape.setColor(color);
    }

    public int getProgress() {
        return currentProgress;
    }
}
























//package edu.cs.birzeit.progress;
//
//import android.graphics.Color;
//import android.graphics.drawable.GradientDrawable;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//public class ProgressControl {
//    private ProgressBar progressBar;
//    private ImageView progress20, progress40, progress60, progress80;
//    private int currentProgress = 20;
//
//    public ProgressControl(ProgressBar progressBar, ImageView progress20, ImageView progress40,
//                           ImageView progress60, ImageView progress80) {
//        this.progressBar = progressBar;
//        this.progress20 = progress20;
//        this.progress40 = progress40;
//        this.progress60 = progress60;
//        this.progress80 = progress80;
//    }
//
//    public void increaseProgress() {
//        if (currentProgress <= 80) {
//            updateCircleColor(currentProgress);
//            currentProgress += 20;
//            progressBar.setProgress(currentProgress);
//        }
//    }
//
//    public void setProgress(int progress) {
//        if (progress >= 20 && progress <= 80) {
//            currentProgress = progress;
//            progressBar.setProgress(currentProgress);
//        }
//    }
//
//    private void updateCircleColor(int currentProgress) {
//        if (currentProgress >= 20)
//            changeCircleColor(progress20, "#02ba18");
//        if (currentProgress >= 40)
//            changeCircleColor(progress40, "#02ba18");
//        if (currentProgress >= 60)
//            changeCircleColor(progress60, "#02ba18");
//        if (currentProgress >= 80)
//            changeCircleColor(progress80, "#02ba18");
//
//    }
//
//    private void changeCircleColor(ImageView view, String colorCode) {
//        int color = Color.parseColor(colorCode);
//        GradientDrawable circleShape = (GradientDrawable) view.getDrawable();
//        circleShape.setColor(color);
//    }
//
//    public int getProgress() {
//        return currentProgress;
//    }
//
//    public void increaseProgressLine() {
//        progressBar.setProgress(currentProgress+=20);
//        updateCircleColor(currentProgress-20);
//    }
//}
