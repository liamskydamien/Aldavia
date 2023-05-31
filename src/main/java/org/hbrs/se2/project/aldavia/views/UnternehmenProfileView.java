package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;



@Route(value = "profileUnternehmen")
@PageTitle("ProfilUnternehmen")
public class UnternehmenProfileView extends Div {

    @Autowired
    UnternehmenProfileControl control;

    private TextField name = new TextField("Name Ihres Unternehmens");

    private TextField beschreibung = new TextField("Beschreibung");
    private TextField ap_vorname = new TextField("Vorname Ansprechpartner");

    private TextField ap_nachname = new TextField("Nachname Ansprechpartner");

   // private TextField adressen = new TextField("Adressen");

    private TextField email = new TextField("Email-Adresse");

    private TextField webside = new TextField("Webseite");

    private Button save = new Button("Save");

    private Binder<UnternehmenProfileDTO> binder = new Binder<>(UnternehmenProfileDTO.class);


    public UnternehmenProfileView(UnternehmenProfileControl control) throws ProfileException {

        if (getCurrentUser() == null) {
            System.out.println("LOG: In Constructor of App View - No User given!");
        } else {
            binder.bindInstanceFields(this);
            binder.setBean(new UnternehmenProfileDTO());


            UserDTO userDTO = getCurrentUser();
            UnternehmenProfileDTO unternehmenDto = control.getUnternehmenProfileDTO(userDTO.getUserid());

            add(createFormLayout(control, unternehmenDto));
            add(save);
            save.setVisible(false);

            name.addKeyDownListener(e -> save.setVisible(true));
            beschreibung.addKeyDownListener(e -> save.setVisible(true));
            ap_vorname.addKeyDownListener(e -> save.setVisible(true));
            ap_nachname.addKeyDownListener(e -> save.setVisible(true));
            email.addKeyDownListener(e -> save.setVisible(true));
            webside.addKeyDownListener(e -> save.setVisible(true));


            save.addClickListener(e -> {
                UnternehmenProfileDTO dto = binder.getBean();
                if(dto == null) {
                    throw new RuntimeException("DTO IS NULL!");
                }
                try {
                    control.createAndUpdateUnternehmenProfile(dto, userDTO.getUserid());
                } catch (ProfileException ex) {
                    throw new RuntimeException(ex);
                }

            });
        }





    }

    private Component createFormLayout(UnternehmenProfileControl control, UnternehmenProfileDTO dto) {
        FlexLayout layout = new FlexLayout();
        layout.setId("flex-layout");
        layout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        layout.setAlignItems(FlexLayout.Alignment.START);

        if(dto.getName() != null) {
            name.setValue(dto.getName());
        }
        if(dto.getBeschreibung() != null) {
            beschreibung.setValue(dto.getBeschreibung());
        }
        if(dto.getEmail() != null) {
            email.setValue(dto.getEmail());
        }
     //   if(dto.getAdressen() != null) {
      //      adressen.setValue(dto.getAdressen().toString());
      //  }
        if(dto.getAp_vorname() != null) {
            ap_vorname.setValue(dto.getAp_vorname());
        }
        if(dto.getAp_nachname() != null) {
            ap_nachname.setValue(dto.getAp_nachname());
        }




        FormLayout formLayout = new FormLayout();
        formLayout.add(name, beschreibung, email, ap_vorname, ap_nachname, webside);
        layout.add(formLayout);

        return layout;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
}
