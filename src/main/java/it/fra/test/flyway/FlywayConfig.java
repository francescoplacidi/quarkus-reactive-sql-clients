package it.fra.test.flyway;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "flyway-config")
public interface FlywayConfig {

    boolean migrateAtStart();

    Datasource datasource();

    /**
     * Datasource
     */
    public interface Datasource {
    
        String url();
        String username();
        String password();

    }

    
}

