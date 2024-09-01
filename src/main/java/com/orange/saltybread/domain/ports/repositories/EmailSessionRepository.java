package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.verifySessions.EmailSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailSessionRepository extends JpaRepository<EmailSession, UUID> {
    Optional<EmailSession> findByEmail(String email);
}
