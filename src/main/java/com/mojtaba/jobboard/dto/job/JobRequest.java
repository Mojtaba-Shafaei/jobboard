package com.mojtaba.jobboard.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class JobRequest {
    @NotBlank(message = "Title is required")
    public String title;

    @NotBlank(message = "Company is required")
    public String company;

    @NotBlank(message = "Location is required")
    public String location;

    @Size(min = 10, message = "Description must be at least 10 characters")
    public String description;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    public Double salary;
}
