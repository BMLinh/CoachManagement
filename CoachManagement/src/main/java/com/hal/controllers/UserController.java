/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hal.controllers;

import com.hal.pojo.User;
import com.hal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Asus
 */
@Controller
public class UserController {
    @Autowired
    private UserService userDetailsService;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute(value = "user") User user) {
        if (user.getPassword().isEmpty() 
                || !user.getPassword().equals(user.getConfirmPassword())) 
            model.addAttribute("errMsg", "Mật khẩu không khớp!!!");
        else {
            if (this.userDetailsService.addUser(user) == true) {
                return "redirect:/login";
            }
            
            model.addAttribute("errMsg", "Có lỗi xảy ra! Vui lòng quay lại sau");
        }
        
        return "register";
    }
}
