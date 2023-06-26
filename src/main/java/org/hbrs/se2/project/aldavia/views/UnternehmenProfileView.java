package org.hbrs.se2.project.aldavia.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.views.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUser;
import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;


@Route(value = Globals.Pages.COMPANY_PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("ProfilUnternehmen")
@CssImport("./styles/views/profile/studentProfile.css")
@Transactional

public class UnternehmenProfileView extends VerticalLayout implements HasUrlParameter<String> {
    private final UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Div companyProfileWrapper = null;
    private final UI ui = UI.getCurrent();
    private PersonalProfileDetailsComponent unternehmenPersonalDetailsComponent;
    private AboutCompany aboutCompany;
    private AnsprechPartnerComponent ansprechPartnerComponent;
    private AdressenComponent adressenComponent;
    private EditAndSaveProfileButton editAndSaveProfileButtonComapany;
    private StellenanzeigeComponent stellenanzeigeComponent;
    private String url;
    private boolean isSameUser = true;

    @Override
    @Transactional
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
            try {
                unternehmenProfileDTO = unternehmenProfileControl.getUnternehmenProfileDTO(parameter);
                url = beforeEvent.getLocation().getPath();
                if(getCurrentUser() == null){
                    isSameUser = false;
                } else{
                    isSameUser = Objects.equals(getCurrentUserName(), parameter);
                }
                if(isSameUser){
                    editAndSaveProfileButtonComapany = new EditAndSaveProfileButton();
                    addClassName("profile-view");

                    editAndSaveProfileButtonComapany.addListenerToEditButton(e -> {
                        switchToEditMode();
                    });
                    editAndSaveProfileButtonComapany.addListenerToSaveButton(e -> {
                        try {
                            switchToViewMode();
                        } catch (PersistenceException | ProfileException persistenceException) {
                            persistenceException.printStackTrace();
                        }
                    });

                    editAndSaveProfileButtonComapany.setEditButtonVisible(true);
                    editAndSaveProfileButtonComapany.setSaveButtonVisible(false);

                    add(editAndSaveProfileButtonComapany);
                }


                ui.access(() -> {
                    if (companyProfileWrapper == null) {
                        unternehmenPersonalDetailsComponent = new PersonalProfileDetailsComponent(unternehmenProfileDTO, unternehmenProfileControl, url);
                        companyProfileWrapper = new Div();
                        companyProfileWrapper.addClassName("profile-wrapper");
                        companyProfileWrapper.add(unternehmenPersonalDetailsComponent);
                        companyProfileWrapper.add(createButtomLayout());
                        add(companyProfileWrapper);
                    }
                });
            } catch (ProfileException ex) {
                throw new RuntimeException(ex);
            }
    }


    @Autowired
    public UnternehmenProfileView(UnternehmenProfileControl control) {
        this.unternehmenProfileControl = control;

    }


    public UnternehmenProfileView(UnternehmenProfileControl control, String url) throws ProfileException {
        this.unternehmenProfileControl = control;
        this.url = url;


        String[] split = url.split("/");
        String parameter = split[split.length - 1];
        unternehmenProfileDTO = unternehmenProfileControl.getUnternehmenProfileDTO(parameter);

        ui.access(() -> {
            if (companyProfileWrapper == null) {
                unternehmenPersonalDetailsComponent = new PersonalProfileDetailsComponent(unternehmenProfileDTO, unternehmenProfileControl, url);
                companyProfileWrapper = new Div();
                companyProfileWrapper.addClassName("profile-wrapper");
                companyProfileWrapper.add(unternehmenPersonalDetailsComponent);
                companyProfileWrapper.add(createButtomLayout());
                add(companyProfileWrapper);
            }
        });

    }

    private void switchToEditMode(){
        editAndSaveProfileButtonComapany.setEditButtonVisible(false);
        editAndSaveProfileButtonComapany.setSaveButtonVisible(true);

        unternehmenPersonalDetailsComponent.switchEditMode();
        aboutCompany.switchEditMode();
        ansprechPartnerComponent.switchEditMode();
        adressenComponent.switchEditMode();
        stellenanzeigeComponent.switchEditMode();
    }

    private void switchToViewMode() throws PersistenceException, ProfileException {
        editAndSaveProfileButtonComapany.setEditButtonVisible(true);
        editAndSaveProfileButtonComapany.setSaveButtonVisible(false);

        unternehmenPersonalDetailsComponent.switchViewMode(getCurrentUserName());
        aboutCompany.switchViewMode(getCurrentUserName());
        ansprechPartnerComponent.switchViewMode(getCurrentUserName());
        adressenComponent.switchViewMode(getCurrentUserName());
        stellenanzeigeComponent.switchViewMode(getCurrentUserName());
    }

    @Transactional
    public VerticalLayout createButtomLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("bottom-layout");
        layout.add(createAboutAnsprechpartnerAdressenLayout());

        stellenanzeigeComponent = new StellenanzeigeComponent(unternehmenProfileDTO, unternehmenProfileControl,url);
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
        layout.add(createAdressenLayout(),createAnsprechpartnerLayout() );
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
