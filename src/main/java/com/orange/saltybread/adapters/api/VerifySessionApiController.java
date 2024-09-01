package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.domain.errors.EmailVerificationFailedException;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationCommand;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationResponse;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationUseCase;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationCommand;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationResponse;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class VerifySessionApiController {

    private final RequestEmailVerificationUseCase requestEmailVerificationUseCase;
    private final ChallengeEmailVerificationUseCase challengeEmailVerificationUseCase;

    @PostMapping("/verifications")
    public ApiResponse<RequestEmailVerificationResponse> requestEmailVerification(
            @RequestBody RequestEmailVerificationCommand command) {
        return ApiResponse.ok(requestEmailVerificationUseCase.requestEmailVerification(command));
    }

    @GetMapping("/verifications")
    public ApiResponse<ChallengeEmailVerificationResponse> challengeEmailVerification(
            @RequestParam(name = "code") UUID code
    ) {
        try {
            return ApiResponse.ok(challengeEmailVerificationUseCase.challengeEmailVerification(new ChallengeEmailVerificationCommand(code)));
        } catch (EmailVerificationFailedException e) {
            return ApiResponse.unauthorized();
        }
    }
}
