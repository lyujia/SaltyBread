package com.orange.saltybread.domain.ports.usecases.users.login;

public record LoginCommand(String email, String password, String ipAddress, String userAgent) {

}
