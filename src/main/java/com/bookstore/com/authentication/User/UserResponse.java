package com.bookstore.com.authentication.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserResponse implements Serializable {

    public String userName;
    public String email;

}
