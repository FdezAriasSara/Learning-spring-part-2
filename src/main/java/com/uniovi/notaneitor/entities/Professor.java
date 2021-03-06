package com.uniovi.notaneitor.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Professor {

    private String name;
    private String surname;
    private String dni;
    private String category;
    @Id
    @GeneratedValue
    private Long id;


    public Professor(String name, String surname, String dni, String category , Long id) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.category = category;
        this.id=id;
    }
    public Professor() {
       //In order for teacher class to be a bean.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", Dni='" + dni + '\'' +
                ", category='" + category + '\'' +
                ", id=" + id +
                '}';
    }
}
