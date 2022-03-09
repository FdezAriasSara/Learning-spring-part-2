package com.uniovi.notaneitor;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//para que las pruebas se ejecuten de forma ordenada creciente según el nombre del método de prueba
class NotaneitorApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\Sara\\Desktop\\Universidad\\3-tercer curso\\segundo cuatri\\(SDI)-Sistemas Distribuidos e Internet\\Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver(); return driver;
    }
    @Test
    void contextLoads() {
    }
    @BeforeEach
    public void setUp(){ driver.navigate().to(URL); } //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){ driver.manage().deleteAllCookies(); }

    //Antes de la primera prueba

    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
    // Cerramos el navegador al finalizar las pruebas
     driver.quit();
    }

}
