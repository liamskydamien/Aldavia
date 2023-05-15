import org.junit.*;
import static org.junit.Assert.*;

// Info: Ich bin noch nicht komplett fertig und werde noch daran arbeiten. :-)

public class RegistrationTest {

    private DatabaseTest database;

    @Before
    public void setUp() {
        database = new DatabaseTest();
        database.setUp();
    }

    @After
    public void tearDown() {
        database.tearDown();
    }

    // Ein bereits registrierter User darf sich nicht nochmal registrieren:
    @Test
    public void testDuplicateRegistration(){}

    // Ob Benutzername bereits belegt ist:
    @Test
    public void testDuplicateUsername(){}

    // Ob E-Mail Adresse bereits belegt ist:
    @Test
    public void testDuplicateEmail(){}

    // Passwortstärke und Kriterien prüfen:
    @Test
    public void testPasswordVerification(){}

    // Ob alle Pflichtfelder befüllt sind prüfen:
    @Test
    public void testAllRequiredFieldsAreFilled{}

    // Ob eine Verifizierungs-Mail versendet wurde:
    @Test
    public void testVerificationEmailSent(){}

    // Ob User in der Datenbank eingetragen wurden prüfen:
    @Test
    public void testNewUserAddedToDatabase(){}

    // Erfolgreiche Registrierung mit "true" Erwartung:
    @Test
    public void testSuccessfulRegistrations:(){}

    // Misslungene Registrierung mit "false" Erwartung:
    @Test
    public void testFailedRegistrations:(){}

    // Leere Eingabe explizit testen:
    @Test
    public void testEmptyInputHandling(){}

    // Überprüfung ob die Syntax der angegebenen E-Mail Adresse gültig ist:
    @Test
    public void testSyntaxValidationEmail(){}

    // Prüfen, ob Fehlermeldungen die bei der Registrierung kommen sollten auch wirklich eintreten:
    @Test
    public void testRegistrationErrorMessages(){}

    // Passwortwiederholung prüfen, ob beide Passwörter übereinstimmen:
    @Test
    public void testPasswordConfirmationMatch(){}

    // Überprüfung der ordnungsgemäßen Implementierung der Passwortverschlüsselung:
    @Test
    public void testPasswordEncryptionImplementation(){}

    // Überprüfung, dass das Passwort nicht dasselbe ist wie der Benutzername:
    @Test
    public void testPasswordNotSameAsUsername(){}
}