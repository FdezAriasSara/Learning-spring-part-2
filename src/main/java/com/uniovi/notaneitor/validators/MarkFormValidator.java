package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class MarkFormValidator implements Validator {
    @Autowired
    private MarksService marksService;
    @Autowired
    private UsersService usersService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Mark.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
            Mark mark=(Mark) target;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"dni","Error.empty");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"score","Error.empty");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","Error.empty");
            String dni=mark.getUser().getDni();
            if (dni.length()!=9) {errors.rejectValue("dni", "Error.markAddition.dni.length");}
            //Chequea también que los primeros 8 carácteres sean DIGÍTOS
            if (!dni.matches("[0-9]{8}[a-zA-Z]]")) {errors.rejectValue("dni", "Error.markAddition.dni.lastChar");}
            //Comprobamos que la descripción de la nota tenga, al menos, una longitud de 20 carácteres.
            if(mark.getDescription().length()<20){
                errors.rejectValue("description","Error.markAddition.description.length");
            }
           //Error si el dni ya está asociado a un usuario registrado.
            if (usersService.getUserByDni(mark.getUser().getDni()) != null) {
                 errors.rejectValue("dni", "Error.signup.dni.duplicate");
            }

            //Comprobamos que la nota sea un número entre 0 y 10.
            if(!(mark.getScore()>=0 && mark.getScore()<=10)){
                errors.rejectValue("score", "Error.markAddition.mark.score");
            }
    }
}
