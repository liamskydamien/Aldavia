package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.List;

public class LanguageComponent extends VerticalLayout implements ProfileComponent{
    private StudentProfileControl studentProfileControl;
    private StudentProfileDTO studentProfileDTO;
    private VerticalLayout addLanguageArea;
    private List<SpracheDTO> spracheList;

    public LanguageComponent(StudentProfileControl studentProfileControl, StudentProfileDTO studentProfileDTO) {
        this.studentProfileControl = studentProfileControl;
        this.studentProfileDTO = studentProfileDTO;
        spracheList = studentProfileDTO.getSprachen();
        setUpUI();
    }
    private void setUpUI() {
        H2 title = new H2("Sprachen");
        add(title);
        add(createAddLanguageArea());
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

    }
    private void updateEditMode() {
    }
    private void updateProfileDTO(String userName) throws PersistenceException, ProfileException {

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


        return addLanguageArea;
    }

    private Button addLanguageButton(TextField addLanguageField, ComboBox<String> languageLevelDropDown) {
        Button addLanguageButton = new Button("Hinzufügen");
        addLanguageButton.setIcon(new Icon("lumo", "plus"));
        addLanguageButton.addClickListener(event -> {
            if(spracheList.size()<11) {
                if (!addLanguageField.getValue().isEmpty()) {
                    SpracheDTO newSkill = new SpracheDTO(addLanguageField.getValue(),languageLevelDropDown.getValue(),);
                    spracheList.add(newSkill);
                    getSkillsAndCreatefield("edit");
                    languageLevelDropDown.clear();
                }
            } else {
                Notification.show("Du hast die maximale Kenntnis-Anzahl erreicht.", 20, Notification.Position.MIDDLE);
            }
        });
        return addLanguageButton;
    }
}
