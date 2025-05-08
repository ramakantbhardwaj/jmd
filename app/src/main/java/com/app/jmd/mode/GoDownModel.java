package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoDownModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lstGodown")
@Expose
private List<LstGodown> lstGodown;

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

public List<LstGodown> getLstGodown() {
return lstGodown;
}

public void setLstGodown(List<LstGodown> lstGodown) {
this.lstGodown = lstGodown;
}

}