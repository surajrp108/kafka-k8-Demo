package com.srp.users.pojos;

import java.util.ArrayList;
import java.util.List;

public class UserDetails {
    private Long id;
    private String name;
    private String email;
    private List<AddressDetail> address = new ArrayList<>();
    private String mobileNumber;
    private boolean isAlsoSupplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressDetail> getAddress() {
        return address;
    }

    public void setAddress(List<AddressDetail> address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean getIsAlsoSupplier() {
        return isAlsoSupplier;
    }

    public void setIsAlsoSupplier(boolean alsoSupplier) {
        isAlsoSupplier = alsoSupplier;
    }
}
