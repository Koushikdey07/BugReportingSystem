package com.major.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Bug_Report")
public class BugReport {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name="Bug_Summary", length = 25)
    private String bugSummary;

    @Column(nullable = false, length = 45)
    private String bugDescription;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String importance;

    @Column(name = "Action")
    private String action="Not Assigned";

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    @JoinColumn(name = "Emp_Id", referencedColumnName = "emp_id")
    private Emp emp;

    @Column(name = "Tlead_Id")
    private Long tlead;
}
