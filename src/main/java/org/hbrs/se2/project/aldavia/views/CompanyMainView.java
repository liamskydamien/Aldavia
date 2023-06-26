package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.util.Globals;
@CssImport("./styles/views/main/main-view.css")
@Route(value = Globals.Pages.COMPANY_MAIN, layout = LoggedInStateLayout.class)
public class CompanyMainView extends VerticalLayout {
    public CompanyMainView() {
        addClassName("main-view");
        Button addStellenanzeigeButton = new Button("Erstellen");
        addStellenanzeigeButton.addClassName("addStellenanzeigeButton");
        addStellenanzeigeButton.addClickListener(e -> {
            addStellenanzeigeButton.getUI().ifPresent(ui -> ui.navigate(Globals.Pages.STELLENANZEIGE_ERSTELLEN_VIEW));
        });

        this.getElement().getStyle().set("background-image", "url('images/CompanyMain.png')");
        add(addStellenanzeigeButton);

    }

}
