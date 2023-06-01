package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = Globals.Pages.PROFILE_VIEW, layout = LoggedInStateLayout.class)
@PageTitle("Profil")
public class StudentProfileView extends Div implements HasUrlParameter<String> {

    @Autowired
    private StudentProfileControl studentProfileControl;
    private H1 title = new H1("Profil");
    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();
        List<String> usernames = parametersMap.get("username");
        if(usernames != null && !usernames.isEmpty()){
            addTextToView(usernames.get(0));
        } else {
            // Handle the case where there is no "username" parameter or the list is empty
            add(new Text("Fehler beim Laden des Profils"));
        }
    }
    public StudentProfileView(StudentProfileControl studentProfileControl) {
        addClassName("profile-view");
        add(title);
    }

    public void addTextToView(String text) {
        try{
        add(new Text(studentProfileControl.getStudentProfile(text).toString()));
        }catch (Exception e){
            add(new Text("Fehler beim Laden des Profils"));
        }
    }


    // TEST TEST

}
