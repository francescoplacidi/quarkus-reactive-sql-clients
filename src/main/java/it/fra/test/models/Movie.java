package it.fra.test.models;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
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
        return client.query("SELECT id, title FROM movies ORDER BY title DESC").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(Movie::fromRow);
    }

    public static Movie fromRow(Row row) {
        return Movie.builder()
                .id(row.getLong("id"))
                .title(row.getString("title"))
                .build();
    }

}
