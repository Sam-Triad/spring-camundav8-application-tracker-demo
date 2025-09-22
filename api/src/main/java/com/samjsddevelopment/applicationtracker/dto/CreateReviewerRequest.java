package com.samjsddevelopment.applicationtracker.dto;

import java.util.Set;
import com.samjsddevelopment.applicationtracker.enums.ReviewerRole;

public record CreateReviewerRequest(
        String firstName,
        String lastName,
        Set<ReviewerRole> roles) {
}
