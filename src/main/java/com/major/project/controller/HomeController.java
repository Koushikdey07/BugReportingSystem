package com.major.project.controller;

import com.major.project.model.Emp;
import com.major.project.repository.EmpRepository;
import com.major.project.repository.RoleRepository;
import com.major.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    EmpService empService;

    @Autowired
    EmpRepository empRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/misc/index")
    public String index()
    {
        return "misc/index";
    }

    @GetMapping("/misc/aboutUs")
    public String aboutUs()
    {
        return "misc/aboutUs";
    }

    @GetMapping("/misc/signup")
    public String getRegister(Model model) {
        Emp emp = new Emp();
        model.addAttribute("emp", emp);

        List<String> domainList = Arrays.asList("Java and J2EE", "Dot Net", "Android");
        model.addAttribute("domainList", domainList);

        return "misc/signUp";
    }

    @PostMapping("/misc/signup")
    public String postRegister(@ModelAttribute("emp") Emp emp) {

        empService.addEmp(emp);
        return "redirect:/misc/index";
    }

}
