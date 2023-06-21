package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.RendererUtil;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.dtos.CarDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.service.StellenanzeigeService;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.views.components.SearchComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CssImport("./styles/views/main/main-view.css")
public class SearchView extends VerticalLayout {

    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");

    private SearchComponent searchComponent;

    private List<StellenanzeigeDTO> stellenanzeigeList;


    public SearchView(SearchControl searchControl) {
        searchButton.setId("search-button");
        setId("main-view");
        setBackgroundImage();
        searchComponent = new SearchComponent(searchControl);
        HorizontalLayout grid = new HorizontalLayout();
        grid.setSizeFull();
        Component ui = setUpUI();
        grid.add(ui);
        grid.setWidth("2600px");



        stellenanzeigeList = searchControl.getAllStellenanzeigen();
        if (stellenanzeigeList != null) {
            grid.add(searchComponent);
        }
        grid.setJustifyContentMode(JustifyContentMode.END);
        add(grid);


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
        this.getElement().getStyle().set("background-size", "cover");
        this.getElement().getStyle().set("background-position", "center");

    }


}
