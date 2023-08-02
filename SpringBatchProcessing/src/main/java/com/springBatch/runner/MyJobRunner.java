package com.springBatch.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class MyJobRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job jobA;
	
	
	@Override
	public void run(String... args) throws Exception {

		JobParameters parameters= new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters();
		launcher.run(jobA, parameters);
		System.out.println("JOB Execution Done");
	}

}
