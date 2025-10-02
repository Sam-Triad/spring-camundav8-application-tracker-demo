package com.samjsddevelopment.applicationtracker.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.samjsddevelopment.applicationtracker.dto.UserTaskDto;
import com.samjsddevelopment.applicationtracker.exception.CamundaStateException;
import com.samjsddevelopment.applicationtracker.exception.NotFoundException;
import com.samjsddevelopment.applicationtracker.mapper.UserTaskMapper;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.search.response.UserTask;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ZeebeClient camundaClient;
    private final UserTaskMapper userTaskMapper;

    public List<UserTaskDto> getAvailableTasks(List<String> userGroups) {
        // SDK does not support filtering by multiple user groups so make multiple
        // requests in a loop
        List<UserTask> items = new ArrayList<>();
        userGroups.forEach(group -> {
            var results = camundaClient.newUserTaskQuery()
                    .filter(f -> f.candidateGroup(group).state("CREATED").assignee(null))
                    .send()
                    .join();
            items.addAll(results.items());
        });

        return userTaskMapper.toDtos(items);
    }

    public List<UserTaskDto> getCurrentUsersTasksInReview(String userId) {
        var results = camundaClient.newUserTaskQuery()
                .filter(f -> f.assignee(userId).state("CREATED"))
                .send()
                .join();
        return userTaskMapper.toDtos(results.items());
    }

    public void makeApplicationDecision(String userId, long userTaskId, boolean approved) {
        var userTaskList = camundaClient.newUserTaskQuery()
                .filter(f -> f.assignee(userId).key(userTaskId).state("CREATED"))
                .send()
                .join().items();

        if (userTaskList.isEmpty() || userTaskList == null) {
            throw new NotFoundException("A user task assigned to the current user was not found");
        }
        if (userTaskList.size() > 1) {
            throw new CamundaStateException("More than one user task was found");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
        camundaClient.newUserTaskCompleteCommand(userTaskId).variables(variables).send().join();
    }

    public void claimTask(String userId, long userTaskId) {
        var userTaskList = camundaClient.newUserTaskQuery()
                .filter(f -> f.key(userTaskId))
                .send()
                .join().items();

        if (userTaskList.isEmpty() || userTaskList == null) {
            throw new NotFoundException("A user task assigned to the current user was not found");
        }
        if (userTaskList.size() > 1) {
            throw new CamundaStateException("More than one user task was found");
        }

        camundaClient.newUserTaskAssignCommand(userTaskId).assignee(userId).send().join();
    }
}
