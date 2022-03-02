package com.uniovi.notaneitor.services;

import org.springframework.stereotype.Service;

@Service
//Vamos a incluir los roles en una lista, pero lo ideal sería incluir una entidad Role en la base de datos y relacionarla
//con la entidad user.
public class RolesService {
    String[] roles = {"ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN"};
    public String[] getRoles() { return roles;
    }

}
