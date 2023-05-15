import org.junit.*;
import java.sql.*;
import static org.junit.Assert.*;

// Info: Ich bin noch nicht komplett fertig und werde noch daran arbeiten. :-)

public class DatabaseTest {

    private EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    /* Stellt eine Verbindung zur Datenbank mit JPA (Java Persistence API) her. Bei JPA ist keine explizite
       Verbindung zur Datenbank nötig, da die JPA-Implementierung alles erledigt. Stattdessen muss man eine
       "EntityManagerFactory" und einen "EntityManager" konfigurieren / verwenden.
    */
    @Before
    public void setUp() {
        // Name der "Persistence Unit", die in der "persistence.xml"-Datei definiert ist:
        entityManagerFactory = Persistence.createEntityManagerFactory("<BitteNamenEinfügen>");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /* Die Schließung der Datenbank-Verbindung übernimmt JPA. Aber man muss den "EntityManager"
       und die "EntityManagerFactory" schließen.
     */
    @After
    public void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}