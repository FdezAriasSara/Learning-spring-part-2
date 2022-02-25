package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserEditionFormValidator implements Validator {
    @Autowired
    private UsersService usersService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        //Da error si el dni está vacío o tiene espacios.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastName","Error.empty");

        String dni=user.getDni();
        if (dni.length()!=9) {errors.rejectValue("dni", "Error.markAddition.dni.length");}
        //Error si el dni ya está asociado a un usuario registrado.
        if (!dni.matches("[0-9]{8}[a-zA-Z]]")) {errors.rejectValue("dni", "Error.markAddition.dni.lastChar");}
        //Comprobamos que el dni no esté en uso por otro miembro
        if (usersService.getUserByDni(dni) != null) {
            errors.rejectValue("dni", "Error.signup.dni.duplicate");
        }
        //Error si la longitud del nombre es incorrecta.
        if (user.getName().length() < 5 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");
        }//Error si la longitud del apellido es incorrecta.
        if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");
        }
    }
}
