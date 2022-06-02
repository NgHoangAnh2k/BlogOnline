package com.binh.blog.controller;

import com.binh.blog.model.User;
import com.binh.blog.model.UserRegistration;
import com.binh.blog.service.UserService;

import java.util.regex.Pattern;

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
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        String msg = "";
        model.addAttribute("msg", msg);
        return "login";
    }
    @PostMapping("/login")
    public String checkLogin(Model model,HttpSession session, User user){
        String msg = "";
        if(userService.getUser(user.getUserName())==null){
            msg = "Username does not exist!";
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "login";
        }
        else if(userService.getUser(user.getUserName()).getPassword().equals(user.getPassword())){
            User user1 = userService.getUser(user.getUserName());
            //model.addAttribute("user", user1);
            session.setAttribute("userID", user1.getId());
            session.setAttribute("userName", user1.getUserName());
            session.setAttribute("role", user1.getRole());
            System.out.println("userID "+ user1.getId());
            System.out.println("User logined: "+ user.getUserName());
            return "redirect:/homepage";
        }
        else {
            msg = "Password is incorrect!";
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "login";
        }

    } 
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("userRegistion", new UserRegistration());
        String msg = "";
        model.addAttribute("msg", msg);
        return "register";
    }
    @PostMapping("/register")
    public String addUser(UserRegistration userRegistion,Model model) {
        String msg="";
        if (userRegistion.getUserName().length()<8||userRegistion.getUserName().length()>16){
            msg= "Username has at least 8 and less than 16 character!";
            model.addAttribute("msg", msg);
            model.addAttribute("userRegistion", userRegistion);
            return "register";
        }
        else if(userRegistion.getPassword().length()<6||userRegistion.getPassword().length()>16){
            msg= "Password has at least 6 and less than 16 character!";
            model.addAttribute("msg", msg);
            model.addAttribute("userRegistion", userRegistion);
            return "register";
        }
        else if (!userRegistion.getPassword().equals(userRegistion.getConfirmPassword())) {
        	msg= "The two password don't match!";
            model.addAttribute("msg", msg);
            model.addAttribute("userRegistion", userRegistion);
            return "register";
        }
        else if(userService.getUser(userRegistion.getUserName())!= null) {
			msg= "This username already exists!";
            model.addAttribute("msg", msg);
            model.addAttribute("userRegistion", userRegistion);
            return "register";
		}
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        if (pattern.matcher(userRegistion.getUserName()).find()){
        	msg= "Username has invalid character!";
            model.addAttribute("msg", msg);
            model.addAttribute("userRegistion", userRegistion);
            return "register";
        }
        userService.save(new User(userRegistion.getUserName(),userRegistion.getPassword(),"User"));
        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("userID");
        session.removeAttribute("userName");
        session.removeAttribute("role");
        return "redirect:/login";
    }
    @GetMapping("/profile/changePass")
    public String showchangePass(){
        return "changePass";
    }
    @PostMapping("/profile/changePass")
    public String changePass(@RequestParam("password") String pass,@RequestParam("newpassword") String newpass,@RequestParam("confirmPassword") String confirmpass,HttpSession session,Model model){
        User user = userService.getUser((String) session.getAttribute("userName"));
        String msg="";
        if(newpass.length()<6||newpass.length()>16){
            msg= "Password has at least 6 and less than 16 character!";
            model.addAttribute("msg", msg);
            // model.addAttribute("userRegistion", user);
            return "changePass";
        }
        else if (!newpass.equals(confirmpass)) {
            msg= "The two password don't match!";
            model.addAttribute("msg", msg);
            // model.addAttribute("userRegistion", userRegistion);
            return "changePass";
        }
        else if(!user.getPassword().equals(pass)){
            msg = "Password incorrect!";
            model.addAttribute("msg", msg);
            return "changePass";
        }
        else{
            user.setPassword(newpass);
            userService.save(user);
            session.removeAttribute("userID");
            session.removeAttribute("userName");
            session.removeAttribute("role");
            return "redirect:/login";
        }
    }
}
