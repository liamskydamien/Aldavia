package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hbrs.se2.project.aldavia.dtos.RolleDTO;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolleDTOImpl implements RolleDTO {

    private String bezeichnung;

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichhnung() {
        return this.bezeichnung;
    }

    @Override
    public String toString() {
        return "RolleDTOImpl{" +
                "bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
