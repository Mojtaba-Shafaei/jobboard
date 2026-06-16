package com.mojtaba.jobboard.service;

import com.mojtaba.jobboard.dto.job.JobRequest;
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

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job createJob(JobRequest request) {
        Job job = JobMapper.toEntity(request);

        return jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    public void deleteJob(long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new JobNotFoundException(id);
        }
    }

    public Job updateJob(Long id, JobRequest request) {
        if (jobRepository.existsById(id)) {
            Job updated = JobMapper.toEntity(request);
            updated.setId(id);
            return jobRepository.save(updated);
        } else {
            throw new JobNotFoundException(id);
        }
    }
}
