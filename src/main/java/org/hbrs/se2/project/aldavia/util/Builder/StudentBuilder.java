package org.hbrs.se2.project.aldavia.util.Builder;

import org.hbrs.se2.project.aldavia.entities.Student;
import java.time.LocalDate;

public class StudentBuilder {
    private String vorname;
    private String nachname;
    private String matrikelNummer;
    private String studiengang;
    private LocalDate studienbeginn;
    private LocalDate geburtsdatum;
    private String lebenslauf;

    public StudentBuilder setVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public StudentBuilder setNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public StudentBuilder setMatrikelNummer(String matrikelNummer) {
        this.matrikelNummer = matrikelNummer;
        return this;
    }

    public StudentBuilder setStudiengang(String studiengang) {
        this.studiengang = studiengang;
        return this;
    }

    public StudentBuilder setStudienbeginn(LocalDate studienbeginn) {
        this.studienbeginn = studienbeginn;
        return this;
    }

    public StudentBuilder setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
        return this;
    }

    public StudentBuilder setLebenslauf(String lebenslauf) {
        this.lebenslauf = lebenslauf;
        return this;
    }

    public Student createProfile() {
        return new Student(vorname, nachname, matrikelNummer, studiengang, studienbeginn, geburtsdatum, lebenslauf);
    }
}
