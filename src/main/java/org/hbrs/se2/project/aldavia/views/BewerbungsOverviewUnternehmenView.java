package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewUnternehmen;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = Globals.Pages.STELLENANZEIGE_BEWERBUNGEN_VIEW, layout = LoggedInStateLayout.class)
public class BewerbungsOverviewUnternehmenView extends Div {
    private final BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen;
    private final UserDTO currentUser;

    @Autowired
    public BewerbungsOverviewUnternehmenView(BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen) {
        this.bewerbungsOverviewUnternehmen = bewerbungsOverviewUnternehmen;
        currentUser = getCurrentUser();
        setUpUI();
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private void setUpUI() {
        add(new H1("Bewerbungen auf Ihre Stellenanzeigen"));
        add(createBewerbungenLayout());
    }

    private VerticalLayout createBewerbungenLayout(){
        try {
            List<StellenanzeigenDataDTO> stellenanzeigenDataDTOS = bewerbungsOverviewUnternehmen.getBewerbungenStellenanzeige(currentUser.getUserid());
            VerticalLayout layout = new VerticalLayout();
            layout.setClassName("header-neutral");
            for (StellenanzeigenDataDTO stellenanzeigenDataDTO : stellenanzeigenDataDTOS) {
                layout.add(createStellenanzeigenOverviewElement(stellenanzeigenDataDTO));
            }
            return layout;
        }
        catch (ProfileException e){
            Notification.show("Fehler beim Laden der Bewerbungen.");
            return new VerticalLayout();
        }
    }

    private Accordion createStellenanzeigenOverviewElement(StellenanzeigenDataDTO stellenanzeigenDataDTO){
        Accordion accordion = new Accordion();
        StellenanzeigeDTO stellenanzeigeDTO = stellenanzeigenDataDTO.getStellenanzeige();
        accordion.add(stellenanzeigeDTO.getBezeichnung(), createStellenAnzeigeLayout(stellenanzeigeDTO));
        accordion.add("Bewerbungen", createBewerbungenLayout(stellenanzeigenDataDTO));
        return accordion;
    }

    private VerticalLayout createStellenAnzeigeLayout(StellenanzeigeDTO stellenanzeigeDTO){
        VerticalLayout layout = new VerticalLayout();
        layout.add(new Label(stellenanzeigeDTO.getBeschreibung()));
        layout.add(new Label(stellenanzeigeDTO.getBeschaeftigungsverhaeltnis()));
        layout.add(new Label(stellenanzeigeDTO.getBeschaeftigungsumfang()));
        layout.add(new Label(stellenanzeigeDTO.getBezahlung()));
        layout.add(createVonBisLayout(stellenanzeigeDTO));
        return layout;
    }

    private HorizontalLayout createVonBisLayout(StellenanzeigeDTO stellenanzeigenDTO){
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(new Label("Von: " + stellenanzeigenDTO.getStart()));
        layout.add(new Label("Bis: " + stellenanzeigenDTO.getEnde()));
        return layout;
    }

    private VerticalLayout createBewerbungenLayout(StellenanzeigenDataDTO stellenanzeigenDataDTO){
        VerticalLayout layout = new VerticalLayout();
        for (BewerbungsDTO bewerbungsDTO : stellenanzeigenDataDTO.getBewerbungen()){
            layout.add(createBewerbungenElement(bewerbungsDTO));
        }
        return layout;
    }

    private Div createBewerbungenElement(BewerbungsDTO bewerbungsDTO){
        Div div = new Div();
        StudentProfileDTO studentProfileDTO = bewerbungsDTO.getStudent();
        div.add(new H2(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname()));
        div.add(new Label(bewerbungsDTO.getBewerbungsSchreiben()));
        div.add(createBewerbungsInformationen(bewerbungsDTO));
        div.add(createButtonLink(studentProfileDTO));
        return div;
    }

    private Button createButtonLink(StudentProfileDTO studentProfileDTO){
        Button button = new Button("Profil ansehen");
        button.addClickListener(e -> UI.getCurrent().navigate("studentProfile/" + studentProfileDTO.getUsername()));
        return button;
    }

    private VerticalLayout createBewerbungsInformationen(BewerbungsDTO bewerbungsDTO){
        VerticalLayout layout = new VerticalLayout();
        layout.add(new Label("E-Mail: " + bewerbungsDTO.getStudent().getEmail()));
        layout.add(new Label("Telefonnummer: " + bewerbungsDTO.getStudent().getTelefonnummer()));
        layout.add(new Label("Bewerbungsdatum: " + bewerbungsDTO.getDatum()));
        layout.add(new Label("Status: " + bewerbungsDTO.getStatus()));
        return layout;
    }

}
