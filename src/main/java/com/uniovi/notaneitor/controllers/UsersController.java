package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.SecurityService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.SignUpFormValidator;
import com.uniovi.notaneitor.validators.UserEditionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private UserEditionFormValidator userEditionFormValidator;

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }



    /**
     * Retorna la vista signup.
     * @param model , user vacío sin datos inicialmente.(Este objeto se está solicitando en el th:object="${user}"
     *              que hemos incluido en la vista.)
     * @return
     */

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    /**
     * Esta respuesta la que recibe los datos del formulario y debe validarlos.
     * Aplicamos el signUpFormValidator, si se produce un error redirigimos a la vista signup,
     * es la vista actual (no se ha podido completar el registro)
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {

        signUpFormValidator.validate(user,result);
        if(result.hasErrors())
            return "signup";
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(),user.getPasswordConfirm());
        return "redirect:home";

    }
    /**
     * Buscamos el usuario que encaja con la id que recibimos como parámetro . Lo guardamos como atributo del modelo
     * y retomamos la plantilla user/edit
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        model.addAttribute("usersList",usersService.getUsers());
        return "user/edit";
    }

    /**
     *
     *
     * @param id del user que está siendo modificado como ModelAtributte (dentro de la id), user con todos los atributos del mismo.
     * @return
     */
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute User user, @PathVariable Long id, BindingResult result) {
        User originalUser=usersService.getUser(id);
        //modificar solo DNI, apellidos y nombre.
        originalUser.setDni(user.getDni());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        //Una vez modificado,lo validamos y si es correcto lo sobreescribimos
        userEditionFormValidator.validate(originalUser,result);
        if(result.hasErrors())
            return "user/edit";
        usersService.addUser(originalUser);
        //Redirigimos a user/details
        return "redirect:/user/details/" + id;
    }


    //login y home muestran únicamente sus vistas correspondientes
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }
}
/**
 *
 *      * signup crea un nuevo usuario con el rol STUDENT, lo identifica automáticamente y redirige la navegación a home.
 *      *
 *      * @param user
 *      * @param model
 *      * @return
 *
 * @RequestMapping(value = "/signup", method = RequestMethod.POST)
 * public String signup(@ModelAttribute("user") User user, Model model) {
 * usersService.addUser(user);
 * securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
 * return "redirect:home";
 * }
 */