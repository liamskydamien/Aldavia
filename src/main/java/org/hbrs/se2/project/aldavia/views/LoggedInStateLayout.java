package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;
import org.hbrs.se2.project.aldavia.control.AuthorizationControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.util.Globals;


@CssImport("./styles/views/navbar/navbar.css")

public class LoggedInStateLayout extends AppLayout {

    //@Autowired
    //private StudentControl studentControl;

    private AuthorizationControl authorizationControl;


    public LoggedInStateLayout() throws ProfileException {

        setUpUI();

    }

    public void setUpUI() throws ProfileException {
        addToNavbar(true, createHeaderContent());
    }


    private Component createHeaderContent() throws ProfileException {



        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setId("header-neutral");
        Image logo = new Image("images/aldavia.png", "AldaVia logo");

        if(checkIfUserIsLoggedIn()){
            navigateStudentHomeLogo(logo);
        } else {
            navigateCompanyHomeLogo(logo);
        }

        layout.add(logo);

        HorizontalLayout topRightLayout = new HorizontalLayout();
        topRightLayout.setId("top-right-layout");
        topRightLayout.setWidthFull();
        topRightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topRightLayout.setAlignItems(FlexComponent.Alignment.CENTER);


        if(checkIfUserIsLoggedIn()){


            if(checkIfUserIsStudent()){
                topRightLayout.add(new Label("Hallo, " + getCurrentUserName()));
                topRightLayout.add(createMenuItemsStudent());
                // Logout-Button am rechts-oberen Rand.
                topRightLayout.add(createLogOutButton());

            } else if (checkIfUserIsUnternehmen()){
                topRightLayout.add(new Label("Hallo, " + getCurrentUserName()));
                topRightLayout.add(createMenuItemsUnternehmen());
                // Logout-Button am rechts-oberen Rand.
                topRightLayout.add(createLogOutButton());
            }
        }

        layout.add(topRightLayout);


        return layout;
    }


    private Component[] createMenuItemsStudent() {

        Icon iconUser = VaadinIcon.USER.create();
        Tab[] tabs = new Tab[]{
                createTabWithIcon("Profile", StudentProfileView.class, iconUser)
        };
        tabs[0].setId("student-srofil-tab");
        return tabs;
    }

    private Component[] createMenuItemsUnternehmen() {

        Button button = new Button("Erstellen");
        Icon iconUser = VaadinIcon.USER.create();
        Tab[] tabs = new Tab[]{
                createTabWithIcon("Profile", StudentProfileView.class, iconUser),
                createButtonInTab(button, CreateStellenanzeigeView.class)
        };
        tabs[0].setId("unternehmen-srofil-tab");
        return tabs;
    }


    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        return tab;
    }

    private static Tab createTabWithIcon(String text, Class<? extends Component> navigationTarget, Icon icon) {
        final Tab tab = new Tab();

        // Erstelle das Icon
        icon.setSize("24px");
        icon.setColor("blue");

        // Erstelle das Text-Label
        Label label = new Label(text);

        // Erstelle den RouterLink mit dem Icon und dem Label als Inhalt
        RouterLink routerLink = new RouterLink(null, navigationTarget);
        routerLink.add(icon, label);

        // Füge den RouterLink zum Tab hinzu
        tab.add(routerLink);
        return tab;
    }

    private static Tab createButtonInTab(Button button, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate(navigationTarget)));
        tab.add(button);
        return tab;
    }

    private MenuBar createLogOutButton() {
        MenuBar bar = new MenuBar();
        Icon iconSignOut = VaadinIcon.SIGN_OUT.create();
        Text textLogOut = new Text("Log out");
        MenuItem item = bar.addItem(new HorizontalLayout(iconSignOut,textLogOut ), e -> logoutUser());
        item.setId("logout-button");
        return bar;
    }


    private static void navigateStudentHomeLogo(Image img) {
        img.addClickListener(event -> img.getUI().ifPresent(ui -> ui.navigate(Globals.Pages.STUDENT_MAIN)));
    }

    private static void navigateCompanyHomeLogo(Image img) {
        img.addClickListener(event -> img.getUI().ifPresent(ui -> ui.navigate(Globals.Pages.COMPANY_MAIN)));
    }


    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    private boolean checkIfUserIsStudent(){
        if(!checkIfUserIsLoggedIn()){
            return false;
        }
        authorizationControl = new AuthorizationControl();
        return authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.STUDENT);
    }

    private boolean checkIfUserIsUnternehmen(){
        if(!checkIfUserIsLoggedIn()){
            return false;
        }
        authorizationControl = new AuthorizationControl();
        return authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.UNTERNEHMEN);
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("/");
    }

    //TODO:Methode spezifisch für Student und unternehmen

    private String getCurrentUserName() {
        return getCurrentUser().getUserid();
    }

    /*private String getCurrentStudentName() throws ProfileException {
        Student student = studentControl.getStudent(getCurrentUserName());
        return student.getVorname();
    }*/






}
