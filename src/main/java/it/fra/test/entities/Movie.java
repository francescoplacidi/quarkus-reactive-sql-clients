package it.fra.test.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
public class Movie extends PanacheEntity {

    @Getter
    @Setter
    public String title;
    
}
