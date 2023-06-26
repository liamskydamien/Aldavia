package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.views.components.SearchComponent;


import java.util.List;

@CssImport("./styles/views/main/main-view.css")
public class SearchView extends VerticalLayout {

    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Search");

    private final SearchComponent searchComponent;


    public SearchView(SearchControl searchControl) {
        searchComponent = new SearchComponent(searchControl);
        VerticalLayout gridVertical = new VerticalLayout();
        this.addClassName("search-view");
        add(gridVertical);
        gridVertical.add(setUpUI());
        searchButton.setId("search-button");
        setId("main-view");
        setBackgroundImage();

        List<StellenanzeigeDTO> stellenanzeigeList = searchControl.getAllStellenanzeigen();
        if (stellenanzeigeList != null) {
            gridVertical.add(searchComponent);
        }

        stellenanzeigeList = searchControl.getAllStellenanzeigen();

        searchField.addValueChangeListener(e -> {
            searchComponent.updateList(searchField.getValue());

        });
    }

    private Component setUpUI() {
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

        return searchLayout;
    }

    private void setBackgroundImage() {
        this.getElement().getStyle().set("background-image", "url('images/Home.png')");
    }


}
