package com.samjsddevelopment.applicationtracker.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.samjsddevelopment.applicationtracker.model.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
}