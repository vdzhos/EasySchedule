package com.easyschedule.services.implementations;

import com.easyschedule.models.User;
import com.easyschedule.repositories.UserRepository;
import com.easyschedule.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<User> opt = userRepo.findById(login);
		if(opt.isEmpty())
			throw new UsernameNotFoundException("No such user in database");
		return new CustomUserDetails(opt.get());
	}

}
