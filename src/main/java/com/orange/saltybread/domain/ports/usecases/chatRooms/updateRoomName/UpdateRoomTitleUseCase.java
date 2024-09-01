package com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName;

import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;

public interface UpdateRoomTitleUseCase {

    public UpdateRoomTitleResponse updateRoomTitle(UpdateRoomTitleCommand command) throws ChatRoomNotFoundException;
}
