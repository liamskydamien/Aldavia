package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;

public class AboutCompany extends VerticalLayout implements ProfileComponent{
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private final UnternehmenProfileControl unternehmenProfileControl;
    private TextArea aboutCompany;
    private H2 title;

    public AboutCompany(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;

        addClassName("about-company");
        aboutCompany = new TextArea();
        aboutCompany.setPlaceholder("Über das Unternehmen");
        aboutCompany.setWidthFull();
        title = new H2("Über das Unternehmen");
        setUpUI();
    }
    private void setUpUI(){
        add(title);
        add(aboutCompany);
        aboutCompany.setClearButtonVisible(true);
        aboutCompany.setMaxLength(1200);
        aboutCompany.setValueChangeMode(ValueChangeMode.EAGER);
        aboutCompany.setHelperText("0/1200");
        aboutCompany.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 1200);
        });
        updateView();
    }

    private void updateView(){
        aboutCompany.setReadOnly(true);
    }

    private void updateEdit(){
        aboutCompany.setReadOnly(false);
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

    private void updateCompanyDTO(String userName) throws ProfileException {
        unternehmenProfileDTO.setBeschreibung(aboutCompany.getValue());
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO, userName);
    }

}

