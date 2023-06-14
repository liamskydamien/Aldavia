package org.hbrs.se2.project.aldavia.views.components;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.service.*;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CssImport("./styles/views/profile/studentProfile.css")
public class SkillsComponent extends VerticalLayout implements ProfileComponent{


    private final StudentProfileControl studentProfileControl;

    private final StudentProfileDTO studentProfileDTO;

    private List<KenntnisDTO> kenntnisDTOS;

    private final Div displaySkill;
    private final HorizontalLayout addSkillsArea;
    private Span noSkill;
    private Button deleteSkill;
    private Map<KenntnisDTO, TextField> skillFields = new HashMap<>();


    public SkillsComponent(StudentProfileDTO studentProfileDTO, StudentProfileControl studentProfileControl ) {
        this.studentProfileDTO = studentProfileDTO;
        kenntnisDTOS = studentProfileDTO.getKenntnisse();
        this.studentProfileControl = studentProfileControl;
        displaySkill = new Div();
        addSkillsArea = new HorizontalLayout();
        deleteSkill = new Button(new Icon("lumo","cross"));
        noSkill = new Span("Es wurden noch keine Kenntnisse hinzugefügt.");
        addClassName("skills-component");
        addClassName("card");
        setUpUI();
    }

    //TODO: Refaktoring Header addskillarea in eine eigene Methode
    private void setUpUI(){
        displaySkill.setClassName("display-Skill");
        deleteSkill.setClassName("deleteButton");

        add(new H2("Kenntnisse"));

        addSkillsArea.addClassName("add-skills-area");
        add(addSkillsArea);
        add(displaySkill);

        //Header
        TextField addSkillField = new TextField();
        addSkillField.addClassName("add-Skill-Field");
        addSkillField.setPlaceholder("Füge neue Kenntnisse hinzu.");
        addSkillField.setClearButtonVisible(true);
        addSkillsArea.add(addSkillField);

        addSkillsArea.add(addSkillButton(addSkillField));


        updateViewMode();
    }

    private void updateViewMode(){
        addSkillsArea.setVisible(false);

        if(kenntnisDTOS.isEmpty()){
            noSkill.getStyle().set("font-style", "italic");
            displaySkill.add(noSkill);
        } else {
            getSkillsAndCreatefield(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEditMode(){

        if(kenntnisDTOS.isEmpty()){
            noSkill.getStyle().set("font-style", "italic");
            displaySkill.add(noSkill);
        }

        addSkillsArea.setVisible(true);

        getSkillsAndCreatefield(Globals.ProfileViewMode.EDIT);


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
        studentProfileDTO.setKenntnisse(kenntnisDTOS);
        studentProfileControl.updateStudentProfile(studentProfileDTO,userName);
    }

    private void getSkillsAndCreatefield(String mode){
        // Clear any existing fields before re-creating them
        displaySkill.removeAll();
        skillFields.clear();

        for(KenntnisDTO k: kenntnisDTOS){
            TextField skill = new TextField();
            skill.setValue(k.getName());
            skill.addClassName("Skill");

            // Store this TextField in the map
            skillFields.put(k, skill);

            if (mode.equals("edit")) {
                skill.setSuffixComponent(deleteSkillButton(k));
                skill.setReadOnly(false);
            } else if (mode.equals("view")) {
                skill.setReadOnly(true);
            }

            displaySkill.add(skill);
        }
    }

    private Button deleteSkillButton(KenntnisDTO kenntnis){
        deleteSkill.addClickListener(buttonClickEvent -> {
            // Remove the KenntnisDTO from the list
            kenntnisDTOS.remove(kenntnis);

            // Also remove the associated TextField from the display and from the map
            TextField skillField = skillFields.get(kenntnis);
            displaySkill.remove(skillField);
            skillFields.remove(kenntnis);
        });
        return deleteSkill;
    }

    private Button addSkillButton(TextField addSkillTextField){
        if(kenntnisDTOS.isEmpty()){
            displaySkill.remove(noSkill);
        }

        Button addSkillButton = new Button();
        addSkillButton.setIcon(new Icon("lumo","plus"));

        addSkillButton.addClickListener(buttonClickEvent ->{
            if(kenntnisDTOS.size()<11) {
                if (!addSkillTextField.getValue().isEmpty()) {
                    KenntnisDTO newSkill = new KenntnisDTO(addSkillTextField.getValue());
                    kenntnisDTOS.add(newSkill);
                    getSkillsAndCreatefield("edit");
                    addSkillTextField.clear();
                }
            } else {
                Notification.show("Du hast die maximale Kenntnis-Anzahl erreicht.", 20, Notification.Position.MIDDLE);
            }

        });
        return addSkillButton;
    }
}
