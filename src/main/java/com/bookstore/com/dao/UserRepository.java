package com.bookstore.com.dao;


import com.bookstore.com.authentication.User.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users , Long> {

    Optional<Users> findByUsername(String name);

    Optional<Users> findByEmail(String email);


}
