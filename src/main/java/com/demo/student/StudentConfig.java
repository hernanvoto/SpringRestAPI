package com.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    /**
     * Developers can implement this interface CommandLineRunner in their Spring
     * Boot applications to execute custom logic when the application starts up.
     * 
     * @param repository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {

        return args -> {

            Student pepe = new Student("Pepe", "pepe@email.com", LocalDate.of(1976, Month.FEBRUARY, 2));

            Student maria = new Student("Maria", "maria@email.com", LocalDate.of(1980, Month.SEPTEMBER, 18));

            repository.saveAll(List.of(pepe, maria));
        };
    }

}
