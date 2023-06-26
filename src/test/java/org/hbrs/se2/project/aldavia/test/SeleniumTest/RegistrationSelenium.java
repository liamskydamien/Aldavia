package org.hbrs.se2.project.aldavia.test.SeleniumTest;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class RegistrationSelenium {
/*
    private static WebDriver driver;

        @BeforeAll
        public static void setUp() {
            // Setze den Pfad zum ChromeDriver
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikla\\Downloads\\chromedriver.exe");

            // Erstelle eine Instanz des ChromeDrivers
            driver = new ChromeDriver();

            driver.manage().window().maximize();

            // Öffne die Registrierungsseite
            driver.get("http://localhost:8080/registration");

        }

        @Test
        public void testRegisterer() {

            //Username
            driver.findElement(By.xpath("//*[@id=\"vaadin-text-field-input-0\"]/slot[2]/input")).sendKeys("TestNiklas");
            //Email
            driver.findElement(By.xpath("//*[@id=\"vaadin-text-field-input-1\"]/slot[2]/input")).sendKeys("TestEmail@Test.de");
            //Vorname
            driver.findElement(By.xpath("//*[@id=\"vaadin-text-field-input-2\"]/slot[2]/input")).sendKeys("TestVorname");
            //Nachname
            driver.findElement(By.xpath("//*[@id=\"vaadin-text-field-input-3\"]/slot[2]/input")).sendKeys("TestNachname");
            //Passwort
            driver.findElement(By.xpath("//*[@id=\"vaadin-password-field-input-4\"]/slot[2]/input")).sendKeys("Passwort123Test");
            //Passwort
            driver.findElement(By.xpath("//*[@id=\"vaadin-password-field-input-5\"]/slot[2]/input")).sendKeys("Passwort123Test");

            //SignUp Button
            driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-horizontal-layout[3]/vaadin-button//div")).click();

            // Klicke auf den Sign Up Button
            // signUpButton.click();

            // Überprüfe, ob die Registrierung erfolgreich war
            WebElement successMessage = driver.findElement(By.id("successMessage"));
            assertTrue(successMessage.isDisplayed(), "Die Registrierung war nicht erfolgreich.");


        }

    @AfterClass
    public static void tearDown () {
        // Schließe den Browser nach dem Test
        driver.quit();
    }

 */

    }

