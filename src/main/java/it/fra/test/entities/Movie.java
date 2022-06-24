package it.fra.test.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "movies")
@Data
@EqualsAndHashCode(callSuper = false)
public class Movie extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "movies_id_seq")
    @SequenceGenerator(name = "movies_id_seq")
    private Long id;
    private String title;
    
}
