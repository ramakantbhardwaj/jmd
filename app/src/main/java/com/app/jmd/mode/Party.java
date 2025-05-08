package com.app.jmd.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Party {
    @SerializedName("PartyCode")
@Expose
private String partyCode;
@SerializedName("PartyName")
@Expose
private String partyName;

@SerializedName("MobilePhone")
@Expose
private String MobilePhone;

public String getPartyCode() {
return partyCode;
}

public void setPartyCode(String partyCode) {
this.partyCode = partyCode;
}

public String getPartyName() {
return partyName;
}

public void setPartyName(String partyName) {
this.partyName = partyName;
}
    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

}