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


@CssImport("./styles/views/main/main-view.css")
@Route(value = Globals.Pages.MAIN_VIEW, layout = NeutralLayout.class)
@PWA(name = "Aldavia", shortName = "Aldavia", enableInstallPrompt = false)
public class HomeView extends SearchView {

    public HomeView(){
        super();
    }

}
