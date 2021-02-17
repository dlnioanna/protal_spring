package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "room", schema = "protal")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "capacity")
    private Integer capacity;

//    @ManyToMany(mappedBy = "movieRoomList", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<Movie> roomMovieList;

    @OneToMany(mappedBy = "roomOfMovieShow", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MovieShow> movieShowsOfRoom;

}
