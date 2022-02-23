package com.uniovi.notaneitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class CustomConfiguration implements WebMvcConfigurer {
    /**
     * Empleamos este bean para detectar la localización.
     * Iniciaremos el idioma Español por defecto.
     * @return el objeto localeResolver
     */
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver= new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es","ES"));
        return localeResolver;
    }

    /**
     * Este interceptor detecta el parámetro idioma.Este bean nos permitirá,
     * mediante su parámetro Lang, utilizar dicho parámetro añadido a una petición.
     * Este parametro será el parámetro "lang"
     * @return el objeto localeChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * El bean de localeChangeInterceptor se añade al registro de interceptores de la app.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
}
