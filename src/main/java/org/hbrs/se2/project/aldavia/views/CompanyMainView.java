package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.util.Globals;

@Route(value = Globals.Pages.COMPANY_MAIN, layout = LoggedInStateLayout.class)
public class CompanyMainView extends VerticalLayout {
    public CompanyMainView() {
        add(new Text("CompanyMainView "));
    }
}
