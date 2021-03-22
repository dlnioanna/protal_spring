package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
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

    @OneToMany(mappedBy = "roomOfMovieShow", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MovieShow> movieShowsOfRoom;

}
