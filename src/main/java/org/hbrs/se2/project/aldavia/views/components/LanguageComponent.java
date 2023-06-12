package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.util.Globals;
import java.util.List;


public class LanguageComponent extends VerticalLayout implements ProfileComponent{
    private StudentProfileControl studentProfileControl;
    private StudentProfileDTO studentProfileDTO;
    private VerticalLayout addLanguageArea;
    private List<SpracheDTO> spracheList;
    private Div displayLanguage;
    private int languageID;

    private Button deleteLanguageButton;
    private Button addLanguageButton;

    private Span noLanguages;

    public LanguageComponent(StudentProfileDTO studentProfileDTO, StudentProfileControl studentProfileControl) {
        this.studentProfileControl = studentProfileControl;
        this.studentProfileDTO = studentProfileDTO;
        spracheList = studentProfileDTO.getSprachen();
        displayLanguage = new Div();
        languageID = 0;
        noLanguages = new Span("Keine Sprachen vorhanden.");
        setUpUI();
    }
    private void setUpUI() {
        deleteLanguageButton.setClassName("deleteButton");
        addLanguageButton.setClassName("addLanguageButton");


        H2 title = new H2("Sprachen");
        add(title);
        add(createAddLanguageArea());
        createAddLanguageArea();
        updateViewMode();

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
    private void updateViewMode() {
        addLanguageArea.setVisible(false);
        if(spracheList.isEmpty()){
            noLanguages.getStyle().set("font-style", "italic");
            add(noLanguages);
        } else {
            getLanguagesAndCreatefield(Globals.ProfileViewMode.VIEW);
        }
    }
    private void updateEditMode() {
        addLanguageArea.setVisible(true);

    }
    private void updateProfileDTO(String userName) throws PersistenceException, ProfileException {
        studentProfileDTO.setSprachen(spracheList);
        studentProfileControl.updateStudentProfile(studentProfileDTO,userName);
    }
    private VerticalLayout createAddLanguageArea() {
        addLanguageArea = new VerticalLayout();

        //Header
        TextField addLanguageField = new TextField();
        addLanguageField.addClassName("add-Skill-Field");
        addLanguageField.setPlaceholder("Füge neue Kenntnisse hinzu.");
        addLanguageField.setClearButtonVisible(true);
        addLanguageArea.add(addLanguageField);

        HorizontalLayout addLanguageLevel = new HorizontalLayout();
        ComboBox<String> languageLevelDropDown = new ComboBox<>();
        languageLevelDropDown.addClassName("add-Language-Level");
        languageLevelDropDown.setPlaceholder("Sprachniveau");
        languageLevelDropDown.setItems(Globals.LanguageLevels.A1,
                               Globals.LanguageLevels.A2,
                               Globals.LanguageLevels.B1,
                               Globals.LanguageLevels.B2,
                               Globals.LanguageLevels.C1,
                               Globals.LanguageLevels.C2,
                               Globals.LanguageLevels.MOTHER_TONGUE);
        addLanguageLevel.add(languageLevelDropDown);
        addLanguageLevel.add(addLanguageButton(addLanguageField,languageLevelDropDown));


        return addLanguageArea;
    }

    private Button addLanguageButton(TextField addLanguageField, ComboBox<String> languageLevelDropDown) {
        addLanguageButton = new Button("Hinzufügen");
        addLanguageButton.setIcon(new Icon("lumo", "plus"));
        addLanguageButton.addClickListener(event -> {
            if(spracheList.size()<11) {
                if (!addLanguageField.getValue().isEmpty()) {
                    SpracheDTO newSkill = new SpracheDTO(addLanguageField.getValue(),languageLevelDropDown.getValue(),languageID);
                    languageID++;
                    spracheList.add(newSkill);
                    getLanguagesAndCreatefield(Globals.ProfileViewMode.EDIT);
                    languageLevelDropDown.clear();
                    addLanguageField.clear();
                }
            } else {
                Notification.show("Du hast die maximale Kenntnis-Anzahl erreicht.", 20, Notification.Position.MIDDLE);
            }
        });
        return addLanguageButton;
    }

    private void getLanguagesAndCreatefield(String mode){
        displayLanguage.removeAll();

        for(SpracheDTO s: spracheList){

            if (mode.equals(Globals.ProfileViewMode.EDIT)) {
                putInlanguageCapsul(s,deleteLanguageButton(s));
            } else if (mode.equals(Globals.ProfileViewMode.VIEW)) {
                putInlanguageCapsul(s);
            }
        }
    }

    private Div putInlanguageCapsul(SpracheDTO s){
        Div languageCapsul = new Div();
        languageCapsul.addClassName("language-Capsul");

        HorizontalLayout languageCapsulLayout = new HorizontalLayout();
        languageCapsulLayout.addClassName("language-Capsul-Layout");

        VerticalLayout languageCapsulLayoutLeft = new VerticalLayout();
        languageCapsulLayoutLeft.addClassName("language-Capsul-Layout-Left");

        Span language = new Span(s.getName());
        language.addClassName("language-Capsul-Text");
        Span languageLevel = new Span(s.getLevel());
        languageLevel.addClassName("language-Capsul-Text");

        languageCapsulLayoutLeft.add(language);
        languageCapsulLayoutLeft.add(languageLevel);
        languageCapsulLayout.add(languageCapsulLayoutLeft);

        languageCapsul.add(languageCapsulLayout);
        displayLanguage.add(languageCapsul);

        return languageCapsul;
    }


    private Div putInlanguageCapsul(SpracheDTO s, Button delete){
        Div languageCapsulWithDelete = new Div();
        languageCapsulWithDelete.addClassName("language-Capsul");

        HorizontalLayout languageCapsulLayout = new HorizontalLayout();
        languageCapsulLayout.addClassName("language-Capsul-Layout");

        VerticalLayout languageCapsulLayoutLeft = new VerticalLayout();
        languageCapsulLayoutLeft.addClassName("language-Capsul-Layout-Left");

        Span language = new Span(s.getName());
        language.addClassName("language-Capsul-Text");
        Span languageLevel = new Span(s.getLevel());
        languageLevel.addClassName("language-Capsul-Text");

        languageCapsulLayoutLeft.add(language);
        languageCapsulLayoutLeft.add(languageLevel);
        languageCapsulLayout.add(languageCapsulLayoutLeft);
        languageCapsulLayout.add(delete);

        languageCapsulWithDelete.add(languageCapsulLayout);
        displayLanguage.add(languageCapsulWithDelete);

        return languageCapsulWithDelete;
    }

    private Button deleteLanguageButton(SpracheDTO s){
        deleteLanguageButton = new Button();
        deleteLanguageButton.setIcon(new Icon("lumo", "cross"));
        deleteLanguageButton.addClickListener(event -> {
            spracheList.remove(s);
            getLanguagesAndCreatefield(Globals.ProfileViewMode.EDIT);
        });
        return deleteLanguageButton;
    }
}
