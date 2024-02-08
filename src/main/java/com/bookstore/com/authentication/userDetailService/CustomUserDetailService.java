package com.bookstore.com.authentication.userDetailService;

import com.bookstore.com.dao.UserRepository;
import com.bookstore.com.authentication.User.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;



    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByEmail(username);


        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("user from customUserDetail: " + user.get());
        return user.get();
    }
}
