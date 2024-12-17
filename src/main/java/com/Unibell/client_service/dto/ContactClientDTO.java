package com.Unibell.client_service.dto;

import java.util.Set;

public record ContactClientDTO(Set<String> phoneNumbers, Set<String> emails) {
}
