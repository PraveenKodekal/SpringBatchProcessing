package com.springBatch.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener {
	
	@Override
	public void beforeJob(JobExecution je) {
		
		System.out.println("started Date and Time : "+ new Date());
		System.out.println("status at starting : "+ je.getStatus());
		
	}
	
	@Override
	public void afterJob(JobExecution je) {
		System.out.println("ending Date and Time : "+ new Date());
		System.out.println("status at ending : "+ je.getStatus());
		
	}

}
