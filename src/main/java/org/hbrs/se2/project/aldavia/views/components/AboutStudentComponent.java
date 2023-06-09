package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;

public class AboutStudentComponent extends VerticalLayout implements ProfileComponent {
    private final StudentProfileControl studentProfileControl;
    private StudentProfileDTO studentProfileDTO;
    private DatePicker dateOfBirth;
    private TextField phoneNumber;
    private EmailField email;



    public AboutStudentComponent(StudentProfileDTO studentProfileDTO) {
        this.studentProfileControl = new StudentProfileControl();
        this.studentProfileDTO = studentProfileDTO;
        this.addClassName("about-student-component");
        this.addClassName("card");

        dateOfBirth = new DatePicker();
        phoneNumber = new TextField();
        email = new EmailField();
        setUpUI();
    }

    public void setUpUI() {

        H2 title = new H2("Über mich");

        HorizontalLayout dateOfBirthLayout = new HorizontalLayout();
        Icon calendarIcon = new Icon("lumo", "calendar");
        calendarIcon.addClassName("about-Icon");
        dateOfBirthLayout.add(calendarIcon);
        dateOfBirthLayout.add(dateOfBirth);
        dateOfBirthLayout.addClassName("date-of-birth-layout");

        HorizontalLayout phoneNumberLayout = new HorizontalLayout();
        Icon phoneIcon = new Icon("lumo", "phone");
        phoneIcon.addClassName("about-Icon");
        phoneNumberLayout.add(phoneIcon);
        phoneNumberLayout.add(phoneNumber);
        phoneNumberLayout.addClassName("phone-number-layout");

        HorizontalLayout emailLayout = new HorizontalLayout();
        Icon emailIcon = new Icon("vaadin", "envelope");
        emailIcon.addClassName("about-Icon");
        emailLayout.add(emailIcon);
        emailLayout.add(email);
        emailLayout.addClassName("email-layout");

        updateViewMode();

        this.add(title);
        this.add(dateOfBirthLayout);
        this.add(phoneNumberLayout);
        this.add(emailLayout);
    }

    private void updateViewMode() {
        dateOfBirth.setValue(studentProfileDTO.getGeburtsdatum());
        phoneNumber.setValue(studentProfileDTO.getTelefonnummer());
        email.setValue(studentProfileDTO.getEmail());
        dateOfBirth.setReadOnly(true);
        phoneNumber.setReadOnly(true);
        email.setReadOnly(true);
    }

    private void updateEditMode() {
        dateOfBirth.setReadOnly(false);
        phoneNumber.setReadOnly(false);
        email.setReadOnly(false);
    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
        updateStudentProfileDTO(userName);
        updateViewMode();
    }

    @Override
    public void switchEditMode() {
        updateEditMode();
    }

    private void updateStudentProfileDTO(String userName) throws PersistenceException, ProfileException {
        if(email.getValue() == null || email.getValue().isEmpty()){
            Notification.show("Bitte geben Sie eine Email-Adresse ein");
            return;
        }
        studentProfileDTO.setGeburtsdatum(dateOfBirth.getValue());
        studentProfileDTO.setTelefonnummer(phoneNumber.getValue());
        studentProfileDTO.setEmail(email.getValue());
        studentProfileControl.updateStudentProfile(studentProfileDTO, userName);
    }


}
