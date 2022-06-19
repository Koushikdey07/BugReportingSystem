package com.major.project.repository;

import com.major.project.model.BugReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugReportRepository extends CrudRepository<BugReport, Integer> {

    //Custom query
    @Query(value = "SELECT * FROM BUG_REPORT WHERE BUG_SUMMARY LIKE %:keyword%",nativeQuery = true)
    List<BugReport> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM BUG_REPORT WHERE TLEAD_ID=:id",nativeQuery = true)
    List<BugReport> sortAccordingReportByTleadId(@Param("id")Long id);
    @Query(value = "SELECT * FROM BUG_REPORT WHERE NOT (ACTION='Not Assigned' OR ACTION='Rectified') AND EMP_ID=:id",nativeQuery = true)
    List<BugReport> sortAccordingActionAndEmpId(@Param("id")Long id);
}
