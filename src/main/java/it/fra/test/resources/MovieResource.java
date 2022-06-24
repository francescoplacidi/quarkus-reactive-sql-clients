package it.fra.test.resources;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import it.fra.test.entities.Movie;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;

@Path("movies")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @GET
    public Uni<List<Movie>> getAll() {
        return Movie.listAll(Sort.by("title"));
    }

    // @GET
    // @Path("{id}")
    // public Uni<Response> getById(@PathParam("id") Long id) {
    //     return Movie.findById(id)
    //         .onItem()
    //         .transform(movie -> Objects.nonNull(movie) ? Response.ok(movie) : Response.status(Status.NOT_FOUND))
    //         .onItem()
    //         .transform(Response.ResponseBuilder::build);
    // }

    // @POST
    // public Uni<Response> create(Movie movie) {
    //     return Movie.save(movie)
    //         .onItem()
    //         .transform(id -> URI.create(String.format("movies/%s", id)))
    //         .onItem()
    //         .transform(uri -> Response.created(uri).build());
    // }

    // @DELETE
    // @Path("{id}")
    // public Uni<Response> delete(@PathParam("id") Long id) {
    //     return Movie.delete(client, id)
    //         .onItem()
    //         .transform(deleted -> deleted.booleanValue() ? Status.NO_CONTENT : Status.NOT_FOUND)
    //         .onItem()
    //         .transform(status -> Response.status(status).build());
    // }

}
