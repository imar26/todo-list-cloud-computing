package com.csye6225.demo.repositories;

import com.csye6225.demo.pojo.Attachment;
import com.csye6225.demo.pojo.Tasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, String> {

    //added last
    ArrayList<Attachment> findAttachmentByTasks(String taskId);

}
