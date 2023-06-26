package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
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
@CssImport("./styles/views/bewerbungsOverview/bewerbungs-overview-unternehmen-view.css")
public class BewerbungsOverviewUnternehmenView extends Div {
    private final BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen;
    private final UserDTO currentUser;
    private final String BESCHREIBUNG = "beschreibung";

    @Autowired
    public BewerbungsOverviewUnternehmenView(BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen) {
        this.bewerbungsOverviewUnternehmen = bewerbungsOverviewUnternehmen;
        currentUser = getCurrentUser();
        addClassName("bewerbungs-overview-unternehmen-view");
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

    private Div createStellenanzeigenOverviewElement(StellenanzeigenDataDTO stellenanzeigenDataDTO){
        Div divStellenanzeigeElement = new Div();
        divStellenanzeigeElement.addClassName("stellenanzeige-element");
        Accordion accordion = new Accordion();
        StellenanzeigeDTO stellenanzeigeDTO = stellenanzeigenDataDTO.getStellenanzeige();
        accordion.add(stellenanzeigeDTO.getBezeichnung(), createStellenAnzeigeLayout(stellenanzeigeDTO));
        accordion.add("Bewerbungen", createBewerbungenLayout(stellenanzeigenDataDTO));
        divStellenanzeigeElement.add(accordion);
        return divStellenanzeigeElement;
    }

    private VerticalLayout createStellenAnzeigeLayout(StellenanzeigeDTO stellenanzeigeDTO){
        VerticalLayout layout = new VerticalLayout();
        Div beschreibung = new Div();
        beschreibung.setText(stellenanzeigeDTO.getBeschreibung());
        beschreibung.addClassName(BESCHREIBUNG);
        layout.add(beschreibung);
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
        if(stellenanzeigenDataDTO.getBewerbungen().isEmpty()){
            layout.add(new Label("Keine Bewerbungen vorhanden."));
            return layout;
        }
        for (BewerbungsDTO bewerbungsDTO : stellenanzeigenDataDTO.getBewerbungen()){
            layout.add(createBewerbungenElement(bewerbungsDTO));
        }
        return layout;
    }

    private Div createBewerbungenElement(BewerbungsDTO bewerbungsDTO){
        Div div = new Div();
        StudentProfileDTO studentProfileDTO = bewerbungsDTO.getStudent();
        div.add(new H2(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname()));
        Div bewerbungsSchreiben = new Div();
        bewerbungsSchreiben.setText(bewerbungsDTO.getBewerbungsSchreiben());
        bewerbungsSchreiben.addClassName(BESCHREIBUNG);
        div.add(bewerbungsSchreiben);
        div.add(createBewerbungsInformationen(bewerbungsDTO));
        div.add(createButtonLink(studentProfileDTO));
        return div;
    }

    private Button createButtonLink(StudentProfileDTO studentProfileDTO){
        Button button = new Button("Profil ansehen");
        button.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.PROFILE_VIEW +"/"+ studentProfileDTO.getUsername()));
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
