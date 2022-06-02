package com.binh.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.binh.blog.model.Post;
import com.binh.blog.service.PostService;
import com.binh.blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @GetMapping("/post")
    public String showPostPage(Model model,HttpSession session,Post post){
        model.addAttribute("post", new Post());
        System.out.println(session.getAttribute("userID"));
        return "post";
    }
    @PostMapping("/post")
    public String savePost(Model model,HttpSession session,Post post){
        Long userID = (Long) session.getAttribute("userID");
        post.setCreator(userService.getUser(userID));
        System.out.println("userID: "+userID);
        System.out.println("post: "+post.toString());
        postService.save(post);
        return "redirect:/homepage";
    }
    @PostMapping("/search")
    public String searchPostbyKeyword(@RequestParam("keyword") String keyword, Model model){
        iLst<Post> listPosts = postService.findByTitle(keyword);
        model.addAttribute("listPost", listPosts);
        return "homepage";
    }
}
