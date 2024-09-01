package com.orange.saltybread.domain.ports.usecases.users.registerUser;

public record RegisterUserCommand(String email, String name, String password, String signature) {

}
