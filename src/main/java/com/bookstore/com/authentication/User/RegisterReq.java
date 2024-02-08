package com.bookstore.com.authentication.User;

import lombok.Data;

@Data
public class RegisterReq {

    private String username;

    private String password;

    private String email;

    private String role;

}
