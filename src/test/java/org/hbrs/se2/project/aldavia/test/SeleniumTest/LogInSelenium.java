package org.hbrs.se2.project.aldavia.test.SeleniumTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class LogInSelenium {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Setze den Pfad zum ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikla\\Downloads\\chromedriver.exe");

        // Erstelle eine Instanz des ChromeDrivers
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        // Öffne die Registrierungsseite
        driver.get("http://localhost:8080/login");

    }

    @Test
    public void testLogIn() {

        //Username
        driver.findElement(By.xpath("//*[@id=\"vaadinLoginUsername\"]/input")).sendKeys("TestNiklas");
        //Email
        driver.findElement(By.xpath("//*[@id=\"vaadinLoginPassword\"]/input")).sendKeys("TestPasswort123123");
        //SignUp Button
        driver.findElement(By.xpath("//*[@id=\"button\"]")).click();

        // Überprüfe, ob die Registrierung erfolgreich war
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assertTrue(successMessage.isDisplayed(), "Die Registrierung war nicht erfolgreich.");


    }

    @AfterClass
    public static void tearDown () {
        // Schließe den Browser nach dem Test
        driver.quit();
    }

}
