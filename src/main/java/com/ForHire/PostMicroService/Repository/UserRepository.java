package com.ForHire.PostMicroService.Repository;


import com.ForHire.PostMicroService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

}