package com.binh.blog.controller;
import com.binh.blog.model.Comment;
import com.binh.blog.service.CommentService;
import com.binh.blog.service.PostService;
import com.binh.blog.service.UserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequestMapping("")
public class CommentController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @PostMapping("/homepage/detail")
    public String saveComment(HttpSession session,Model model,Comment comment,@RequestParam("postID") String postID){
        Long id = (Long) session.getAttribute("userID");
        comment.setCreator(userService.getUser(id));
        comment.setPost(postService.getPost(Long.parseLong(postID)));
        commentService.save(comment);
        return "redirect:/homepage/detail?postID="+postID;
    }
}
