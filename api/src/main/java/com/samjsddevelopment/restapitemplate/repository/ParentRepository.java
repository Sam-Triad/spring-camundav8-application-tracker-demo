package com.samjsddevelopment.restapitemplate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samjsddevelopment.restapitemplate.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, UUID> {
    
}
