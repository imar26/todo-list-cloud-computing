package com.csye6225.demo.pojo;
import javax.persistence.*;

@Entity
public class Attachment {

    @Id

    private String attachmentId;

    @Column
    private String name;

    public Attachment(){

    }

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Tasks tasks;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

}