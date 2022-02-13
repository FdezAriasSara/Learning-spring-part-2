package com.uniovi.notaneitor.controllers;
//Imprescindible : Importar el paquete que contiene las anotaciones que usemos
import com.uniovi.notaneitor.entities.Mark;
import org.springframework.web.bind.annotation.*;

@RestController //Indica que la clase es un controlador REST y que responde a peticiones REST

public class MarksController {
    //Añadimos un metodo (el nombre no es relevante, pero sí la notación @RequestMapping)
    //que debe contener la url de la petición que responde cada método
    @RequestMapping("/mark/list")
    public String getList() {
        return "Getting List";
    }
    //!!!!MUY IMPORTANTE:Si el objeto del modelo en el que se mapean los parámetros tiene más atributos de los recibidos estos atributos no se inicializarán, serán null.
    @RequestMapping(value = "/mark/add",method = RequestMethod.POST)  //Indicamos que la petición será de tipo POST->POR DEFECTO se activa GET
     // public String setMark(@RequestParam String description, @RequestParam String score) {-> signatura antigua.
    public String setMark(@ModelAttribute Mark mark)  {//Ahora Mapeamos los parametros DIRECTAMENTE de un objeto MARK
        return "added: " + mark.getDescription() + " with score : " + mark.getScore() + " id: " + mark.getId();
    }

    @RequestMapping("/mark/details/{id}")
    //mediante el parametro @PathVariable indicamos que el parámetro se encuentra en el path
    //usando la posición que ocupa en el mismo.
    public String getDetail(@PathVariable Long id) {
        return "Getting Details=>"+id;
        //PARA VER CAMBIOS-> PARAR SERVIDOR Y VOLVER A EJECUTARLO
    }


    //######## Parámetros de petición GET usando un identificador #################
    //@RequestMapping("/mark/details")
    //mediante el parametro @RequestParam indicamos que el mark/details
    //es una petición GET y que puede recibir un parametro de tipo long , llamado id
    // public String getDetail(@RequestParam Long id) {
    //    return "Getting Details=>"+id;
    //el controlador busca un parámetro en cualquier parte de la URL con clave ID
    //PARA VER CAMBIOS-> PARAR SERVIDOR Y VOLVER A EJECUTARLO
    //}
}