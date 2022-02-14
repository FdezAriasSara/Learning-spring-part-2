package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service//Notación que indica que esta clase es un servicio-> Los sevicios crean BEANS
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public List<Mark> getMarks() {
        List<Mark> marks=new ArrayList<>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public Mark getMark(Long id) {
        return marksRepository.findById(id).get();
    }

    public void addMark(Mark mark) { // Si en Id es null le asignamos el ultimo + 1 de la lista
      marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }
}
//Implementación sin repositorio;
/*
 private final List<Mark> marksList = new LinkedList<>(); -> Emplearemos un repositorio
    @PostConstruct//Esta anotación indica que esta función actúa como "Inicializador"
    public void init() {
        marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
        marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    }
    public List<Mark> getMarks() {

        return marksList;
    }
     public Mark getMark(Long id) {
        return marksList.stream().filter(mark -> mark.getId().equals(id)).findFirst().get();
    }

    public void addMark(Mark mark) { // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (mark.getId() == null) {
            mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
        }
        marksList.add(mark);
    }

    public void deleteMark(Long id) {
        marksList.removeIf(mark -> mark.getId().equals(id));
    }
*/