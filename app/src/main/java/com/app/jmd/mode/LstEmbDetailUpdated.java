package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstEmbDetailUpdated {
    @SerializedName("detslno")
    @Expose
    private String detslno;

    public LstEmbDetailUpdated(String detslno, String designcode, String sizecode, String colourcode, String lotno, String qty) {
        this.detslno = detslno;
        this.designcode = designcode;
        this.sizecode = sizecode;
        this.colourcode = colourcode;
        this.lotno = lotno;
        this.qty = qty;
    }

    @SerializedName("designcode")
    @Expose
    private String designcode;
    @SerializedName("sizecode")
    @Expose
    private String sizecode;

    @SerializedName("colourcode")
    @Expose
    private String colourcode;
    @SerializedName("lotno")
    @Expose
    private String lotno;
    @SerializedName("qty")
    @Expose
    private String qty;

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
    public LstEmbDetailUpdated() {

    }

    public String getSizecode() {
        return sizecode;
    }

    public void setSizecode(String sizecode) {
        this.sizecode = sizecode;
    }

    public String getColourcode() {
        return colourcode;
    }

    public void setColourcode(String colourcode) {
        this.colourcode = colourcode;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
