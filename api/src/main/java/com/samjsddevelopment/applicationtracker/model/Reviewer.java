package com.samjsddevelopment.applicationtracker.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviewer")
public class Reviewer {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "reviewers")
    @Builder.Default
    private Set<Application> applications = new HashSet<>();
}
