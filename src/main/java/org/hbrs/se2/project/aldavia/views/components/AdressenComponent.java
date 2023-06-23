package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class AdressenComponent extends VerticalLayout implements ProfileComponent{
    private final UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Set<AdresseDTO> adressen;
    private TextField strasse;
    private TextField hausnr;
    private TextField plz;
    private TextField ort;
    private TextField land;
    private Span noAdressen;
    private Div displayAdressen;
    private FlexLayout editLayout;

    public AdressenComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        adressen = unternehmenProfileDTO.getAdressen();
        displayAdressen = new Div();
        editLayout = new FlexLayout();
        displayAdressen.addClassName("display-adressen");
        this.addClassName("adressen-component");
        this.addClassName("card");
        strasse = new TextField("Straße");
        strasse.setPlaceholder("Straße");
        hausnr = new TextField("Hausnummer");
        hausnr.setPlaceholder("Hausnummer");
        plz = new TextField("PLZ");
        plz.setPlaceholder("PLZ");
        ort = new TextField("Ort");
        ort.setPlaceholder("Ort");
        land = new TextField("Land");
        land.setPlaceholder("Land");
        noAdressen = new Span("Keine Adressen vorhanden");
        setUpUI();
    }

    private void setUpUI() {
        H2 title = new H2("Adressen");
        add(title);
        add(noAdressen);
        noAdressen.setVisible(false);
        add(createAdressPicker());
        add(displayAdressen);

        updateView();


    }

    private void updateView() {
        editLayout.setVisible(false);
        displayAdressen.setVisible(true);
        if (unternehmenProfileDTO.getAdressen().isEmpty()) {
            noAdressen.setVisible(true);
        } else {
           renderAdressen(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEdit() {
        editLayout.setVisible(true);
        renderAdressen(Globals.ProfileViewMode.EDIT);
    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
        updateUnternehmenProfileDTO(userName);
        updateView();

    }

    @Override
    public void switchEditMode() {
        updateEdit();
    }

    @SneakyThrows
    private void updateUnternehmenProfileDTO(String userName) {
        unternehmenProfileDTO.setAdressen(adressen);
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO, userName);
    }

    private FlexLayout createAdressPicker() {
        editLayout.add(strasse, hausnr, plz, ort, land, saveAdressButton());
        return editLayout;
    }

    private void renderAdressen(String mode) {
        displayAdressen.removeAll();
        for (AdresseDTO adresse : adressen) {
            displayAdressen.add(createAdressDisplay(mode, adresse));
        }
    }

    private VerticalLayout createAdressDisplay(String mode, AdresseDTO adresse) {
        VerticalLayout adressDisplay = new VerticalLayout();
        adressDisplay.addClassName("adress-display");
        System.out.println("mode: " + mode);
        if (mode.equals(Globals.ProfileViewMode.EDIT)) {
            System.out.println("edited gerade");
            HorizontalLayout buttons = new HorizontalLayout();
            buttons.setJustifyContentMode(JustifyContentMode.END);
            buttons.setWidthFull();
            buttons.add(deleteAdresseButton(adresse));
            adressDisplay.add(buttons);
        }
        adressDisplay.add(new Span(adresse.getStrasse()+ " " + adresse.getHausnummer()));
        adressDisplay.add(new Span(adresse.getPlz() + " " + adresse.getOrt()));
        adressDisplay.add(new Span(adresse.getLand()));
        return adressDisplay;
    }



    private Button saveAdressButton() {
        Button saveAdress = new Button("Hinzufügen");
        saveAdress.addClickListener(e -> {
            AdresseDTO adresseNeu = null;
            if (Objects.equals(strasse.getValue(), "") || Objects.equals(hausnr.getValue(), "") || Objects.equals(plz.getValue(), "") || Objects.equals(ort.getValue(), "")
                    || Objects. equals(land.getValue(), "")) {
                Notification.show("Bitte alle Adress-Felder ausfüllen!");
            } else {
                adresseNeu = AdresseDTO.builder()
                        .strasse(strasse.getValue())
                        .hausnummer(hausnr.getValue())
                        .plz(plz.getValue())
                        .ort(ort.getValue())
                        .land(land.getValue())
                        .build();
            }
            adressen.add(adresseNeu);
            renderAdressen(Globals.ProfileViewMode.EDIT);
            strasse.clear();
            hausnr.clear();
            plz.clear();
            ort.clear();
            land.clear();
        });
        return saveAdress;
    }

    private Button deleteAdresseButton(AdresseDTO adresse) {
        Button deleteAdress = new Button(new Icon("lumo", "cross"));
        deleteAdress.addClickListener(e -> {

            adressen.remove(adresse);
            renderAdressen(Globals.ProfileViewMode.EDIT);
        });
        return deleteAdress;
    }

}
