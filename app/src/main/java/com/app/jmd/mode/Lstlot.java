package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lstlot {



    @SerializedName("designpath")
    @Expose
    private String designpath;
    @SerializedName("designcode")
    @Expose
    private String designcode;
    @SerializedName("designname")
    @Expose
    private String designname;
    @SerializedName("sizecode")
    @Expose
    private String sizecode;
    @SerializedName("sizename")
    @Expose
    private String sizename;
    @SerializedName("colourcode")
    @Expose
    private String colourcode;
    @SerializedName("colourname")
    @Expose
    private String colourname;
    @SerializedName("lotno")
    @Expose
    private String lotno;
    @SerializedName("Qty")
    @Expose
    private String qty;

    public String getDesignpath() {
        return designpath;
    }

    public void setDesignpath(String designpath) {
        this.designpath = designpath;
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

    public String getColourcode() {
        return colourcode;
    }

    public void setColourcode(String colourcode) {
        this.colourcode = colourcode;
    }

    public String getColourname() {
        return colourname;
    }

    public void setColourname(String colourname) {
        this.colourname = colourname;
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
