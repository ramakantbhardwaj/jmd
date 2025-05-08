package com.app.jmd.interfaces;

public interface FilterSpinner {
    void customerFilter(String partyCode, String partyName);
    void supplierFilter(String partyCode, String partyName );
    void marketerFilter(String partyCode, String partyName );
    void sizeFilter(String partyCode, String partyName );
}