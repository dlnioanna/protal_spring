package com.protal.myApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "guest", schema = "protal")
public class Guest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "guest_name")
    private String name;

    @NonNull
    @Column(name = "email")
    private String email;

//    @OneToOne(mappedBy = "guestOfTicket")
    @Column
    private Integer ticketId;

}
