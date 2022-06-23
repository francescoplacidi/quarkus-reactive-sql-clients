package it.fra.test.resources;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import it.fra.test.models.Movie;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("movies")
public class MovieResource {

    @Inject
    PgPool client;

    @GET
    public Multi<Movie> getAll() {
        return Movie.findAll(client);
    }

}
