package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.example.demo.dao.Username;
import com.example.demo.dao.blog;
import com.example.demo.dao.blogreop;
import com.example.demo.dao.blogservice;
import com.example.demo.dao.signuprepo;
import jakarta.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class controller {
    
    @Autowired
    private signuprepo signup;
    @Autowired
    private blogreop blogrepo;
    @Autowired
    private blogservice bs;
    
    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        @SuppressWarnings("unused")
		HttpSession session = request.getSession(true);
        mv.setViewName("hello.html");
        return mv;
    }
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String uname, @RequestParam String password, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Username user = signup.findByUnameAndPassword(uname, password);
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", uname);
            mv.setViewName("redirect:/home"); 
        } else {
            mv.setViewName("signup.html"); 
            mv.addObject("error", "Invalid username or password");
        }
        return mv;
    }
    
    @GetMapping("/signup")
    public ModelAndView showSignupForm() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("signup.html");
        return mv;
    }

    @PostMapping("/signup")
    @Transactional
    public ModelAndView submitForm(@RequestParam String name, @RequestParam String uname, @RequestParam String email,
            @RequestParam BigInteger phone, @RequestParam String password, @RequestParam String gender) {
        Username formData = new Username();
        formData.setName(name);
        formData.setuname(uname); 
        formData.setEmail(email);
        formData.setPhone(phone);
        formData.setPassword(password);
        formData.setGender(gender);
        signup.save(formData);
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("hello.html"); 
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            mv.addObject("username", username);
        }
        List<blog> ll=bs.getAllBlogs();
        mv.addObject("blogs", ll);
        mv.setViewName("home.html");
        return mv;
    }
    
    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView mv = new ModelAndView();
        List<blog> ll=bs.getAllBlogs();
        mv.addObject("blogs", ll);
        mv.setViewName("home.html");
        mv.setViewName("profile.html");
        return mv;
    }
    
    @GetMapping("/addblog")
    public ModelAndView addblog() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addblog.html");
        return mv;
    }
    
    @PostMapping("/submitblog")
    public ModelAndView submitblog(@RequestParam String bname, @RequestParam String blog,HttpServletRequest request) {
    	HttpSession session=request.getSession(false);
    	String uname;
    	ModelAndView mv = new ModelAndView();
    	if(session!=null)
    	{
    	uname=(String) session.getAttribute("username");
        blog bg = new blog();
        bg.setUname(uname);
        bg.setBlogname(bname);
        bg.setBlogtext(blog);
        blogrepo.save(bg);
        mv.setViewName("profile.html");
        return mv;
    	}
    	else
    	{
    		mv.setViewName("hello.html");
    		return mv;
    	}
    }
}
