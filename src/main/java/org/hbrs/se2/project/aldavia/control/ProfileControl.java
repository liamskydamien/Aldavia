package org.hbrs.se2.project.aldavia.control;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ProfileControl {
    public String getInformation(String name) {
        return "Information" + name;
    }


}
