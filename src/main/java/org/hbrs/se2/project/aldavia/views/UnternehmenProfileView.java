package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.util.Globals;


@Route(value = "profileUnternehmen")
@PageTitle("ProfilUnternehmen")
public class UnternehmenProfileView extends Div {

    private TextField beschreibung = new TextField("Beschreibung");
    private TextField ap_vorname = new TextField("Vorname Ansprechpartner");

    private TextField ap_nachname = new TextField("Nachname Ansprechpartner");

    private TextField adressen = new TextField("Adressen");

    private TextField mail = new TextField("Email-Adresse");


    public UnternehmenProfileView(UnternehmenProfileControl control) throws ProfileException {


        User user = (User) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        UnternehmenProfileDTO dto = control.getUnternehmenProfileDTO(user.getUserid());

        add(createFormLayout(control, dto));




    }

    private Component createFormLayout(UnternehmenProfileControl control, UnternehmenProfileDTO dto) {
        if(dto.getBeschreibung() != null) {
            beschreibung.setValue(dto.getBeschreibung());
        }
        if(dto.getEmail() != null) {
            mail.setValue(dto.getEmail());
        }
        if(dto.getAdressen() != null) {
            adressen.setValue(dto.getAdressen().toString());
        }
        if(dto.getAp_vorname() != null) {
            ap_vorname.setValue(dto.getAp_vorname());
        }
        if(dto.getAp_nachname() != null) {
            ap_nachname.setValue(dto.getAp_nachname());
        }




        FormLayout formLayout = new FormLayout();
        formLayout.add(beschreibung, mail, adressen, ap_vorname, ap_nachname);

        return formLayout;
    }
}
