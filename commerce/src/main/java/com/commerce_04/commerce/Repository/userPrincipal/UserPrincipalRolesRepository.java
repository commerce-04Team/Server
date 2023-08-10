package com.commerce_04.commerce.Repository.userPrincipal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrincipalRolesRepository extends JpaRepository<UserPrincipalRoles, Integer> {
    void deleteByUserPrincipal(UserPrincipal userPrincipal);
}