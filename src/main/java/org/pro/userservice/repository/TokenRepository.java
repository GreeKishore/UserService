package org.pro.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.pro.userservice.entity.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeleted(String value, boolean deleted);
}