package com.samjsddevelopment.applicationtracker.dto;

import java.util.UUID;

public record ApplicationDto(
        UUID id,
        UUID applicantId,
        String information) {
}