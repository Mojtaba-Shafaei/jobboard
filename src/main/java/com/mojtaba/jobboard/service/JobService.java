package com.mojtaba.jobboard.service;

import com.mojtaba.jobboard.dto.job.JobRequest;
import com.mojtaba.jobboard.dto.job.JobResponse;
import com.mojtaba.jobboard.exception.JobNotFoundException;
import com.mojtaba.jobboard.mapper.JobMapper;
import com.mojtaba.jobboard.model.Job;
import com.mojtaba.jobboard.repository.JobRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream().map(JobMapper::toResponse).toList();
    }

    public JobResponse createJob(JobRequest request) {
        Job job = JobMapper.toEntity(request);

        Job insertedJob = jobRepository.save(job);
        return JobMapper.toResponse(insertedJob);
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));

        return JobMapper.toResponse(job);
    }

    public void deleteJob(long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new JobNotFoundException(id);
        }
    }

    public JobResponse updateJob(Long id, JobRequest request) {
        if (jobRepository.existsById(id)) {
            Job updated = JobMapper.toEntity(request);
            updated.setId(id);
            Job job = jobRepository.save(updated);
            return JobMapper.toResponse(job);
        } else {
            throw new JobNotFoundException(id);
        }
    }
}
