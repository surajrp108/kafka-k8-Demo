package com.srp.users.service.impl;

import com.srp.users.entities.RegisterEntity;
import com.srp.users.exceptions.GeneralException;
import com.srp.users.repository.UserRegisterRepository;
import com.srp.users.service.UserRegisterService;
import com.srp.users.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Singleton;
import java.time.LocalDate;

@Singleton
public class UserRegisterServiceImpl implements UserRegisterService {

    private final Logger log = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    private final UserRegisterRepository registerRepository;
    private final UserService userService;

    UserRegisterServiceImpl(UserRegisterRepository registerRepository, UserService userService) {
        this.registerRepository = registerRepository;
        this.userService = userService;
    }

    @Override
    public String generateOtpIfEmailNotExist(String email) {
        Assert.isTrue(!StringUtils.isEmpty(email), "Email can not be Null");
        log.info("generateOtpIfEmailNotExist: {}", email);
        try {
            RegisterEntity user = registerRepository.findByUserEmail(email)
                    .orElse(new RegisterEntity(email, null, null));

            if (StringUtils.isEmpty(user.getOtp()) || (user.getUpdatedOn() != null && user.getUpdatedOn().compareTo(LocalDate.now()) > 1200)) {
                user.setOtp(RandomString.make(4));
                user.setUpdatedOn(LocalDate.now());
            }

            RegisterEntity saved = registerRepository.save(user);

            return saved.getOtp();
        } catch (Exception ex) {
            throw GeneralException.getInstance("Invalid email id", ex);
        }
    }

    @Override
    public Long validateOtp(String email, String otp) {

        Assert.isTrue(!StringUtils.isEmpty(email), "Email can not be Null");
        Assert.isTrue(!StringUtils.isEmpty(otp), "OTP must be Provided");
        log.info("validateOtp: email: {}", email);

        RegisterEntity registerEntity = registerRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralException("Invalid inputs email/otp."));
        if (registerEntity.getOtp().equals(otp)) {
            registerRepository.deleteById(registerEntity.getUserEmail());
            return userService.createUserOnSignUp(email);
        }
        throw new GeneralException("Invalid inputs email/otp.");
    }

}
