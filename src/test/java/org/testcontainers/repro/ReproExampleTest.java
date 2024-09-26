package org.testcontainers.repro;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ReproExampleTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReproExampleTest.class);

    /**
     * Placeholder for a piece of code that demonstrates the bug. You can use this as a starting point, or replace
     * entirely.
     * <p>
     * Ideally this would be a failing test. If it's excessively difficult to form as a test (e.g. relates to log
     * output, teardown or other side effects) then it would be sufficient to explain the behaviour in the issue
     * description.
     */
    @Test
    public void demonstration() {
        try (
                PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.8-alpine")
        ) {
            container.withInitScript("init.sql");
            container.start();

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(container.getJdbcUrl());
            dataSource.setUsername(container.getUsername());
            dataSource.setPassword(container.getPassword());

            runCommands(dataSource, "DOCKER");

            dataSource.setUrl(System.getenv("POSTGRES_NATIVE_URL"));
            dataSource.setUsername(System.getenv("POSTGRES_NATIVE_USERNAME"));
            dataSource.setPassword("");

            runCommands(dataSource, "NATIVE");


        }
    }

    private void runCommands(DataSource dataSource, String type) {
        Instant start = Instant.now();
        LOG.info("STARTING RUN AGAINST {}", type);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        for (int i = 0; i < 10_000; i++) {
            String uuid = UUID.randomUUID().toString();
            template.update("INSERT INTO demo(uuid) VALUES (:id)", Map.of("id", uuid));

            String returned = template.queryForObject("SELECT uuid FROM demo where uuid = :uuid", Map.of("uuid", uuid), String.class);
            assertEquals(returned, uuid);
        }
        Long msDiff = Instant.now().minusMillis(start.toEpochMilli()).toEpochMilli();
        LOG.info("FINISHED RUN AGAINST {}, TOOK {} MS", type, msDiff);
    }
}
