package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;;
import java.sql.Time;
import java.util.List;


@Data
@Entity
@Table(name = "movie_show", schema = "protal")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = MovieShow.class)
public class MovieShow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "show_date")
    private Date showDate;

    @OneToMany(mappedBy = "movieShow")
    @JsonManagedReference
    private List<Ticket> showTickets;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    // @JsonBackReference
    private Movie movieOfMovieShow;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonManagedReference
    private Room roomOfMovieShow;

    public Long getStartTime() {
        return startTime.getTime();
    }

    public Long getEndTime() {
        return endTime.getTime();
    }

}
