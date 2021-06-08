package com.srp.users.service;

import com.srp.users.pojos.UserDetails;

public interface UserService  {

    UserDetails getUserDetails(Long id);

    boolean setAsSupplier(Long id, boolean isSupplier);

    Long createUserOnSignUp(String email);

    UserDetails updateUserDetails(UserDetails userDetails);

}
