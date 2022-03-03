package com.uniovi.notaneitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    //Hay que añadir este dialecto para que el motor thymeleaf pueda usar los atributos sec: y los objetos de utilidad
    //de thymeleaf en las vistas.-> EN ALGUNAS VERSIONES DE SPRINGBOOT NO ES NECESARIO.
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    @Override
    //Lo utilizamos para definir cómo los filtros de Spring Security llevan a cabo la autenticación en nuestra app web.
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    //Lo utilizaremos para cifrar las contraseñas en la app
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().
                antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").
                //AÑADIMOS ROLES SIGUIENDO EL ORDEN DE MÁS ESPECÍFICO A MÁS GENERICO-> EL ORDEN IMPORTA
                        permitAll().antMatchers("/mark/add")
                .hasAuthority("ROLE_PROFESSOR")
                .antMatchers("/mark/edit/*")
                .hasAuthority("ROLE_PROFESSOR")
                .antMatchers("/mark/delete/*")
                .hasAuthority("ROLE_PROFESSOR")
                .antMatchers("/mark/**")
                .hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN") //solo un role admin puede acceder a gestión de
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/home").and().logout().permitAll();
    }
    //configuración del adaptador de seguridad- Inicio
    //Comentado debido al error de dependencia circular.
    /**
     *

     @Override protected void configure(HttpSecurity http) throws Exception {
     http.csrf()
     .disable()
     .authorizeRequests()
     .antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup", "/login/**")
     .permitAll().anyRequest()
     .authenticated().and().
     formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/home").and().logout().permitAll();
     }
     @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
     }
     */
    //configuración del adaptador de seguridad -final
}