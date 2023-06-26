package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

public class AdressenComponent extends VerticalLayout implements ProfileComponent{
    private final UnternehmenProfileControl unternehmenProfileControl;
    private final UnternehmenProfileDTO unternehmenProfileDTO;
    private final Set<AdresseDTO> adressen;
    private final TextField strasse;
    private final TextField hausnr;
    private final TextField plz;
    private final TextField ort;
    private final TextField land;
    private final Span noAdressen;
    private final Div displayAdressen;
    private final FormLayout editLayout;
    private Dialog popup;
    private final HorizontalLayout editArea;

    private final Logger logger = LoggerFactory.getLogger(AdressenComponent.class);

    public AdressenComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        adressen = unternehmenProfileDTO.getAdressen();
        logger.info("SIZE Adresse DTO"+adressen.size());
        displayAdressen = new Div();
        editArea = new HorizontalLayout();
        editLayout = new FormLayout();
        editLayout.addClassName("edit-adresse-layout");
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
        editArea.add(add());
        editArea.setWidthFull();
        editArea.setJustifyContentMode(JustifyContentMode.END);
        add(editArea);
        add(displayAdressen);

        updateView();


    }

    private void updateView() {
        editArea.setVisible(false);
        displayAdressen.setVisible(true);
        if (unternehmenProfileDTO.getAdressen().isEmpty()) {
            noAdressen.setVisible(true);
            displayAdressen.setVisible(false);
        } else {
            noAdressen.setVisible(false);
           renderAdressen(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEdit() {
        editArea.setVisible(true);
        noAdressen.setVisible(false);
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

    private Button add(){
        Button add = new Button("Hinzufügen", new Icon("lumo", "plus"));
        add.setClassName("editSaveButton");
        add.addClickListener(e -> {
            addAdressenPopup();
            popup.open();
        });
        return add;
    }

    private void addAdressenPopup(){
        popup = new Dialog();
        popup.getElement().getThemeList().add("adressen-popup");
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.END);
        Button close = new Button(new Icon("lumo", "cross"));
        close.addClickListener(e -> popup.close());
        header.add(close);

        popup.add(new H2("Adresse Hinzufügen"),header,createAdressPicker());
    }

    private FormLayout createAdressPicker() {
        editLayout.add(strasse, hausnr, plz, ort, land, saveAdressButton());
        editLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("600px",2));
        return editLayout;
    }

    private void renderAdressen(String mode) {
        displayAdressen.removeAll();
        int i = 0;
        for (AdresseDTO adresse : adressen) {
            ++i;
            displayAdressen.add(createAdressDisplay(mode, adresse, i));
        }
    }

    private VerticalLayout createAdressDisplay(String mode, AdresseDTO adresse, int index) {
        VerticalLayout adresseWrapper = new VerticalLayout();
        HorizontalLayout adressDisplay = new HorizontalLayout();
        adressDisplay.addClassName("adress-display");
        if (mode.equals(Globals.ProfileViewMode.EDIT)) {
           logger.info("edited gerade");
            HorizontalLayout buttons = new HorizontalLayout();
            buttons.setJustifyContentMode(JustifyContentMode.END);
            buttons.setWidthFull();
            buttons.add(deleteAdresseButton(adresse));
            adresseWrapper.add(buttons);
        }

        Span adressNr = new Span(String.valueOf(index));
        adressNr.getElement().getThemeList().add("badge pill");
        adressDisplay.add(adressNr);

        VerticalLayout adress = new VerticalLayout();
        adress.add(new Span(adresse.getStrasse()+ " " + adresse.getHausnummer()));
        adress.add(new Span(adresse.getPlz() + " " + adresse.getOrt()));
        adress.add(new Span(adresse.getLand()));
        adressDisplay.add(adress);
        adresseWrapper.add(adressDisplay);
        return adresseWrapper;
    }



    private Button saveAdressButton() {
        Button saveAdress = new Button("Hinzufügen");
        saveAdress.setId("saveAdressButton");
        saveAdress.setClassName("editSaveButton");
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
            logger.info("SIZE Adresse DTO Nach save "+ adressen.size());
            strasse.clear();
            hausnr.clear();
            plz.clear();
            ort.clear();
            land.clear();
            renderAdressen(Globals.ProfileViewMode.EDIT);
            popup.close();



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
