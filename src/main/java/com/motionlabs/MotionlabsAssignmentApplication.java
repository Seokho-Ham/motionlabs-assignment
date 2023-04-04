package com.motionlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MotionlabsAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotionlabsAssignmentApplication.class, args);
    }

}
