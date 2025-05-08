package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lstprod {

    @SerializedName("Manager")
    @Expose
    private String manager;
    @SerializedName("Master")
    @Expose
    private String master;
    @SerializedName("Design")
    @Expose
    private String design;
    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("Days")
    @Expose
    private String days;

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

}
