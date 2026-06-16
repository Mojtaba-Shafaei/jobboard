package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.dto.job.JobRequest;
import com.mojtaba.jobboard.dto.job.JobResponse;
import com.mojtaba.jobboard.model.Job;
import com.mojtaba.jobboard.service.JobService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RequestMapping("/api/jobs")
@RestController
public class JobController {
    private final JobService jobService;


    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobResponse> getAllJob() {
        return jobService.getAllJobs();
    }

    @PostMapping
    public Job createJob(@RequestBody @Valid JobRequest jobRequest) {
        return jobService.createJob(jobRequest);
    }

    @GetMapping("/{id}")
    public JobResponse getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @PutMapping("/{id}")
    public JobResponse updateJob(@PathVariable Long id, @RequestBody @Valid JobRequest request) {
        return jobService.updateJob(id, request);
    }
}
