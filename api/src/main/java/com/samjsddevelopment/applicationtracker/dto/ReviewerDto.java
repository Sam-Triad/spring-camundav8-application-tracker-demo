package com.samjsddevelopment.applicationtracker.dto;

import java.util.Set;
import java.util.UUID;
import com.samjsddevelopment.applicationtracker.enums.ReviewerRole;

public record ReviewerDto(
        UUID id,
        String firstName,
        String lastName,
        Set<ReviewerRole> roles) {
}