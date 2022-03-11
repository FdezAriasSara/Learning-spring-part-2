package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//para que las pruebas se ejecuten de forma ordenada creciente según el nombre del método de prueba
class NotaneitorApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\Sara\\Desktop\\Universidad\\3-tercer curso\\segundo cuatri\\(SDI)-Sistemas Distribuidos e Internet\\Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String URL = "http://localhost:8090";    //Común a Windows y a MACOSX

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeAll
    static public void begin() {
    }    static WebDriver driver = getDriver(PathFirefox, Geckodriver);

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        // Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    } //Después de cada prueba se borran las cookies del navegador

    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @Test
    @Order(1)
    void PR01A() {
        //En esta prueba no hace falta realizar assertions , ya que no se devuelve el objeto html
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    //Antes de la primera prueba

    @Test
    @Order(2)
    void PR01B() {
        //En esta prueba empleamos una assertion para comprobar que el elemento devuelto es el esperado.
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver, PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(), PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05->En este caso vamos al formulario de registro, lo rellenamos y esperamos a visualizar la cabecera de la lista de notas para ese alumno (“Notas del usuario”)
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
    // Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {

        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH());

        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06B. Prueba del formulario de registro. Nombre corto. // Propiedad: Error.signup.dni.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06C. Prueba del formulario de registro. Apellido corto.
    // Propiedad: Error.signup.lastName.length
    @Test
    @Order(8)
    public void PR06C() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefa", "Pe", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
        //Comprobamos el error
        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06D. Prueba del formulario de registro. Contraseña corta.
    // Propiedad: Error.signup.password.length
    @Test
    @Order(8)
    public void PR06D() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefa", "Benítez", "7", "7");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
        //Comprobamos el error
        String checkText = PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR06E: Prueba del formulario de registro. Contraseñas no coinciden..
    // Propiedad: Error.signup.passwordConfirm.coincidence
    @Test
    @Order(8)
    public void PR06E() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefa", "Benítez", "77141234", "123444221");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
        //Comprobamos el error
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR07: Identificación válida con usuario de ROL usuario (99999990A/123456).
    @Test @Order(9) public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        //
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText); Assertions.assertEquals(checkText, result.get(0).getText());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR08: Identificación válida con usuario de ROL profesor (99999993D/123456).
    @Test @Order(10) public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText); Assertions.assertEquals(checkText, result.get(0).getText());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR09: Identificación válida con usuario de ROL Administrador (99999988F/123456).
    @Test @Order(11) public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada del administrador
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText); Assertions.assertEquals(checkText, result.get(0).getText());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR10: Identificación inválida con usuario de ROL alumno (99999990A/123456).

    @Test @Order(12) public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText); Assertions.assertEquals(checkText, result.get(0).getText());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    //PR11: Identificación válida y desconexión con usuario de ROL usuario (99999990A/123456).
    @Test @Order(13) public void PR11() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText); Assertions.assertEquals(checkText, result.get(0).getText());
        Assertions.assertEquals(checkText, result.get(0).getText());
        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
    }
    //PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de estudiante


    @Test @Order(14) public void PR12() {
        PO_PrivateView.fillLoginForm(driver,"99999990A","123456","Notas de usuario");
        //En este caso una vez identificado el usuario, comprobamos que se vean 4 filas de notas.
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr", PO_View.getTimeout());
        Assertions.assertEquals(4, markList.size());
        // Para ello empleamos la consulta xpath "//tbody/tr" y a continuación comprobaremos que haya 4 objetos WebElement
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());

        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }
    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test @Order(15) public void PR13() { //Comprobamos que entramos en la pagina privada de Alumno
        PO_PrivateView.fillLoginForm(driver,"99999990A","123456","Notas del usuario");
       //  SeleniumUtils.esperarSegundos(driver, 1);
         //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
         // Esperamos por la ventana de detalle
          String checkText = "Detalles de la nota";
         List<WebElement>  result = PO_View.checkElementBy(driver, "text", checkText);
          Assertions.assertEquals(checkText,result.get(0).getText());
        //Ahora nos desconectamos comprobamas que aparece el menu de registrarse
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }@Test @Order(16) public void PR14() {
        //Vamos al formulario de login.
        PO_PrivateView.fillLoginForm(driver,"99999993D","123456","99999993D");

        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'mark-menu')]/a
        PO_PrivateView.accessMenuOption(driver,"marks","mark/add");
        //Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
        String checkText = "Nota Nueva 1";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");
        //Esperamos a que se muestren los enlaces de paginación de la lista de notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elements.get(3).click();

        //Comprobamos que aparece la nota en la página
        elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
        //Ahora nos desconectamos y comprobamos que aparece el menú de registrarse
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
}
    @Test @Order(17) public void PR15() { //Vamos al formulario
        PO_PrivateView.fillLoginForm(driver,"99999993D","123456","99999993D");
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        PO_PrivateView.accessMenuOption(driver,"marks","list");
        //Pinchamos en la opción de lista de notas.
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elements.get(3).click();
        //Esperamos a que se muestren los enlaces de paginación la lista de notas Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
        elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
        //Volvemos a la última página
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.get(3).click();
        //Y esperamos a que NO aparezca la última "Nueva Nota 1"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota Nueva 1",PO_View.getTimeout());
        //Ahora nos desconectamos comprobamos que aparece el menú de registrarse
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.logout(driver,loginText);
    }
}


