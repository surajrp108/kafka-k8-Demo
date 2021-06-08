package com.srp.users.utils;

import com.srp.users.entities.AddressEntity;
import com.srp.users.entities.UserEntity;
import com.srp.users.pojos.AddressDetail;
import com.srp.users.pojos.UserDetails;

public class UserMapper {

    public static UserDetails getUserSimpleDetails(UserEntity source){
        UserDetails user = new UserDetails();
        user.setId(source.getId());
        user.setEmail(source.getEmail());
        user.setName(source.getName());
        user.setMobileNumber(source.getMobileNumber());
        user.setIsAlsoSupplier(source.isAlsoSupplier());

        return user;
    }

    public static void mapBaseUserDetailsToEntity(UserDetails source, UserEntity entity) {
        entity.setAlsoSupplier(source.getIsAlsoSupplier());
        entity.setEmail(source.getEmail());
        entity.setName(source.getName());
        entity.setMobileNumber(source.getMobileNumber());
    }

    public static void mapAddressDetailsToEntity(AddressDetail source, AddressEntity addressEntity) {
        addressEntity.setLine1(source.getLine1());
        addressEntity.setLine2(source.getLine2());
        addressEntity.setCountry(source.getCountry());
        addressEntity.setPincode(source.getPincode());
        addressEntity.setMobileNo(source.getMobileNo());
        addressEntity.setState(source.getState());
        addressEntity.setType(source.getType());
    }
    public static AddressDetail getAddressDetails(AddressEntity source) {
        AddressDetail destination = new AddressDetail();
        destination.setLine1(source.getLine1());
        destination.setLine2(source.getLine2());
        destination.setCountry(source.getCountry());
        destination.setPincode(source.getPincode());
        destination.setMobileNo(source.getMobileNo());
        destination.setState(source.getState());
        destination.setType(source.getType());
        return destination;
    }
}
