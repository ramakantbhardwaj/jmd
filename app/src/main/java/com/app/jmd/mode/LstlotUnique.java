package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstlotUnique {

    @SerializedName("lotno")
    @Expose
    private String lotno;

    public LstlotUnique(String lotno, boolean isChecked) {
        this.lotno = lotno;
        this.isChecked = isChecked;
    }

    @SerializedName("designcode")
    @Expose
    private String designcode;
    @SerializedName("qty")
    @Expose
    private String qty;
    boolean isChecked;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = true;
    }


    @SerializedName("Repeat")
    @Expose
    private String Repeat;
    @SerializedName("designname")
    @Expose
    private String designname;
    @SerializedName("designpath")
    @Expose
    private String designpath;

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

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

    public String getDesignpath() {
        return designpath;
    }

    public void setDesignpath(String designpath) {
        this.designpath = designpath;
    }
    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
