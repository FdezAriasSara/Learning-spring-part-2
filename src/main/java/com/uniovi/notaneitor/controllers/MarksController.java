package com.uniovi.notaneitor.controllers;
//Imprescindible : Importar el paquete que contiene las anotaciones que usemos

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.MarkFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
//@RestController //Indica que la clase es un controlador REST y que responde a peticiones REST

public class MarksController {
    @Autowired// INYECTAMOS EL SERVICIO DE NOTAS-> Este servicio creará la bean de Notas!
    private MarksService marksService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MarkFormValidator markFormValidator;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping("/mark/list/update")
    public String updateList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list::tableMarks";//retorna el fragmento table marks
    }

    //Añadimos un metodo (el nombre no es relevante, pero sí la notación @RequestMapping)
    //que debe contener la url de la petición que responde cada método
    @RequestMapping("/mark/list")
    public String getList(Model model) {

        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";//Retornamos la vista a marks/list
    }


    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result) {
        markFormValidator.validate(mark, result);
        if (result.hasErrors())
            return "mark/add";
        marksService.addMark(mark);

        return "redirect:/mark/list";
    }


    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/add")//Respuesta para GET /mark/add-> responde con la vista add.html
    public String getMark() {
        return "mark/add";
    }

    @RequestMapping(value = "/mark/details/{id}")//Respuesta para GET /mark/add-> responde con la vista add.html
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    /**
     * Buscamos la nota que encaja con la id que recibimos como parámetro . Guardamos esa nota como atributo del modelo
     * y retomamos la plantilla mark/edit
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    /**
     * @param mark
     * @param id   de la nota que está siendo modificada como ModelAtributte (dentro de la id), mark con todos los atributos de la nota.
     * @return
     */
    @RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@Validated Mark mark, @PathVariable Long id) {
        Mark originalMark = marksService.getMark(id);
        //modificar solo scores y descripction
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }

    /**
     * Respuesta para poner a true el atributo resend.
     */
    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(Model model, @PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    /**
     * Respuesta para poner a false el atributo resend.
     */
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(Model model, @PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
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
   /*
    @RequestMapping("/mark/details/{id}")
    //mediante el parametro @PathVariable indicamos que el parámetro se encuentra en el path
    //usando la posición que ocupa en el mismo.
    public String getDetail(@PathVariable Long id) {
        // return "Getting Details=>"+id;->PREVIO AL USO DE BEANS.
        //PARA VER CAMBIOS-> PARAR SERVIDOR Y VOLVER A EJECUTARLO
        return marksService.getMark(id).toString();

    }

 */
}