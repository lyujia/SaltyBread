package com.orange.saltybread.domain.ports.usecases.users.updatePassword;

import java.util.UUID;

public record UpdatePasswordCommand(UUID userId, String password, String newPassword) {

}
