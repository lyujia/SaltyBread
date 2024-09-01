package com.orange.saltybread.domain.ports.usecases.users.registerUser;

import java.util.UUID;

public record RegisterUserResponse(UUID userId, String name) {

}
