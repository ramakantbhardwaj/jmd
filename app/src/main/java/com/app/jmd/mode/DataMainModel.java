package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMainModel {

    @SerializedName("slno")
    @Expose
    private String slno;
    @SerializedName("vchdate")
    @Expose
    private String vchdate;
    @SerializedName("managercode")
    @Expose
    private String managercode;
    @SerializedName("mastercode")
    @Expose
    private String mastercode;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("lstProductionDetail")
    @Expose
    private List<LstProductionDetail> lstProductionDetail=null;

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getVchdate() {
        return vchdate;
    }

    public void setVchdate(String vchdate) {
        this.vchdate = vchdate;
    }

    public String getManagercode() {
        return managercode;
    }

    public void setManagercode(String managercode) {
        this.managercode = managercode;
    }

    public String getMastercode() {
        return mastercode;
    }

    public void setMastercode(String mastercode) {
        this.mastercode = mastercode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<LstProductionDetail> getLstProductionDetail() {
        return lstProductionDetail;
    }

    public void setLstProductionDetail(List<LstProductionDetail> lstProductionDetail) {
        this.lstProductionDetail = lstProductionDetail;
    }


    public void setLstProductionDetail(String lstProductionDetail) {

    }
}