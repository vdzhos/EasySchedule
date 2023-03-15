package com.easyschedule.services.implementations;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.dtos.UserPutDTO;
import com.easyschedule.exceptions.user.LoginUsedException;
import com.easyschedule.exceptions.user.UserAccessForbiddenException;
import com.easyschedule.exceptions.user.UserNotFoundException;
import com.easyschedule.models.User;
import com.easyschedule.repositories.UserRepository;
import com.easyschedule.services.interfaces.UserService;
import com.easyschedule.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Value("${custom.admin-code}")
    private String adminCode;

    @Value("${custom.user-code}")
    private String userCode;

    @Override
    public User addUser(UserDTO dto) {
        if(userRepo.findById(dto.getLogin()).isPresent())
            throw new LoginUsedException("This login is already in use!");
        Role role = dto.getRoleCode().equals(adminCode) ? Role.ADMIN : Role.REGULAR;
        return userRepo.save(new User(dto.getLogin(), dto.getPassword(), role));
    }
    @Override
    public User getUser(String login) {
        return userRepo.findById(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    @Transactional
    public boolean deleteUser(String login)  {
        getUser(login);
        userRepo.deleteById(login);
        return true;
    }
    @Override
    @Transactional
    public User updateUser(UserPutDTO dto) {
        User user = getUser(dto.getLogin());
        if(!user.getPassword().equals(dto.getPassword()))
            throw new UserAccessForbiddenException("wrong password");
        user.setPassword(dto.getNewPassword());
        return userRepo.save(user);
    }

}
