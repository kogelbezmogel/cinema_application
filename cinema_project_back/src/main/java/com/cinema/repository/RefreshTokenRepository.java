package com.cinema.repository;

import com.cinema.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public RefreshToken findRefreshTokenByToken(String token);

    public void deleteByToken(String token);
}
