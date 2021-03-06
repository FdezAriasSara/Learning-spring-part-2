package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service//Notación que indica que esta clase es un servicio-> Los sevicios crean BEANS
public class MarksService {

    private final HttpSession httpSession;
    @Autowired
    private MarksRepository marksRepository;

    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable,String searchText, User user) {
        Page<Mark> marks =new PageImpl<Mark>(new LinkedList<Mark>());
       searchText = "%"+searchText+"%";//COMODÍN SQL: en si la cadena introducida se encuentra en la descripción o el nombre del usuario
        if (user.getRole().equals("ROLE_STUDENT")) {//Si el usuario es estudiante se mostraran sus propias notas.
            marks = marksRepository.searchByDescriptionNameAndUser(pageable,searchText, user);
        } if (user.getRole().equals("ROLE_PROFESSOR")) {
            //Si es profesor las notas se filtrarán del conjunto total de notas.
            marks = marksRepository.searchByDescriptionAndName(pageable,searchText); }
        return marks;
    }
    public Page<Mark> getMarksForUser(Pageable pageable,User user) {
       Page<Mark> marks =new PageImpl<Mark>(new LinkedList<Mark>());
        //Si el rol del ususario es de estudiante, le mostrará solo sus propias notas.
        if (user.getRole().equals("ROLE_STUDENT")) { marks = marksRepository.findAllByUser(pageable,user);}
        //Si el rol del usuario es de profesor le mostrará TODAS las notas
        if (user.getRole().equals("ROLE_PROFESSOR")) { marks = getMarks(pageable); } return marks;
    }
    public Page<Mark> getMarks(Pageable pageable) {
        Page<Mark> marks =  marksRepository.findAll(pageable);
        return marks;
    }
    public void setMarkResend(Boolean revised, Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();//Datos del usuario autenticado.
        String dni=auth.getName(); //el atributo name del usuario autenticado corresponde con el dni .
        Mark mark= marksRepository.findById(id).get();//obtenemos la nota que intentamos modificar
        if(mark.getUser().getDni().equals(dni)){//si pertenece al usuario autenticado
            marksRepository.updateResend(revised,id);//Cambiamos el estado de resend.
        }

    }
    public Mark getMark(Long id) {
        //Obtenemos la lista de notas consultadas en la sesión
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        //Si dicha lista está vacia, se crea una nueva.
        if (consultedList == null) {
            consultedList = new HashSet<Mark>();
        }
        //Obtenemos la nota que queríamos buscar mediante el id
        Mark obtainedMark = marksRepository.findById(id).get();
        //la añadimos a la lista de notas consultadas.
        consultedList.add(obtainedMark);
        //Asignamos la lista al atributo consultedList
        httpSession.setAttribute("consultedList", consultedList);
        //retornamos la nota que queríamos buscar.
        return obtainedMark;
    }

        public void addMark (Mark mark){ // Si en Id es null le asignamos el ultimo + 1 de la lista
            marksRepository.save(mark);
        }

        public void deleteMark (Long id){
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