package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.service.*;
import org.hbrs.se2.project.aldavia.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;

@CssImport("./styles/views/profile/studentProfile.css")

public class StudentPersonalDetailsComponent extends HorizontalLayout implements ProfileComponent {


    private final StudentProfileControl studentProfileControl;

    private StudentProfileDTO studentProfileDTO;
    private Div profilePicture = new Div();
    private Div introduction = new Div();
    private TextField firstNameAndLastName;
    private TextField studiengang;
    private TextArea description;
    private final StudentService studentService = new StudentService();
    private final KenntnisseService kenntnisseService = new KenntnisseService();
    private final QualifikationenService qualifikationenService = new QualifikationenService();
    private final SprachenService sprachenService = new SprachenService();
    private final TaetigkeitsfeldService taetigkeitsfeldService = new TaetigkeitsfeldService();



    public StudentPersonalDetailsComponent(StudentProfileDTO studentProfileDTO) {
        this.studentProfileDTO = studentProfileDTO;
        studentProfileControl = new StudentProfileControl(studentService, kenntnisseService, qualifikationenService, sprachenService, taetigkeitsfeldService);
        addClassName("student-personal-details-component");
        firstNameAndLastName = new TextField();
        firstNameAndLastName.addClassName("first-name-and-last-name");
        studiengang = new TextField();
        description = new TextArea();
        setUpUI();

    }

    private void setUpUI(){


        VerticalLayout introductionLayout = new VerticalLayout();
        introductionLayout.add(firstNameAndLastName,studiengang,description);
        introduction.add(introductionLayout);

        profilePicture.setClassName("profile-picture");
        introduction.setClassName("introductionField");
        introduction.addClassName("card");
        updateViewMode();
        add(profilePicture);
        add(introduction);
    }



    public void updateViewMode() {
        firstNameAndLastName.setValue(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
        studiengang.setValue(studentProfileDTO.getStudiengang());
        description.setValue(studentProfileDTO.getBeschreibung());
        firstNameAndLastName.setReadOnly(true);
        studiengang.setReadOnly(true);
        description.setReadOnly(true);
    }

    public void updateEditMode() {
        firstNameAndLastName.setReadOnly(false);
        studiengang.setReadOnly(false);
        description.setReadOnly(false);

    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
        updateProfileDTO(userName);
        updateViewMode();

    }

    @Override
    public void switchEditMode() {
        updateEditMode();
    }

    private void updateProfileDTO(String userName) throws PersistenceException, ProfileException {
        String firstname = Utils.splitOnSpaces(firstNameAndLastName.getValue())[0];
        String lastname = Utils.splitOnSpaces(firstNameAndLastName.getValue())[1];
        studentProfileDTO.setVorname(firstname);
        studentProfileDTO.setNachname(lastname);
        studentProfileDTO.setStudiengang(studiengang.getValue());
        studentProfileDTO.setBeschreibung(description.getValue());
        System.out.println("Übergebene studentProfileDTO: " + studentProfileDTO.getNachname() + " " + studentProfileDTO.getVorname() + " " + studentProfileDTO.getStudiengang() + " " + studentProfileDTO.getBeschreibung());
        System.out.println("Übergebene username: " + userName);
        studentProfileControl.updateStudentProfile(studentProfileDTO, userName);
    }

}
