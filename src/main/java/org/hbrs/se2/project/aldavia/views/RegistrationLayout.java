package org.hbrs.se2.project.aldavia.views;


import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.hbrs.se2.project.aldavia.control.RegistrationControl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static com.vaadin.flow.component.tabs.TabsVariant.LUMO_MINIMAL;

@CssImport("./styles/views/regView/reg-view.css")
@Route(value = "registration", layout = NeutralLayout.class)
@JsModule("./styles/shared-styles.js")
public class RegistrationLayout extends VerticalLayout {

    private final Tabs tabs = new Tabs();
    private final Div pages = new Div();
    private final Map<Tab, Div> tabsToPages = new HashMap<>();

    private final RegistrationControl regControl;


    @Autowired
    public RegistrationLayout(RegistrationControl regControl) {
        this.regControl = regControl;
        this.setId("reg-view");
        pages.setClassName("pages");
        tabs.setClassName("reg-tabs");


        setBackgroundImage();
        setUpUI();
    }

    private void setUpUI() {

        createTab("Student", new RegistrationViewStudent(regControl));
        createTab("Company", new RegViewCompany(regControl));

        //Default tab
        tabsToPages.get(tabs.getComponentAt(0)).setVisible(true);
        tabsToPages.get(tabs.getComponentAt(1)).setVisible(false);

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        FlexLayout layout = new FlexLayout();
        layout.setId("flex-layout");
        layout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        layout.setAlignItems(FlexLayout.Alignment.START);

        tabs.getElement().getStyle().set("position", "relative");
        tabs.getElement().getStyle().set("left", "50px"); // 50px nach rechts verschieben

        layout.add(tabs, pages);


        add(layout);
    }

    private void createTab(String title, Div page){
        Tab tab = new Tab(title);
        tab.setClassName("reg-tab");
        tabs.add(tab);
        tabsToPages.put(tab, page);
        pages.add(page);

    }

    private void setBackgroundImage() {

        this.getElement().getStyle().set("background-image", "url('images/registration.png')");
        this.getElement().getStyle().set("background-size", "cover");
        this.getElement().getStyle().set("background-position", "center");

    }



}