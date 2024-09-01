package com.orange.saltybread.adapters.dto;

import java.util.ArrayList;

public record CreateRoomRequest(String title, ArrayList<String> friendEmails) {
}
