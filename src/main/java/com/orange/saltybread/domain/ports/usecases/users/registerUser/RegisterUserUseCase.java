package com.orange.saltybread.domain.ports.usecases.users.registerUser;

import com.orange.saltybread.domain.errors.EmailAlreadyExistsException;
import com.orange.saltybread.domain.errors.InvalidEmailFormatException;
import com.orange.saltybread.domain.errors.SignatureVerificationException;

public interface RegisterUserUseCase {

    public RegisterUserResponse registerUser(RegisterUserCommand command)
            throws EmailAlreadyExistsException, InvalidEmailFormatException, SignatureVerificationException;
}
