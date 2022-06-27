package it.fra.test.flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.flywaydb.core.Flyway;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class RunFlyway {

    @Inject
    FlywayConfig config;

    public void runFlywayMigration(@Observes StartupEvent event) {
        if (config.migrateAtStart()) {
            Flyway flyway = Flyway.configure().dataSource(
                config.datasource().url(), config.datasource().username(), config.datasource().password())
            .load();
            flyway.migrate();
        }
    }    
    
}
