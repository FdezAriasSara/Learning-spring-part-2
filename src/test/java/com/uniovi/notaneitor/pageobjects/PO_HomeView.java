package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_HomeView extends PO_NavView {
    static public void checkWelcomeToPage(WebDriver driver, int language) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("welcome.message", language), getTimeout());
    }
/**
 *Otra forma de verificar una condición es devolver el elemento HTML y que la validación del resultado se realice
 * en el test con la correspondiente Assertion. Esta es otra forma de hacer este método:
 */
    static public List<WebElement> getWelcomeMessageText(WebDriver driver, int language) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        return SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("welcome.message", language), getTimeout());
    }

    /**
     * En este metodo estamos reutlizando checkWelcomePage para comprobar otras funcionalidades, en este caso la internacionalizacion
     *
     */
    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage, int locale1, int locale2) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        PO_HomeView.checkWelcomeToPage(driver, locale1);

        //Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);

        //Comprobamos que el texto de bienvenida haya cambiado a segundo idioma

        PO_HomeView.checkWelcomeToPage(driver, locale2);
        //Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
        //Esperamos a que se cargue el saludo de bienvenida en Español

        PO_HomeView.checkWelcomeToPage(driver, locale1); }
}