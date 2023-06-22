package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.components.AddStellenanzeigeFormComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUser;
import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.STELLENANZEIGE_ERSTELLEN_VIEW, layout = LoggedInStateLayout.class)
@CssImport("./styles/views/stellenanzeigeBewerbung/creatStellenanzeigeView.css")
public class CreateStellenanzeigeView extends VerticalLayout {
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private UnternehmenProfileControl unternehmenProfileControl;
    private AddStellenanzeigeFormComponent addStellenanzeigeFormComponent;
    private Set<Stellenanzeige> stellenanzeigeSet;

    @Autowired
    public CreateStellenanzeigeView(UnternehmenProfileControl unternehmenProfileControl) throws ProfileException {
        this.unternehmenProfileControl = unternehmenProfileControl;
        addStellenanzeigeFormComponent = new AddStellenanzeigeFormComponent();
        this.setAlignItems(Alignment.CENTER);

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidthFull();
        titleLayout.setJustifyContentMode(JustifyContentMode.START);
        H2 title = new H2("Stellenanzeige erstellen");
        titleLayout.add(title);

        add(titleLayout);
        add(addStellenanzeigeFormComponent);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(saveStellenanzeigeButton());
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonLayout.setWidthFull();
        add(buttonLayout);

        String companyId = getCurrentUser().getUserid();
        unternehmenProfileDTO = unternehmenProfileControl.getUnternehmenProfileDTO(companyId);
        stellenanzeigeSet = unternehmenProfileDTO.getStellenanzeigen();

        addClassName("createStellenanzeigeView");


    }

    private Button saveStellenanzeigeButton() {
        Button addStellenanzeige = new Button("Erstellen");
        addStellenanzeige.addClassName("addStellenanzeigeButton");
        addStellenanzeige.addClickListener(e -> {
            Stellenanzeige neueStellenanzeige = new Stellenanzeige();
            neueStellenanzeige.setBezeichnung(addStellenanzeigeFormComponent.getBezeichnung().getValue());
            neueStellenanzeige.setBeschreibung(addStellenanzeigeFormComponent.getBeschreibung().getValue());
            neueStellenanzeige.setEnde(addStellenanzeigeFormComponent.getEnde().getValue());
            neueStellenanzeige.setStart(addStellenanzeigeFormComponent.getStart().getValue());
            neueStellenanzeige.setTaetigkeitsfelder(addStellenanzeigeFormComponent.getTaetigkeitsfelder());
            neueStellenanzeige.setBeschaeftigungsverhaeltnis(addStellenanzeigeFormComponent.getBeschaeftigungsverhaeltnis().getValue());
            neueStellenanzeige.setBezahlung(addStellenanzeigeFormComponent.getBezahlung().getValue());
            neueStellenanzeige.setErstellungsdatum(LocalDate.now());
            stellenanzeigeSet.add(neueStellenanzeige);
            saveStellenanzeige();

        });
        return addStellenanzeige;
    }

    private void saveStellenanzeige() {
        try {
            unternehmenProfileDTO.setStellenanzeigen(stellenanzeigeSet);
            unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO,getCurrentUserName());

            if(addStellenanzeigeFormComponent.getBezeichnung().getValue().isEmpty()
                    || addStellenanzeigeFormComponent.getBeschreibung().getValue().isEmpty()
                    || addStellenanzeigeFormComponent.getBeschaeftigungsverhaeltnis().getValue().isEmpty()
                    || addStellenanzeigeFormComponent.getStart().getValue() == null) {
                Notification.show("Bitte füllen Sie alle erforderlichen Felder aus!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            else{
                if(unternehmenProfileDTO.getStellenanzeigen().size() == stellenanzeigeSet.size()){
                    getUI().get().navigate(Globals.Pages.COMPANY_PROFILE_VIEW+"/"+getCurrentUserName());
                    Notification note = Notification.show("Stellenanzeige wurde erfolgreich erstellt");
                    note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    note.setPosition(Notification.Position.BOTTOM_START);
                }
                else{
                    Notification note = Notification.show("Stellenanzeige konnte nicht erstellt werden");
                    note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    note.setPosition(Notification.Position.BOTTOM_START);
                }
            }

        } catch (ProfileException e) {
            e.printStackTrace();
        }
    }
}
