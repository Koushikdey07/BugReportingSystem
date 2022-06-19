package com.major.project.service;

import com.major.project.model.BugReport;
import com.major.project.repository.BugReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BugReportService {

    @Autowired
    BugReportRepository bugReportRepository;

    public BugReport getReportById(int id) {
        Optional<BugReport> result=bugReportRepository.findById(id);
        return result.get();
    }

    public List<BugReport> getAllBugReport(){
        List<BugReport> result=(List<BugReport>)bugReportRepository.findAll();
        return result;
    }


    public List<BugReport> findByInstance(String keyword){
        return bugReportRepository.findByKeyword(keyword);
    }

    public List<BugReport> sortBugReportByTleadId(Long id){
        return bugReportRepository.sortAccordingReportByTleadId(id);
    }

    public List<BugReport> sortByActionAndEmpId(Long id){
        return bugReportRepository.sortAccordingActionAndEmpId(id);
    }
    public BugReport updateStatus(BugReport bugReport)
    {
        Optional<BugReport> result=bugReportRepository.findById(bugReport.getId());
        BugReport oldBugReport=result.get();
        oldBugReport.setAction(bugReport.getAction());
        bugReportRepository.save(oldBugReport);
        return oldBugReport;
    }
}
