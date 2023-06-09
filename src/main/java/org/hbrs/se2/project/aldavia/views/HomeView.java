package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.dependency.CssImport;
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
