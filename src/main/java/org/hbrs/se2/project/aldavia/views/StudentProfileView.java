package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;

import java.util.List;
import java.util.Map;

@Route(value = "profile/student", layout = NeutralLayout.class)
public class StudentProfileView extends Div implements HasUrlParameter<String>, HasDynamicTitle {

    private final StudentProfileControl studentProfileControl;

    private String title = "";

    private StudentProfileDTO studentProfileDTO;

    public StudentProfileView(StudentProfileControl studentProfileControl) {
        this.studentProfileControl = studentProfileControl;
    }
    @Override
    public String getPageTitle() {
        return title;
    }

    @Override
    public void setParameter(BeforeEvent event,
                @OptionalParameter String parameter) {
            Location location = event.getLocation();
            QueryParameters queryParameters = location
                    .getQueryParameters();
            Map<String, List<String>> parametersMap = queryParameters.getParameters();
            try {
                studentProfileDTO = studentProfileControl.getStudentProfile(parametersMap.get("username").get(0));
                title = studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname();
            }
            catch (ProfileException e) {
                e.printStackTrace();
            }
    }




}
