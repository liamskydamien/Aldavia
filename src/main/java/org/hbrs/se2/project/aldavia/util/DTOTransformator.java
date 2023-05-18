package org.hbrs.se2.project.aldavia.util;

import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.dtos.impl.*;
import org.hbrs.se2.project.aldavia.entities.*;

import java.util.List;

public class DTOTransformator {
    public static StudentProfileDTO transformStudentProfileDTO(Student student) {
        // Create new StudentProfileDTOImpl
        StudentProfileDTOImpl studentProfileDTO = new StudentProfileDTOImpl();

        // Get Email,Phone & Beschreibung number from User
        String email = student.getUser().getEmail();
        String telefonnummer = student.getUser().getPhone();
        String beschreibung = student.getUser().getBeschreibung();

        //Create new StudentProfileDTOImpl and set values

        List<Sprache> sprachen = student.getSprachen();
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder();
        List<Qualifikation> qualifikationen = student.getQualifikationen();
        List<Kenntnis> kenntnisse = student.getKenntnisse();

        if(sprachen != null){
            studentProfileDTO.setSprachen(sprachen.stream().map(DTOTransformator::transformSpracheDTOImpl).toList());
        }
        else {
            studentProfileDTO.setSprachen(null);
        }

        if(taetigkeitsfelder != null){
            studentProfileDTO.setTaetigkeitsfelder(taetigkeitsfelder.stream().map(DTOTransformator::transformTaetigkeitsfeldDTOImpl).toList());
        }
        else {
            studentProfileDTO.setTaetigkeitsfelder(null);
        }

        if(qualifikationen != null){
            studentProfileDTO.setQualifikationen(qualifikationen.stream().map(DTOTransformator::transformQualifikationsDTOImpl).toList());
        }
        else {
            studentProfileDTO.setQualifikationen(null);
        }

        if(kenntnisse != null){
            studentProfileDTO.setKenntnisse(kenntnisse.stream().map(DTOTransformator::transformKenntnisDTOImpl).toList());
        }
        else {
            studentProfileDTO.setKenntnisse(null);
        }

        studentProfileDTO.setVorname(student.getVorname());
        studentProfileDTO.setNachname(student.getNachname());
        studentProfileDTO.setMatrikelNummer(student.getMatrikelNummer());
        studentProfileDTO.setStudiengang(student.getStudiengang());
        studentProfileDTO.setStudienbeginn(student.getStudienbeginn());
        studentProfileDTO.setGeburtsdatum(student.getGeburtsdatum());
        studentProfileDTO.setEmail(email);
        studentProfileDTO.setBeschreibung(beschreibung);
        studentProfileDTO.setTelefonnummer(telefonnummer);

        //Return StudentProfileDTOImpl
        return studentProfileDTO;
    }

    public static Student transformStudent(StudentProfileDTO studentProfileDTO) {
        Student student = new Student();
        student.setVorname(studentProfileDTO.getVorname());
        student.setNachname(studentProfileDTO.getNachname());
        student.setMatrikelNummer(studentProfileDTO.getMatrikelNummer());
        student.setStudiengang(studentProfileDTO.getStudiengang());
        student.setStudienbeginn(studentProfileDTO.getStudienbeginn());
        student.setGeburtsdatum(studentProfileDTO.getGeburtsdatum());
        student.setSprachen(studentProfileDTO.getSprachen().stream().map(DTOTransformator::transformSpracheDTO).toList());
        student.setTaetigkeitsfelder(studentProfileDTO.getTaetigkeitsfelder().stream().map(DTOTransformator::transformTaetigkeitsfeldDTO).toList());
        student.setQualifikationen(studentProfileDTO.getQualifikationen().stream().map(DTOTransformator::transformQualifikationsDTO).toList());
        student.setKenntnisse(studentProfileDTO.getKenntnisse().stream().map(DTOTransformator::transformKenntnisDTO).toList());
        return student;
    }


    private static SpracheDTO transformSpracheDTOImpl(Sprache sprache) {
        SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
        spracheDTO.setBezeichnung(sprache.getName());
        spracheDTO.setLevel(sprache.getLevel());
        return spracheDTO;
    }

    private static QualifikationsDTO transformQualifikationsDTOImpl(Qualifikation qualifikation) {
        QualifikationsDTOImpl qualifikationsDTO = new QualifikationsDTOImpl();
        qualifikationsDTO.setBezeichnung(qualifikation.getBezeichnung());
        qualifikationsDTO.setBereich(qualifikation.getBereich());
        return qualifikationsDTO;
    }

    private static KenntnisDTO transformKenntnisDTOImpl(Kenntnis kenntnis) {
        KenntnisDTOImpl kenntnisDTO = new KenntnisDTOImpl();
        kenntnisDTO.setBezeichnung(kenntnis.getBezeichnung());
        return kenntnisDTO;
    }

    private static TaetigkeitsfeldDTO transformTaetigkeitsfeldDTOImpl(Taetigkeitsfeld taetigkeitsfeld) {
        TaetigkeitsfeldDTOImpl taetigkeitsfeldDTO = new TaetigkeitsfeldDTOImpl();
        taetigkeitsfeldDTO.setBezeichnung(taetigkeitsfeld.getBezeichnung());
        return taetigkeitsfeldDTO;
    }

}
