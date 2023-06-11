package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdresseDTOImpl implements AddressDTO {
    private String strasse;

    private int id;

    private String hausnummer;

    private String plz;

    private String ort;

    private String land;

    private List<Unternehmen> unternehmen;




}