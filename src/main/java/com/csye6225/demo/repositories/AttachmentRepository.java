package com.csye6225.demo.repositories;

import com.csye6225.demo.pojo.Tasks;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Tasks, String> {
}
