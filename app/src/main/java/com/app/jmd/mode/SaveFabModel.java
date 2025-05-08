package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveFabModel {

@SerializedName("slno")
@Expose
private String slno;
@SerializedName("vchdate")
@Expose
private String vchdate;
@SerializedName("partycode")
@Expose
private String partycode;
@SerializedName("mastercode")
@Expose
private String mastercode;
@SerializedName("godowncode")
@Expose
private String godowncode;
@SerializedName("remark")
@Expose
private String remark;
@SerializedName("lstEmbDetail")
@Expose
private List<LstEmbDetail> lstEmbDetail;

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

public String getPartycode() {
return partycode;
}

public void setPartycode(String partycode) {
this.partycode = partycode;
}

public String getMastercode() {
return mastercode;
}

public void setMastercode(String mastercode) {
this.mastercode = mastercode;
}

public String getGodowncode() {
return godowncode;
}

public void setGodowncode(String godowncode) {
this.godowncode = godowncode;
}

public String getRemark() {
return remark;
}

public void setRemark(String remark) {
this.remark = remark;
}

public List<LstEmbDetail> getLstEmbDetail() {
return lstEmbDetail;
}

public void setLstEmbDetail(List<LstEmbDetail> lstEmbDetail) {
this.lstEmbDetail = lstEmbDetail;
}
    public void setLstEmbDetail(String lstEmbDetail) {

    }

}