package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.util.Globals;

@Route(value = Globals.Pages.STUDENT_MAIN, layout = LoggedInStateLayout.class)
public class StudentMainView extends SearchView {
    public StudentMainView() {
        super();
    }
}
