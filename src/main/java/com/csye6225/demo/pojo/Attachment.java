package com.csye6225.demo.pojo;
import javax.persistence.*;

@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachmentId;

    @Column
    private String name;

    public Attachment(String nameame){
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Tasks tasks;

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getName() {
        return name;
    }

    public void setTaskName(String name) {
        this.name = name;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

}