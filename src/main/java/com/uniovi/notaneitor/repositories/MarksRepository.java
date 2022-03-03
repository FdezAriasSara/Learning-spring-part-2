package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

//ESTA CLASE YA ES UN BEAN-> lo podemos inyectar en cualquier componente donde queramos utilizarlo
public interface MarksRepository extends CrudRepository<Mark, Long> {
    //Hay que añadir las anotaciones JPA en la clase Mark para que esta sea reconocida como un repositorio
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id=?2")
    void updateResend(Boolean resend,Long id);
}