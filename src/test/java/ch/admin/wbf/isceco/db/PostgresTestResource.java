package ch.admin.wbf.isceco.db;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

// just a little class to help with the tests, nothing more
public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
            POSTGRES_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

    @Override
    public Map<String, String> start() {
        POSTGRES_CONTAINER.start();
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", POSTGRES_CONTAINER.getJdbcUrl());
        properties.put("quarkus.datasource.username", POSTGRES_CONTAINER.getUsername());
        properties.put("quarkus.datasource.password", POSTGRES_CONTAINER.getPassword());
        Log.info("JDBC URL: " + POSTGRES_CONTAINER.getJdbcUrl());
        Log.info("Username: " + POSTGRES_CONTAINER.getUsername());
        Log.info("Password: " + POSTGRES_CONTAINER.getPassword());
        return properties;
    }

    @Override
    public void stop() {
        POSTGRES_CONTAINER.stop();
    }
}
