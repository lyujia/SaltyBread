package com.orange.saltybread.domain.ports.views;

import com.orange.saltybread.domain.aggregates.messages.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageView {
    List<Message> searchMessages(UUID roomId, Optional<String> username, Optional<String> keyword, Optional<LocalDate> sendDate, Optional<LocalDateTime> cursor, int limit);
}
