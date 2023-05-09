package org.hbrs.se2.project.aldavia.registration;


import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import org.hbrs.se2.project.aldavia.util.Globals;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import static com.vaadin.flow.component.tabs.TabsVariant.LUMO_MINIMAL;

@CssImport("./styles/views/main/main-view.css")
@Route("layout")
@JsModule("./styles/shared-styles.js")
public class RegistrationLayout extends AppLayout {

    private Tab details = createTab("Student", RegistrationView.class);
    private Tab payment = createTab("Unternehmen", RegViewCompany.class);
    public RegistrationLayout() {
        setUpUI();
    }

    private void setUpUI() {
        addToNavbar(true, createHeaderContent(createTabs()));
        addToDrawer(createDrawerContent());

    }

    private Component createHeaderContent(Component tabs) {
        // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.add(tabs);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode( FlexComponent.JustifyContentMode.EVENLY );


        // Interner Layout
        HorizontalLayout topRightPanel = new HorizontalLayout();
        topRightPanel.setWidthFull();
        topRightPanel.setJustifyContentMode( FlexComponent.JustifyContentMode.END );
        topRightPanel.setAlignItems( FlexComponent.Alignment.CENTER );

        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        //    helloUser = new H1();
        //   topRightPanel.add(helloUser);

        // Logout-Button am rechts-oberen Rand.
        MenuBar bar = new MenuBar();
        bar.addItem("Home" , e -> navigateToLogin());
        topRightPanel.add(bar);

        layout.add( topRightPanel );
        return layout;
    }

    private Component createDrawerContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();

        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "HelloCar logo"));
        logoLayout.add(new H1("HelloCar"));

        // Hinzufügen des Menus inklusive der Tabs
        layout.add(logoLayout);
        return layout;
    }

    private void navigateToLogin() {
        UI ui = this.getUI().get();
        ui.getPage().setLocation("/");
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab,Class.class, navigationTarget);
        return tab;
    }

    private Component createTabs() {
        FormLayout formLayout = new FormLayout();


        Tabs tabs = new Tabs(details, payment);
        tabs.addThemeVariants(LUMO_MINIMAL);
        formLayout.add(tabs);
        return formLayout;
    }
}