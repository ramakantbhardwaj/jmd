package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstProductionDetail {

    @SerializedName("detslno")
    @Expose
    private String detslno;
    @SerializedName("designcode")
    @Expose
    private String designcode;

    public String getDesignname() {
        return designname;
    }

    public void setDesignname(String designname) {
        this.designname = designname;
    }

    public String getSizename() {
        return sizename;
    }

    public void setSizename(String sizename) {
        this.sizename = sizename;
    }

    @SerializedName("designname")
    @Expose
    private String designname;
    @SerializedName("sizecode")
    @Expose
    private String sizecode;
    @SerializedName("sizename")
    @Expose
    private String sizename;
    @SerializedName("qty")
    @Expose
    private String qty;

    public LstProductionDetail(String detslno, String designcode,String designname, String sizecode,String sizename, String qty) {
        this.detslno = detslno;
        this.designcode = designcode;
        this.designname = designname;
        this.sizename = sizename;
        this.sizecode = sizecode;
        this.qty = qty;
    }

    public String getDetslno() {
        return detslno;
    }

    public void setDetslno(String detslno) {
        this.detslno = detslno;
    }

    public String getDesigncode() {
        return designcode;
    }

    public void setDesigncode(String designcode) {
        this.designcode = designcode;
    }
    public LstProductionDetail() {

    }

    public String getSizecode() {
        return sizecode;
    }

    public void setSizecode(String sizecode) {
        this.sizecode = sizecode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
