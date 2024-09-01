package com.orange.saltybread.domain.ports.usecases.users.updateUser;

import java.util.UUID;

public record UpdateUserInfoResponse(UUID userId, String name) {

}
