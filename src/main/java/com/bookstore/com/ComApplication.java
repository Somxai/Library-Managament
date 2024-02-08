package com.bookstore.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
@EnableCaching
public class ComApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ComApplication.class, args);
		System.out.println("hello world");





	}

}
