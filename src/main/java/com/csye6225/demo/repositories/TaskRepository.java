package com.csye6225.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.csye6225.demo.pojo.Tasks;

public interface TaskRepository extends CrudRepository<Tasks, String> {

    Tasks findByTaskId(String taskId);

}
