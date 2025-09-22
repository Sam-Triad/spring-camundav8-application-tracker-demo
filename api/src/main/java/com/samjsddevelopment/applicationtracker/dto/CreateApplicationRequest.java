package com.samjsddevelopment.applicationtracker.dto;

import java.util.UUID;

public record CreateApplicationRequest(
        UUID applicantId) {
}