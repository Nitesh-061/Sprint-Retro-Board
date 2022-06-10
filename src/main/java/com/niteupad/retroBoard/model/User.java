package com.niteupad.retroBoard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
The preceding class also does the same things as the Comment class. The newly added
annotations, which are also from the Lombok library, are @AllArgsConstructor, which
will generate a constructor with the id, username, password, and role properties, and
@NoArgsConstructor, which will generate a default constructor.
 */

@Entity
@Table(name = "rb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role;
}