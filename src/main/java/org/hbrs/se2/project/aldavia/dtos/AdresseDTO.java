package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdresseDTO{
    private String strasse;
    private int id;
    private String hausnummer;
    private String plz;
    private String ort;
    private String land;
    private List<UnternehmenProfileDTO> unternehmen;
}