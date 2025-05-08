package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstGodown {
    @SerializedName("Godowncode")
    @Expose
    private String godowncode;
    @SerializedName("Godownname")
    @Expose
    private String godownname;

    public String getGodowncode() {
        return godowncode;
    }

    public void setGodowncode(String godowncode) {
        this.godowncode = godowncode;
    }

    public String getGodownname() {
        return godownname;
    }

    public void setGodownname(String godownname) {
        this.godownname = godownname;
    }

}
