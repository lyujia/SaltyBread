package com.orange.saltybread.adapters.service;

import com.orange.saltybread.domain.errors.SignatureGenerationException;
import com.orange.saltybread.domain.errors.SignatureVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class SignatureService {
    private static final String HMAC_ALGORITHM = "HMacSHA256";
    private final Key emailSecret;

    public String generateSignature(String email) throws SignatureGenerationException {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(emailSecret);
            return String.valueOf(
                    Hex.encode(mac.doFinal(email.getBytes())));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SignatureGenerationException();
        }
    }

    public boolean verifySignature(String email, String signature) throws SignatureVerificationException {
        try {
            String generatedSignature = generateSignature(email);
            return generatedSignature.equals(signature);
        } catch (SignatureGenerationException e) {
            throw new SignatureVerificationException();
        }
    }

}
