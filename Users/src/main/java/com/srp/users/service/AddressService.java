package com.srp.users.service;

import com.srp.users.pojos.AddressDetail;

import java.util.List;

public interface AddressService {
    List<AddressDetail> getAddress(Long userId);
    Long addAddress(Long userId, AddressDetail address);
    boolean deleteAddress(Long id);
}
