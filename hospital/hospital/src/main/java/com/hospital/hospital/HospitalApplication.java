package com.hospital.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
		System.out.println("Application Started...");
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		System.out.println("Run Successfully!");
	}
}
