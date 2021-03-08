package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Movie.class)
@Table(name = "movie", schema = "protal")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "movie_year")
    private Integer movieYear;

    @Column(name = "poster", length = 1000)
    private byte[] poster;

    @NonNull
    @Column(name = "rating")
    private Double rating;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "movieOfMovieShow", fetch = FetchType.LAZY)
    private List<MovieShow> movieShowsOfMovie;

}
