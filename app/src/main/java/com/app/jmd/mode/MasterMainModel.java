package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterMainModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lstParty")
@Expose
private List<LstParty> lstParty;

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

public List<LstParty> getLstParty() {
return lstParty;
}

public void setLstParty(List<LstParty> lstParty) {
this.lstParty = lstParty;
}

}