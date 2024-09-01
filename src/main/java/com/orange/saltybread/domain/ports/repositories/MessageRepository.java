package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.messages.Message;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

}
