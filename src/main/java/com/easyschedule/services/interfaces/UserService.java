package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.dtos.UserPutDTO;
import com.easyschedule.exceptions.user.LoginUsedException;
import com.easyschedule.exceptions.user.UserAccessForbiddenException;
import com.easyschedule.exceptions.user.UserNotFoundException;
import com.easyschedule.models.User;

public interface UserService {

    User addUser(UserDTO dto) throws LoginUsedException;

    User getUser(String login) throws UserNotFoundException;

    boolean deleteUser(String login) throws UserNotFoundException;

    User updateUser(UserPutDTO user) throws UserNotFoundException, UserAccessForbiddenException;

}
