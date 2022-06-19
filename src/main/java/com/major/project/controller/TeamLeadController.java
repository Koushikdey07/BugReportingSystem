package com.major.project.controller;

import com.major.project.model.BugReport;
import com.major.project.model.CustomEmpDetails;
import com.major.project.model.Emp;
import com.major.project.repository.BugReportRepository;
import com.major.project.service.BugReportService;
import com.major.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class TeamLeadController {

    @Autowired
    EmpService empService;

    @Autowired
    BugReportService bugReportService;

    @Autowired
    BugReportRepository bugReportRepository;

    @GetMapping(value = {"/tlead/login"})
    public String showTlMainLoginPage(){
        return "tlead/login";
    }

    @GetMapping("/tlead/home")
    public String home()
    {
        return "tlead/home";
    }

    @GetMapping("/tlead/report")
    public String getReport(Model model){
        BugReport bugReport=new BugReport();
        model.addAttribute("bugReport", bugReport);

        List<String> platformList= Arrays.asList("Java and J2EE","Dot Net","Android");
        model.addAttribute("platformList",platformList);

        List<String> importanceList= Arrays.asList("High","Medium","Low");
        model.addAttribute("importanceList",importanceList);

        return "tlead/affixNewBugs";
    }
    @PostMapping("/tlead/report")
    public String postReport(@ModelAttribute("bugReport") BugReport bugReport){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomEmpDetails customEmp = (CustomEmpDetails)auth.getPrincipal();
        Long tleadId = customEmp.getID();

        bugReport.setTlead(tleadId);

        bugReportRepository.save(bugReport);

        return "redirect:/tlead/home";
    }
    @GetMapping("/tlead/analyse")
    public String reportAnalyse(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomEmpDetails customEmp = (CustomEmpDetails)auth.getPrincipal();
        Long tleadId = customEmp.getID();

        List<BugReport> list=bugReportService.sortBugReportByTleadId(tleadId);

        model.addAttribute("reports", list);
        return "tlead/analyseBugReport";
    }
    @GetMapping("/tlead/assignDev/{id}")
    public String assignDev(@PathVariable int id, Model model)
    {
        BugReport report=bugReportService.getReportById(id);

        model.addAttribute("bugReport",report);

        model.addAttribute("empList",empService.getByAction());
        return "tlead/assign";
    }
    @PostMapping("/tlead/updateDev/{id}")
    public String updateDev(BugReport bugReport, @RequestParam("emp") List<Long> id,Model model)
    {
        Optional<BugReport> oldBugReport=bugReportRepository.findById(bugReport.getId());

        Long empID=id.get(0);

        Emp empName=empService.getEmpById(empID);

        bugReport.setEmp(empName);
        bugReport.setAction("Assigned");
        bugReport.setImportance(oldBugReport.get().getImportance());
        bugReport.setCreateDateTime(oldBugReport.get().getCreateDateTime());
        bugReport.setUpdateDateTime(oldBugReport.get().getUpdateDateTime());
        bugReport.setTlead(oldBugReport.get().getTlead());

        bugReportRepository.save(bugReport);

        return "redirect:/tlead/analyse";
    }
    @GetMapping("/tlead/dataIs")
    public String dataIs() { return "tlead/dataReductionIs"; }

    @GetMapping("/tlead/search")
    public String search(BugReport bugReport, Model model, String keyword)
    {
        if(keyword!=null) {
            List<BugReport> list = bugReportService.findByInstance(keyword);
            model.addAttribute("searchedData", list);
            return "tlead/dataReductionFs";
        }
        else
        {
            model.addAttribute("msg", "No Data Found");
            return "tlead/dataReductionFs";
        }
    }
}
