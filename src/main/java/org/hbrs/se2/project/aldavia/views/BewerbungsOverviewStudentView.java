package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewStudent;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.List;

@Route(value = "bewerbungsOverviewStudent", layout = LoggedInStateLayout.class)
public class BewerbungsOverviewStudentView extends Div {

    private BewerbungsOverviewStudent bewerbungsOverviewStudent;
    private UserDTO currentUser;

    public BewerbungsOverviewStudentView() {
        this.bewerbungsOverviewStudent = new BewerbungsOverviewStudent(new StudentService(), new BewerbungsService());
        currentUser = getCurrentUser();
        setUpUI();
    }

    private void setUpUI() {
        add(new H1("Deine Bewerbungen"));
        //List<BewerbungsDTO>
    }

    private VerticalLayout createBewerbungenLayout(){
        try {
            //List<BewerbungsDTO> bewerbungen = bewerbungsOverviewStudent.getBewerbungen(currentUser.getUserid());
            VerticalLayout layout = new VerticalLayout();
            layout.setSizeFull();
            layout.setId("header-neutral");
            return layout;
        }
        catch (Exception e){
            throw new RuntimeException("Bewerbungen konnten nicht geladen werden");
        }
    }

    private Div createBewerbung(BewerbungsDTO bewerbung){
        Div div = new Div();
        //div.setText(bewerbung.getStellenanzeige().getTitel());
        return div;
    }



    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
