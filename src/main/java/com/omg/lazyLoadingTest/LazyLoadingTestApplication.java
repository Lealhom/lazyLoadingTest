package com.omg.lazyLoadingTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class LazyLoadingTestApplication {

//-javaagent:"C:\\spring-instrument-4.1.6.RELEASE.jar"

	public static void main(String[] args) {
		SpringApplication.run(LazyLoadingTestApplication.class, args);
		Properties properties = System.getProperties();
	}
}
