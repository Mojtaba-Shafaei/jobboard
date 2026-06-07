package com.mojtaba.jobboard.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jobs")
public class Job {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false)
    private String company;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Getter
    @Setter
    private Double salary;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    public Job() {
        this.createdAt = LocalDateTime.now();
    }
}
