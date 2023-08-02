package com.springBatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springBatch.entity.Products;




@Configuration
@EnableBatchProcessing
public class BatchConfig {
	

//1.Reader class object

	@Bean
	// bean is used to create the object in a container and makes it as a singleton
	public FlatFileItemReader<Products> reader(){
		return null;
	}
	
	
//2.processor class object
	
	@Bean
	public ItemProcessor<Products, Products> processor(){
		return null;
	}
	
	
//3.writer class object
	
	@Bean
	public JdbcBatchItemWriter<Products> writer(){
		return null;
	}
	
//4.listener class object
	
	@Bean
	public JobExecutionListener listener() {
		return null;
	}
	
//5.autowire step builder factory
	@Autowired
	private StepBuilderFactory sf;
	
//6.step object
	
	@Bean
	public Step stepA() {
		return null;
	}

//7.autowire jobBuilderFactory
	
	@Autowired
	private JobBuilderFactory jf;
	
	
//8. job object
	@Bean
	public Job jobA() {
		return null;
	}

}
