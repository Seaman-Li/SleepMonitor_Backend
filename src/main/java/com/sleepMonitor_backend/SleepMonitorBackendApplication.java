package com.sleepMonitor_backend;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class SleepMonitorBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(SleepMonitorBackendApplication.class, args);
	}

}
