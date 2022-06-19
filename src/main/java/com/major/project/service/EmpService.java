package com.major.project.service;

import com.major.project.model.CustomEmpDetails;
import com.major.project.model.Emp;
import com.major.project.model.Role;
import com.major.project.repository.EmpRepository;
import com.major.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmpService implements UserDetailsService {
    @Autowired
    EmpRepository empRepository;

    @Autowired
    RoleRepository roleRepository;

    public void addEmp(Emp emp){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(emp.getPassword());
        emp.setPassword(encodedPassword);
        List<Role> roles= new ArrayList<>();
        roles.add(roleRepository.findById(3).get());
        emp.setRoles(roles);
        emp.setEnabled(true);
        empRepository.save(emp);
    }

    public Emp getEmpById(Long id) {
        return empRepository.findById(id).get();
    }


    public List<Emp> getAllEmp(){
        String keyword="DEV";
        return empRepository.findOnlyDev(keyword);
    }

    public List<Emp> getByAction(){
        String keyword="DEV";
        return empRepository.getAccordingAction(keyword);
    }

    public Emp updateAction(Emp emp)
    {
        Optional<Emp> result=empRepository.findById(emp.getId());
        Emp oldEmp=result.get();
        oldEmp.setAction("Activated");
        empRepository.save(oldEmp);
        return oldEmp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Emp emp = empRepository.findByEmail(username);

        if (emp == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new CustomEmpDetails(emp);
    }

}
