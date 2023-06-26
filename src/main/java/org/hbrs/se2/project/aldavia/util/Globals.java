package org.hbrs.se2.project.aldavia.util;

public class Globals {
    public static String CURRENT_USER = "current_User";

    public static class Pages {

        private Pages() {
        }

        public static final String STUDENT_MAIN = "studentMain";
        public static final String COMPANY_MAIN = "companyMain";

        public static final String COMPANY_PROFILE = "profileUnternehmen";

        public static final String PROFILE_VIEW = "profileStudent";
        public static final String COMPANY_PROFILE_VIEW = "companyProfile";
        public static final String STELLENANZEIGE_BEWERBUNGEN_VIEW = "stellenanzeigeBewerbungen";
        public static final String STELLENANZEIGE_ERSTELLEN_VIEW = "stellenanzeigeErstellen";
        public static final String NOT_LOGIN_COMPANY_VIEW = "profile";


        public static final String LOGIN_VIEW = "login";
        public static final String MAIN_VIEW = "";
    }
    public static class Beschaefting{
        private Beschaefting(){}
        public static final String ALLE = "Alle";
        public static final String PRAKTIKUM = "Praktikum";
        public static final String FESTANSTELLUNG = "Festanstellung";
        public static final String WERKSTUDENT = "Werkstudent";
        public static final String TEILZEIT = "Teilzeit";
        public static final String VOLLZEIT = "Vollzeit";

    }
    public static class Roles {

        private Roles() {
        }

        public static final String ADMIN = "admin";
        public static final String USER = "user";

        public static final String STUDENT = "student";

        public static final String UNTERNEHMEN = "unternehmen";

    }
    public static class LanguageLevels {

        private LanguageLevels() {
        }
        public static final String MOTHER_TONGUE = "Muttersprache";
        public static final String A1 = "A1";
        public static final String A2 = "A2";
        public static final String B1 = "B1";
        public static final String B2 = "B2";
        public static final String C1 = "C1";
        public static final String C2 = "C2";
    }


    public static class ProfileViewMode {
        public static final String VIEW = "view";
        public static final String EDIT = "edit";
    }

}
