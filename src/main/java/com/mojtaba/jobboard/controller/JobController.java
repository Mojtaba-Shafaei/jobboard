package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.model.Job;
import com.mojtaba.jobboard.service.JobService;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/jobs")
@RestController
public class JobController {
    private final JobService jobService;


    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<Job> getAllJob() {
        return jobService.getAllJobs();
    }
}
