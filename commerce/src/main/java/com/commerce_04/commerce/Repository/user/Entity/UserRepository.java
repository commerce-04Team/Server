package com.commerce_04.commerce.Repository.user.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserName(String name);

    boolean existsUserById(String user_id);

    boolean existsUserByNickName(String nickName);
}