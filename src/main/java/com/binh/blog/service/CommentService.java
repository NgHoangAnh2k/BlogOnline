package com.binh.blog.service;

import com.binh.blog.model.Comment;
import com.binh.blog.reponsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public boolean deletePost(Long id) {
        commentRepository.deleteById(id);
        return true;
    }
    public Long countByType(String type){
        List<Comment> Comments = commentRepository.findAll();
        Long count = 0L;
        for(Comment comment:Comments){
            if(comment.getPost().getType().equalsIgnoreCase(type)) count++;
        }
        return count;
    }
}