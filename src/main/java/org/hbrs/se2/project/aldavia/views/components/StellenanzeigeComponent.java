package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.List;
import java.util.Set;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;

public class StellenanzeigeComponent extends VerticalLayout implements ProfileComponent{
    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Set<Stellenanzeige> stellenanzeigeSet;
    private Div displayStellenanzeige;
    private HorizontalLayout createStellenanzeige;
    private H2 title;
    private Span noStellenanzeige;

    public StellenanzeigeComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        stellenanzeigeSet = unternehmenProfileDTO.getStellenanzeigen();
        displayStellenanzeige = new Div();
        displayStellenanzeige.addClassName("displayStellenanzeige");
        createStellenanzeige = new HorizontalLayout();
        createStellenanzeige.addClassName("createStellenanzeige" );
        noStellenanzeige = new Span("Keine Stellenanzeige vorhanden");
        noStellenanzeige.addClassName("noStellenanzeige");
        addClassName("stellenanzeige");
        setUpUI();
    }

    private void setUpUI(){
        title = new H2("Stellenanzeige");
        createStellenanzeige.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        add(title);
        add(createStellenanzeige);
        add(displayStellenanzeige);
        displayStellenanzeige.add(noStellenanzeige);
        noStellenanzeige.setVisible(false);
        updateView();
    }

    private void updateView(){
        displayStellenanzeige.removeAll();
        createStellenanzeige.setVisible(false);
        if(stellenanzeigeSet.isEmpty()) {
            noStellenanzeige.setVisible(true);
            noStellenanzeige.getStyle().set("font-style", "italic");
        }else{
            noStellenanzeige.setVisible(false);
            getAndCreateStellenanzeige(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEdit(){
        createStellenanzeige.setVisible(true);
        noStellenanzeige.setVisible(false);
        getAndCreateStellenanzeige(Globals.ProfileViewMode.EDIT);
    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
        updateUnternehmenProfileDTO();
        updateView();

    }

    @Override
    public void switchEditMode() {
        updateEdit();
    }

    private void updateUnternehmenProfileDTO() throws ProfileException {
        unternehmenProfileDTO.setStellenanzeigen(stellenanzeigeSet);
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO,getCurrentUserName());
    }

    private void getAndCreateStellenanzeige(String mode){
        for(Stellenanzeige stellenanzeige : stellenanzeigeSet){
            displayStellenanzeige.add(renderStellenanzeige(stellenanzeige,mode));
        }
    }

    private VerticalLayout renderStellenanzeige(Stellenanzeige stellenanzeige, String mode){
        VerticalLayout stellenanzeigenLayout = new VerticalLayout();
        stellenanzeigenLayout.addClassName("stellenanzeigenLayout");
        stellenanzeigenLayout.addClassName("card");
        HorizontalLayout editAndDeleteStellenanzeigeArea = editAndDeleteStellenanzeigeArea(stellenanzeige);
        stellenanzeigenLayout.add(editAndDeleteStellenanzeigeArea);

        if(mode.equals(Globals.ProfileViewMode.EDIT)) {
            editAndDeleteStellenanzeigeArea.setVisible(true);
        } else {
            editAndDeleteStellenanzeigeArea.setVisible(false);
        }

        VerticalLayout stellenanzeigeInfoLeft = new VerticalLayout();
        stellenanzeigeInfoLeft.addClassName("stellenanzeigeInfoLeft");

        Span stellenanzeigeTitel = new Span(stellenanzeige.getBezeichnung());
        stellenanzeigeTitel.addClassName("stellenanzeigeTitel");

        Span stellenanzeigeBeschaeftigungsverheltnis = new Span(stellenanzeige.getBeschaeftigungsverhaeltnis());
        stellenanzeigeBeschaeftigungsverheltnis.addClassName("stellenanzeigeBeschaeftigungsverheltnis");

        stellenanzeigeInfoLeft.add(stellenanzeigeTitel, stellenanzeigeBeschaeftigungsverheltnis, renderTaetigkeit(stellenanzeige.getTaetigkeitsfelder()));

        Details stellenanzeigeCapsul = new Details(stellenanzeigeInfoLeft,bewerbungenScroler(stellenanzeige.getBewerbungen()));
        stellenanzeigeCapsul.addThemeVariants(DetailsVariant.REVERSE);
        stellenanzeigenLayout.add(stellenanzeigeCapsul);
        return stellenanzeigenLayout;
    }

    private HorizontalLayout renderTaetigkeit(List<Taetigkeitsfeld> taetigkeitsfeldList){
        HorizontalLayout taetigkeitLayout = new HorizontalLayout();
        taetigkeitLayout.addClassName("kenntnisseLayout");
        if(taetigkeitsfeldList.size()>3){
            for (int i = 0; i < 3; i++){
                Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldList.get(i);
                Span taetigkeit = new Span(taetigkeitsfeld.getBezeichnung());
                taetigkeit.addClassName("stellenanzeige-taetigkeit");
                taetigkeitLayout.add(taetigkeit);
            }
            Span restlicheTaetigkeiten = new Span("+" + (taetigkeitsfeldList.size()-3) + " weitere");
            restlicheTaetigkeiten.addClassName("stellenanzeige-taetigkeit");
            taetigkeitLayout.add(restlicheTaetigkeiten);
        } else {
            for (Taetigkeitsfeld taetigkeitsfeld : taetigkeitsfeldList){
                Span taetigkeit = new Span(taetigkeitsfeld.getBezeichnung());
                taetigkeit.addClassName("stellenanzeige-taetigkeit");
                taetigkeitLayout.add(taetigkeit);
            }
        }

        return taetigkeitLayout;
    }

    private Scroller bewerbungenScroler(List<Bewerbung> bewerbungList){
        Scroller bewerbungScroller = new Scroller();
        VerticalLayout bewerbungLayout = new VerticalLayout();
        bewerbungLayout.addClassName("bewerbungLayout");
        for (Bewerbung bewerbung : bewerbungList){
            bewerbungLayout.add(renderBewerbung(bewerbung));
        }
        return bewerbungScroller;
    }


    //TODO: Bewerbung rendern welche Daten?
    private VerticalLayout renderBewerbung(Bewerbung bewerbung){
        VerticalLayout bewerbungLayout = new VerticalLayout();
        bewerbungLayout.addClassName("bewerbungLayout");
        Span bewerbungDatum = new Span(bewerbung.getDatum().toString());
        bewerbungLayout.add(bewerbungDatum);
        return bewerbungLayout;
    }

    private HorizontalLayout editAndDeleteStellenanzeigeArea(Stellenanzeige stellenanzeigen) {
        HorizontalLayout editAndDeleteStellenanzeigeArea = new HorizontalLayout();
        editAndDeleteStellenanzeigeArea.addClassName("editAndDeleteStellenanzeigeArea");
        editAndDeleteStellenanzeigeArea.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return editAndDeleteStellenanzeigeArea;

    }

    private void deleteStellenanzeige(Stellenanzeige stellenanzeige){
        stellenanzeigeSet.remove(stellenanzeige);
        updateView();
    }

    private void editStellenanzeige(Stellenanzeige stellenanzeige){

    }
}
