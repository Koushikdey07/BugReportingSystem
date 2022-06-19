package com.major.project.repository;

import com.major.project.model.Emp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends CrudRepository<Emp, Long> {

    @Query("SELECT e FROM Emp e inner join e.roles r WHERE r.name = :roles")
    List<Emp> findOnlyDev(@Param("roles") String roles);

    @Query("SELECT e FROM Emp e inner join e.roles r WHERE r.name = :roles AND e.action= 'Activated'")
    List<Emp> getAccordingAction(@Param("roles") String roles);
    @Query("SELECT e FROM Emp e WHERE e.email = :username")
    public Emp findByEmail(@Param("username") String email);
}
