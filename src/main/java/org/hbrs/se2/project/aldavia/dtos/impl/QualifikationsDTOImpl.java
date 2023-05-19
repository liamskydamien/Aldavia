package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.Getter;
import lombok.Setter;
import org.hbrs.se2.project.aldavia.dtos.QualifikationsDTO;

@Getter
@Setter
public class QualifikationsDTOImpl implements QualifikationsDTO {
    private String bezeichnung;
    private String bereich;
    private String beschreibung;
    private String beschaeftigungsart;
}
