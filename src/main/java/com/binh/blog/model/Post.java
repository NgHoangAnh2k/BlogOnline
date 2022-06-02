package com.binh.blog.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String type;

    @ManyToOne
    private User creator;
    @Override
    public String toString(){
        return id+" "+title+" "+body+" "+type+"/"+creator.toString();
    }
}