package com.example.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Security.users.domain.Users;
import com.example.Security.users.service.UsersService;

@Controller
public class MainController {
    
    @Autowired
    private UsersService usersService ;

    @GetMapping("")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/index")
    public String showIndex(){
        return "/index";
    } 

    @GetMapping("/user")
    public String userPage(){
        return "user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')") // Approach 2 to assign roles to users 
    public String adminPage(Model model){
        model.addAttribute("users",usersService.findAllUsers());
        return "admin";
    }

    @GetMapping("/login")
    public String loginPage(){ 
        return "login";
    }

    @GetMapping(value = "/admin/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new Users());
        return "userRegister";
    }

    @GetMapping("/user/get/{id}") 
    public @ResponseBody Users getUser(@PathVariable Long id){
        return usersService.findUserById(id); 
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable Long id){
        usersService.deleteUserById(usersService.findUserById(id));
        return "redirect:/admin"; 
    }

    @GetMapping("/admin/edit/{id}")
    public String editPage(Model model , @PathVariable Long id){
        model.addAttribute("user",usersService.findUserById(id)); 
        return "userRegister";
    }

    @PostMapping("/admin/register")
    public String register(@ModelAttribute Users user){
        usersService.addUser(user); 
        return "redirect:/admin";
    }

}
