package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Data
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "movie_show", schema = "protal")
public class MovieShow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "show_date")
    private Date showDate;

//    @Column(name = "movie_id")
//    private Integer movieId;
//
//    @Column(name = "room_id")
//    private Integer roomId;

    @OneToMany(mappedBy = "movieShow")
    @JsonManagedReference
    private List<Ticket> showTickets;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonBackReference
    private Movie movieOfMovieShow;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonManagedReference
    private Room roomOfMovieShow;
}
