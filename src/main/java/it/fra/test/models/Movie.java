package it.fra.test.models;

import javax.ws.rs.client.Client;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private Long id;
    private String title;

    public static Multi<Movie> findAll(PgPool client) {
        return client
            .query("SELECT id, title FROM movies ORDER BY title DESC")
            .execute()
            .onItem()
            .transformToMulti(set -> Multi.createFrom().iterable(set))
            .onItem()
            .transform(Movie::fromRow);
    }

    public static Uni<Movie> findById(PgPool client, Long id) {
        return client
            .preparedQuery("SELECT * FROM movies WHERE id = $1")
            .execute(Tuple.of(id))
            .onItem()
            .transform(m -> m.iterator().hasNext() ? fromRow(m.iterator().next()) : null);
    }

    public static Uni<Long> save(PgPool client, Movie movie) {
        return client
            .preparedQuery("INSERT INTO movies (title) VALUES ($1) RETURNING id")
            .execute(Tuple.of(movie.getTitle()))
            .onItem()
            .transform(rows -> rows.iterator().next().getLong("id"));
    }

    public static Uni<Boolean> delete(PgPool client, Long id) {
        return client
            .preparedQuery("DELETE FROM movies WHERE id = $1")
            .execute(Tuple.of(id))
            .onItem()
            .transform(rows -> rows.rowCount() == 1);
    }

    public static Movie fromRow(Row row) {
        return Movie.builder()
                .id(row.getLong("id"))
                .title(row.getString("title"))
                .build();
    }

}
