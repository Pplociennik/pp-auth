package com.github.pplociennik.auth.e2e.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * TODO: Describe this class.
 *
 * @author Created by: Pplociennik at 15.02.2024 14:55
 */
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@org.springframework.test.context.ContextConfiguration( classes = ContextConfiguration.class )
@TestPropertySource( locations = "classpath:application-test.properties" )
//@AutoConfigureTestDatabase( connection = EmbeddedDatabaseConnection.H2 )
public abstract class AbstractMsSQLTest {

    private static final String DOCKER_IMAGE_TAG = "latest";
    private static final String DOCKER_IMAGE_NAME = "mcr.microsoft.com/mssql/server:" + DOCKER_IMAGE_TAG;
    @Container
    static MSSQLServerContainer container = new MSSQLServerContainer( DOCKER_IMAGE_NAME );

    static {
        container.acceptLicense();
        container.start();
    }

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties( DynamicPropertyRegistry aDynamicPropertyRegistry ) {
        aDynamicPropertyRegistry.add( "spring.datasource.url", container::getJdbcUrl );
        aDynamicPropertyRegistry.add( "spring.datasource.driverClassName", container::getDriverClassName );
        aDynamicPropertyRegistry.add( "spring.datasource.username", container::getUsername );
        aDynamicPropertyRegistry.add( "spring.datasource.password", container::getPassword );
//        aDynamicPropertyRegistry.add( "spring.jpa.hibernate.ddl-auto", () -> "create" );
    }
}
