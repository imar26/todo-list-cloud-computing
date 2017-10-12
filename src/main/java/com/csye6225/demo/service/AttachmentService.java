package com.csye6225.demo.service;

import com.csye6225.demo.pojo.Attachment;
import com.csye6225.demo.pojo.Tasks;
import com.csye6225.demo.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
    //added last
    public ArrayList<Attachment> findAttachment(String taskId) {
        return (ArrayList<Attachment>) attachmentRepository.findAttachmentByTasks(taskId);
    }
    public void deleteAttachment(Attachment attachment){
        attachmentRepository.delete(attachment);
    }

    public Attachment findByAttachmentId (String attachmentId) {
        return attachmentRepository.findByAttachmentId(attachmentId);
    }
}
