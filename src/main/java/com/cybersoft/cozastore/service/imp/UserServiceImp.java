package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.payload.request.SignupRequest;

public interface UserServiceImp {
    boolean addUser(SignupRequest request);

}
