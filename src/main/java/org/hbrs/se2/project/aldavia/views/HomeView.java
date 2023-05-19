package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.hbrs.se2.project.aldavia.util.Globals;

@PWA(name = "Aldavia", shortName = "Aldavia", enableInstallPrompt = false)
@CssImport("./styles/views/main/main-view.css")
@Route(value = Globals.Pages.MAIN_VIEW, layout = NeutralLayout.class)

public class HomeView extends VerticalLayout {

    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");

    public HomeView() {
        searchButton.setId("search-button");
        setId("main-view");
        setBackgroundImage();
        setUpUI();
    }

    private void setUpUI() {
        this.setHeightFull();
        this.setSizeFull();


        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setId("search-layout");
        searchLayout.setAlignItems(Alignment.START);
        searchLayout.setJustifyContentMode(JustifyContentMode.START);
        searchLayout.setWidth("50%");

        searchField.setPlaceholder("Search");
        searchField.setClearButtonVisible(true);
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setSuffixComponent(searchButton);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setClassName("search-field");
        searchField.setWidth("100%");

        searchLayout.add(searchField);
        add(searchLayout);
    }



    private void setBackgroundImage() {

        this.getElement().getStyle().set("background-image", "url('images/Home.png')");
        this.getElement().getStyle().set("background-size", "cover");
        this.getElement().getStyle().set("background-position", "center");

    }


}
