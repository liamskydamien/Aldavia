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
public class RatingDTOImpl implements RatingDTO {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getRating() {
        return 0;
    }
}