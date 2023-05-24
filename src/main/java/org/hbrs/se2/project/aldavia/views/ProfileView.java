package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "profile")
@PageTitle("Profil")
public class ProfileView extends Div implements HasUrlParameter<String> {

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
        addTextToView(parametersMap.get("username").get(0));
    }
    public ProfileView(StudentProfileControl studentProfileControl) {
        addClassName("profile-view");
        add(title);
    }

    public void addTextToView(String text) {
        try{
        add(new Text(makeDataVisible(studentProfileControl.getStudentProfile(text))));
        }catch (Exception e){
            add(new Text("Fehler beim Laden des Profils"));
        }
    }

    private String makeDataVisible(StudentProfileDTO data){
        String result = "";
        result += "Vorname: " + data.getVorname() + " Nachname:" + data.getNachname() +"\n";
        result += "Email: " + data.getEmail() + "\n";
        result += "Telefonnummer: " + data.getTelefonnummer() + "\n";
        result += "Geburtsdatum: " + data.getGeburtsdatum() + "\n";
        result += "Studiengang: " + data.getStudiengang() + "\n";
        result += "Studienbeginn: " + data.getStudienbeginn() + "\n";
        result += "Beschreibung: " + data.getBeschreibung() + "\n";
        result += "Kenntnisse: " + "\n";
        for(KenntnisDTO kenntnis : data.getKenntnisse()){
            result += kenntnis.getName() + "\n";
        }
        result += "Qualifikationen: " + "\n";
        for(QualifikationsDTO qualifikation : data.getQualifikationen()){
            result += qualifikation.getBezeichnung() + "\n";
            result += qualifikation.getBeschreibung() +"\n";
            result += "Von: " + qualifikation.getVon() + " Bis: " + qualifikation.getBis() + "\n";
            result += "Ort: " + qualifikation.getInstitution() + "\n";
        }
        result += "Sprachen: " + "\n";
        for(SpracheDTO sprache : data.getSprachen()){
            result += sprache.getName() + " auf Niveau von " + sprache.getLevel() + "\n";
        }
        result += "Interessiert sich für Tätigkeitsfelder: " + "\n";
        for(TaetigkeitsfeldDTO taetigkeitsfeld : data.getTaetigkeitsfelder()){
            result += taetigkeitsfeld.getName() + "\n";
        }
        return result;
    }



    // TEST TEST

}
