package com.cr.app.postapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "body")
    private String body;

    public Comment(Long postId, String email, String name, String body) {
        this.postId = postId;
        this.email = email;
        this.name=name;
        this.body = body;

    }

    public Comment() {
    }

    @Override
    public String toString() {
        return "{" +
                "postId=" + postId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                "}";
    }
}



