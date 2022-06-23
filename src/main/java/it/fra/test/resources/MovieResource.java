package it.fra.test.resources;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import it.fra.test.models.Movie;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Slf4j
@Path("movies")
public class MovieResource {

    @Inject
    PgPool client;

    @PostConstruct
    void config() {
        initDb();
    }

    @GET
    public Multi<Movie> getAll() {
        return Movie.findAll(client);
    }

    private void initDb() {
        log.info("Initializing DB...");
        client.query("DROP TABLE IF EXISTS movies").execute()
                .flatMap(m -> client.query(
                        "CREATE TABLE movies (id SERIAL PRIMARY KEY, title TEXT NOT NULL)").execute())
                .flatMap(m -> client.query(
                        "INSERT INTO movies (title) VALUES('The Lord of the Rings')").execute())
                .flatMap(m -> client.query(
                        "INSERT INTO movies (title) VALUES('Harry Potter')").execute())
                .flatMap(m -> client.query(
                        "INSERT INTO movies (title) VALUES('Soul')").execute())
                .await()
                .indefinitely();
        log.info("DB initialized!");
    }

}
