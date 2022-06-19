package com.major.project.controller;

import com.major.project.model.BugReport;
import com.major.project.model.CustomEmpDetails;
import com.major.project.service.BugReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@Controller
public class DevController {

    @Autowired
    BugReportService bugReportService;


    @GetMapping("/dev/login")
    public String showDevLoginPage(){
        return "dev/login";
    }

    @GetMapping("/dev/home")
    public String devHome() {
        return "dev/home";
    }

    @GetMapping("/dev/inbox")
    public String devInbox(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomEmpDetails customEmp = (CustomEmpDetails)auth.getPrincipal();
        Long empId = customEmp.getID();

        List<BugReport> list = bugReportService.sortByActionAndEmpId(empId);
        model.addAttribute("reports", list);

        return "dev/inbox";
    }

    @GetMapping("/dev/recStatus/{id}")
    public String recStatus(@PathVariable int id, Model model) {

        model.addAttribute("dataList", bugReportService.getReportById(id));

        List<String> statusList = Arrays.asList("Rectified", "Not Rectified", "Pending");
        model.addAttribute("statusList", statusList);

        return "dev/recStatus";
    }

    @PostMapping("/dev/updateStatus/{id}")
    public String updateStatus(@ModelAttribute("bugReport") BugReport bugReport) {

        bugReportService.updateStatus(bugReport);

        return "redirect:/dev/inbox";
    }

}
