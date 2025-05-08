package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstSize {

    @SerializedName("Sizecode")
    @Expose
    private String sizecode;
    @SerializedName("Sizename")
    @Expose
    private String sizename;

    public String getSizecode() {
        return sizecode;
    }

    public void setSizecode(String sizecode) {
        this.sizecode = sizecode;
    }

    public String getSizename() {
        return sizename;
    }

    public void setSizename(String sizename) {
        this.sizename = sizename;
    }
}
