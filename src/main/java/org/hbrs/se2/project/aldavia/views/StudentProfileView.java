package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.components.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("Profil")
@CssImport("./styles/views/profile/studentProfile.css")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    private final StudentProfileControl studentProfileControl;

    private final UI ui = UI.getCurrent();

    private StudentProfileDTO studentProfileDTO;
    private Div profileWrapper = null;

    private StudentPersonalDetailsComponent studentPersonalDetailsComponent;
    private AboutStudentComponent aboutStudentComponent;
    private SkillsComponent skillsComponent;
    private LanguageComponent languageComponent;
    private QualificationComponent qualificationComponent;

    private Button editButton;
    private Button saveButton;

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        try {
            studentProfileDTO = studentProfileControl.getStudentProfile(parameter);
            ui.access(() -> {

                if (profileWrapper == null) {
                    studentPersonalDetailsComponent = new StudentPersonalDetailsComponent(studentProfileDTO,studentProfileControl);

                    profileWrapper = new Div();
                    profileWrapper.addClassName("profile-wrapper");
                    profileWrapper.add(studentPersonalDetailsComponent);
                    profileWrapper.add(createBottomLayout());
                    add(profileWrapper);
                }
            });
        } catch (ProfileException e) {
            throw new RuntimeException(e);
        }
    }
    @Autowired
    public StudentProfileView(StudentProfileControl studentProfileControl){
        this.studentProfileControl = studentProfileControl;
        addClassName("profile-view");

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.addClassName("topButtons");
        topLayout.setWidthFull();
        topLayout.setJustifyContentMode(JustifyContentMode.END);
        editButton = new Button("Bearbeiten", event -> switchToEditMode());
        saveButton = new Button("Speichern", event -> {
            try {
                switchToViewMode();
            } catch (PersistenceException | ProfileException e) {
                throw new RuntimeException(e);
            }
        });
        editButton.setVisible(true);
        saveButton.setVisible(false);
        editButton.addClassName("editSaveButton");
        saveButton.addClassName("editSaveButton");
        topLayout.add(editButton);
        topLayout.add(saveButton);
        add(topLayout);

    }

    private void switchToEditMode(){
        editButton.setVisible(false);
        saveButton.setVisible(true);
        studentPersonalDetailsComponent.switchEditMode();
        aboutStudentComponent.switchEditMode();
        skillsComponent.switchEditMode();
        languageComponent.switchEditMode();
        qualificationComponent.switchEditMode();

    }

    private void switchToViewMode() throws PersistenceException, ProfileException {
        editButton.setVisible(true);
        saveButton.setVisible(false);

        studentPersonalDetailsComponent.switchViewMode(getCurrentUserName());
        aboutStudentComponent.switchViewMode(getCurrentUserName());
        skillsComponent.switchViewMode(getCurrentUserName());
        languageComponent.switchViewMode(getCurrentUserName());
        qualificationComponent.switchViewMode(getCurrentUserName());
    }

    private HorizontalLayout createBottomLayout(){
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.addClassName("bottom");
        bottomLayout.add(createLeftLayout());
        bottomLayout.add(createQualifikationsLayout());
        return bottomLayout;
    }

    private VerticalLayout createLeftLayout(){
        aboutStudentComponent = new AboutStudentComponent(studentProfileDTO,studentProfileControl);
        skillsComponent = new SkillsComponent(studentProfileDTO,studentProfileControl);
        languageComponent = new LanguageComponent(studentProfileDTO,studentProfileControl);
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassName("left");
        leftLayout.add(aboutStudentComponent);
        leftLayout.add(skillsComponent);
        leftLayout.add(languageComponent);
        return leftLayout;
    }



    /*private HorizontalLayout createInteressenLayout(){
        HorizontalLayout interessenLayout = new HorizontalLayout();
        interessenLayout.addClassName("interessen");
        for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : studentProfileDTO.getTaetigkeitsfelder()){
            interessenLayout.add(new Label(taetigkeitsfeldDTO.getName()));
        }
        return interessenLayout;
    }*/


    private VerticalLayout createQualifikationsLayout(){
        VerticalLayout qualifikationsLayout = new VerticalLayout();
        qualifikationsLayout.addClassName("qualifikationenRight");
        qualificationComponent = new QualificationComponent(studentProfileControl,studentProfileDTO);
        qualifikationsLayout.add(qualificationComponent);
        return qualifikationsLayout;
    }


}
