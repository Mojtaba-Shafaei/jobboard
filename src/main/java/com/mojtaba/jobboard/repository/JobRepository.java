package com.mojtaba.jobboard.repository;

import com.mojtaba.jobboard.model.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
