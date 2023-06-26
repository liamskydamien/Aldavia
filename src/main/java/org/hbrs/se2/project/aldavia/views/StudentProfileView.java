package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.components.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("Profil")
@CssImport("./styles/views/profile/studentProfile.css")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    private final StudentProfileControl studentProfileControl;

    private final UI ui = UI.getCurrent();

    private StudentProfileDTO studentProfileDTO;
    private Div profileWrapper = null;

    private PersonalProfileDetailsComponent studentPersonalDetailsComponent;
    private AboutStudentComponent aboutStudentComponent;
    private SkillsComponent skillsComponent;
    private LanguageComponent languageComponent;
    private QualificationComponent qualificationComponent;
    private EditAndSaveProfileButton editAndSaveProfileButton;
    private InterestComponent interestComponent;
    private String url;
    private boolean isUser = true;

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        try {
            studentProfileDTO = studentProfileControl.getStudentProfile(parameter);
            url = event.getLocation().getPath();

            if(!Objects.equals(getCurrentUserName(), parameter)){
                isUser = false;
            } else {
                isUser = true;
            }

            if(isUser){
                editAndSaveProfileButton.addListenerToEditButton(e -> {
                    switchToEditMode();
                });
                editAndSaveProfileButton.addListenerToSaveButton(e -> {
                    try {
                        switchToViewMode();
                    } catch (PersistenceException | ProfileException persistenceException) {
                        persistenceException.printStackTrace();
                    }
                });

                editAndSaveProfileButton.setEditButtonVisible(true);
                editAndSaveProfileButton.setSaveButtonVisible(false);

                add(editAndSaveProfileButton);
            }


            ui.access(() -> {

                if (profileWrapper == null) {
                    studentPersonalDetailsComponent = new PersonalProfileDetailsComponent(studentProfileDTO,studentProfileControl,url);
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
        editAndSaveProfileButton = new EditAndSaveProfileButton();
        addClassName("profile-view");

    }

    private void switchToEditMode(){
        editAndSaveProfileButton.setEditButtonVisible(false);
        editAndSaveProfileButton.setSaveButtonVisible(true);

        studentPersonalDetailsComponent.switchEditMode();
        aboutStudentComponent.switchEditMode();
        skillsComponent.switchEditMode();
        languageComponent.switchEditMode();
        qualificationComponent.switchEditMode();
        interestComponent.switchEditMode();

    }

    private void switchToViewMode() throws PersistenceException, ProfileException {
        editAndSaveProfileButton.setEditButtonVisible(true);
        editAndSaveProfileButton.setSaveButtonVisible(false);

        studentPersonalDetailsComponent.switchViewMode(getCurrentUserName());
        aboutStudentComponent.switchViewMode(getCurrentUserName());
        skillsComponent.switchViewMode(getCurrentUserName());
        languageComponent.switchViewMode(getCurrentUserName());
        qualificationComponent.switchViewMode(getCurrentUserName());
        interestComponent.switchViewMode(getCurrentUserName());
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
        interestComponent = new InterestComponent(studentProfileDTO,studentProfileControl);
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassName("left");
        leftLayout.add(aboutStudentComponent);
        leftLayout.add(skillsComponent);
        leftLayout.add(languageComponent);
        leftLayout.add(interestComponent);
        return leftLayout;
    }


    private VerticalLayout createQualifikationsLayout(){
        VerticalLayout qualifikationsLayout = new VerticalLayout();
        qualifikationsLayout.addClassName("qualifikationenRight");
        qualificationComponent = new QualificationComponent(studentProfileControl,studentProfileDTO);
        qualifikationsLayout.add(qualificationComponent);
        return qualifikationsLayout;
    }


}
