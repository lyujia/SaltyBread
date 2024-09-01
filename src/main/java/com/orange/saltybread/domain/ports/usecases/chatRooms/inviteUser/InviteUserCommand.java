package com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser;

import java.util.List;
import java.util.UUID;

public record InviteUserCommand(UUID roomId, List<String> friendEmails) {

}
