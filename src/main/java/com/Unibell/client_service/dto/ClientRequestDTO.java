package com.Unibell.client_service.dto;

import com.Unibell.client_service.entity.Email;
import com.Unibell.client_service.entity.PhoneNumber;

import java.util.Set;

public record ClientRequestDTO(String name, Set<String>phoneNumbers, Set<String> emails) {
}
