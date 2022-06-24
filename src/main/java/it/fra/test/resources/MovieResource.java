package it.fra.test.resources;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import it.fra.test.models.Movie;

import java.net.URI;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;

@Path("movies")
public class MovieResource {

    @Inject
    PgPool client;

    @GET
    public Multi<Movie> getAll() {
        return Movie.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getById(@PathParam("id") Long id) {
        return Movie.findById(client, id)
            .onItem()
            .transform(movie -> Objects.nonNull(movie) ? Response.ok(movie) : Response.status(Status.NOT_FOUND))
            .onItem()
            .transform(Response.ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Movie movie) {
        return Movie.save(client, movie)
            .onItem()
            .transform(id -> URI.create(String.format("movies/%s", id)))
            .onItem()
            .transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Movie.delete(client, id)
            .onItem()
            .transform(deleted -> deleted.booleanValue() ? Status.NO_CONTENT : Status.NOT_FOUND)
            .onItem()
            .transform(status -> Response.status(status).build());
    }

}
