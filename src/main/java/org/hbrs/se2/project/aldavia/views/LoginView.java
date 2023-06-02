package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.aldavia.control.AuthorizationControl;
import org.hbrs.se2.project.aldavia.control.LoginControl;
import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.util.UserLoginEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 * ToDo: Integration einer Seite zur Registrierung von Benutzern
 */
@CssImport("./styles/views/login/login-view.css")
@Route(value = Globals.Pages.LOGIN_VIEW, layout = NeutralLayout.class )
@RouteAlias(value = "login")

public class LoginView extends VerticalLayout {

    @Autowired
    private LoginControl loginControl;

    private AuthorizationControl authorizationControl;

    public LoginView() {

        this.setHeightFull();
        this.setSizeFull();
        setBackgroundImage();


        LoginForm component = new LoginForm();
        component.setId("login-form");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Welcome Back");
        i18n.getForm().setUsername("Benutzername oder E-Mail");
        i18n.getForm().setPassword("Passwort");
        i18n.getForm().setSubmit("Log in");

        component.setI18n(i18n);

        component.addLoginListener(e -> {

            boolean isAuthenticated = false;
            ;

            try {
                isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
            if (isAuthenticated) {
                System.out.println("Login erfolgreich");

                grabAndSetUserIntoSession();

                // Hier den Benutzer aus der Session holen
                UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);


                authorizationControl = new AuthorizationControl();
                System.out.println("UserDTO: " + userDTO.getRoles());
                System.out.println("UserDTO: " + userDTO.getUserid());
                if(authorizationControl.isUserInRole(userDTO, Globals.Roles.STUDENT )) {
                    navigateToStudentMainPage();
                } else if (authorizationControl.isUserInRole( userDTO, Globals.Roles.UNTERNEHMEN )) {
                    navigateToCompanyPage();
                } else {
                    Dialog dialog = new Dialog();
                    dialog.add( new Text( "Sie haben keine Berechtigung für diese Seite!") );
                    dialog.setWidth("400px");
                    dialog.setHeight("150px");
                    dialog.open();
                }

            } else {
                component.setError(true);

            }
        });

        add(component);
        this.setAlignItems( Alignment.END );
    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, userDTO );
    }


    private void navigateToStudentMainPage() {
        // Navigation zur Startseite, hier auf die Teil-Komponente Show-Cars.
        // Die anzuzeigende Teil-Komponente kann man noch individualisieren, je nach Rolle,
        // die ein Benutzer besitzt
        UI.getCurrent().navigate(Globals.Pages.STUDENT_MAIN);

    }

    private void navigateToCompanyPage() {
        UI.getCurrent().navigate(Globals.Pages.COMPANY_MAIN);
    }

    private void setBackgroundImage() {

        this.getElement().getStyle().set("background-image", "url('images/login.png')");
        this.getElement().getStyle().set("background-size", "cover");
        this.getElement().getStyle().set("background-position", "center");

    }
}
