package com.csye6225.demo.service;

import com.csye6225.demo.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
}
