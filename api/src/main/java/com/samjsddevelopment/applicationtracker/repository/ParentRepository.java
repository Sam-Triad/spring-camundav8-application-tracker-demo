package com.samjsddevelopment.applicationtracker.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samjsddevelopment.applicationtracker.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, UUID> {
    
}
