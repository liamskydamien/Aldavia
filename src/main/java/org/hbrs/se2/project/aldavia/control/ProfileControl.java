package org.hbrs.se2.project.aldavia.control;

import org.springframework.stereotype.Component;

@Component
public class ProfileControl {
    public String getInformation(String name) {
        return "Information" + name;
    }


}
