package com.binh.blog.controller;
import com.binh.blog.model.Comment;
import com.binh.blog.model.Post;
import com.binh.blog.model.User;
import com.binh.blog.service.CommentService;
import com.binh.blog.service.PostService;
import com.binh.blog.service.UserService;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @GetMapping
    public String showIndexPage(){
        return "index";
    }
    @GetMapping("/homepage")
    public String showHomePage(Model model,User user){
        List<Post> listPost = postService.getAllPosts();
        //System.out.println("post: "+listPost.get(0).getTitle());
        model.addAttribute("listPost", listPost);
        return "homepage";
    }
    @PostMapping("/homepage")
    public String returnHomePage(Model model,User user){
        List<Post> listPost = postService.getAllPosts();
        //System.out.println("post: "+listPost.get(0).getTitle());
        model.addAttribute("listPost", listPost);
        return "homepage";
    }
    @GetMapping("/homepage/profile")
    public String showProfile(Model model){
        return "profile";
    }
    @GetMapping("/about")
    public String showAboutUs(Model model){
        return "about";
    }
    @GetMapping("/userinfo")
    public String showAdminPage(Model model, HttpSession session, User user){
        List<User> ds = userService.getAllUsers();
        model.addAttribute("dsUser", ds);
        Long id = (Long) session.getAttribute("userID");
        if(userService.getUser(id).getRole().equalsIgnoreCase("Admin")) 
        return "userinfo";
        return "redirect:/homepage";
    }
    @PostMapping("/userinfo")
    public String showAdminPage(){
        return "redirect:";
    }
    @GetMapping("/homepage/detail")
    public String viewDetailPost(@RequestParam Long postID,Model model){
        model.addAttribute("post", postService.getPost(postID));
        model.addAttribute("comment", new Comment());
        List<Comment> listComment = commentService.getComments(postID);
        model.addAttribute("listComment", listComment);
        return "detail";
    }
}
