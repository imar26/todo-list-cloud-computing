package com.csye6225.demo.repositories;

import com.csye6225.demo.pojo.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, String> {
}
