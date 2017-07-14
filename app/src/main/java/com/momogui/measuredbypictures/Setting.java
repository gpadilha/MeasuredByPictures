package com.momogui.measuredbypictures;

import java.util.UUID;

/**
 * Created by momo on 2017-07-13.
 */

public class Setting {
    private UUID myUUID;
    private String myTitle;
    private double myLength;



    public Setting(){
        this(UUID.randomUUID());
    }

    public Setting(UUID id){
        myUUID = id;
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

    public double getMyLength() {
        return myLength;
    }

    public void setMyLength(double myLength) {
        this.myLength = myLength;
    }
}
