package com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName;

import java.util.UUID;

public record UpdateRoomTitleCommand(UUID roomId, String title) {

}
