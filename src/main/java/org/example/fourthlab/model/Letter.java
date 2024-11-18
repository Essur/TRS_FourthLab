package org.example.fourthlab.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class Letter {
    private int id;
    private int senderId;
    private int receiverId;
    private String subject;
    private String body;
    private Date createdAt;

    public Letter(int senderId, int receiverId, String subject, String body) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
        this.body = body;
    }
}