package com.motionlabs.ui;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.motionlabs.util.DatabaseCleaner;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.nio.charset.StandardCharsets;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestDocsTest {

    protected static final String DOCUMENT_NAME_DEFAULT_FORMAT = "{class-name}/{method-name}";

    protected RequestSpecification spec;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setRestdocs(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
            .setPort(port)
            .addFilter(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .setContentType(ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8).toString())
            .build();
    }

    @BeforeEach
    void setDatabase() {
        databaseCleaner.setDatabase();
    }

}
