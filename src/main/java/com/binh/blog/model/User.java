package com.binh.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
//Đánh dấu lớp thực thể
@Entity
@AllArgsConstructor
@Getter
@Setter
public class User {
    //Khóa
    @Id
    //Thiết lập mã tự động tăng
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String role;
    public User(String userName,String password,String role){
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    @Override
    public String toString(){
        return id+" "+userName+" "+password+" "+role;
    }
} 
