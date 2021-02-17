package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;;
import java.util.Objects;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "ticket", schema = "protal")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "time_edited")
    private Date timeEdited;

    @NonNull
    @Column(name = "used")
    private Integer used;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase ticketPurchase;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "movie_show_id", referencedColumnName = "id")
    private MovieShow movieShow;

    @NonNull
    @ManyToOne
    @JsonBackReference("userTickets")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User buyer;

//    @OneToOne
//    @JoinColumn(name = "guest_id", referencedColumnName = "id")
    private Integer guestId;


}
