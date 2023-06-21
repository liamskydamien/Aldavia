package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class AdressenComponent extends VerticalLayout implements ProfileComponent{
    private final UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private TextField strasse;
    private TextField hausnr;
    private TextField plz;
    private TextField ort;
    private TextField land;

    public AdressenComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        this.addClassName("adressen-component");
        this.addClassName("card");
        TextField strasse = new TextField("Straße");
        TextField hausnr = new TextField("Hausnummer");
        TextField plz = new TextField("PLZ");
        TextField ort = new TextField("Ort");
        TextField land = new TextField("Land");
        setUpUI();
    }

    private void setUpUI() {

    }

    private void updateView() {

    }

    private void updateEdit() {

    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {

    }

    @Override
    public void switchEditMode() {

    }

    private void updateUnternehmenProfileDTO() {

    }

    private Component createAdressPicker() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(strasse, hausnr, plz, ort, land);
        layout.setWidth("50px");
        return layout;
    }



    public Adresse reverseInput(String adresseString) {
        String[] split = adresseString.split(" ");
        Adresse adresseDelete = Adresse.builder()
                .strasse(split[0])
                .hausnummer(split[1])
                .plz(split[2])
                .ort(split[3])
                .land(split[4])
                .build();

        return adresseDelete;

    }

    private Button saveAdressButton() {
        Button saveAdress = new Button("Adresse speichern");
        saveAdress.addClickListener(e -> {
            Adresse adresseNeu;
            if (strasse.getValue() == "" || hausnr.getValue() == "" || plz.getValue() == "" || ort.getValue() == ""
                    || land.getValue() == "") {
                Notification.show("Bitte alle Adress-Felder ausfüllen!");
            } else {
                adresseNeu = Adresse.builder()
                        .strasse(strasse.getValue())
                        .hausnummer(hausnr.getValue())
                        .plz(plz.getValue())
                        .ort(ort.getValue())
                        .land(land.getValue())
                        .build();
            }
        });
        return saveAdress;
    }

    /*
     //Adresse zu vorhandenen Adressen hinzufügen
                    Set<Adresse> vorhandeneAdressen = unternehmenDto.getAdressen();
                    vorhandeneAdressen.add(adresseNeu);
                    unternehmenDto.setAdressen(vorhandeneAdressen);
                    try {
                        control.createAndUpdateUnternehmenProfile(unternehmenDto, userDTO.getUserid());
                    } catch (ProfileException ex) {
                        throw new RuntimeException(ex);
                    }

     */
}
