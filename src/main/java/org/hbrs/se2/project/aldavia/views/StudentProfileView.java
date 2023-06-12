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
import org.hbrs.se2.project.aldavia.views.components.AboutStudentComponent;
import org.hbrs.se2.project.aldavia.views.components.SkillsComponent;
import org.hbrs.se2.project.aldavia.views.components.StudentPersonalDetailsComponent;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("Profil")
@CssImport("./styles/views/profile/studentProfile.css")
public class StudentProfileView extends VerticalLayout implements HasUrlParameter<String> {

    private final StudentProfileControl studentProfileControl;

    private final UI ui = UI.getCurrent();

    private StudentProfileDTO studentProfileDTO;
    private Div profilePicture = new Div();
    private Div profileWrapper = null;

    private StudentPersonalDetailsComponent studentPersonalDetailsComponent;
    private AboutStudentComponent aboutStudentComponent;
    private SkillsComponent skillsComponent;

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

    }

    private void switchToViewMode() throws PersistenceException, ProfileException {
        editButton.setVisible(true);
        saveButton.setVisible(false);

        studentPersonalDetailsComponent.switchViewMode(getCurrentUserName());
        aboutStudentComponent.switchViewMode(getCurrentUserName());
        skillsComponent.switchViewMode(getCurrentUserName());
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
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassName("left");
        leftLayout.add(aboutStudentComponent);
        leftLayout.add(skillsComponent);
        leftLayout.add(createSprachenLayout());
        return leftLayout;
    }



    private HorizontalLayout createInteressenLayout(){
        HorizontalLayout interessenLayout = new HorizontalLayout();
        interessenLayout.addClassName("interessen");
        for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : studentProfileDTO.getTaetigkeitsfelder()){
            interessenLayout.add(new Label(taetigkeitsfeldDTO.getName()));
        }
        return interessenLayout;
    }


    private VerticalLayout createQualifikationsLayout(){
        VerticalLayout qualifikationsLayout = new VerticalLayout();
        qualifikationsLayout.addClassName("qualifikationen");
        qualifikationsLayout.addClassName("card");
        qualifikationsLayout.add(new H2("Qualifikationen"));
        for (QualifikationsDTO qualifikationsDTO : studentProfileDTO.getQualifikationen()){
            qualifikationsLayout.add(createQualifikation(qualifikationsDTO));
        }
        return qualifikationsLayout;
    }

    private VerticalLayout createQualifikation(QualifikationsDTO qualifikationsDTO){
        VerticalLayout qualifikationLayout = new VerticalLayout();
        qualifikationLayout.addClassName("qualifikation");
        qualifikationLayout.add(new H3(qualifikationsDTO.getBezeichnung()));
        qualifikationLayout.add(createInstitutionBeschaeftigungsArt(qualifikationsDTO.getInstitution(), qualifikationsDTO.getBeschaeftigungsart()));
        qualifikationLayout.add(createVonBis(qualifikationsDTO.getVon().toString(), qualifikationsDTO.getBis().toString()));
        qualifikationLayout.add(new Label(qualifikationsDTO.getBeschreibung()));
        return qualifikationLayout;
    }

    private HorizontalLayout createInstitutionBeschaeftigungsArt(String institution, String beschaeftigungsart){
        HorizontalLayout institutionBeschaeftigungsArtLayout = new HorizontalLayout();
        institutionBeschaeftigungsArtLayout.addClassName("institution-beschaeftigungsart");
        institutionBeschaeftigungsArtLayout.add(new Label(institution));
        institutionBeschaeftigungsArtLayout.add(new Label(beschaeftigungsart));
        return institutionBeschaeftigungsArtLayout;
    }

    private HorizontalLayout createVonBis(String von, String bis){
        HorizontalLayout vonBisLayout = new HorizontalLayout();
        vonBisLayout.addClassName("von-bis");
        vonBisLayout.add(new Label("Von: " + von));
        vonBisLayout.add(new Label("Bis: " + bis));
        return vonBisLayout;
    }


    private VerticalLayout createSprachenLayout(){
        VerticalLayout sprachenLayout = new VerticalLayout();
        sprachenLayout.addClassName("sprachen");
        sprachenLayout.addClassName("card");
        sprachenLayout.add(new H2("Sprachen"));
        for (SpracheDTO sprache : studentProfileDTO.getSprachen()){
            sprachenLayout.add(createSprache(sprache));
        }
        return sprachenLayout;
    }

    private Div createSprache(SpracheDTO sprache){
        Div spracheDiv = new Div();
        spracheDiv.addClassName("sprache");
        spracheDiv.add(new H3(sprache.getName()));
        spracheDiv.add(new Label(sprache.getLevel()));
        return spracheDiv;
    }



}
