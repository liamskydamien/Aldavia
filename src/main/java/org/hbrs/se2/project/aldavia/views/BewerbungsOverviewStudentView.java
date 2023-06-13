package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewStudent;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.List;

@Route(value = "bewerbungsOverviewStudent", layout = LoggedInStateLayout.class)
public class BewerbungsOverviewStudentView extends Div {

    private final BewerbungsOverviewStudent bewerbungsOverviewStudent;
    private UserDTO currentUser;

    public BewerbungsOverviewStudentView(BewerbungsOverviewStudent bewerbungsOverviewStudent) {
        this.bewerbungsOverviewStudent = bewerbungsOverviewStudent;
        currentUser = getCurrentUser();
        setUpUI();
    }

    private void setUpUI() {
        add(new H1("Deine Bewerbungen"));
        add(createBewerbungenLayout());
    }

    private VerticalLayout createBewerbungenLayout(){
        try {
            List<BewerbungsDataDTO> bewerbungen = bewerbungsOverviewStudent.getBewerbungenStudent(currentUser.getUserid());
            if(bewerbungen.isEmpty()){
                return new VerticalLayout(new H3("Du hast dich noch auf keine Stellenanzeige beworben."));
            }
            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setId("header-neutral");
            for (BewerbungsDataDTO bewerbung : bewerbungen) {
                layout.add(createBewerbung(bewerbung));
            }
            return layout;
        }
        catch (Exception e){
            Notification.show(e.toString());
        }
        return new VerticalLayout();
    }

    private Div createBewerbung(BewerbungsDataDTO bewerbung){
        Div div = new Div();
        div.add(StellenanzeigeBewerbungsLayout(bewerbung));
        return div;
    }

    private HorizontalLayout StellenanzeigeBewerbungsLayout(BewerbungsDataDTO bewerbung){
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("stellenanzeige-bewerbungs-layout");
        layout.add(createStellenanzeigenInfosLayout(bewerbung));
        layout.add(createBewerbungsstatus(bewerbung));
        return layout;
    }

    private VerticalLayout createBewerbungsstatus(BewerbungsDataDTO bewerbungsDataDTO){
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("bewerbungsstatus-layout");
        layout.add(new Label("Beworben am: " + bewerbungsDataDTO.getDatum().toString()));
        switch (bewerbungsDataDTO.getStatus()){
           case "zusage":
                layout.add(new Icon(VaadinIcon.CHECK));
                break;
          case "absage":
                layout.add(new Icon(VaadinIcon.CLOSE));
                break;
          default:
                layout.add(new Icon(VaadinIcon.CLOCK));
                break;
        }
        return layout;
    }

    private VerticalLayout createStellenanzeigenInfosLayout(BewerbungsDataDTO bewerbung){
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("stellenanzeigen-infos-layout");
        Anchor anchor = new Anchor();
        anchor.setText(bewerbung.getUnternehmen().getName());
        anchor.setHref(bewerbung.getUnternehmen().getProfileLink());
        layout.add(anchor);
        layout.add(new H2(bewerbung.getStellenanzeige().getBezeichnung()));
        layout.add(new H3(bewerbung.getStellenanzeige().getBeschaeftigungsverhaeltnis()));
        layout.add(createVonBis(bewerbung.getStellenanzeige().getStart().toString(), bewerbung.getStellenanzeige().getEnde().toString()));
        layout.add(createTaetigkeitenLayout(bewerbung));
        return layout;
    }

    private HorizontalLayout createVonBis(String von, String bis){
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("von-bis-layout");
        layout.add(new Label("Von " + von));
        layout.add(new Label("Bis " + bis));
        return layout;
    }

    private HorizontalLayout createTaetigkeitenLayout(BewerbungsDataDTO bewerbung){
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("taetigkeiten-layout");
        layout.add(new Label("Tätigkeiten: "));
        for (TaetigkeitsfeldDTO taetigkeit : bewerbung.getStellenanzeige().getTaetigkeitsfelder()) {
            Div div = new Div();
            div.setText(taetigkeit.getName());
            layout.add(div);
        }
        return layout;
    }



    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
