package com.binh.blog.controller;
import com.binh.blog.model.Statistical;
import com.binh.blog.model.User;
import com.binh.blog.service.CommentService;
import com.binh.blog.service.PostService;
import com.binh.blog.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/userinfo/edit")
    public String showEditPage(@RequestParam String userName,Model model){
        model.addAttribute("user", userService.getUser(userName));
        return "edit";
    }
    @PostMapping("/userinfo/edit")
    public String updateUser(User user,Model model){
        User newUser = userService.getUser(user.getId());
        newUser.setRole(user.getRole());
        userService.save(newUser);
        return "redirect:/userinfo";
    }
    @GetMapping("/userinfo/delete")
    public String deleteUser(@RequestParam String userName,Model model){
        model.addAttribute("user", userService.getUser(userName));
        return "delete";
    }
    @GetMapping("/userinfo/confirm")
    public String confirm(@RequestParam String userName,Model model){
        userService.delete(userService.getUser(userName));
        return "redirect:/userinfo";
    }
    @GetMapping("/userinfo/addUser")
    public String addNewUser(Model model){
        model.addAttribute("user", new User());
        return "addUser";
    }
    @PostMapping("/userinfo/addUser")
    public String addNewUser(Model model, User user){
        String msg="";
        System.out.println("User: "+user.getUserName());
        if(userService.getUser(user.getUserName())!= null) {
            msg= "This username already exists!";
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "addUser";
        }
        userService.save(new User(user.getUserName(),user.getPassword(),user.getRole()));
        return "redirect:";
    }
    @PostMapping("/searchUser")
    public String searchUserByKeyword(@RequestParam("keyword") String keyword, Model model){
        List<User> listUsers = userService.findByName(keyword);
        model.addAttribute("dsUser", listUsers);
        return "userinfo";
    }
    @PostMapping("/statistical")
    public String showStatistical(Model model){
        List<String> types = new ArrayList<>();
        types.add("News");
        types.add("Entertainment");
        types.add("Sharing");
        types.add("Confidant");
        types.add("Technology");
        types.add("Others");
        List<Statistical> statisticals = new ArrayList<>();
        for(String s:types){
            statisticals.add(new Statistical(s, postService.countByType(s), commentService.countByType(s)));
        }
        model.addAttribute("statisticals", statisticals);
        return "thongke";
    }
}
