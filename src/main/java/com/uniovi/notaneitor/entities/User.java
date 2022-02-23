package com.uniovi.notaneitor.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String dni;
    private String name;
    private String lastName;
    private String role;
    private String password;
    @Transient//propiedad que no se almacena en la tabla
    private String passwordConfirm;

    //mappedBy->Para indicar que la entidad es la inversa en la relación= un usuario puede tener muchas notas.
    //cascade-> relación en cascada-> los cambios tendrán efecto en otros objetos relacionados, en este caso, notas.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Mark> marks;

    public User(String dni, String name, String lastName) {
        super();
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }

    public User() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}
