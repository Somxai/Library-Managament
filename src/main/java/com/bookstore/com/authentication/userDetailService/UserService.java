package com.bookstore.com.authentication.userDetailService;

import com.bookstore.com.authentication.User.*;
import com.bookstore.com.authentication.jwt.JwtService;
import com.bookstore.com.dao.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    JwtService jwtService;

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    UserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Transactional
    public UserResponse findByName(String name) {
        Optional<Users> users = userRepository.findByUsername(name);

        if (users.isEmpty()) {
            throw new NullPointerException("User not found");

        }
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(users.get().getUsername());
        userResponse.setEmail(users.get().getEmail());

        return userResponse;
    }

    @Transactional
    public LoginResponse login(LoginReq loginReq) {
        if (loginReq == null) {
            throw new NullPointerException("login object is null");
        }

        try {
            logger.info("email from service : " + loginReq.getEmail());
            logger.info("password from service :" + loginReq.getPassword());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginReq.getEmail(), loginReq.getPassword()
            ));



            logger.info("User before query");
            var users = userRepository.findByEmail(loginReq.getEmail());
            var token = jwtService.generateToken(users.get());
            logger.info("User :" + users.get());

            return LoginResponse.builder().token(token).build();
        } catch (BadCredentialsException e) {
            logger.info(e.getMessage());
            throw new BadCredentialsException("bad credential:" + e.getMessage());
        }
    }



    @Transactional
    public RegisterResponse register(RegisterReq registerReq){
    if (registerReq == null){
        throw new NullPointerException("object register request is null");
    }

        logger.info("userReqObject: " + registerReq.getUsername());
        Users users = new Users();
        users.setUsername(registerReq.getUsername());
        users.setEmail(registerReq.getEmail());
        users.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        users.setRole(registerReq.getRole());

        logger.info("Users after Map: " + users);
        userRepository.save(users);

        var token = jwtService.generateToken(users);
        return new RegisterResponse(token);
    }


}
