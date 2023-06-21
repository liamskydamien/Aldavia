package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.util.Globals;


@CssImport("./styles/views/main/main-view.css")
@Route(value = Globals.Pages.MAIN_VIEW, layout = NeutralLayout.class)
@PWA(name = "Aldavia", shortName = "Aldavia", enableInstallPrompt = false)
@RouteAlias(value = "start")
public class HomeView extends SearchView {

    public HomeView(SearchControl searchControl){
        super(searchControl);
    }

}
