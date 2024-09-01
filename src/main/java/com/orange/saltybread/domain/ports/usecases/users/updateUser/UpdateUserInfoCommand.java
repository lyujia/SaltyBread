package com.orange.saltybread.domain.ports.usecases.users.updateUser;

import java.util.UUID;

public record UpdateUserInfoCommand(UUID userId, String name) {

}
