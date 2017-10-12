package com.csye6225.demo.service;

import com.csye6225.demo.pojo.Attachment;
import com.csye6225.demo.pojo.Tasks;
import com.csye6225.demo.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
    //added last
    public ArrayList<Attachment> findAttachment(String taskId) {

        System.out.println("enter att service"+taskId);
        return (ArrayList<Attachment>) attachmentRepository.findAttachmentByTasks(taskId);
    }
}
