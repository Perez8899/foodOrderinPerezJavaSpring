package com.perez.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.perez.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)   //do not display password
    private String password;
    private USER_ROLE role; //private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer") //don't create separate table for this
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto>favorities = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //When we delete the user, the related address is also deleted
    private List<Address> addresses = new ArrayList<>();

   }
