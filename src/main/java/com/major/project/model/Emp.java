package com.major.project.model;

import lombok.Data;


import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name="emps")
public class Emp {
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Emp_name", length = 25)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "emps_roles", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }


    private String domain;

    private String doj;

    @Column(name="Contact", length = 10)
    private String phNo;

    @Column(name="Location ", length = 20)
    private String loc;

    @Column(name = "Action",nullable = false)
    private String action="pending";

    private boolean enabled;


}
