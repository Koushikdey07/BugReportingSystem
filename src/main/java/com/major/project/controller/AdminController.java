package com.major.project.controller;


import com.major.project.model.BugReport;
import com.major.project.model.Emp;
import com.major.project.service.BugReportService;
import com.major.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController
{
    @Autowired
    EmpService empService;

    @Autowired
    BugReportService bugReportService;

    @GetMapping("/admin/login")
    public String showAdminLoginPage(){
        return "admin/login";
    }

    @GetMapping("/admin/home")
    public String adminHome()
    {
        return "admin/home";
    }

    @GetMapping("/admin/recruit")
    public String recruit(Model model)
    {
        List<Emp> list=empService.getAllEmp();
        model.addAttribute("emps", list);
        return "admin/recEmp";
    }
    @GetMapping("/admin/action/{id}")
    public String updateAction(@ModelAttribute("emp") Emp emp)
    {
       empService.updateAction(emp);
       return "redirect:/admin/recruit";
    }
    @GetMapping("/admin/trace")
    public String getHist(Model model)
    {
        List<BugReport> list=bugReportService.getAllBugReport();
        model.addAttribute("reports", list);
        return "admin/traceHist";
    }

}