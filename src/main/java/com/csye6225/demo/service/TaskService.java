package com.csye6225.demo.service;

import com.csye6225.demo.pojo.Tasks;
import com.csye6225.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Tasks findByTaskId (String taskId) {
        return taskRepository.findByTaskId(taskId);
    }

    public void updateTask(Tasks tasks) {
        System.out.println("From Service " +tasks.getDescription());
        taskRepository.save(tasks);
    }
    public void deleteTask(Tasks tasks){
        taskRepository.delete(tasks);
    }
}
