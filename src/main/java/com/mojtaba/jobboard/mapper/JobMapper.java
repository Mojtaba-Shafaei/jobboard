package com.mojtaba.jobboard.mapper;

import com.mojtaba.jobboard.dto.job.JobRequest;
import com.mojtaba.jobboard.model.Job;

public class JobMapper {
    public static Job toEntity(JobRequest request) {
        Job job = new Job();

        job.setTitle(request.title);
        job.setCompany(request.company);
        job.setLocation(request.location);
        job.setDescription(request.description);
        job.setSalary(request.salary);

        return job;
    }
}
