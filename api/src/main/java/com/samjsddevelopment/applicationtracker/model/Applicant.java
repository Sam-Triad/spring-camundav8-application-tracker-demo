package com.samjsddevelopment.applicationtracker.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "applicants")
public class Applicant {
    
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Application> applications = new HashSet<>();

}
