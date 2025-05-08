package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DesignDetailsMainModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lstCostDetail")
@Expose
private List<LstCostDetail> lstCostDetail;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<LstCostDetail> getLstCostDetail() {
return lstCostDetail;
}

public void setLstCostDetail(List<LstCostDetail> lstCostDetail) {
this.lstCostDetail = lstCostDetail;
}

}