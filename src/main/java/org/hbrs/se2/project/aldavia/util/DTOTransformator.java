package org.hbrs.se2.project.aldavia.util;

import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.dtos.impl.*;
import org.hbrs.se2.project.aldavia.entities.*;

public class DTOTransformator {
    public static StudentProfileDTO transformStudentProfileDTO(Student student) {

        // Get Email & Phone number from User
        String email = student.getUser().getEmail();
        String telefonnummer = student.getUser().getPhone();

        //Create new StudentProfileDTOImpl and set values
        StudentProfileDTOImpl studentProfileDTO = new StudentProfileDTOImpl();
        studentProfileDTO.setVorname(student.getVorname());
        studentProfileDTO.setNachname(student.getNachname());
        studentProfileDTO.setMatrikelNummer(student.getMatrikelNummer());
        studentProfileDTO.setStudiengang(student.getStudiengang());
        studentProfileDTO.setStudienbeginn(student.getStudienbeginn());
        studentProfileDTO.setGeburtsdatum(student.getGeburtsdatum());
        studentProfileDTO.setEmail(email);
        studentProfileDTO.setTelefonnummer(telefonnummer);
        studentProfileDTO.setKenntnisse(student.getKenntnisse().stream().map(DTOTransformator::transformKenntnisDTOImpl).toList());
        studentProfileDTO.setSprachen(student.getSprachen().stream().map(DTOTransformator::transformSpracheDTOImpl).toList());
        studentProfileDTO.setQualifikationen(student.getQualifikationen().stream().map(DTOTransformator::transformQualifikationsDTOImpl).toList());
        studentProfileDTO.setTaetigkeitsfelder(student.getTaetigkeitsfelder().stream().map(DTOTransformator::transformTaetigkeitsfeldDTOImpl).toList());

        //Return StudentProfileDTOImpl
        return studentProfileDTO;
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
