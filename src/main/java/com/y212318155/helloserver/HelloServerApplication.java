package com.y212318155.helloserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.y212318155.helloserver.mapper")
public class HelloServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloServerApplication.class, args);
	}
}