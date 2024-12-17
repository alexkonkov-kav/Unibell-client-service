package com.Unibell.client_service.dto;

import java.util.Set;

public record ClientRequestDTO(String name, Set<String>phoneNumbers, Set<String> emails) {
}
