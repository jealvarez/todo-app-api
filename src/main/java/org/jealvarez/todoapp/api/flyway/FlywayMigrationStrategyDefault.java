package org.jealvarez.todoapp.api.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrationStrategyDefault implements FlywayMigrationStrategy {

    @Override
    public void migrate(final Flyway flyway) {
        flyway.migrate();
    }

}
