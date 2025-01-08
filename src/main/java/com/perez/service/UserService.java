package com.perez.service;

import com.perez.Exception.UserException;
import com.perez.model.User;


public interface UserService {
    public User findUserProfileByJwt (String jwt) throws UserException;

    public User findUserByEmail(String email) throws UserException;


}
