package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.LoggedInStateLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Route(value = Globals.Pages.STELLENANZEIGE_BEWERBUNGEN_VIEW, layout = LoggedInStateLayout.class)
public class ShowBewerbungOfStellenanzeigeComponent extends VerticalLayout implements HasUrlParameter<String> {
    private UnternehmenProfileControl unternehmenProfileControl;

    private UnternehmenProfileDTO unternehmenProfileDTO;
    private StudentProfileControl studentProfileControl;
    private StellenanzeigeDTO pickedStellenanzeige;
    private Div displayBewerbungen;
    private Span noBewerbungen;
    private List<BewerbungsDTO> bewerbungen;
    @SneakyThrows
    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        if (parameter != null) {
            unternehmenProfileDTO = unternehmenProfileControl.getUnternehmenProfileDTO(parameter);
            // Laden der Bewerbungen für die entsprechende Stellenanzeige
            Set<StellenanzeigeDTO> stellenanzeige = unternehmenProfileDTO.getStellenanzeigen();
            for(StellenanzeigeDTO s : stellenanzeige){
                if(s.getId() == Long.parseLong(parameter)){
                    this.pickedStellenanzeige = s;
                }
            }
            if(pickedStellenanzeige != null){
                bewerbungen = pickedStellenanzeige.getBewerbungen();
            }
        }

    }
    public ShowBewerbungOfStellenanzeigeComponent(UnternehmenProfileControl unternehmenProfileControl, UnternehmenProfileDTO unternehmenProfileDTO, StudentProfileControl studentProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        this.studentProfileControl = studentProfileControl;
        noBewerbungen = new Span("Es gibt keine Bewerbungen für diese Stellenanzeige");
        displayBewerbungen = new Div();
        displayBewerbungen.addClassName("display-bewerbungen");
        setUpUi();
    }

    public void setUpUi(){
        H2 header = new H2("Bewerbungen für die Stellenanzeige: " + pickedStellenanzeige.getBezeichnung());
        this.add(header);
        this.add(createStellenanzeigeLayout());
        if(bewerbungen.size() == 0) {
            this.add(noBewerbungen);
        }else{
            for(BewerbungsDTO b : bewerbungen){
                displayBewerbungen.add(createBewerbungLayout(b));
            }
            this.add(displayBewerbungen);
        }

        HorizontalLayout zurueck = new HorizontalLayout();
        Button zurueckButton = new Button("Zurück zum Profil", event -> {
            UI.getCurrent().navigate(Globals.Pages.COMPANY_PROFILE_VIEW);
        });
        zurueck.add(zurueckButton);
        this.add(zurueck);
    }

    @SneakyThrows
    private HorizontalLayout createBewerbungLayout(BewerbungsDTO bewerbung) {
        HorizontalLayout bewerbungLayout = new HorizontalLayout();
        bewerbungLayout.addClassName("bewerbung-layout");
        bewerbungLayout.addClassName("card");
        bewerbungLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        bewerbungLayout.setWidthFull();

        VerticalLayout bewerbungInfoLeft = new VerticalLayout();
        bewerbungInfoLeft.addClassName("bewerbungInfoLeft");

        //Profile
        HorizontalLayout profile = new HorizontalLayout();
        profile.addClassName("profile");

        //Profilbild und Studentenname
        StudentProfileDTO student = getStudent(bewerbung.getStudent().getUsername());
        Image profileImg;
        if(student.getProfilbild() == null || student.getProfilbild().equals("")){
            profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
        } else {
            // lade das Profilbild aus studentProfileDTO.getProfilbild()
            String fileName = student.getProfilbild();
            String path = "./src/main/webapp/profile-images/" + fileName;
            StreamResource resource = new StreamResource(fileName, () -> {
                try {
                    return new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return InputStream.nullInputStream(); // In case of an error return an empty stream
                }
            });
            profileImg = new Image(resource, "Profilbild");
        }
        profileImg.addClassName("profileImg");
        profileImg.setWidth("50px");

        Span studentName = new Span(student.getVorname() + " " + student.getNachname());
        studentName.addClassName("studentName");

        profile.add(profileImg, studentName);

        //Bewerbung
        Span bewerbungsDatum = new Span("Bewerbungsdatum: " + bewerbung.getDatum().toString());
        bewerbungsDatum.addClassName("bewerbungsDatum");
        Span bewerbungsSchreiben = new Span(bewerbung.getBewerbungsSchreiben());
        bewerbungsSchreiben.addClassName("bewerbungsSchreiben");

        bewerbungInfoLeft.add(profile, bewerbungsDatum, bewerbungsSchreiben);

        //ButtonBereich
        VerticalLayout buttonBereich = new VerticalLayout();
        buttonBereich.addClassName("buttonBereich");
        buttonBereich.setAlignItems(Alignment.CENTER);
        buttonBereich.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonBereich.add(profilAnsehenButton(student.getUsername()));

        bewerbungLayout.add(bewerbungInfoLeft, buttonBereich);
        return bewerbungLayout;

    }

    public VerticalLayout createStellenanzeigeLayout(){
        VerticalLayout stellenanzeigeLayout = new VerticalLayout();
        stellenanzeigeLayout.addClassName("stellenanzeige-layout");

        HorizontalLayout stellenanzeigeInfo = new HorizontalLayout();
        stellenanzeigeInfo.addClassName("stellenanzeigeInfo");
        stellenanzeigeInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        stellenanzeigeInfo.setWidthFull();

        VerticalLayout stellenanzeigeInfoLeft = new VerticalLayout();
        stellenanzeigeInfoLeft.addClassName("stellenanzeigeInfoLeft");
        Span stellenanzeigeTitel = new Span(pickedStellenanzeige.getBezeichnung());
        stellenanzeigeTitel.addClassName("stellenanzeigeTitel");
        Span stellenanzeigeBeschaeftigungsverheltnis = new Span(pickedStellenanzeige.getBeschaeftigungsverhaeltnis());
        stellenanzeigeBeschaeftigungsverheltnis.addClassName("stellenanzeigeBeschaeftigungsverheltnis");
        stellenanzeigeInfoLeft.add(stellenanzeigeTitel, stellenanzeigeBeschaeftigungsverheltnis, renderTaetigkeit(pickedStellenanzeige.getTaetigkeitsfelder()));

        VerticalLayout stellenanzeigeInfoRight = new VerticalLayout();
        stellenanzeigeInfoRight.addClassName("stellenanzeigeInfoRight");
        Span stellenanzeigeDatum = new Span(pickedStellenanzeige.getErstellungsdatum().toString());
        stellenanzeigeDatum.addClassName("stellenanzeigeDatum");
        stellenanzeigeInfoRight.add(stellenanzeigeDatum);


        stellenanzeigeInfo.add(stellenanzeigeInfoLeft, stellenanzeigeInfoRight);
        stellenanzeigeLayout.add(stellenanzeigeInfo);
        return stellenanzeigeLayout;
    }

    private HorizontalLayout renderTaetigkeit(List<TaetigkeitsfeldDTO> taetigkeitsfeldListe){
        HorizontalLayout taetigkeitenLayout = new HorizontalLayout();
        taetigkeitenLayout.addClassName("kenntnisseLayout");
        if(taetigkeitsfeldListe.size()>3){
            for (int i = 0; i < 3; i++){
                TaetigkeitsfeldDTO pickedTaetigkeitsfeld = taetigkeitsfeldListe.get(i);
                Span taetigkeitCapsul = new Span(pickedTaetigkeitsfeld.getName());
                taetigkeitCapsul.getElement().getThemeList().add("badge pill");
                taetigkeitCapsul.addClassName("stellenanzeige-taetigkeit");
                taetigkeitCapsul.getElement().getThemeList().add("badge pill");
                taetigkeitenLayout.add(taetigkeitCapsul);
            }
            Span restlicheTaetigkeiten = new Span("+" + (taetigkeitsfeldListe.size()-3) + " weitere");
            restlicheTaetigkeiten.addClassName("stellenanzeige-taetigkeit");
            taetigkeitenLayout.add(restlicheTaetigkeiten);
        } else {
            for (TaetigkeitsfeldDTO taetigkeitsfeld : taetigkeitsfeldListe){
                Span taetigkeitCapsul = new Span(taetigkeitsfeld.getName());
                taetigkeitCapsul.addClassName("stellenanzeige-taetigkeit");
                taetigkeitCapsul.getElement().getThemeList().add("badge pill");
                taetigkeitenLayout.add(taetigkeitCapsul);
            }
        }

        return taetigkeitenLayout;
    }

    private StudentProfileDTO getStudent(String id) throws ProfileException {
        return studentProfileControl.getStudentProfile(id);
    }

    private Anchor profilAnsehenButton(String id){
        Button profilAnsehenButton = new Button("Profil ansehen");
        Anchor anchor = new Anchor(Globals.Pages.PROFILE_VIEW + "/" + id, profilAnsehenButton);
        anchor.setTarget("_blank");
        return anchor;
    }

}
