package org.hbrs.se2.project.aldavia.test.Utils;

import org.hbrs.se2.project.aldavia.util.Globals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestGlobals {

    @Test
    public void testPages(){
        assertEquals("studentMain", Globals.Pages.STUDENT_MAIN);
        assertEquals("companyMain", Globals.Pages.COMPANY_MAIN);
        assertEquals("stellenanzeigeErstellen", Globals.Pages.STELLENANZEIGE_ERSTELLEN_VIEW);
        assertEquals("profileStudent", Globals.Pages.PROFILE_VIEW);
        assertEquals("companyProfile", Globals.Pages.COMPANY_PROFILE_VIEW);
        assertEquals("login", Globals.Pages.LOGIN_VIEW);
        assertEquals("", Globals.Pages.MAIN_VIEW);
        assertEquals("profileUnternehmen", Globals.Pages.COMPANY_PROFILE);
        assertEquals("stellenanzeigeBewerbungen", Globals.Pages.STELLENANZEIGE_BEWERBUNGEN_VIEW);
    }

    @Test
    public void testRoles(){
        assertEquals("admin", Globals.Roles.ADMIN);
        assertEquals("user", Globals.Roles.USER);
        assertEquals("student", Globals.Roles.STUDENT);
        assertEquals("unternehmen", Globals.Roles.UNTERNEHMEN);
    }

    @Test
    public void testLanguageLevels(){
        assertEquals("Muttersprache", Globals.LanguageLevels.MOTHER_TONGUE);
        assertEquals("A1", Globals.LanguageLevels.A1);
        assertEquals("A2", Globals.LanguageLevels.A2);
        assertEquals("B1", Globals.LanguageLevels.B1);
        assertEquals("B2", Globals.LanguageLevels.B2);
        assertEquals("C1", Globals.LanguageLevels.C1);
        assertEquals("C2", Globals.LanguageLevels.C2);
    }

    @Test
    public void testProfileViewMode(){
        assertEquals("view", Globals.ProfileViewMode.VIEW);
        assertEquals("edit", Globals.ProfileViewMode.EDIT);
    }
}
