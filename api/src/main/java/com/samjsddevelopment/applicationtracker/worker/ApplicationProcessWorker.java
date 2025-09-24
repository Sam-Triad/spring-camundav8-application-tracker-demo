package com.samjsddevelopment.applicationtracker.worker;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationProcessWorker {
    
    @JobWorker(type = "request_approvals")
    public void requestApprovals(final ActivatedJob job) {
        log.info("Requesting Approvals for " + job.getBpmnProcessId());
    }
}
