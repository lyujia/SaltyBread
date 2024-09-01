package com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom;

import java.util.ArrayList;
import java.util.UUID;

public record CreateRoomCommand(UUID userId, String title, ArrayList<String> friendEmails) {

}
