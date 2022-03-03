package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
//ESTA CLASE YA ES UN BEAN-> lo podemos inyectar en cualquier componente donde queramos utilizarlo
public interface MarksRepository extends CrudRepository<Mark, Long> {
    @Query("SELECT r FROM Mark r WHERE r.user = ?1 ORDER BY r.id ASC")//para asegurar que el orden sea ascendente
    List<Mark> findAllByUser(User user);
    //Hay que añadir las anotaciones JPA en la clase Mark para que esta sea reconocida como un repositorio
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id=?2")
    void updateResend(Boolean resend,Long id);

    /**
     * Devuelve TODAS las notas de la app cuando el texto buscado coincide con el nombre del usuario o de la descripción
      */
    @Query("SELECT r FROM Mark r Where (LOWER (r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1))")
    List<Mark> searchByDescriptionAndName(String searchText);
    /**
     * Devuelve las notas relacionadas con el usuario enviado como parámetro, cuando el texto buscado coincide con el nombre
     * del usuario o de la descripción de la nota.
     */
    @Query("SELECT r FROM Mark r Where (LOWER (r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user=?2")
    List<Mark> searchByDescriptionNameAndUser(String searchText,User user);
    //USO DE LA FUNCION LOWER PARA EVITAR SENSIBILIDAD A MAYUSCULAS O MINUSCULAS.
}