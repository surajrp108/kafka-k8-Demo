package com.srp.users.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
public class RegisterEntity {
    @Id
    @Column
    @Email
    String userEmail;

    @Column
    @Max(4)
    @Min(4)
    String otp;

    @Column
    private LocalDate updatedOn;

    public RegisterEntity() {
    }

    public RegisterEntity(String userEmail, String otp, LocalDate time) {
        this.userEmail = userEmail;
        this.otp = otp;
        this.updatedOn = time;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
