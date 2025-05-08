package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstEmbDetail {
    @SerializedName("detslno")
    @Expose
    private String detslno;

    public LstEmbDetail(String detslno, String designcode, String sizecode, String colourcode, String lotno, String qty, String designname, String sizename, String designpath) {
        this.detslno = detslno;
        this.designcode = designcode;
        this.sizecode = sizecode;
        this.colourcode = colourcode;
        this.lotno = lotno;
        this.qty = qty;
        this.designname = designname;
        this.sizename = sizename;
        this.designpath = designpath;
    }

    @SerializedName("designcode")
    @Expose
    private String designcode;
    @SerializedName("sizecode")
    @Expose
    private String sizecode;

    public String getDesignpath() {
        return designpath;
    }

    public void setDesignpath(String designpath) {
        this.designpath = designpath;
    }

    @SerializedName("designpath")
    @Expose
    private String designpath;
    @SerializedName("sizename")
    @Expose
    private String sizename;

    public String getSizename() {
        return sizename;
    }

    public void setSizename(String sizename) {
        this.sizename = sizename;
    }

    public String getDesignname() {
        return designname;
    }

    public void setDesignname(String designname) {
        this.designname = designname;
    }

    @SerializedName("designname")
    @Expose
    private String designname;
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
    public LstEmbDetail() {

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
