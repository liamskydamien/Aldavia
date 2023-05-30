package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;

import java.util.ArrayList;
import java.util.List;

public class StudentProfileDTOFactory{

    private static StudentProfileDTOFactory instance;

    public static synchronized StudentProfileDTOFactory getInstance() {
        if (instance == null){
            instance = new StudentProfileDTOFactory();
        }
        return instance;
    }

    private StudentProfileDTOFactory() {
    }

    public StudentProfileDTO createStudentProfileDTO(Student student){
        try {
            User user = student.getUser();
            List<TaetigkeitsfeldDTO> taetigkeitsfeldDTOList = new ArrayList<>();
            List<QualifikationsDTO> qualifikationsDTOList = new ArrayList<>();
            List<SpracheDTO> spracheDTOList = new ArrayList<>();
            List<KenntnisDTO> kenntnisDTOList = new ArrayList<>();
            if (student.getSprachen() != null){
                if (!student.getSprachen().isEmpty()) {
                    spracheDTOList = student.getSprachen().stream().map(this::createSpracheDTO).toList();
                }
            }
            if (student.getKenntnisse() != null){
                if (!student.getKenntnisse().isEmpty()) {
                    kenntnisDTOList = student.getKenntnisse().stream().map(this::createKenntnisDTO).toList();
                }
            }

            if(student.getTaetigkeitsfelder() != null) {
                if (!student.getTaetigkeitsfelder().isEmpty()) {
                    taetigkeitsfeldDTOList = student.getTaetigkeitsfelder().stream().map(this::createTaetigkeitsfeldDTO).toList();
                }
            }

            if(student.getQualifikationen() != null){
                if (!student.getQualifikationen().isEmpty()) {
                    qualifikationsDTOList = student.getQualifikationen().stream().map(this::createQualifikationsDTO).toList();
                }
            }

            return StudentProfileDTO.builder()
                    .email(user.getEmail())
                    .vorname(student.getVorname())
                    .nachname(student.getNachname())
                    .matrikelNummer(student.getMatrikelNummer())
                    .studiengang(student.getStudiengang())
                    .studienbeginn(student.getStudienbeginn())
                    .geburtsdatum(student.getGeburtsdatum())
                    .kenntnisse(kenntnisDTOList)
                    .taetigkeitsfelder(taetigkeitsfeldDTOList)
                    .lebenslauf(student.getLebenslauf())
                    .telefonnummer(user.getPhone())
                    .profilbild(user.getProfilePicture())
                    .sprachen(spracheDTOList)
                    .qualifikationen(qualifikationsDTOList)
                    .beschreibung(user.getBeschreibung())
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private SpracheDTO createSpracheDTO(Sprache sprache) {
        if (sprache == null){
            return null;
        }
        return SpracheDTO.builder()
                .name(sprache.getBezeichnung())
                .level(sprache.getLevel())
                .id(sprache.getId())
                .build();
    }

    private KenntnisDTO createKenntnisDTO(Kenntnis kenntnis) {
        if (kenntnis == null){
            return null;
        }
        return KenntnisDTO.builder()
                .name(kenntnis.getBezeichnung())
                .build();
    }

    private TaetigkeitsfeldDTO createTaetigkeitsfeldDTO(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfeld == null){
            return null;
        }
        return TaetigkeitsfeldDTO.builder()
                .name(taetigkeitsfeld.getBezeichnung())
                .build();
    }

    private QualifikationsDTO createQualifikationsDTO(Qualifikation qualifikation) {
        if (qualifikation == null){
            return null;
        }
        return QualifikationsDTO.builder()
                .bezeichnung(qualifikation.getBezeichnung())
                .beschreibung(qualifikation.getBeschreibung())
                .bereich(qualifikation.getBereich())
                .institution(qualifikation.getInstitution())
                .beschaeftigungsart(qualifikation.getBeschaftigungsverhaltnis())
                .von(qualifikation.getVon())
                .bis(qualifikation.getBis())
                .id(qualifikation.getId())
                .build();
    }

}

