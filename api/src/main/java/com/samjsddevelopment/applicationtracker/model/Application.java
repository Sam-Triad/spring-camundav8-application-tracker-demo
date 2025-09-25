package com.samjsddevelopment.applicationtracker.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.samjsddevelopment.applicationtracker.enums.ApplicationStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private long processInstanceKey;

    @ManyToOne(fetch = FetchType.LAZY)
    private Applicant applicant;

    @ManyToMany
    @JoinTable(
        name = "application_reviewers",
        joinColumns = @JoinColumn(name = "application_id"),
        inverseJoinColumns = @JoinColumn(name = "reviewer_id")
    )
    @Builder.Default
    private Set<Reviewer> reviewers = new HashSet<>();

    @Lob
    private String information;

    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum applicationStatus;

}
