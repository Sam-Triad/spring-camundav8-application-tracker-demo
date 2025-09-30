package com.samjsddevelopment.applicationtracker.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.samjsddevelopment.applicationtracker.dto.ApplicationDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicationRequest;
import com.samjsddevelopment.applicationtracker.dto.UpdateApplicationRequest;
import com.samjsddevelopment.applicationtracker.exception.CamundaStateException;
import com.samjsddevelopment.applicationtracker.exception.NotFoundException;
import com.samjsddevelopment.applicationtracker.mapper.ApplicationMapper;
import com.samjsddevelopment.applicationtracker.model.Application;
import com.samjsddevelopment.applicationtracker.repository.ApplicantRepository;
import com.samjsddevelopment.applicationtracker.repository.ApplicationRepository;

import io.camunda.zeebe.client.ZeebeClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ApplicationService {

        private final ApplicationRepository applicationRepository;
        private final ApplicantRepository applicantRepository;
        private final ApplicationMapper applicationMapper;
        private final ZeebeClient camundaClient;

        private static final String PROCESS_ID = "Application_Process";

        @Transactional
        public ApplicationDto createApplication(CreateApplicationRequest request) {
                var applicant = applicantRepository.findById(request.applicantId())
                                .orElseThrow(
                                                () -> new EntityNotFoundException("Applicant not found with id: "
                                                                + request.applicantId()));

                var application = Application.builder()
                                .applicant(applicant)
                                .build();
                var savedApplication = applicationRepository.save(application);

                var processInstanceResult = camundaClient.newCreateInstanceCommand()
                                .bpmnProcessId(PROCESS_ID)
                                .latestVersion()
                                .variables(java.util.Map.of("applicationId", savedApplication.getId().toString()))
                                .send()
                                .join();
                application.setProcessInstanceKey(processInstanceResult.getProcessInstanceKey());
                applicationRepository.save(application);

                return applicationMapper.toDto(savedApplication);
        }

        public ApplicationDto updateApplication(UUID id, UpdateApplicationRequest request) {
                var application = applicationRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

                application.setInformation(request.information());
                var updatedApplication = applicationRepository.save(application);
                return applicationMapper.toDto(updatedApplication);
        }

        public void submitApplication(UUID id, String userId, String userEmail) {
                var application = applicationRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Application not found with id: " + id));

                // Find user task and get key
                var results = camundaClient.newUserTaskQuery()
                                .filter(f -> f.processInstanceKey(application.getProcessInstanceKey()))
                                .send()
                                .join();
                var items = results.items();
                if (items == null || items.isEmpty()) {
                        throw new CamundaStateException(
                                        "No user task found for process instance: "
                                                        + application.getProcessInstanceKey());
                }
                var userTaskKey = items.getFirst().getKey();

                // Assign and complete user task
                camundaClient.newUserTaskAssignCommand(userTaskKey)
                                .assignee(userId)
                                .send()
                                .join(); // TODO: Set user id from rest request

                Map<String, Object> variables = new HashMap<>();
                variables.put("completedBy", userId);
                variables.put("completedByEmail", userEmail);
                variables.put("completedAt", Instant.now().toString());

                camundaClient.newUserTaskCompleteCommand(userTaskKey)
                                .variables(variables)
                                .send()
                                .join();

        }

        public Page<ApplicationDto> listApplications(Pageable pageable) {
                var page = applicationRepository.findAll(pageable);
                var dtos = page.map(applicationMapper::toDto);
                return dtos;
        }

}
