package com.momogui.measuredbypictures;

import java.util.UUID;

/**
 * Created by momo on 2017-07-13.
 */

public class Setting {
    private UUID myUUID;
    private String myTitle;
    private double myHeight;


    public Setting(){
        this(UUID.randomUUID());
    }

    public Setting(UUID id){
        myUUID = id;
    }

    public Setting(Double height) {
        this(UUID.randomUUID());
        myHeight = height;

    }

    public UUID getMyUUID() {
        return myUUID;
    }

    public void setMyUUID(UUID myUUID) {
        this.myUUID = myUUID;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    public double getMyHeight() {
        return myHeight;
    }

    public void setMyHeight(double myHeight) {
        this.myHeight = myHeight;
    }
}
