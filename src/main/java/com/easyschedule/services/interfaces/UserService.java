package com.easyschedule.services.interfaces;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.dtos.UserPutDTO;
import com.easyschedule.models.User;

public interface UserService {

    User addUser(UserDTO dto);

    User getUser(String login);

    boolean deleteUser(String login);

    User updateUser(UserPutDTO user);

    User updateUser(User user);

}
