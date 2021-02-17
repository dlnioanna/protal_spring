package com.protal.myApp.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "user", schema = "protal")
@NoArgsConstructor
@RequiredArgsConstructor
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "telephone")
    private Long telephone;

    @NonNull
    @Column(name = "email")
    private String email;

    @Column(name = "image", length = 1000)
    private byte[] image;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference(value="userPurchases")
    private List<Purchase> userPurchases;

    @OneToMany(mappedBy = "buyer" , fetch = FetchType.LAZY)
    @JsonManagedReference(value="userTickets")
    private List<Ticket> ticketsBought;


    public static final String ROLE_USER="USER";
    public static final String ROLE_ADMIN="ADMIN";
}
