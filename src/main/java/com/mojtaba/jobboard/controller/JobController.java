package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.dto.job.JobRequest;
import com.mojtaba.jobboard.model.Job;
import com.mojtaba.jobboard.service.JobService;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Job createJob(@RequestBody JobRequest jobRequest) {
        return jobService.createJob(jobRequest);
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody JobRequest request) {
        return jobService.updateJob(id, request);
    }
}
