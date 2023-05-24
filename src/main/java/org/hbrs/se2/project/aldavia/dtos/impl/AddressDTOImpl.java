package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hbrs.se2.project.aldavia.dtos.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressDTOImpl implements AddressDTO {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getStreet() {
        return null;
    }

    @Override
    public String ZipCode() {
        return null;
    }

    @Override
    public String HouseNumber() {
        return null;
    }
}