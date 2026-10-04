package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.dto.job.JobResponse;
import com.mojtaba.jobboard.service.JobService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JobControllerTest {

    private static final String JOB_JSON =
            "{\"title\":\"Dev\",\"company\":\"Acme\",\"location\":\"Tehran\","
                    + "\"description\":\"Backend developer\",\"salary\":1200}";

    private JobService jobService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        jobService = mock(JobService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new JobController(jobService)).build();
    }

    @Test
    void getAllJob_returnsPaginatedJobs() throws Exception {
        PageRequest pageable = PageRequest.of(1, 5);
        when(jobService.getAllJobs(pageable))
                .thenReturn(new PageImpl<>(List.of(sampleJob()), pageable, 12));

        mockMvc.perform(get("/api/jobs").param("page", "1").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Dev"))
                .andExpect(jsonPath("$.totalElements").value(12))
                .andExpect(jsonPath("$.totalPages").value(3));

        verify(jobService).getAllJobs(pageable);
    }

    @Test
    void createJob_returnsSavedJob() throws Exception {
        when(jobService.createJob(any())).thenReturn(sampleJob());

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JOB_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dev"))
                .andExpect(jsonPath("$.company").value("Acme"));

        verify(jobService).createJob(argThat(req -> "Dev".equals(req.title) && "Acme".equals(req.company)));
    }

    @Test
    void getJobById_returnsJob() throws Exception {
        when(jobService.getJobById(5L)).thenReturn(sampleJob());

        mockMvc.perform(get("/api/jobs/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Dev"));

        verify(jobService).getJobById(5L);
    }

    @Test
    void deleteJob_deletesAndReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/jobs/5"))
                .andExpect(status().isOk());

        verify(jobService).deleteJob(5L);
    }

    @Test
    void updateJob_returnsUpdatedJob() throws Exception {
        when(jobService.updateJob(eq(5L), any())).thenReturn(sampleJob());

        mockMvc.perform(put("/api/jobs/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JOB_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dev"));

        verify(jobService).updateJob(eq(5L), argThat(req -> "Dev".equals(req.title)));
    }

    private JobResponse sampleJob() {
        JobResponse job = new JobResponse();
        job.id = 5L;
        job.title = "Dev";
        job.company = "Acme";
        job.location = "Tehran";
        job.salary = 1200.0;
        return job;
    }
}
