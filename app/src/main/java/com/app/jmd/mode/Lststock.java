package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lststock {
    @SerializedName("Issue")
    @Expose
    private String issue;
    @SerializedName("vchdate")
    @Expose
    private String vchdate;
    @SerializedName("partyname")
    @Expose
    private String partyname;
    @SerializedName("lotno")
    @Expose
    private String lotno;
    @SerializedName("designname")
    @Expose
    private String designname;
    @SerializedName("remark")
    @Expose
    private Object remark;
    @SerializedName("typename")
    @Expose
    private String typename;
    @SerializedName("sizename")
    @Expose
    private String sizename;
    @SerializedName("colourname")
    @Expose
    private String colourname;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("rate")
    @Expose
    private Object rate;
    @SerializedName("amount")
    @Expose
    private Object amount;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getVchdate() {
        return vchdate;
    }

    public void setVchdate(String vchdate) {
        this.vchdate = vchdate;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getDesignname() {
        return designname;
    }

    public void setDesignname(String designname) {
        this.designname = designname;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getSizename() {
        return sizename;
    }

    public void setSizename(String sizename) {
        this.sizename = sizename;
    }

    public String getColourname() {
        return colourname;
    }

    public void setColourname(String colourname) {
        this.colourname = colourname;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Object getRate() {
        return rate;
    }

    public void setRate(Object rate) {
        this.rate = rate;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }
}
