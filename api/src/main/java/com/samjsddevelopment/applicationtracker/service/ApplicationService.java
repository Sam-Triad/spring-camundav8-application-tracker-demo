package com.samjsddevelopment.applicationtracker.service;

import java.util.UUID;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import com.samjsddevelopment.applicationtracker.dto.ApplicationDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicationRequest;
import com.samjsddevelopment.applicationtracker.dto.UpdateApplicationRequest;
import com.samjsddevelopment.applicationtracker.mapper.ApplicationMapper;
import com.samjsddevelopment.applicationtracker.model.Application;
import com.samjsddevelopment.applicationtracker.repository.ApplicantRepository;
import com.samjsddevelopment.applicationtracker.repository.ApplicationRepository;

import io.camunda.client.CamundaClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplicationMapper applicationMapper;
    private final CamundaClient camundaClient;

    private static final String PROCESS_ID = "Application_Process";

    @Transactional
    public ApplicationDto createApplication(CreateApplicationRequest request) {
        var applicant = applicantRepository.findById(request.applicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found with id: " + request.applicantId()));

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

    @SneakyThrows
    public void submitApplication(UUID id) {
        var application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));
//        var results = camundaClient.newUserTaskSearchRequest().filter((f) -> f.processInstanceKey(application.getProcessInstanceKey())).send().join();
//        var results2 = camundaClient.newUserTaskSearchRequest().filter((f) -> f.bpmnProcessId(String.valueOf(application.getProcessInstanceKey()))).send().join();
        var results3 = camundaClient.newUserTaskSearchRequest().send().join();

        var test = results3.singleItem();
    }
    
}
