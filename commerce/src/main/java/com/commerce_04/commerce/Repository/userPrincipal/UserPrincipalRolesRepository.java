package com.commerce_04.commerce.Repository.userPrincipal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrincipalRolesRepository extends JpaRepository<UserPrincipalRoles, Integer> {
    void deleteByUserPrincipal(UserPrincipal userPrincipal);

    Optional<UserPrincipalRoles> findByUserPrincipal(UserPrincipal userPrincipal);
}