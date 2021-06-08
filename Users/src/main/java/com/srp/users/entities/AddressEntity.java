package com.srp.users.entities;

import com.srp.users.enums.AddressType;

import javax.persistence.*;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String line1;
    @Column
    private String line2;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private String pincode;
    @Column
    private String mobileNo;
    @Column
    private AddressType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private UserEntity  user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }
}
