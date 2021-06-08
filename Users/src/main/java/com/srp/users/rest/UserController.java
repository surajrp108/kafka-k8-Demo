package com.srp.users.rest;


import com.srp.users.pojos.AddressDetail;
import com.srp.users.pojos.UserDetails;
import com.srp.users.service.AddressService;
import com.srp.users.service.UserRegisterService;
import com.srp.users.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/user")
public class UserController {

    private final AddressService addressService;
    private final UserService userService;
    private final UserRegisterService registerService;

    public UserController(UserService service, UserRegisterService registerService, AddressService addressService) {
        this.userService = service;
        this.registerService = registerService;
        this.addressService = addressService;
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String userSignUp(String email) {
        return registerService.generateOtpIfEmailNotExist(email);
    }

    @POST
    @Path("signup/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Long validateSignUp(Map<String, String> userData) {
        return registerService.validateOtp(userData.get("email"), userData.get("otp"));
    }

    @GET
    @Path("/{id}")
    public UserDetails getUserDetails(@PathParam("id") Long id) {
        return userService.getUserDetails(id);
    }

    @PUT
    public UserDetails updateUserDetails(UserDetails userDetails) {
        return userService.updateUserDetails(userDetails);
    }

    @PUT
    @Path("/{id}/supplier/{value}")
    public boolean updateAsSupplier(@PathParam("id") Long id, @PathParam("value") boolean value) {
        return userService.setAsSupplier(id, value);
    }

    @POST
    @Path("/{id}/address")
    public Long addAddress(@PathParam("id") Long userId, AddressDetail addressDetail){
        return addressService.addAddress(userId, addressDetail);
    }

    @GET
    @Path("/{id}/address")
    public List<AddressDetail> getAddress(@PathParam("id") Long userId){
        return addressService.getAddress(userId);
    }

//    @DELETE
//    @Path("/address/{id}")
//    public boolean deleteAddress(@PathParam("id") Long addressId){
//        return addressService.deleteAddress(addressId);
//    }
}
