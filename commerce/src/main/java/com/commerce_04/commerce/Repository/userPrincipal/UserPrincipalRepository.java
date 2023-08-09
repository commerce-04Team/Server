package com.commerce_04.commerce.Repository.userPrincipal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrincipalRepository extends JpaRepository<UserPrincipal, String> {

    @Query("SELECT up FROM UserPrincipal up JOIN FETCH up.userPrincipalRoles upr JOIN FETCH upr.roles WHERE up.userPrincipalId = :id ")
    Optional<UserPrincipal> findByEmailFetchJoin(String id);

    boolean existsByEmail(String email);
}