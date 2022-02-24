package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SignUpFormValidator implements Validator {
    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        //Da error si el dni está vacío o tiene espacios.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        //Error si el dni no tiene suficientes números/tiene demasiados.
        if (user.getDni().length() < 5 || user.getDni().length() > 24) {
            errors.rejectValue("dni", "Error.signup.dni.length");
        }
        //Error si el dni ya está asociado a un usuario registrado.
        if (usersService.getUserByDni(user.getDni()) != null) {
            errors.rejectValue("dni", "Error.signup.dni.duplicate");
        }
        //Error si la longitud del nombre es incorrecta.
        if (user.getName().length() < 5 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");
        }//Error si la longitud del apellido es incorrecta.
        if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");
        }//Error si la longitud de la contraseña es incorrecta.
        if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");
        }//Error si la contraseña es incorrecta.
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
        }
    }}