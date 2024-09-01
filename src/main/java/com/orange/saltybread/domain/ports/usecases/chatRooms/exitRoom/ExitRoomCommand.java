package com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom;

import java.util.UUID;

public record ExitRoomCommand(UUID userId, UUID roomId) {

}
