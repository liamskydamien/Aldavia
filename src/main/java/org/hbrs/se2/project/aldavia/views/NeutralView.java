package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

@PWA(name = "Aldavia", shortName = "Aldavia", enableInstallPrompt = false)
@CssImport("./styles/views/neutralView/neutral-view.css")
public class NeutralView extends AppLayout {

    private Tabs menu;

    public NeutralView() {
        setUpUI();
    }

    public void setUpUI() {
        addToNavbar(true, createHeaderContent());
    }

    private Component createHeaderContent() {


        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setId("header-neutral");
        Image logo = new Image("images/aldavia.png", "AldaVia logo");
        navigateHomeLogo(logo);
        layout.add(logo);

        HorizontalLayout topRightLayout = new HorizontalLayout();
        topRightLayout.setId("top-right-layout");
        topRightLayout.setWidthFull();
        topRightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topRightLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        topRightLayout.add(createMenuItems());

        layout.add(topRightLayout);


        return layout;
    }

    private Component[] createMenuItems() {

        Button button = new Button("Sign Up");
        button.setId("sign-up-button");
        Tab[] tabs = new Tab[]{
                createTab("Log In", LoginView.class),
                createButtonInTab(button, AppView.class )
        };
        tabs[0].setId("login-tab");
        tabs[1].setId("sign-up-tab");
        return tabs;
    }



    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        return tab;
    }

    private static Tab createButtonInTab(Button button, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate(navigationTarget)));
        tab.add(button);
        return tab;
    }

    //TODO cnavigate actually to the landing page
    private static void navigateHomeLogo(Image img) {
        img.addClickListener(event -> img.getUI().ifPresent(ui -> ui.navigate("")));
    }
}

