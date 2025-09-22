package com.samjsddevelopment.applicationtracker.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.samjsddevelopment.applicationtracker.dto.ApplicationDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicationRequest;
import com.samjsddevelopment.applicationtracker.dto.SubmitApplicationResponse;
import com.samjsddevelopment.applicationtracker.dto.UpdateApplicationRequest;
import com.samjsddevelopment.applicationtracker.mapper.ApplicationMapper;
import com.samjsddevelopment.applicationtracker.model.Applicant;
import com.samjsddevelopment.applicationtracker.model.Application;
import com.samjsddevelopment.applicationtracker.repository.ApplicantRepository;
import com.samjsddevelopment.applicationtracker.repository.ApplicationRepository;

import io.camunda.client.CamundaClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplicationMapper applicationMapper;
    private final CamundaClient camundaClient;

    private static final String PROCESS_ID = "Application_Process";

    public ApplicationDto createApplication(CreateApplicationRequest request) {
        Applicant applicant = applicantRepository.findById(request.applicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found with id: " + request.applicantId()));

        Application application = Application.builder()
                .applicant(applicant)
                .build();

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.toDto(savedApplication);
    }

    public ApplicationDto updateApplication(UUID id, UpdateApplicationRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

        application.setInformation(request.information());
        Application updatedApplication = applicationRepository.save(application);
        return applicationMapper.toDto(updatedApplication);
    }

    public SubmitApplicationResponse submitApplication(UUID id) {


        var processInstanceResult = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(java.util.Map.of("applicationId", id.toString()))
                .send()
                .join();

        return new SubmitApplicationResponse(processInstanceResult.getProcessInstanceKey());
    }
}
