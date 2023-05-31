package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Route(value = Globals.Pages.PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("Profil")
public class StudentProfileView extends Div implements HasUrlParameter<String> {

    private final StudentProfileControl studentProfileControl;

    private final UI ui = UI.getCurrent();

    private StudentProfileDTO studentProfileDTO;
    private H1 name = new H1();
    private H2 title = new H2();
    private Text description = new Text("");
    private Div profilePicture = new Div();


    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        try {
            studentProfileDTO = studentProfileControl.getStudentProfile(parameter);
            ui.access(() -> {
                add(createIntroductionLayout());
                add(createBottomLayout());
            });
        } catch (ProfileException e) {
            throw new RuntimeException(e);
        }
    }
    @Autowired
    public StudentProfileView(StudentProfileControl studentProfileControl){
        this.studentProfileControl = studentProfileControl;
        addClassName("profile-view");
    }


    private void updateProfilePicture(){
    }

    private HorizontalLayout createBottomLayout(){
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.addClassName("bottom");
        bottomLayout.add(createLeftLayout());
        bottomLayout.add(createQualifikationsLayout());
        return bottomLayout;
    }

    private VerticalLayout createLeftLayout(){
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassName("left");
        leftLayout.add(createAboutLayout());
        leftLayout.add(createKenntnisseLayout());
        leftLayout.add(createSprachenLayout());
        return leftLayout;
    }



    private HorizontalLayout createIntroductionLayout(){
        HorizontalLayout introductionLayout = new HorizontalLayout();
        introductionLayout.addClassName("introduction");
        introductionLayout.add(profilePicture);
        introductionLayout.add(createDescriptionLayout());
        return introductionLayout;
    }

    private VerticalLayout createDescriptionLayout(){
        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.addClassName("description");
        name = new H1(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
        title = new H2(studentProfileDTO.getStudiengang());
        description = new Text(studentProfileDTO.getBeschreibung());
        descriptionLayout.add(name);
        descriptionLayout.add(title);
        descriptionLayout.add(description);
        return descriptionLayout;
    }

    private VerticalLayout createAboutLayout(){
        VerticalLayout aboutLayout = new VerticalLayout();
        aboutLayout.addClassName("about");
        aboutLayout.add(new H2("Über mich"));
        aboutLayout.add(createIconTextLayout(new Icon(VaadinIcon.CALENDAR_O), studentProfileDTO.getGeburtsdatum().toString()));
        aboutLayout.add(createIconTextLayout(new Icon(VaadinIcon.PHONE), studentProfileDTO.getTelefonnummer()));
        aboutLayout.add(createIconTextLayout(new Icon(VaadinIcon.ENVELOPE_O), studentProfileDTO.getEmail()));
        aboutLayout.add(createInteressenLayout());
        return aboutLayout;
    }

    private HorizontalLayout createInteressenLayout(){
        HorizontalLayout interessenLayout = new HorizontalLayout();
        interessenLayout.addClassName("interessen");
        for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : studentProfileDTO.getTaetigkeitsfelder()){
            interessenLayout.add(new Label(taetigkeitsfeldDTO.getName()));
        }
        return interessenLayout;
    }

    private HorizontalLayout createIconTextLayout(Icon icon, String text){
        HorizontalLayout iconTextLayout = new HorizontalLayout();
        iconTextLayout.addClassName("icon-text");
        iconTextLayout.add(icon);
        iconTextLayout.add(new Label(text));
        return iconTextLayout;
    }

    private VerticalLayout createQualifikationsLayout(){
        VerticalLayout qualifikationsLayout = new VerticalLayout();
        qualifikationsLayout.addClassName("qualifikationen");
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
        vonBisLayout.add(new Label(von));
        vonBisLayout.add(new Label(bis));
        return vonBisLayout;
    }

    private VerticalLayout createKenntnisseLayout(){
        VerticalLayout kenntnisseLayout = new VerticalLayout();
        kenntnisseLayout.addClassName("kenntnisse");
        kenntnisseLayout.add(new H2("Kenntnisse"));
        for (KenntnisDTO kenntnis : studentProfileDTO.getKenntnisse()){
            kenntnisseLayout.add(createKenntnis(kenntnis.getName()));
        }
        return kenntnisseLayout;
    }

    private Div createKenntnis(String kenntnis){
        Div kenntnisDiv = new Div();
        kenntnisDiv.addClassName("kenntnis");
        kenntnisDiv.add(new Label(kenntnis));
        return kenntnisDiv;
    }

    private VerticalLayout createSprachenLayout(){
        VerticalLayout sprachenLayout = new VerticalLayout();
        sprachenLayout.addClassName("sprachen");
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


    // TEST TEST

}
