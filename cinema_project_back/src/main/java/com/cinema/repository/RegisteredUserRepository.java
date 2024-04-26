package com.cinema.repository;

import com.cinema.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    Optional<RegisteredUser> findRegisteredUserByLogin(String username);

    Optional<RegisteredUser> findRegisteredUserByMail(String mail);

    Optional<RegisteredUser> findById(Long id);
}
