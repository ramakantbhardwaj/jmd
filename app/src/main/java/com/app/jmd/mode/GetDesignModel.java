package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDesignModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lstDesign")
@Expose
private List<LstDesign> lstDesign;

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

public List<LstDesign> getLstDesign() {
return lstDesign;
}

public void setLstDesign(List<LstDesign> lstDesign) {
this.lstDesign = lstDesign;
}

}