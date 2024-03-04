package com.bpb.securecoding;

import com.bpb.securecoding.model.Student;
import com.bpb.securecoding.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bpb.securecoding.repository")
public class SecurecodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurecodingApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(StudentRepository repository) {
		return args -> {
			repository.save(new Student("Debopam Poddar", "A", 4.1));
			repository.save(new Student("Joyanta Banerjee", "A+", 4.6));
			repository.save(new Student("Vipul Gupta", "A+", 4.4));
		};
	}

}
