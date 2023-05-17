import org.junit.*;
import static org.junit.Assert.*;

// Info: Ich bin noch nicht komplett fertig und werde noch daran arbeiten. :-)

public class SecurityTest {

    // Überprüfen, dass SQL Injection und so nicht möglich sind: -> Testen, indem man eine Injection als Anmeldeversuch macht und dann schaut, ob man sich immer noch richtig anmelden kann (nur mit richtigen Anmeldedaten möglich)
    @Test
    public void testSQLInjectionProtection(){}
}