package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstCostDetail {

    @SerializedName("slno")
    @Expose
    private String slno;
    @SerializedName("designname")
    @Expose
    private String designname;
    @SerializedName("designpath")
    @Expose
    private String designpath;
    @SerializedName("costsheetpath")
    @Expose
    private String costsheetpath;
    @SerializedName("detslno")
    @Expose
    private String detslno;
    @SerializedName("clothcode")
    @Expose
    private String clothcode;
    @SerializedName("clothname")
    @Expose
    private String clothname;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
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

    public String getCostsheetpath() {
        return costsheetpath;
    }

    public void setCostsheetpath(String costsheetpath) {
        this.costsheetpath = costsheetpath;
    }

    public String getDetslno() {
        return detslno;
    }

    public void setDetslno(String detslno) {
        this.detslno = detslno;
    }

    public String getClothcode() {
        return clothcode;
    }

    public void setClothcode(String clothcode) {
        this.clothcode = clothcode;
    }

    public String getClothname() {
        return clothname;
    }

    public void setClothname(String clothname) {
        this.clothname = clothname;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
