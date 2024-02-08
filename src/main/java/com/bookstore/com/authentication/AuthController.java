package com.bookstore.com.authentication;

import com.bookstore.com.authentication.User.LoginReq;
import com.bookstore.com.authentication.User.LoginResponse;
import com.bookstore.com.authentication.User.RegisterReq;
import com.bookstore.com.authentication.User.RegisterResponse;
import com.bookstore.com.authentication.userDetailService.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/authentication")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public RegisterResponse register(@Valid @RequestBody RegisterReq registerReq) {
        if (registerReq == null) {
            throw new NullPointerException("signUp form is null");
        }
        logger.info("userRegister Req: " + registerReq.getUsername());
        return userService.register(registerReq);

    }

    @PostMapping(path = "/login")
    public LoginResponse login(@Valid @RequestBody LoginReq loginReq){
        if (loginReq == null){
            throw new NullPointerException("signUp form is null");
        }
        logger.info("email: "  + loginReq.getEmail());
        logger.info("password: "  + loginReq.getPassword());
        return userService.login(loginReq);

    }



}
