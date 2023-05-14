package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.ProfileControl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "profile")
@PageTitle("Profil")
public class ProfileView extends Div implements HasUrlParameter<String> {

    @Autowired
    private ProfileControl profileControl;
    private H1 title = new H1("Profil");
    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();
        addTextToView(parametersMap.get("username").get(0));
    }
    public ProfileView(ProfileControl profileControl) {
        addClassName("profile-view");
        add(title);
    }

    public void addTextToView(String text) {
        add(new Text(profileControl.getStudentProfile(text).toString()));
    }


    // TEST TEST

}
