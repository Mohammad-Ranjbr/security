package com.example.Security;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Security.jwt.JwtAuth;
import com.example.Security.jwt.JwtUtils;
import com.example.Security.users.domain.Users;
import com.example.Security.users.service.UsersService;

@Controller
public class MainController {
    
    @Autowired
    private UsersService usersService ; 
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public MainController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/index")
    public String showIndex(){
        return "/index";
    } 

    // @GetMapping("/user")
    // public String userPage(){
    //     return "user";
    // }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('OP_ACCESS_USER')") 
    public String userPage(){
        return "user";
    }

    @GetMapping("/login")
    public String loginPage(){ 
        return "login";
    }

    // @GetMapping("/admin")
    // @PreAuthorize("hasAuthority('ADMIN')") 
    // public String adminPage(Model model){
    //     model.addAttribute("users",usersService.findAllUsers());
    //     return "admin";
    // }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('OP_ACCESS_ADMIN')")  

    public String adminPage(Model model){
        model.addAttribute("users",usersService.findAllUsers());
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        usersService.deleteUserById(usersService.findUserById(id));
        return "redirect:/admin";
    } 

    // @GetMapping("/admin/register")
    // public String registerUserPage(Model model){
    //     model.addAttribute("user",new Users()); 
    //     return "userRegister";
    // }

    @GetMapping("/admin/register") 
    @PreAuthorize("hasAuthority('OP_NEW_USER')") 
    public String registerUserPage(Model model){
        model.addAttribute("user",new Users()); 
        return "userRegister";
    }

    @PostMapping("/admin/register")
    public String registerUser(@ModelAttribute Users user){
        usersService.addUSer(user); 
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id , Model model){
        model.addAttribute("user",usersService.findUserById(id)); 
        return "userRegister"; 
    } 

    @GetMapping("/user/get/{id}")
    @PostAuthorize("returnObject.email == authentication.name")
    public @ResponseBody Users getUser(@PathVariable Long id){
        return usersService.findUserById(id);  
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error"; 
    }

    @GetMapping("/getCookie")
    public String getCookie(HttpServletRequest request){
        for(Cookie cookie : request.getCookies()){
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        }
        return "login"; 
    }

    @GetMapping("/setCookie")
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("user", "Mohammad");
        cookie.setMaxAge(60);
        response.addCookie(cookie); 
        return "login";
    } 

    @PostMapping("/jwt/login")
    public @ResponseBody ResponseEntity<?> jwtLogin(@RequestBody JwtAuth jwtAuth , HttpServletResponse response){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuth.getUsername(), jwtAuth.getPassword()));
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        response.addHeader("Authorization",jwtUtils.generateToken(jwtAuth.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);

    }
    
    @GetMapping("/jwt/hello")
    public @ResponseBody String jwtHello(){
        return "Hello Jwt";
    } 

    @GetMapping("/info")
    public @ResponseBody
    Principal getCookie(Principal principal) {
        return principal; 
    }

}
