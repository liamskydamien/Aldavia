package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.QualifikationsDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.time.LocalDate;
import java.util.List;

@CssImport("./styles/views/profile/studentProfile.css")
public class QualificationComponent extends VerticalLayout implements ProfileComponent{

    private final StudentProfileControl studentProfileControl;

    private final StudentProfileDTO studentProfileDTO;
    private List<QualifikationsDTO> qualificationList;
    private final TextField bezeichnung;
    private final TextField beschreibung;
    private final TextField bereich;
    private final TextField institution;
    private final TextField beschaeftigungsart;
    private final DatePicker von;
    private final DatePicker bis;
    private Dialog addQualificationPopUp;
    private Dialog editQualificationPopUp;
    private Button addQualification;
    private Span noQualifications;
    private VerticalLayout displayQualifications;
    HorizontalLayout addQualificationLayout;


    //TODO: add delete button
    public QualificationComponent(StudentProfileControl studentProfileControl, StudentProfileDTO studentProfileDTO) {
        this.studentProfileControl = studentProfileControl;
        this.studentProfileDTO = studentProfileDTO;
        qualificationList = studentProfileDTO.getQualifikationen();

        bezeichnung = new TextField("Bezeichnung");
        beschreibung = new TextField("Beschreibung");
        bereich = new TextField("Bereich");
        institution = new TextField("Institution");
        von = new DatePicker("Von");
        bis = new DatePicker("Bis");
        beschaeftigungsart = new TextField("Beschäftigungsart");

        noQualifications = new Span("Keine Qualifikationen vorhanden.");

        displayQualifications = new VerticalLayout();
        displayQualifications.addClassName("displayQualifications");

        addQualification = new Button("Hinzufügen");
        addQualification.setIcon(new Icon("lumo", "plus"));
        addQualification.addClassName("addQualificationButton");

        addQualificationLayout = new HorizontalLayout();

        addClassName("card");
        addClassName("qualificationComponent");
        setUpUI();


    }

    private void setUpUI() {
        H2 header = new H2("Qualifikationen");


        addQualificationLayout.addClassName("addQualificationLayout");
        addQualificationLayout.add(addQualificationButton());
        addQualificationLayout.setJustifyContentMode(JustifyContentMode.END);


        this.add(header);
        this.add(addQualificationLayout);
        addQualificationLayout.setVisible(false);
        this.add(displayQualifications);
        updateViewMode();


    }

    private void updateViewMode() {
        addQualificationLayout.setVisible(false);
        if(qualificationList.isEmpty()){
            displayQualifications.add(noQualifications);
        }else{
            getQulifikationAndCreateCard(Globals.ProfileViewMode.VIEW);
        }
    }

    private void updateEditMode() {
        addQualificationLayout.setVisible(true);
        displayQualifications.remove(noQualifications);
        getQulifikationAndCreateCard(Globals.ProfileViewMode.EDIT);

    }

    private void updateProfileDTO(String userName) throws PersistenceException, ProfileException {
        studentProfileControl.updateStudentProfile(studentProfileDTO, userName);
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

    private void createAddQualificationPopUp() {
        addQualificationPopUp = new Dialog();
        addQualificationPopUp.setModal(true);
        addQualificationPopUp.setCloseOnOutsideClick(false);
        addQualificationPopUp.setCloseOnEsc(true);


        // Form Layout
        FormLayout addQualificationForm = new FormLayout();
        addQualificationForm.addClassName("addQualificationForm");


        // Text Fields
        bezeichnung.setPlaceholder("e.g Werkstudent im Bereich Softwareentwicklung");
        beschreibung.setPlaceholder("e.g Entwicklung von Webanwendungen");
        bereich.setPlaceholder("e.g Softwareentwicklung");
        institution.setPlaceholder("e.g Aldavia GmbH");
        beschaeftigungsart.setPlaceholder("e.g Teilzeit");

        // Date Picker
        HorizontalLayout createVonBis = new HorizontalLayout();
        createVonBis.add(von, bis);
        von.setPlaceholder("-Beginn-");
        bis.setPlaceholder("-Ende-");


        // Buttons
        HorizontalLayout closePopUpLayout = new HorizontalLayout();
        closePopUpLayout.setJustifyContentMode(JustifyContentMode.END);
        closePopUpLayout.add(closeAddPopUpButton());


        addQualificationForm.add(bezeichnung, beschaeftigungsart , bereich, institution, beschreibung, createVonBis);
        addQualificationPopUp.add(closePopUpLayout,addQualificationForm,saveButton());

    }

    private Button closeAddPopUpButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addClassName("closeButton");
        closeButton.addClickListener(e -> {
            clearFields();
            addQualificationPopUp.close();
        });
        return closeButton;
    }

    private Button saveButton() {
        Button saveButton = new Button("Speichern");
        saveButton.addClassName("saveButton");
        saveButton.addClickListener(e -> {
            createNewQalification();
            clearFields();
            addQualificationPopUp.close();
            getQulifikationAndCreateCard(Globals.ProfileViewMode.EDIT);
        });


        return saveButton;
    }

    private Button addQualificationButton(){
        addQualification.addClickListener(e -> {
            createAddQualificationPopUp();
            addQualificationPopUp.open();
        });
        return addQualification;
    }

    private void createNewQalification() {
        // NEW QUALIFICATION
        QualifikationsDTO newQualification = new QualifikationsDTO();
        newQualification.setBezeichnung(bezeichnung.getValue());
        newQualification.setBeschreibung(beschreibung.getValue());
        newQualification.setBereich(bereich.getValue());
        newQualification.setInstitution(institution.getValue());
        newQualification.setBeschaeftigungsart(beschaeftigungsart.getValue());
        newQualification.setVon(von.getValue());
        newQualification.setBis(bis.getValue());
        newQualification.setId(-1);
        qualificationList.add(newQualification);
    }

    private void getQulifikationAndCreateCard(String mode){
        System.out.println("In render");
        System.out.println("Listen Anzahl: " + qualificationList.size());
        displayQualifications.removeAll();
        for(QualifikationsDTO qualifikationsDTO : qualificationList){
            createQualificationCard(qualifikationsDTO, mode);
        }
    }

    private void createQualificationCard(QualifikationsDTO qualifikationsDTO, String mode){

        VerticalLayout qualificationCardLayout = new VerticalLayout();
        qualificationCardLayout.addClassName("qualificationCardLayout");
        qualificationCardLayout.setWidthFull();

        // Header(Bezeichnung und Von-Bis)
        FlexLayout qualificationCardHeader = new FlexLayout();
        qualificationCardHeader.addClassName("qualificationCardHeader");
        qualificationCardHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);
        qualificationCardHeader.setWidthFull();
        H3 bezeichnung = new H3(qualifikationsDTO.getBezeichnung());
        Span vonBis = new Span(qualifikationsDTO.getVon() + " - " + qualifikationsDTO.getBis());
        qualificationCardHeader.add(bezeichnung, vonBis);


        // Institution
        Span institution = new Span(qualifikationsDTO.getInstitution());
        institution.addClassName("institution");

        // Beschaeftigungsart
        Span beschaeftigungsart = new Span(qualifikationsDTO.getBeschaeftigungsart());

        // Beschreibung
        Span beschreibung = new Span(qualifikationsDTO.getBeschreibung());
        beschreibung.addClassName("beschreibung");

        // Bereich
        Span bereich = new Span(qualifikationsDTO.getBereich());
        bereich.addClassName("bereich");

        if(mode.equals(Globals.ProfileViewMode.EDIT)){
            HorizontalLayout editButtonLayout = new HorizontalLayout();
            editButtonLayout.setJustifyContentMode(JustifyContentMode.END);
            editButtonLayout.setWidthFull();
            editButtonLayout.add(editButton(qualifikationsDTO));
            qualificationCardLayout.add(editButtonLayout);
        }

        qualificationCardLayout.add(qualificationCardHeader, institution, beschaeftigungsart, bereich, beschreibung);
        displayQualifications.add(qualificationCardLayout);
    }

    private Button editButton(QualifikationsDTO qualifikationsDTO) {
        Button editButton = new Button(new Icon("lumo", "edit"));
        editButton.addClickListener(e -> {
            createEditQualificationPopUp(qualifikationsDTO);
            editQualificationPopUp.open();
        });
        return editButton;
    }

    private void createEditQualificationPopUp(QualifikationsDTO qualifikationsDTO){
        editQualificationPopUp = new Dialog();
        editQualificationPopUp.setModal(true);
        editQualificationPopUp.setCloseOnOutsideClick(false);
        editQualificationPopUp.setCloseOnEsc(true);

        // Form Layout
        FormLayout editQualificationForm = new FormLayout();
        editQualificationForm.addClassName("editQualificationForm");

        // Text Fields

        bezeichnung.setValue(qualifikationsDTO.getBezeichnung());
        beschaeftigungsart.setValue(qualifikationsDTO.getBeschaeftigungsart());
        beschreibung.setValue(qualifikationsDTO.getBeschreibung());
        bereich.setValue(qualifikationsDTO.getBereich());
        institution.setValue(qualifikationsDTO.getInstitution());
        von.setValue(qualifikationsDTO.getVon());
        bis.setValue(qualifikationsDTO.getBis());

        // Date Picker
        HorizontalLayout createVonBis = new HorizontalLayout();
        createVonBis.add(von, bis);

        // Buttons
        HorizontalLayout closePopUpLayout = new HorizontalLayout();
        closePopUpLayout.setJustifyContentMode(JustifyContentMode.END);
        closePopUpLayout.add(closeEditPopUpButton());


        editQualificationForm.add(bezeichnung, beschaeftigungsart, beschreibung, bereich, institution, createVonBis);
        editQualificationPopUp.add(closePopUpLayout,editQualificationForm,saveEditButton(qualifikationsDTO));
    }
    private Button closeEditPopUpButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addClassName("closeButton");
        closeButton.addClickListener(e -> {
            clearFields();
            editQualificationPopUp.close();
        });
        return closeButton;
    }


    private Button saveEditButton(QualifikationsDTO qualifikationsDTO) {
        Button saveEdit = new Button("Speichern");
        saveEdit.setClassName("saveButton");
        saveEdit.addClickListener(e -> {
            updateQualification(qualifikationsDTO);
            clearFields();
            editQualificationPopUp.close();
            getQulifikationAndCreateCard(Globals.ProfileViewMode.EDIT);
        });
        return saveEdit;
    }

    private void updateQualification(QualifikationsDTO qualifikationsDTO){
        qualifikationsDTO.setBezeichnung(bezeichnung.getValue());
        qualifikationsDTO.setBeschaeftigungsart(beschaeftigungsart.getValue());
        qualifikationsDTO.setBeschreibung(beschreibung.getValue());
        qualifikationsDTO.setBereich(bereich.getValue());
        qualifikationsDTO.setInstitution(institution.getValue());
        qualifikationsDTO.setVon(von.getValue());
        qualifikationsDTO.setBis(bis.getValue());
    }


    private void clearFields() {
        bezeichnung.clear();
        beschaeftigungsart.clear();
        beschreibung.clear();
        bereich.clear();
        institution.clear();
        von.clear();
        bis.clear();
    }
}
