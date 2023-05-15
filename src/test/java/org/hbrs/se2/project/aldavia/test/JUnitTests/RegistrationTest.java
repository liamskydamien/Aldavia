import org.junit.*;
import static org.junit.Assert.*;

// Info: Ich bin noch nicht komplett fertig und werde noch daran arbeiten. :-)

public class RegistrationTest {

    private DatabaseTest database;

    @Before
    public void setUp() {
        database = new DatabaseTest();
        database.setUp();
        userRegistration = new UserRegistration(database);
    }

    @After
    public void tearDown() {
        database.tearDown();
    }

    // Erfolgreiche Registrierung:
    @Test
    public void testSuccessfulRegistration() {
        assertTrue(userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "abc123")));
    }

    // Wenn sich ein Benutzer versucht mit einer bereits in der Datenbank existierenden "Email" zu registrieren:
    @Test
    public void testDuplicateEmail() {
        userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "abc123"));
        assertFalse(userRegistration.registerUser(new User("TestUser_2", "testuser_1@example.com", "abc456")));
    }

    // Wenn sich ein Benutzer versucht mit einer bereits in der Datenbank existierendem "Usernamen" zu registrieren:
    @Test
    public void testDuplicateUsername() {
        userRegistration.registerUser(new User("nickname123", "testuser_1@example.com", "abc123"));

        assertFalse(userRegistration.registerUser(new User("nickname123", "testuser_2@example.com", "abc456")));
    }

    // Ein bereits registrierter User darf sich nicht nochmal registrieren:
    @Test
    public void testDuplicateRegistration() {
        userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "abc123"));

        assertFalse(userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "abc123")));
    }

    // Ob User in der Datenbank eingetragen wurde prüfen:
    @Test
    public void testNewUserAddedToDatabase() {

        User user = new User("TestUser_1", "testuser_1@example.com", "abc123");
        assertTrue(userRegistration.registerUser(user));

        User registeredUser = database.getUser(user.getUsername());
        assertNotNull(registeredUser);
        assertEquals(user.getUsername(), registeredUser.getUsername());
        assertEquals(user.getEmail(), registeredUser.getEmail());
        assertEquals(user.getPassword(), registeredUser.getPassword());
    }

    // Ob alle Pflichtfelder befüllt sind prüfen:
    @Test
    public void testAllRequiredFieldsAreFilled() {
        UserRegistrationRequest request = new UserRegistrationRequest("TestUser_1", "testuser_1@example.com", "abc123");
        assertTrue(userRegistration.registerUser(request));

        request = new UserRegistrationRequest("", "testuser_1@example.com", "abc123");
        assertFalse(userRegistration.registerUser(request));

        request = new UserRegistrationRequest("TestUser_1", "", "abc123");
        assertFalse(userRegistration.registerUser(request));

        request = new UserRegistrationRequest("TestUser_1", "testuser_1@example.com", "");
        assertFalse(userRegistration.registerUser(request));

        request = new UserRegistrationRequest("", "", "");
        assertFalse(userRegistration.registerUser(request));
    }

    // Überprüfung ob die Syntax der angegebenen E-Mail Adresse gültig ist:
    @Test
    public void testSyntaxValidationEmail() {
        String[] validEmails = {"test.user_1@example.com", "test.user_1@example.co.uk", "testuser_1@bar.io"};
        String[] invalidEmails = {"test.user_1", "test.user_1@", "@testuser_1.com", "testuser_1@.io"};

        for (String email : validEmails) {
            assertTrue(userRegistration.validateEmailSyntax(email));
        }

        for (String email : invalidEmails) {
            assertFalse(userRegistration.validateEmailSyntax(email));
        }
    }

    @Test
    public void testVerificationEmailSent() {
        User user = new User("TestUser_1", "testuser_1@example.com", "abc123");
        assertTrue(userRegistration.registerUser(user));

        // Benutzer versucht sich einzuloggen ohne die E-Mail zu bestätigen:
        assertFalse(userRegistration.login(user.getUsername(), user.getPassword()));

        // Benutzer bestätigt seine E-Mail:
        assertTrue(userRegistration.verifyEmail(user.getEmail()));

        // Benutzer versucht sich nach der E-Mail Bestätigung erneut einzuloggen:
        assertTrue(userRegistration.login(user.getUsername(), user.getPassword()));
    }

    // Prüfen, ob Fehlermeldungen die bei der Registrierung kommen sollten auch wirklich eintreten:
    @Test
    public void testRegistrationErrorMessages() {
        assertFalse(userRegistration.registerUser(new User("", "test.user_1@", "abc123")));

        List<String> errorMessages = userRegistration.getRegistrationErrorMessages();
        assertEquals(4, errorMessages.size());
        assertTrue(errorMessages.contains("Alle Felder müssen ausgefüllt werden."));
        assertTrue(errorMessages.contains("Bitte geben Sie eine gültige E-Mail Adresse an."));
        assertTrue(errorMessages.contains("Das Passwort muss mindestens 8 Zeichen lang sein."));
        assertTrue(errorMessages.contains("Das Passwort stimmt nicht mit der Passwortwiederholung überein."));
        // ToDo: Hier noch mehr ErrorMessages hinzufügen.
    }

    // Passwortwiederholung prüfen, ob beide Passwörter übereinstimmen:
    @Test
    public void testPasswordConfirmationMatch() {
        assertTrue(userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "abc123", "abc123")));

        // Ein weiterer Benutzer mit ungleicher Passwortwiederholung:
        assertFalse(userRegistration.registerUser(new User("InvalidUser_1", "test.user_1@", "abc123", "456test")));
    }

    // Überprüfung der ordnungsgemäßen Implementierung der Passwortverschlüsselung:
    @Test
    public void testPasswordEncryptionImplementation() {
        User user = new User("TestUser_1", "testuser_1", "abc123");
        userRegistration.registerUser(user);

        User storedUser = userRegistration.getUser(user.getUsername());
        assertNotNull(storedUser);
        assertNotEquals(user.getPassword(), storedUser.getPassword());
    }

    // Überprüfung, dass das Passwort nicht dasselbe ist wie der Benutzername:
    @Test
    public void testPasswordNotSameAsUsername() {
        assertFalse(userRegistration.registerUser(new User("TestUser_1", "testuser_1@example.com", "TestUser_1")));
    }

    // Passwortstärke und Kriterien prüfen:
    @Test
    public void testPasswordVerification() {
        assertTrue(userRegistration.passwordVerification("abc123"));
    }
}