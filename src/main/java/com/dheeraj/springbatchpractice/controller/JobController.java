package com.dheeraj.springbatchpractice.controller;

import com.dheeraj.springbatchpractice.football.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    JobLauncher jobLauncher;
    JobRegistry jobRegistry;

    @PostMapping("/{jobName}")
    public void runJob(@PathVariable String jobName) throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(jobRegistry.getJob(jobName), new JobParameters());
    }
}
