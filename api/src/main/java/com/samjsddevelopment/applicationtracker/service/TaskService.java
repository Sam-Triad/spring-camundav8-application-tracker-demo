package com.samjsddevelopment.applicationtracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.samjsddevelopment.applicationtracker.dto.UserTaskDto;
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
                    .filter(f -> f.candidateGroup(group).state("CREATED").assignee(""))
                    .send()
                    .join();
            items.addAll(results.items());
        });

        return userTaskMapper.toDtos(items);
    }

    public List<UserTaskDto> getCurrentUsersTasksInReview(String userId) {
        var results = camundaClient.newUserTaskQuery()
                .filter(f -> f.assignee(userId))
                .send()
                .join();
        return userTaskMapper.toDtos(results.items());
    }
}
