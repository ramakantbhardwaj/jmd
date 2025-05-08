package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingProductionModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lstprod")
@Expose
private List<Lstprod> lstprod;

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

public List<Lstprod> getLstprod() {
return lstprod;
}

public void setLstprod(List<Lstprod> lstprod) {
this.lstprod = lstprod;
}

}