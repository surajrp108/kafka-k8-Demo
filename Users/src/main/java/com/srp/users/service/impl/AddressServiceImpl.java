package com.srp.users.service.impl;

import com.srp.users.entities.AddressEntity;
import com.srp.users.entities.UserEntity;
import com.srp.users.exceptions.GeneralException;
import com.srp.users.pojos.AddressDetail;
import com.srp.users.repository.UserRepository;
import com.srp.users.service.AddressService;
import com.srp.users.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);


    AddressServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<AddressDetail> getAddress(Long userId) {
        Assert.isTrue(userId != null, "Invalid user id");
        log.debug("getAddress: userId: " + userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GeneralException("User details not found"));
        return user.getAddresses().stream().map(UserMapper::getAddressDetails).collect(Collectors.toList());
    }

    @Override
    public Long addAddress(Long userId, AddressDetail address) {
        Assert.isTrue(userId != null, "Invalid user id");
        Assert.isTrue(address != null, "Invalid address details");

        log.debug("addAddress: userId: " + userId);
        log.debug("addAddress: address: " + address);

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GeneralException("User details not found"));
        AddressEntity addressEntity = new AddressEntity();
        UserMapper.mapAddressDetailsToEntity(address, addressEntity);
        user.getAddresses().add(addressEntity);
        userRepository.save(user);

        return addressEntity.getId();
    }

    @Override
    public boolean deleteAddress(Long id) {
        return false;
    }
}
