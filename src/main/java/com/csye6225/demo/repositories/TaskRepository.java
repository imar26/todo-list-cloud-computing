package com.csye6225.demo.repositories;

import com.csye6225.demo.pojo.Tasks;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Tasks, String> {

    Tasks findByTaskId(String taskId);

}
