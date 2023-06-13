package SeleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationSelenium {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Setze den Pfad zum ChromeDriver
        System.setProperty("webdriver.chrome.driver" , "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        // Erstelle eine Instanz des ChromeDrivers
        driver = new ChromeDriver();

        // Öffne die Registrierungsseite
        driver.get("http://localhost:8080/registration");
    }

    @AfterMethod
    public void tearDown() {
        // Schließe den Browser nach dem Test
        driver.quit();
    }

    @Test
    public void testRegistrierung() {
        // Registrierungsdaten
        String Username = "testNiklas";
        String Email = "testuser@example.com";
        String Vorname = "TestNiklas";
        String Nachname = "TestMues";
        String Passwort = "password123";

        // Finde die Registrierungselemente
        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\\\"flex-layout\\\"]/div/div[1]/vaadin-form-layout/vaadin-text-field[1]"));
        WebElement EmailField = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-form-layout/vaadin-text-field[2]"));
        WebElement VornameField = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-form-layout/vaadin-text-field[3]"));
        WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-form-layout/vaadin-text-field[4]"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-form-layout/vaadin-password-field[1]"));
        WebElement passwordCheckField = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-form-layout/vaadin-password-field[2]"));
        WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"flex-layout\"]/div/div[1]/vaadin-horizontal-layout[3]/vaadin-button"));

        // Setze die Registrierungsdaten
        usernameField.sendKeys(Username);
        EmailField.sendKeys(Email);
        VornameField.sendKeys(Vorname);
        lastNameField.sendKeys(Nachname);
        passwordField.sendKeys(Passwort);
        passwordCheckField.sendKeys(Passwort);

        // Klicke auf den Sign Up Button
        signUpButton.click();

        // Überprüfe, ob die Registrierung erfolgreich war
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        Assert.assertTrue(successMessage.isDisplayed(), "Die Registrierung war nicht erfolgreich.");
    }
}
