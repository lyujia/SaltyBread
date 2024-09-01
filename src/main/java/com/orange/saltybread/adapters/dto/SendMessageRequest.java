package com.orange.saltybread.adapters.dto;

import java.util.UUID;

public record SendMessageRequest(String message, UUID roomId) {
}
