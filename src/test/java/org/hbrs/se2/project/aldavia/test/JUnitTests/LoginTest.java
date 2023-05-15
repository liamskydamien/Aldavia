import org.junit.*;
import static org.junit.Assert.*;

// Info: Ich bin noch nicht komplett fertig und werde noch daran arbeiten. :-)

public class LoginTest {

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

    // Überprüfung, ob Benutzer nach Registrierung erfolgreich eingeloggt wird oder ob er seine E-Mail Adresse vorher bestätigen muss:
    @Test
    public void testEmailVerificationBeforeLogin(){}

    // Prüfung der erfolgreichen Anmeldung mit "gültigen" Anmeldedaten mit Erwartung "true":
    @Test
    public void testSuccessfulLoginWithValidCredentials(){}

    // Pürfung der misslungenen Anmeldung mit "ungültigen" Anmeldedaten mit Erwartung "false":
    @Test
    public void testFailedLoginWithInvalidCredentials()(){}

    // Wenn der Benutzer ohne Anmeldung versucht in einen geschützten Bereich zu kommen auf die Login-Seite weiterleiten Prüfung:
    @Test
    public void testRedirectToLoginPageForUnauthorizedAccessAttempt(){}

    // Prüfen, ob geschützte Funktionen nach Login dem Benutzer gewährt wird:
    @Test
    public void testProtectedFunctionsAfterLogin(){}

    // Ob Fehlermeldungen die bei Login kommen sollten auch kommen Prüfung:
    @Test
    public void testLoginErrorMessages(){}

    // Prüfen, ob Benutzer bei mehrfachen erfolglosen Login-Versuchen vorrübergehend gesperrt wird (Schutz vor Brute-Force Angriffen), um Sicherheitsrisiko zu minimieren:
    @Test
    public void testBruteForceAttackProtection(){}

    // Prüfen, ob der Benutzer die Möglichkeit hat sein Passwort zurückzusetzen wenn er es vergessen hat:
    @Test
    public void testPasswordReset(){}

    // Überprüfung ob der Benutzer nach einer bestimmten Zeit ausgeloggt wird um die Sicherheit zu erhöhen:
    @Test
    public void TimeoutLogoutTest(){}

    // Zusätzliche Tests auf leere Anmeldedaten (User leer, PW leer, beides leer):
    @Test
    public void emptyCredentialsTest(){}
}