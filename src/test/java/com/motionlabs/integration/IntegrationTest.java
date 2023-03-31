package com.motionlabs.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void clearDatabase() {
        databaseCleaner.execute();
    }

}
