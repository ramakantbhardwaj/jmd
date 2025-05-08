package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstDesign {

    @SerializedName("designcode")
    @Expose
    private String designcode;
    @SerializedName("designname")
    @Expose
    private String designname;

    public String getDesigncode() {
        return designcode;
    }

    public void setDesigncode(String designcode) {
        this.designcode = designcode;
    }

    public String getDesignname() {
        return designname;
    }

    public void setDesignname(String designname) {
        this.designname = designname;
    }
}
