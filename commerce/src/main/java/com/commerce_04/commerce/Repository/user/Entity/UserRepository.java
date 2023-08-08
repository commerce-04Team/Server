package com.commerce_04.commerce.Repository.user.Entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUserName(String name);
}