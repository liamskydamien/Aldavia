package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;

public class StellenanzeigeComponent extends VerticalLayout implements ProfileComponent{
    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private H2 title;

    public StellenanzeigeComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        addClassName("stellenanzeige");
        addClassName("card");
        setUpUI();
    }

    private void setUpUI(){
    }

    private void updateView(){

    }

    private void updateEdit(){

    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {

    }

    @Override
    public void switchEditMode() {

    }

    private void updateUnternehmenProfileDTO(){

    }
}
