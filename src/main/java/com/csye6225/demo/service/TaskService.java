package com.csye6225.demo.service;

import com.csye6225.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
}
