package com.uniovi.notaneitor.entities;

import javax.persistence.*;

@Entity
public class Mark {
    @Id
    @GeneratedValue //para generar automáticamente las ids
    private Long id;
    private String description;
    private Double score;
    @ManyToOne //una nota pertenece a un único usuario.
    @JoinColumn(name="user_id")//la columna va a establecer una asociación entre las entidades.
    private User user;

    public Mark() {
    }
    public Mark(User user) {
        this.user=user;
    }
    public Mark(Long id, String description, Double score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }
    public Mark(String description, Double score,User user) {

        this.description = description;
        this.score = score;
        this.user=user;
    }
    @Override
    public String toString() {
        return "Mark{" + "id=" + id + ", description='" + description + '\'' + ", score=" + score + '}';
    }
    public User getUser(){return this.user;}
    public void setUser(User user){this.user=user;}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}