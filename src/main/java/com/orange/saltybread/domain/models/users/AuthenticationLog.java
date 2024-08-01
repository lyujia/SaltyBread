package com.orange.saltybread.domain.models.users;

public class AuthenticationLog {
    private String username;
    private String ipAddress;
    public AuthenticationLog(String username, String ipAddress) {
        this.username = username;
        this.ipAddress = ipAddress;
    }
}

