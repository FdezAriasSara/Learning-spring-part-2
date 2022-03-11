package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) { //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        // Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        // Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
    static public void fillLoginForm(WebDriver driver,String dni , String password,String checkText){
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, dni, password);
        PO_View.checkElementBy(driver, "text", checkText);
    }
    static public void logout(WebDriver driver, String loginText){
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);

    }
    static public void accessMenuOption(WebDriver driver,String menuName, String viewName){
        //Pinchamos en la opción de menú: //li[contains(@id, 'menuName-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id,'"+menuName+"-menu')]/a");
        elements.get(0).click();
        //Esperamos a que aparezca la opción que buscamos (referenciada por href): //a[contains(@href, 'mark/add')]
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href,'"+viewName+"')]");
        elements.get(0).click();
    }
}