package org.hbrs.se2.project.aldavia.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.util.Globals;


@Route(value = "registrationCompany", layout = RegistrationLayout.class)
@PageTitle("RegistrationCompany")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegViewCompany extends Div {

    private TextField name = new TextField("Username");

    private TextField companyName = new TextField("Unternehmensname");
    private TextField standort = new TextField("Hauptstandort");
    private TextField mail = new TextField("Email-Adresse");
    private TextField password = new TextField("Passwort");
    private TextField passwordCheck = new TextField("Passwort check");

    private Dialog dialog = new Dialog();

    private Button register = new Button("Register");

    private Binder<RegistrationDTOStudent> binder = new Binder<>(RegistrationDTOStudent.class);

    public RegViewCompany(RegistrationControlCompany regControl) {


        addClassName("RegistrationCompany");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        // Default Mapping of Cars attributes and the names of this View based on names
        // Source: https://vaadin.com/docs/flow/binding-data/tutorial-flow-components-binder-beans.html
        binder.bindInstanceFields(this);
        clearForm();



        register.addClickListener(e -> {
            // Speicherung der Daten über das zuhörige Control-Object.
            // Daten des Autos werden aus Formular erfasst und als DTO übergeben.
            // Zusätzlich wird das aktuelle UserDTO übergeben.
            //UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
            //carService.createCar(binder.getBean() ,  userDTO );

            RegistrationDTOStudent dto = binder.getBean();
            dto.setStatusStudent(true);

            //Password check

            if (dto.getMail().equals("") || dto.getUserName().equals("") || dto.getPassword().equals("")
                    || passwordCheck.getValue().equals("")) {

                Notification.show("Bitte alle Felder ausfüllen!");
            } else if (!(dto.getPassword().equals(passwordCheck.getValue()))) {
                Notification.show("Passwörter stimmen nicht überein!");
                password.clear();
                passwordCheck.clear();

            } else {
                openDialog(regControl,dto);

            }
        });
    }

    private void clearForm() {
        binder.setBean(new RegistrationDTOStudent());
    }

    private Component createTitle() {
        return new H3("Unternehmens Registrierung");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, mail,companyName, standort, password, passwordCheck);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);

        return buttonLayout;
    }


    private VerticalLayout openDialog(RegistrationControlCompany regControl, RegistrationDTOStudent dto) {
        VerticalLayout layout = new VerticalLayout();
        Div text = new Div();
        text.add("Wollen Sie mit den angegebenen Daten fortfarhen?");
        layout.add(text);
        Button confirmButton = new Button("Confirm", e -> {
            clearForm();
            regControl.registerUser(dto);
            Notification.show("Registrierung erfolgreich!");

            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
        } );
        Button cancelButton = new Button("Cancel", e -> dialog.close());

        layout.add(confirmButton);
        layout.add(cancelButton);

        return layout;
    }




}

