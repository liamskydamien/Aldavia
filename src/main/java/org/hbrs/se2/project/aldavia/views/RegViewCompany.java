package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.RegistrationControl;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.hbrs.se2.project.aldavia.dtos.RegistrationResult;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.util.Utils;


@Route(value = "registrationCompany")
@PageTitle("RegistrationCompany")
@CssImport("./styles/views/regView/reg-view.css")
public class RegViewCompany extends Div {

    private TextField username = new TextField("Username");
    private TextField mail = new TextField("Email-Adresse");

    private TextField companyName= new TextField("Name Ihres Unternehmens");
    private TextField standort = new TextField("Hauptstandort");
    private PasswordField password = new PasswordField("Passwort");
    private ProgressBar pbar = new ProgressBar(0.0,10.0);
    private PasswordField passwordCheck = new PasswordField("Passwort check");

    private Button register = new Button("Sign Up");
    private Button generatePassword = new Button("Generate Password");

    private Dialog dialog = new Dialog();

    private Details details;

    private Binder<RegistrationDTOCompany> binder = new Binder<>(RegistrationDTOCompany.class);


    public RegViewCompany(RegistrationControl regControl) {
        pbar.setWidth("390px");

        username.setRequiredIndicatorVisible(true);
        mail.setRequiredIndicatorVisible(true);
        companyName.setRequiredIndicatorVisible(true);
        standort.setRequiredIndicatorVisible(true);
        password.setRequiredIndicatorVisible(true);
        passwordCheck.setRequiredIndicatorVisible(true);



        addClassName("RegistrationCompany");

        add(createTitle());
        add(createFormLayout());
        add(progressBar());
        add(passwordGenerator());
        add(createButtonLayout());

        createDialog(regControl);



        //Aufsetzen der Progress-Bar
        password.addValueChangeListener(event -> {
            if (password.getValue().length() < 11 ) {
                pbar.setValue(password.getValue().length());
            }
        });
        password.setValueChangeMode(ValueChangeMode.EAGER);

        mail.addKeyDownListener(event -> {
            if (!(mail.getValue().contains("@"))) {
                mail.setErrorMessage("Bitte geben Sie ein '@' Zeichen ein");
                mail.setInvalid(true);
            }
        });
        mail.setValueChangeMode(ValueChangeMode.EAGER);

        passwordCheck.addKeyDownListener(event -> {
            if(!password.getValue().equals(passwordCheck.getValue())){
                passwordCheck.setErrorMessage("Passwörter stimmen nicht überein!");
                passwordCheck.setInvalid(true);
            }
        });


        binder.bindInstanceFields(this);
        clearForm();

        generatePassword.addClickListener(e -> {
            password.setValue(Utils.generateRandomPassword(10));
            passwordCheck.setValue(password.getValue());
        });


        register.addClickListener(e -> {
            // Speicherung der Daten über das zuhörige Control-Object.
            // Daten des Autos werden aus Formular erfasst und als DTO übergeben.
            // Zusätzlich wird das aktuelle UserDTO übergeben.
            //UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
            //carService.createCar(binder.getBean() ,  userDTO );

            RegistrationDTOCompany dto = binder.getBean();


            if (dto.getMail().equals("") || dto.getUserName().equals("") || dto.getPassword().equals("")
                    || dto.getCompanyName().equals("") || dto.getStandort().equals("")) {

                Notification.show("Bitte alle Felder ausfüllen!");
            }else if(dto.getPassword().length() < 10) {
                Notification.show("Ihr Passwort erfüllt nicht die vorgeschiebene Länge!");
            } else if (!(dto.getPassword().equals(passwordCheck.getValue()))) {
                Notification.show("Passwörter stimmen nicht überein!");
                password.clear();
                passwordCheck.clear();

            } else {
                dialog.open();
            }
        });
    }

    private void clearForm() {
        binder.setBean(new RegistrationDTOCompany());
    }

    private Component createTitle() {
        return new H3("Unternehmen Registrierung");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(username, mail, companyName, standort, password, passwordCheck);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.setClassName("register-button");
        buttonLayout.add(register);
        createDetailsInformation();
        buttonLayout.add(details);

        return buttonLayout;
    }
    private Component progressBar() {
        HorizontalLayout layout = new HorizontalLayout();
        Div progressBarLabel = new Div();
        progressBarLabel.setText("Password length");
        layout.add(pbar);
        layout.add(progressBarLabel);
        layout.setClassName("progressBar");
        return layout;

    }
    private Component passwordGenerator() {
        HorizontalLayout l = new HorizontalLayout();
        generatePassword.addThemeVariants(ButtonVariant.LUMO_SMALL);
        generatePassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        l.add(generatePassword);
        l.setHeight("60px");
        return l;

    }

    private VerticalLayout createDialogLayout(RegistrationControl regControl) {
        RegistrationDTOCompany dto = binder.getBean();
        VerticalLayout layout = new VerticalLayout();
        Div text = new Div();
        text.add("Wollen Sie mit den angegebenen Daten fortfahren?");
        layout.add(text);
        Button confirmButton = new Button("Confirm", e -> {
            RegistrationResult result  = register(regControl);
            if (result.getResult() == true) {
                Notification.show("Registrierung erfolgreich!");
                UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
                clearForm();
                dialog.close();
            } else if (result.getReason().equals(RegistrationResult.EMAIL_ALREADY_EXISTS)) {
                Notification.show("Die Email Adresse ist bereits vorhanden!");
                dialog.close();
            } else {
                Notification.show("Der Username ist bereits belegt!");
                dialog.close();
            }


        } );
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout lh = new HorizontalLayout();
        lh.add(cancelButton);
        lh.add(confirmButton);

        layout.add(lh);

        return layout;
    }

    private void createDialog(RegistrationControl regControl) {
        dialog.add(createDialogLayout(regControl));
    }

    private RegistrationResult register(RegistrationControl regControl) {
        RegistrationDTOCompany dto = binder.getBean();
        return regControl.createUnternehmen(dto);
    }

    private void createDetailsInformation(){
        Span infos = new Span("Weitere Daten können Sie anschließend unter 'Profil' bearbeiten");
        details = new Details("Details", infos);
    }


}
