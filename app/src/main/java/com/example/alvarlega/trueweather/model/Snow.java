package com.example.alvarlega.trueweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {


    @SerializedName("3h")
    @Expose
    private Double threeHours;

    public Double getThreeHours() {
        return threeHours;
    }

    public void setThreeHours(Double threeHours) {
        this.threeHours = threeHours;
    }
}
