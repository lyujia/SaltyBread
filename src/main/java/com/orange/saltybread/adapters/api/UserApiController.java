package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.adapters.dto.LoginRequest;
import com.orange.saltybread.adapters.dto.UpdatePasswordRequest;
import com.orange.saltybread.adapters.dto.UpdateUserInfoRequest;
import com.orange.saltybread.domain.errors.*;
import com.orange.saltybread.domain.ports.usecases.users.deleteUser.DeregisterUserResponse;
import com.orange.saltybread.domain.ports.usecases.users.deleteUser.DeregisterUserUseCase;
import com.orange.saltybread.domain.ports.usecases.users.login.LoginCommand;
import com.orange.saltybread.domain.ports.usecases.users.login.LoginResponse;
import com.orange.saltybread.domain.ports.usecases.users.login.LoginUseCase;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserCommand;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserResponse;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserUseCase;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordCommand;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordResponse;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordUseCase;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoCommand;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoResponse;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final RegisterUserUseCase registerUserUseCase;
    private final DeregisterUserUseCase deregisterUserUseCase;
    private final LoginUseCase loginUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UpdateUserInfoUseCase updateUserInfoUseCase;


    @PostMapping
    public ApiResponse<RegisterUserResponse> registerUser(@RequestBody RegisterUserCommand command) {
        try {
            return ApiResponse.ok(registerUserUseCase.registerUser(command));
        } catch (EmailAlreadyExistsException e) {
            return ApiResponse.conflict();
        } catch (InvalidEmailFormatException e) {
            return ApiResponse.badRequest();
        } catch (SignatureVerificationException e) {
            return ApiResponse.unauthorized();
        }
    }

    @DeleteMapping
    public ApiResponse<DeregisterUserResponse> deregisterUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        try {
            return ApiResponse.ok(deregisterUserUseCase.deregisterUser(userId));
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(HttpServletRequest servletRequest,
                                            @RequestHeader("x-forwarded-for") Optional<String> forwardedIpAddress,
                                            @RequestHeader("user-agent") String userAgent, @RequestBody LoginRequest request) {
        try {
            String ipAddress =
                    forwardedIpAddress.orElseGet(servletRequest::getRemoteAddr);
            LoginCommand command = new LoginCommand(request.email(), request.password(), ipAddress,
                    userAgent);
            return ApiResponse.ok(new LoginResponse(loginUseCase.login(command)));
        } catch (LoginFailException e) {
            return ApiResponse.unauthorized();
        }
    }

    @PutMapping("/password")
    public ApiResponse<UpdatePasswordResponse> updatePassword(
            @RequestBody UpdatePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        try {
            return ApiResponse.ok(updatePasswordUseCase.updatePassword(new UpdatePasswordCommand(userId,
                    request.oldPassword(), request.newPassword())));
        } catch (PasswordNotMatchException e) {
            return ApiResponse.unauthorized();
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    @PutMapping("/info")
    public ApiResponse<UpdateUserInfoResponse> updateUserInfo(
            @RequestBody UpdateUserInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        try {
            return ApiResponse.ok(
                    updateUserInfoUseCase.updateUserInfo(new UpdateUserInfoCommand(userId, request.name())));
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    //패스워드 분실했을경우
    //이메일 변경*
    //캐시사용
    //rabbitmq?
}
