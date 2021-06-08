package com.srp.users.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    @Email
    private String email;

    @Column
    private String name;

    @Column
    private String mobileNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserAuthEntity auth;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses = new ArrayList<>();

    @Column
    private boolean isAlsoSupplier = false;

    public UserEntity() {
    }

    public UserEntity(String email) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserAuthEntity getAuth() {
        return auth;
    }

    public void setAuth(UserAuthEntity auth) {
        this.auth = auth;
    }

    public boolean isAlsoSupplier() {
        return isAlsoSupplier;
    }

    public void setAlsoSupplier(boolean alsoSupplier) {
        isAlsoSupplier = alsoSupplier;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }
}
