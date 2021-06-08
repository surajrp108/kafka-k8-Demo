package com.srp.users.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AddressType {
    HOME(false, 0),
    OFFICE(false, 1),
    WORKSHOP(true, 0),
    WARE_HOUSE(true, 1);

    boolean isSupplierType;
    int index;
    AddressType(boolean isSupplierType, int index){
        this.index = index;
        this.isSupplierType = isSupplierType;
    }

    public boolean isSupplierType() {
        return isSupplierType;
    }

    public boolean isUserType() {
        return !isSupplierType;
    }

    public void setSupplierType(boolean supplierType) {
        isSupplierType = supplierType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    List<AddressType> getSupplierAddressEnums(){
        return Arrays.stream(AddressType.values()).filter(AddressType::isSupplierType).collect(Collectors.toList());
    }

    List<AddressType> getUserAddressEnums(){
        return Arrays.stream(AddressType.values()).filter(AddressType::isUserType).collect(Collectors.toList());
    }
}
