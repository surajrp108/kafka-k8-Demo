package com.srp.users.service;

public interface UserRegisterService {
    String generateOtpIfEmailNotExist(String email);

    Long validateOtp(String email, String otp);

}
