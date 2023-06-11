package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;

import java.util.Iterator;
import java.util.List;

@CssImport("./styles/views/profile/studentProfile.css")
public class SkillsComponent extends VerticalLayout implements ProfileComponent{

    private final StudentProfileControl studentProfileControl;

    private final StudentProfileDTO studentProfileDTO;

    private List<KenntnisDTO> kenntnisDTOS;

    private final Div displaySkill = new Div();
    private final HorizontalLayout addSkillsArea = new HorizontalLayout();
    Span noSkill = new Span("Es wurden noch keine Kenntnisse hinzugefügt.");


    public SkillsComponent(StudentProfileDTO studentProfileDTO) {
        this.studentProfileDTO = studentProfileDTO;
        kenntnisDTOS = studentProfileDTO.getKenntnisse();
        studentProfileControl = new StudentProfileControl();
        addClassName("skills-component");
        addClassName("card");
        setUpUI();
    }
    private void setUpUI(){
        displaySkill.setClassName("display-Skill");

        add(new H2("Kenntnisse"));

        addSkillsArea.addClassName("add-skills-area");
        add(addSkillsArea);
        add(displaySkill);

        updateViewMode();
    }

    private void updateViewMode(){

        if(kenntnisDTOS.isEmpty()){
            noSkill.getStyle().set("font-style", "italic");
            displaySkill.add(noSkill);
        } else {
            getSkillsAndCreatefield("view");
        }

    }

    private void updateEditMode(){

        if(kenntnisDTOS.isEmpty()){
            noSkill.getStyle().set("font-style", "italic");
            displaySkill.add(noSkill);
        }

            TextField addSkillField = new TextField();
            addSkillField.addClassName("add-Skill-Field");
            addSkillField.setPlaceholder("Füge neue Kenntnisse hinzu.");
            addSkillField.setClearButtonVisible(true);
            addSkillsArea.add(addSkillField);

            addSkillsArea.add(addSkillButton(addSkillField));


            getSkillsAndCreatefield("edit");


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
        if(!studentProfileDTO.getKenntnisse().isEmpty()){
            if(mode.equals("view")){
                for(KenntnisDTO k: kenntnisDTOS){
                    String bezeichnung = k.getName();
                    TextField skill = new TextField();
                    skill.setValue(bezeichnung);
                    skill.addClassName("Skill");
                    skill.setRequiredIndicatorVisible(true);
                    displaySkill.add(skill);
                }
            } else if (mode.equals("edit")) {
                for(KenntnisDTO k: kenntnisDTOS){
                    String bezeichnung = k.getName();
                    TextField skill = new TextField();
                    skill.setValue(bezeichnung);
                    skill.addClassName("Skill");
                    skill.setSuffixComponent(deleteSkillButton(bezeichnung));
                    displaySkill.add(skill);
                }
            }

        }

    }

    private Button deleteSkillButton(String name){
        Button deleteSkill = new Button(new Icon("lumo","cross"));
        deleteSkill.addClickListener(buttonClickEvent -> {
            Iterator<KenntnisDTO> iterator = kenntnisDTOS.iterator();
            while (iterator.hasNext()) {
                KenntnisDTO k = iterator.next();
                if(k.getName().equals(name)) {
                    iterator.remove();
                    break;  // stop iterating once we've removed the item
                }
            }
            // Now you need to clear the display and regenerate it, because you've changed the underlying data.
            displaySkill.removeAll();
            getSkillsAndCreatefield("edit");
        });
        return deleteSkill;
    }

    private Button addSkillButton(TextField addSkillTextField){
        if(kenntnisDTOS.isEmpty()){
            displaySkill.remove(noSkill);
        }
        //TODO Icon anstatt "Hinzufuegen"
        Button addSkillButton = new Button("Hinzufügen");

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
