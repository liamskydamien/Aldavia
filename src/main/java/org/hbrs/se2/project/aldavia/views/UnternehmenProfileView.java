package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.COMPANY_PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("ProfilUnternehmen")
@CssImport("./styles/views/profile/studentProfile.css")
@Transactional

public class UnternehmenProfileView extends VerticalLayout implements HasUrlParameter<String> {
    private final UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Div profileWrapper = null;
    private final UI ui = UI.getCurrent();
    private PersonalProfileDetailsComponent unternehmenPersonalDetailsComponent;
    private AboutCompany aboutCompany;
    private AnsprechPartnerComponent ansprechPartnerComponent;
    private AdressenComponent adressenComponent;
    private EditAndSaveProfileButton editAndSaveProfileButton;
    private StellenanzeigeComponent stellenanzeigeComponent;


    private Button save = new Button("Save");

    private Button saveAdress = new Button("Save Adress");

    private Button plusAdresse = new Button("+ Adresse");

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
            try {
                unternehmenProfileDTO = unternehmenProfileControl.getUnternehmenProfileDTO(parameter);
                ui.access(() -> {
                    if (profileWrapper == null) {
                        unternehmenPersonalDetailsComponent = new PersonalProfileDetailsComponent(unternehmenProfileDTO, unternehmenProfileControl);
                        profileWrapper = new Div();
                        profileWrapper.addClassName("profile-wrapper");
                        profileWrapper.add(unternehmenPersonalDetailsComponent);
                        profileWrapper.add(createButtomLayout());
                        add(profileWrapper);
                    }
                });
            } catch (ProfileException ex) {
                throw new RuntimeException(ex);
            }
    }


    public UnternehmenProfileView(UnternehmenProfileControl control) throws ProfileException {
        this.unternehmenProfileControl = control;
        editAndSaveProfileButton = new EditAndSaveProfileButton();
        addClassName("profile-view");

        editAndSaveProfileButton.addListenerToEditButton(e -> {
            switchToEditMode();
        });
        editAndSaveProfileButton.addListenerToSaveButton(e -> {
            try {
                switchToViewMode();
            } catch (PersistenceException | ProfileException persistenceException) {
                persistenceException.printStackTrace();
            }
        });

        editAndSaveProfileButton.setEditButtonVisible(true);
        editAndSaveProfileButton.setSaveButtonVisible(false);

        add(editAndSaveProfileButton);

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
    private void switchToEditMode(){
        editAndSaveProfileButton.setEditButtonVisible(false);
        editAndSaveProfileButton.setSaveButtonVisible(true);

        unternehmenPersonalDetailsComponent.switchEditMode();
        aboutCompany.switchEditMode();
        ansprechPartnerComponent.switchEditMode();
        adressenComponent.switchEditMode();
    }

    private void switchToViewMode() throws PersistenceException, ProfileException {
        editAndSaveProfileButton.setEditButtonVisible(true);
        editAndSaveProfileButton.setSaveButtonVisible(false);

        unternehmenPersonalDetailsComponent.switchViewMode(getCurrentUserName());
        aboutCompany.switchViewMode(getCurrentUserName());
        ansprechPartnerComponent.switchViewMode(getCurrentUserName());
        adressenComponent.switchViewMode(getCurrentUserName());
    }

    private VerticalLayout createButtomLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("bottom-layout");
        layout.add(createAboutAnsprechpartnerAdressenLayout());

        stellenanzeigeComponent = new StellenanzeigeComponent(unternehmenProfileDTO, unternehmenProfileControl);
        layout.add(stellenanzeigeComponent);
        return layout;
    }

    private VerticalLayout createAboutAnsprechpartnerAdressenLayout() {
        aboutCompany = new AboutCompany(unternehmenProfileDTO, unternehmenProfileControl);
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("about-ansprechpartner-adressen");
        layout.add(aboutCompany);
        layout.add(createAnsprechpartnerAdressenLayout());
        return layout;
    }

    private HorizontalLayout createAnsprechpartnerAdressenLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("ansprechpartner-adressen");
        layout.add(createAnsprechpartnerLayout(), createAdressenLayout());
        return layout;
    }

    private VerticalLayout createAnsprechpartnerLayout() {
        ansprechPartnerComponent = new AnsprechPartnerComponent(unternehmenProfileDTO, unternehmenProfileControl);
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("ansprechpartner");
        layout.add(ansprechPartnerComponent);
        return layout;
    }

    private VerticalLayout createAdressenLayout() {
        adressenComponent = new AdressenComponent(unternehmenProfileDTO, unternehmenProfileControl);
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("adressen");
        layout.add(adressenComponent);
        return layout;
    }

}
