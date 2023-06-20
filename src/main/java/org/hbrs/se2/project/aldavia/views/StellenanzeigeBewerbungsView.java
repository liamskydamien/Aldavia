package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Route(value = "stellenanzeigeBewerbungsView", layout = LoggedInStateLayout.class)
@Component
//TODO: Stellenanzeige DTO umwandeln
public class StellenanzeigeBewerbungsView extends VerticalLayout {
    private Stellenanzeige stellenanzeige;

    @Autowired
    public StellenanzeigeBewerbungsView(Stellenanzeige stellenanzeige) {
        addClassName("stellenanzeigeBewerbungsView");
        this.stellenanzeige = stellenanzeige;
    }
}
