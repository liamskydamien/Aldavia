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
    private final UnternehmenProfileDTO unternehmenProfileDTO;
    private final UnternehmenProfileControl unternehmenProfileControl;
    private final TextArea aboutCompanyTextField;
    private final H2 title;

    public AboutCompany(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;

        addClassName("about-company");
        aboutCompanyTextField = new TextArea();
        aboutCompanyTextField.addClassName("about-company-textarea");
        aboutCompanyTextField.setPlaceholder("Über das Unternehmen");
        aboutCompanyTextField.setWidthFull();
        title = new H2("Über das Unternehmen");
        setUpUI();
    }
    private void setUpUI(){
        add(title);
        add(aboutCompanyTextField);
        aboutCompanyTextField.setClearButtonVisible(true);
        aboutCompanyTextField.setMaxLength(1200);
        aboutCompanyTextField.setValueChangeMode(ValueChangeMode.EAGER);
        aboutCompanyTextField.setHelperText("0/1200");
        aboutCompanyTextField.addValueChangeListener(e -> e.getSource()
                .setHelperText(e.getValue().length() + "/" + 1200));
        updateView();
    }

    private void updateView(){
        aboutCompanyTextField.setReadOnly(true);

        if(unternehmenProfileDTO.getBeschreibung() != null){
            aboutCompanyTextField.setValue(unternehmenProfileDTO.getBeschreibung());
        }
    }

    private void updateEdit(){
        aboutCompanyTextField.setReadOnly(false);
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
        unternehmenProfileDTO.setBeschreibung(aboutCompanyTextField.getValue());
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO, userName);



    }

}

