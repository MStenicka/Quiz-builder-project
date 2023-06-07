package com.ateam.backend.repositories;

import com.ateam.backend.models.appUser.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long>{

    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUsernameOrEmail(String username, String email);

    Optional<AppUser> findByResetCode(String resetCode);
}


