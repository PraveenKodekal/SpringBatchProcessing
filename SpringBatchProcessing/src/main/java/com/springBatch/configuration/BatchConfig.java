package com.springBatch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.springBatch.entity.Products;
import com.springBatch.listener.MyJobListener;
import com.springBatch.processor.ProductProcessor;




@Configuration
@EnableBatchProcessing
public class BatchConfig {
	

//1.Reader class object

	
	@Bean
	// bean is used to create the object in a container and makes it as a singleton
	public FlatFileItemReader<Products> reader(){
		
		FlatFileItemReader<Products> reader= new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("products.csv"));
		
		// read one line and convert into multiple line
		
		/**
		 * @info Demo d = new Demo() -- object creation 
		 * 
		 * where the subclass object is unknown we canot write the constructor inside the class 
		 * Demo d = new Demo() { } -- Anonymous inner class
		 * 
		 * 
		 * 1. instance blocks are used when the multiple consructors are having the common logic
		 * 2. instance blocks are used when the constructors are  not possible
		 * Demo d= new Demo() {{  }} -- Anonymous inner class with instance block
		 * 
		 * 
		 */
		reader.setLineMapper(new DefaultLineMapper<>() {{
			
			// line mapper needs Line tokensizer
			// tokenization is a process of converting lengthy string into multiple small strings using delimeter
			
			setLineTokenizer(new DelimitedLineTokenizer() {{
			
				setDelimiter(DELIMITER_COMMA);
				setNames("productId","productCode","productCost");
			}});
			
			
			
			// FieldSetMapper bind the data member
			
			setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(Products.class);
			}});
			
			
		
			
		}});
		return reader;
	}
	
	
//2.processor class object
	
	@Bean
	public ItemProcessor<Products, Products> processor(){
		return new ProductProcessor();
	}
	
	@Autowired
	private DataSource dataSource;
//3.writer class object
	
	@Bean
	public JdbcBatchItemWriter<Products> writer(){
		
		JdbcBatchItemWriter<Products> writer= new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("INSERT INTO PRODUCTS(PID, PCODE, PCOST, PDISC, PGST) VALUES ( :productId, :productCode, :productCost, :productDiscount, :productGst)");
			
		// read the data from object and set to sql query
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}
	
//4.listener class object
	
	@Bean
	public JobExecutionListener listener() {
		return new MyJobListener();
	}
	
//5.autowire step builder factory
	@Autowired
	private StepBuilderFactory sf;
	
//6.step object
	
	@Bean
	public Step stepA() {
		return sf.get("stepA")  				//stepName
				.<Products, Products>chunk(5)	//<I,O>chunk(size)
				.reader(reader())				//ReaderObject
				.processor(processor())			//ProcessorObject
				.writer(writer())				//writerObject
				.build();						// create the stepBuilder
	}

//7.autowire jobBuilderFactory
	
	@Autowired
	private JobBuilderFactory jf;
	
	
//8. job object
	@Bean
	public Job jobA() {
		return jf.get("jobA")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.start(stepA())
				.build();
	}

}
