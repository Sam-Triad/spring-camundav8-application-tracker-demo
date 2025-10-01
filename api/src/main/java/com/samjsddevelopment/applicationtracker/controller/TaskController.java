package com.samjsddevelopment.applicationtracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.dto.ApprovalDecisionRequest;
import com.samjsddevelopment.applicationtracker.dto.UserTaskDto;
import com.samjsddevelopment.applicationtracker.service.TaskService;

import io.camunda.zeebe.client.api.search.response.UserTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/in-review")
    @Operation(summary = "List tasks assigned to current user")
    public ResponseEntity<List<UserTaskDto>> getTasksInReview(
            @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getClaimAsString("preferred_username");

        return ResponseEntity.ok(taskService.getCurrentUsersTasksInReview(userId));
    }

    @GetMapping("/available")
    @Operation(summary = "List unassigned tasks")
    public ResponseEntity<List<UserTaskDto>> getAvailableTasks(
            @AuthenticationPrincipal Jwt jwt) {
        var userGroups = jwt.getClaimAsStringList("groups");
        if (userGroups == null) {
            userGroups = Collections.emptyList();
        }

        return ResponseEntity.ok(taskService.getAvailableTasks(userGroups));
    }

    @PostMapping("/decide-approval")
    public ResponseEntity<Void> decideApproval(@AuthenticationPrincipal Jwt jwt, @RequestBody ApprovalDecisionRequest approvalDecisionRequest) {
        var userId = jwt.getClaimAsString("preferred_username");
        taskService.makeApplicationDecision(userId, approvalDecisionRequest.userTaskId(), approvalDecisionRequest.approved());
        return ResponseEntity.ok().build();
    }
    

}
