package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.data.repository.CrudRepository;
//ESTA CLASE YA ES UN BEAN-> lo podemos inyectar en cualquier componente donde queramos utilizarlo
public interface MarksRepository extends CrudRepository<Mark, Long> {
    //Hay que añadir las anotaciones JPA en la clase Mark para que esta sea reconocida como un repositorio
}