package com.csye6225.demo.pojo;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Tasks {

    /*public Tasks(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
    } */

    public Tasks(){

    }

    @Id
    private String taskId;

    @Size(max = 4096)
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "tasks")
    private Set<Attachment> attachment;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(Set<Attachment> attachment) {
        this.attachment = attachment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}