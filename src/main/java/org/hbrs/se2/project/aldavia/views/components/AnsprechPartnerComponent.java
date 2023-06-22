package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;

public class AnsprechPartnerComponent extends VerticalLayout implements ProfileComponent {

    private final UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private H2 title;
    private TextField vornameAnsprechPartner;
    private TextField nachnameAnsprechPartner;
    private Span noAnsprechPartner;
    private Span ansprechPartner = new Span();

    public AnsprechPartnerComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        noAnsprechPartner = new Span("Kein Ansprechpartner vorhanden");
        noAnsprechPartner.addClassName("noAnsprechPartner");
        title = new H2("Ansprechpartner");
        addClassName("ansprechpartner");
        addClassName("card");
        setUpUI();
    }

    private void setUpUI(){
        add(title);
        add(noAnsprechPartner);
        vornameAnsprechPartner = new TextField();
        vornameAnsprechPartner.setPlaceholder("Vorname");
        vornameAnsprechPartner.setWidthFull();
        nachnameAnsprechPartner = new TextField();
        nachnameAnsprechPartner.setPlaceholder("Nachname");
        nachnameAnsprechPartner.setWidthFull();
        add(vornameAnsprechPartner);
        add(nachnameAnsprechPartner);
        updateView();
    }

    private void updateView(){
        if(unternehmenProfileDTO.getAp_nachname()==null || unternehmenProfileDTO.getAp_vorname()==null || unternehmenProfileDTO.getAp_nachname().equals("") || unternehmenProfileDTO.getAp_vorname().equals("")) {
            noAnsprechPartner.setVisible(true);
            noAnsprechPartner.getStyle().set("font-style", "italic");
            vornameAnsprechPartner.setVisible(false);
            nachnameAnsprechPartner.setVisible(false);
        } else {
            noAnsprechPartner.setVisible(false);
            ansprechPartner = new Span();
            ansprechPartner.setText(unternehmenProfileDTO.getAp_vorname() + " " + unternehmenProfileDTO.getAp_nachname());
            add(ansprechPartner);
            vornameAnsprechPartner.setValue(unternehmenProfileDTO.getAp_vorname());
            nachnameAnsprechPartner.setValue(unternehmenProfileDTO.getAp_nachname());
            vornameAnsprechPartner.setVisible(false);
            nachnameAnsprechPartner.setVisible(false);
        }

    }

    private void updateEdit(){
        noAnsprechPartner.setVisible(false);
        ansprechPartner.setVisible(false);
        vornameAnsprechPartner.setVisible(true);
        nachnameAnsprechPartner.setVisible(true);
        vornameAnsprechPartner.setReadOnly(false);
        nachnameAnsprechPartner.setReadOnly(false);
    }
    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
            updateCompanyDTO(userName);
            updateView();
    }

    @Override
    public void switchEditMode() {
        updateEdit();
    }

    private void updateCompanyDTO(String userName) throws PersistenceException, ProfileException {
        if(vornameAnsprechPartner.getValue().isEmpty() && !(nachnameAnsprechPartner.getValue().isEmpty())
                || !(vornameAnsprechPartner.getValue().isEmpty()) && nachnameAnsprechPartner.getValue().isEmpty()){
            Notification.show("Bitte geben Sie einen Vor- und Nachnamen ein!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            unternehmenProfileDTO.setAp_vorname(vornameAnsprechPartner.getValue());
            unternehmenProfileDTO.setAp_nachname(nachnameAnsprechPartner.getValue());
            unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO, userName);
        }
    }
}
