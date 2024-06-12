package com.apj.platform.fm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmApplication.class, args);
	}

}
