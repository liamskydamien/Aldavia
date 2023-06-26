package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = Globals.Pages.NOT_LOGIN_COMPANY_VIEW, layout = NeutralLayout.class)
@CssImport("./styles/views/profile/studentProfile.css")
public class NotLogInCompanyView extends Div implements HasUrlParameter<String> {
    String url;
    UnternehmenProfileControl unternehmenProfileControl;

    @SneakyThrows
    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        url = beforeEvent.getLocation().getPath();
        add(new UnternehmenProfileView(unternehmenProfileControl, url));
    }

    @Autowired
    public NotLogInCompanyView(UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
    }


}
