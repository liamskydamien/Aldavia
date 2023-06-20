package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;
@Transactional
public class StellenanzeigeComponent extends VerticalLayout implements ProfileComponent{
    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Set<Stellenanzeige> stellenanzeigeSet;
    private Div displayStellenanzeige;
    private HorizontalLayout createStellenanzeige;
    private H2 title;
    private Span noStellenanzeige;
    private Dialog editDialog;
    private Dialog addDialog;

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
        noStellenanzeige.addClassName("card");
        editDialog = new Dialog();
        addDialog = new Dialog();
        addClassName("stellenanzeige");
        setUpUI();
    }

    private void setUpUI(){
        title = new H2("Stellenanzeige");
        createStellenanzeige.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        createStellenanzeige.add(addStellenanzeige());
        createStellenanzeige.setWidthFull();
        displayStellenanzeige.setWidthFull();
        add(title);
        add(createStellenanzeige);
        add(displayStellenanzeige);
        displayStellenanzeige.add(noStellenanzeige);
        createStellenanzeige.setVisible(true);
        updateView();
    }

    private void updateView(){

        System.out.println(stellenanzeigeSet);
        System.out.println(stellenanzeigeSet.isEmpty());
        System.out.println(stellenanzeigeSet.size());
        if(stellenanzeigeSet.isEmpty()) {
            displayStellenanzeige.add(noStellenanzeige);
            noStellenanzeige.setVisible(true);
            noStellenanzeige.getStyle().set("font-style", "italic");
            noStellenanzeige.setWidthFull();
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
        displayStellenanzeige.removeAll();
        for(Stellenanzeige stellenanzeige : stellenanzeigeSet){
            displayStellenanzeige.add(renderStellenanzeige(stellenanzeige,mode));
        }
    }

    private VerticalLayout renderStellenanzeige(Stellenanzeige stellenanzeige, String mode){
        //Main Body
        VerticalLayout stellenanzeigenLayout = new VerticalLayout();
        stellenanzeigenLayout.addClassName("stellenanzeigenLayout");
        stellenanzeigenLayout.addClassName("card");
        stellenanzeigenLayout.setWidthFull();

        //Edit Header
        HorizontalLayout editAndDeleteStellenanzeigeArea = new HorizontalLayout();
        editAndDeleteStellenanzeigeArea.addClassName("editAndDeleteStellenanzeigeArea");
        editAndDeleteStellenanzeigeArea.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        editAndDeleteStellenanzeigeArea.setWidthFull();
        editAndDeleteStellenanzeigeArea.add(editStellenanzeige(stellenanzeige),deleteStellenanzeige(stellenanzeige));
        stellenanzeigenLayout.add(editAndDeleteStellenanzeigeArea);

        if(mode.equals(Globals.ProfileViewMode.EDIT)) {
            editAndDeleteStellenanzeigeArea.setVisible(true);
        } else {
            editAndDeleteStellenanzeigeArea.setVisible(false);
        }

        //Info Body
        HorizontalLayout stellenanzeigeInfo = new HorizontalLayout();
        stellenanzeigeInfo.addClassName("stellenanzeigeInfo");
        stellenanzeigeInfo.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        stellenanzeigeInfo.setWidthFull();

        VerticalLayout stellenanzeigeInfoLeft = new VerticalLayout();
        stellenanzeigeInfoLeft.addClassName("stellenanzeigeInfoLeft");
        Span stellenanzeigeTitel = new Span(stellenanzeige.getBezeichnung());
        stellenanzeigeTitel.addClassName("stellenanzeigeTitel");
        Span stellenanzeigeBeschaeftigungsverheltnis = new Span(stellenanzeige.getBeschaeftigungsverhaeltnis());
        stellenanzeigeBeschaeftigungsverheltnis.addClassName("stellenanzeigeBeschaeftigungsverheltnis");
        stellenanzeigeInfoLeft.add(stellenanzeigeTitel, stellenanzeigeBeschaeftigungsverheltnis, renderTaetigkeit(stellenanzeige.getTaetigkeitsfelder()));

        VerticalLayout stellenanzeigeInfoRight = new VerticalLayout();
        stellenanzeigeInfoRight.addClassName("stellenanzeigeInfoRight");
        Span stellenanzeigeDatum = new Span(stellenanzeige.getErstellungsdatum().toString());
        stellenanzeigeDatum.addClassName("stellenanzeigeDatum");
        stellenanzeigeInfoRight.add(stellenanzeigeDatum);


        stellenanzeigeInfo.add(stellenanzeigeInfoLeft, stellenanzeigeInfoRight);
        stellenanzeigenLayout.add(stellenanzeigeInfo);
        return stellenanzeigenLayout;
    }

    private HorizontalLayout renderTaetigkeit(List<Taetigkeitsfeld> taetigkeitsfeldList){
        HorizontalLayout taetigkeitLayout = new HorizontalLayout();
        taetigkeitLayout.addClassName("kenntnisseLayout");
        if(taetigkeitsfeldList.size()>3){
            for (int i = 0; i < 3; i++){
                Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldList.get(i);
                Span taetigkeit = new Span(taetigkeitsfeld.getBezeichnung());
                taetigkeit.getElement().getThemeList().add("badge pill");
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
                taetigkeit.getElement().getThemeList().add("badge pill");
                taetigkeitLayout.add(taetigkeit);
            }
        }

        return taetigkeitLayout;
    }

    private void navigateToBewerbung(Stellenanzeige stellenanzeige){
        UI.getCurrent().navigate(Globals.Pages.STELLENANZEIGE_BEWERBUNGEN_VIEW+"/" + stellenanzeige.getId());
    }

    //TODO: Woanders hin?

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
        editAndDeleteStellenanzeigeArea.add(editStellenanzeige(stellenanzeigen), deleteStellenanzeige(stellenanzeigen));
        return editAndDeleteStellenanzeigeArea;

    }

    private Button deleteStellenanzeige(Stellenanzeige stellenanzeige){
        Button deleteStellenanzeige = new Button(new Icon("lumo", "cross"));
        deleteStellenanzeige.addClassName("deleteStellenanzeige");
        deleteStellenanzeige.addClickListener(e -> {
            stellenanzeigeSet.remove(stellenanzeige);
            updateView();
        });
        return deleteStellenanzeige;
    }

    private Button editStellenanzeige(Stellenanzeige stellenanzeige){
        Button editStellenanzeige = new Button(new Icon("lumo", "edit"));
        editStellenanzeige.addClassName("editStellenanzeige");
        editStellenanzeige.addClickListener(e -> {
            editPopup(stellenanzeige);
            editDialog.open();
        });
        return editStellenanzeige;
    }

    private void editPopup(Stellenanzeige stellenanzeige){

        editDialog.setCloseOnEsc(false);
        editDialog.setCloseOnOutsideClick(false);
        editDialog.setWidth("400px");
        editDialog.setHeight("150px");
        editDialog.setModal(true);
        editDialog.setCloseOnEsc(true);

        VerticalLayout dialogLayout = new VerticalLayout();
        H1 dialogTitel = new H1("Stellenanzeige bearbeiten");
        dialogLayout.addClassName("dialogLayout");

        //Todo: Formular für Stellenanzeige bearbeiten



        editDialog.add(dialogLayout);
    }

    private Button addStellenanzeige(){
        Button addStellenanzeige = new Button("Erstellen");
        addStellenanzeige.setIcon(new Icon("lumo", "plus"));
        addStellenanzeige.addClassName("addStellenanzeige");
        addStellenanzeige.addClickListener(e -> {
            addPopup();
            addDialog.open();
        });
        return addStellenanzeige;
    }

    private void addPopup() {
        addDialog = new Dialog();
        addDialog.setCloseOnEsc(false);
        addDialog.setCloseOnOutsideClick(false);
        addDialog.setWidth("900px");
        addDialog.setHeight("7000px");
        addDialog.setModal(true);
        addDialog.setCloseOnEsc(true);

        VerticalLayout dialogLayout = new VerticalLayout();
        H1 dialogTitel = new H1("Stellenanzeige hinzufügen");
        dialogLayout.addClassName("dialogLayout");
        dialogLayout.add(dialogTitel);


        // Form Layout
       AddStellenanzeigeFormComponent addStellenanzeigeFormComponent = new AddStellenanzeigeFormComponent();
        dialogLayout.add(addStellenanzeigeFormComponent);

        // Buttons
        HorizontalLayout closePopUpLayout = new HorizontalLayout();
        closePopUpLayout.setJustifyContentMode(JustifyContentMode.END);
        closePopUpLayout.add(closeAddPopUpButton());

        HorizontalLayout saveLayout = new HorizontalLayout();
        saveLayout.setJustifyContentMode(JustifyContentMode.END);
        saveLayout.add(saveStellenanzeigeButton(addStellenanzeigeFormComponent));

        addDialog.add(closePopUpLayout);
        addDialog.add(dialogLayout);
        addDialog.add(saveLayout);
    }

    private Button closeAddPopUpButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addClassName("closeButton");
        closeButton.addClickListener(e -> {
            addDialog.close();
        });
        return closeButton;
    }

    private Button saveStellenanzeigeButton(AddStellenanzeigeFormComponent addStellenanzeigeFormComponent) {
        Button addStellenanzeigeButton = new Button("Erstellen");
        addStellenanzeigeButton.addClassName("addStellenanzeigeButton");
        addStellenanzeigeButton.addClickListener(e -> {
            Stellenanzeige stellenanzeige = new Stellenanzeige();
            stellenanzeige.setBezeichnung(addStellenanzeigeFormComponent.getBezeichnung().getValue());
            stellenanzeige.setBeschreibung(addStellenanzeigeFormComponent.getBeschreibung().getValue());
            stellenanzeige.setEnde(addStellenanzeigeFormComponent.getEnde().getValue());
            stellenanzeige.setStart(addStellenanzeigeFormComponent.getStart().getValue());
            stellenanzeige.setTaetigkeitsfelder(addStellenanzeigeFormComponent.getTaetigkeitsfelder());
            stellenanzeige.setBeschaeftigungsverhaeltnis(addStellenanzeigeFormComponent.getBeschaeftigungsverhaeltnis().getValue());
            stellenanzeige.setBezahlung(addStellenanzeigeFormComponent.getBezahlung().getValue());
            stellenanzeige.setErstellungsdatum(LocalDate.now());
            stellenanzeigeSet.add(stellenanzeige);
            try {
                updateUnternehmenProfileDTO();
            } catch (ProfileException ex) {
                throw new RuntimeException(ex);
            }
            updateView();
            addDialog.close();
        });
        return addStellenanzeigeButton;
    }
}
