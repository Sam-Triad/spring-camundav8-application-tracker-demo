package com.samjsddevelopment.applicationtracker.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.samjsddevelopment.applicationtracker.enums.ReviewerRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ElementCollection(targetClass = ReviewerRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "reviewer_roles", joinColumns = @JoinColumn(name = "reviewer_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<ReviewerRole> roles = new HashSet<>();

    @ManyToMany(mappedBy = "reviewers")
    @Builder.Default
    private Set<Application> applications = new HashSet<>();
}
