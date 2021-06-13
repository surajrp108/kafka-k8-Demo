package com.srp.users.service.impl;

import com.google.gson.Gson;
import com.srp.users.entities.UserAuthEntity;
import com.srp.users.entities.UserEntity;
import com.srp.users.exceptions.GeneralException;
import com.srp.users.pojos.UserDetails;
import com.srp.users.repository.UserRepository;
import com.srp.users.service.UserService;
import com.srp.users.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Singleton;

@Singleton
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Gson gson = new Gson();

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails getUserDetails(Long id) {
        log.info("getUserDetails: {}", id);
        UserEntity user = this.getUserEntity(id);
        return UserMapper.getUserSimpleDetails(user);
    }

    private UserEntity getUserEntity(Long id) {
        log.info("getUserEntity: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralException("User Not found"));
    }

    @Override
    public boolean setAsSupplier(Long id, boolean isSupplier) {
        log.info("setAsSupplier: id={} and isSupplier={}", id, isSupplier);
        UserEntity user = this.getUserEntity(id);
        user.setAlsoSupplier(isSupplier);
        userRepository.save(user);
        return true;
    }

    @Override
    public Long createUserOnSignUp(String email) {
        log.info("createUserOnSignUp: email={}", email);
        UserEntity user = new UserEntity(email);
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUser(user);
        userAuthEntity.setPassword("ooool");
        user.setAuth(userAuthEntity);

        UserEntity saved = this.userRepository.save(user);
        return saved.getId();
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails) {
        Assert.isTrue(userDetails!= null, "Invalid inputs");
        Assert.isTrue(userDetails.getId() != null, "Invalid user details");

        log.info("updateUserDetails: {}", gson.toJson(userDetails));
        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new GeneralException("User details not found"));
        UserMapper.mapBaseUserDetailsToEntity(userDetails, user);
        UserEntity saved = userRepository.save(user);

        return UserMapper.getUserSimpleDetails(saved);
    }
}
