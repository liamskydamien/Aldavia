package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
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
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestComponent extends VerticalLayout implements ProfileComponent {


    private final StudentProfileControl studentProfileControl;

    private final StudentProfileDTO studentProfileDTO;

    private final List<TaetigkeitsfeldDTO> taetigkeitsfelder;

    private final Div displayTaetigkeit;

    private final HorizontalLayout addTaetigkeitArea;
    private final Span noTaetigkeit;
    private final Map<TaetigkeitsfeldDTO, TextField> taetigkeitsFields = new HashMap<>();

    public InterestComponent(StudentProfileDTO studentProfileDTO, StudentProfileControl studentProfileControl ) {
        this.studentProfileDTO = studentProfileDTO;
        taetigkeitsfelder = studentProfileDTO.getTaetigkeitsfelder();
        this.studentProfileControl = studentProfileControl;
        displayTaetigkeit = new Div();
        addTaetigkeitArea = new HorizontalLayout();
        noTaetigkeit = new Span("Es wurden noch keine Kenntnisse hinzugefügt.");
        addClassName("skills-component");
        addClassName("card");

        setUpUI();
    }


    //TODO: Refaktoring Header addskillarea in eine eigene Methode
    private void setUpUI(){
        displayTaetigkeit.setClassName("display-Skill");

        add(new H2("Interessen"));

        addTaetigkeitArea.addClassName("add-skills-area");
        add(addTaetigkeitArea);
        add(displayTaetigkeit);

        //Header
        TextField addSkillField = new TextField();
        addSkillField.addClassName("add-Skill-Field");
        addSkillField.setPlaceholder("Neue Interessen");
        addSkillField.setClearButtonVisible(true);
        addTaetigkeitArea.add(addSkillField);

        addTaetigkeitArea.add(addTaetigkeitButton(addSkillField));


        updateViewMode();
    }

    private void updateViewMode(){
        addTaetigkeitArea.setVisible(false);

        if(taetigkeitsfelder.isEmpty()){
            noTaetigkeit.getStyle().set("font-style", "italic");
            displayTaetigkeit.add(noTaetigkeit);
        } else {
            getSkillsAndCreatefield(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEditMode(){

        if(taetigkeitsfelder.isEmpty()){
            noTaetigkeit.getStyle().set("font-style", "italic");
            displayTaetigkeit.add(noTaetigkeit);
        }

        addTaetigkeitArea.setVisible(true);

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
        studentProfileDTO.setTaetigkeitsfelder(taetigkeitsfelder);
        studentProfileControl.updateStudentProfile(studentProfileDTO,userName);
    }

    private void getSkillsAndCreatefield(String mode){
        // Clear any existing fields before re-creating them
        displayTaetigkeit.removeAll();
        taetigkeitsFields.clear();

        if(mode.equals(Globals.ProfileViewMode.EDIT)) {
            for (TaetigkeitsfeldDTO t : taetigkeitsfelder) {
                TextField skill = new TextField();
                skill.setValue(t.getName());
                skill.addClassName("Skill");

                // Store this TextField in the map
                taetigkeitsFields.put(t, skill);
                skill.setSuffixComponent(deleteTaetigkeitButton(t));
                skill.setReadOnly(false);
                displayTaetigkeit.add(skill);
            }
        } else if(mode.equals(Globals.ProfileViewMode.VIEW)) {
            for (TaetigkeitsfeldDTO t : taetigkeitsfelder) {
                Span skill = new Span(t.getName());
                skill.getElement().getThemeList().add("badge pill");
                displayTaetigkeit.add(skill);

        }



        }
    }

    private Button deleteTaetigkeitButton(TaetigkeitsfeldDTO taetigkeitsfeldDTO){
        Button deleteSkill = new Button(new Icon("lumo","cross"));
        deleteSkill.setClassName("deleteButton");

        deleteSkill.addClickListener(buttonClickEvent -> {
            // Remove the TaetigkeitsfeldDTO from the list
            taetigkeitsfelder.remove(taetigkeitsfeldDTO);

            // Also remove the associated TextField from the display and from the map
            TextField skillField = taetigkeitsFields.get(taetigkeitsfeldDTO);
            displayTaetigkeit.remove(skillField);
            taetigkeitsFields.remove(taetigkeitsfeldDTO);
        });
        return deleteSkill;
    }

    private Button addTaetigkeitButton(TextField addSkillTextField){
        if(taetigkeitsfelder.isEmpty()){
            displayTaetigkeit.remove(noTaetigkeit);
        }

        Button addSkillButton = new Button();
        addSkillButton.setIcon(new Icon("lumo","plus"));

        addSkillButton.addClickListener(buttonClickEvent ->{
            if(taetigkeitsfelder.size()<11) {
                if (!addSkillTextField.getValue().isEmpty()) {
                    TaetigkeitsfeldDTO newTaetigkeit = new TaetigkeitsfeldDTO(addSkillTextField.getValue());
                    taetigkeitsfelder.add(newTaetigkeit);
                    getSkillsAndCreatefield("edit");
                    addSkillTextField.clear();
                }
            } else {
                Notification.show("Du hast die maximale Interessen-Anzahl erreicht.", 20, Notification.Position.MIDDLE);
            }

        });
        return addSkillButton;
    }

}
