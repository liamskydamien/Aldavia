package org.hbrs.se2.project.aldavia.views.components;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;
@Transactional
@CssImport("./styles/views/profile/studentProfile.css")
public class StellenanzeigeComponent extends VerticalLayout implements ProfileComponent{
    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private Set<StellenanzeigeDTO> stellenanzeigeSet;
    private Div displayStellenanzeige;
    private HorizontalLayout createStellenanzeige;
    private H2 title;
    private Span noStellenanzeige;
    private Dialog addDialog;
    private Button addStellenanzeige;
    private String url;
    private HorizontalLayout noStellenanzeigeArea;

    public StellenanzeigeComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl, String url) {
        this.unternehmenProfileControl = unternehmenProfileControl;
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        this.url = url;
        stellenanzeigeSet = unternehmenProfileDTO.getStellenanzeigen();
        displayStellenanzeige = new Div();
        displayStellenanzeige.addClassName("displayStellenanzeige");
        noStellenanzeige = new Span("Keine Stellenanzeige vorhanden");
        noStellenanzeige.addClassName("noStellenanzeige");
        noStellenanzeige.addClassName("card");
        noStellenanzeigeArea = new HorizontalLayout();
        addDialog = new Dialog();
        addClassName("stellenanzeige");
        setUpUI();
    }


    public void setUpUI(){
        title = new H2("Stellenanzeige");
        add(title);
        add(createAddstellenanzeigeArea());
        add(displayStellenanzeige);
        displayStellenanzeige.setWidthFull();
        noStellenanzeigeArea.add(noStellenanzeige);
        noStellenanzeigeArea.setWidthFull();
        displayStellenanzeige.add(noStellenanzeigeArea);
        updateView();
    }


    public void updateView(){

        createStellenanzeige.setVisible(true);
        if(stellenanzeigeSet.isEmpty()) {
            displayStellenanzeige.add(noStellenanzeigeArea);
            noStellenanzeigeArea.setVisible(true);
            noStellenanzeige.getStyle().set("font-style", "italic");
            noStellenanzeigeArea.setWidthFull();
        }else{
            noStellenanzeigeArea.setVisible(false);
            createStellenanzeige.setVisible(true);
            getAndCreateStellenanzeige(Globals.ProfileViewMode.VIEW);
        }

    }

    private void updateEdit(){
        noStellenanzeigeArea.setVisible(false);
        createStellenanzeige.setVisible(false);
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
@Transactional
public void getAndCreateStellenanzeige(String mode){
        displayStellenanzeige.removeAll();
        for(StellenanzeigeDTO stellenanzeige : stellenanzeigeSet){
            displayStellenanzeige.add(renderStellenanzeige(stellenanzeige,mode));
        }
    }
    @Transactional
    public VerticalLayout renderStellenanzeige(StellenanzeigeDTO stellenanzeige, String mode){
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
        editAndDeleteStellenanzeigeArea.add(deleteStellenanzeige(stellenanzeige));
        stellenanzeigenLayout.add(editAndDeleteStellenanzeigeArea);

        if(mode.equals(Globals.ProfileViewMode.EDIT)) {
            editAndDeleteStellenanzeigeArea.setVisible(true);
        } else if(mode.equals(Globals.ProfileViewMode.VIEW)) {
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
        Span zeitraum = new Span();
        if(stellenanzeige.getEnde() == null || stellenanzeige.getEnde().toString().equals("")){
            zeitraum.setText("Ab " + stellenanzeige.getStart().getDayOfYear() + " " + stellenanzeige.getStart().getMonthValue() + " " + stellenanzeige.getStart().getYear());
        } else{
            zeitraum.setText(stellenanzeige.getStart().getDayOfMonth() + " " + stellenanzeige.getStart().getMonthValue() + " " + stellenanzeige.getStart().getYear() + " - " + stellenanzeige.getEnde().getDayOfYear() + " " + stellenanzeige.getEnde().getMonthValue() + " " + stellenanzeige.getEnde().getYear());
        }
        Span bezahlung = new Span("Bezahlung: "+stellenanzeige.getBezahlung());
        bezahlung.addClassName("bezahlung");

        if(stellenanzeige.getBezahlung() != null && !stellenanzeige.getBezahlung().equals("")){
            stellenanzeigeInfoLeft.add(stellenanzeigeTitel, stellenanzeigeBeschaeftigungsverheltnis,zeitraum,bezahlung);

        } else {
            stellenanzeigeInfoLeft.add(stellenanzeigeTitel, stellenanzeigeBeschaeftigungsverheltnis,zeitraum);
        }

        // Unterer Teil der Stellenanzeige
        VerticalLayout stellenanzeigenUnten = new VerticalLayout();
        stellenanzeigenUnten.addClassName("stellenanzeigenUnten");
        stellenanzeigenUnten.setWidthFull();
        Div textBlock = new Div();
        textBlock.addClassName("beschreibung");
        textBlock.setText(stellenanzeige.getBeschreibung());
        Details beschreibugnDetails = new Details("Stellenbeschreibung", textBlock);
        beschreibugnDetails.addThemeVariants(DetailsVariant.REVERSE);

        List<TaetigkeitsfeldDTO> taetigkeitsfeldList = stellenanzeige.getTaetigkeitsfelder();
        stellenanzeigenUnten.add(beschreibugnDetails, renderTaetigkeit(taetigkeitsfeldList));


        HorizontalLayout stellenanzeigeInfoRight = new HorizontalLayout();
        stellenanzeigeInfoRight.addClassName("stellenanzeigeInfoRight");
        stellenanzeigeInfoRight.setJustifyContentMode(JustifyContentMode.END);
        stellenanzeigeInfoRight.setWidthFull();
        Span stellenanzeigeDatum = new Span("Erstellt am: " + stellenanzeige.getErstellungsdatum().getDayOfMonth() + " " + stellenanzeige.getErstellungsdatum().getMonthValue() + " " + stellenanzeige.getErstellungsdatum().getYear());
        stellenanzeigeDatum.addClassName("stellenanzeigeDatum");
        stellenanzeigeInfoRight.add(stellenanzeigeDatum);
        //stellenanzeigeInfoRight.add(bewerbungAnsehenButton(stellenanzeige));


        stellenanzeigeInfo.add(stellenanzeigeInfoLeft, stellenanzeigeInfoRight);
        stellenanzeigenLayout.add(stellenanzeigeInfo, stellenanzeigenUnten);
        return stellenanzeigenLayout;
    }
    @Transactional
    public HorizontalLayout renderTaetigkeit(List<TaetigkeitsfeldDTO> taetigkeitsfeldList){
        HorizontalLayout taetigkeitLayout = new HorizontalLayout();
        taetigkeitLayout.addClassName("kenntnisseLayout");
        if(taetigkeitsfeldList.size()>3){
            for (int i = 0; i < 3; i++){
                TaetigkeitsfeldDTO taetigkeitsfeld = taetigkeitsfeldList.get(i);
                Span taetigkeit = new Span(taetigkeitsfeld.getName());
                taetigkeit.getElement().getThemeList().add("badge pill");
                taetigkeit.addClassName("stellenanzeige-taetigkeit");
                taetigkeitLayout.add(taetigkeit);
            }
            Span restlicheTaetigkeiten = new Span("+" + (taetigkeitsfeldList.size()-3) + " weitere");
            restlicheTaetigkeiten.addClassName("stellenanzeige-taetigkeit");
            taetigkeitLayout.add(restlicheTaetigkeiten);
        } else {
            for (TaetigkeitsfeldDTO taetigkeitsfeld : taetigkeitsfeldList){
                Span taetigkeit = new Span(taetigkeitsfeld.getName());
                taetigkeit.addClassName("stellenanzeige-taetigkeit");
                taetigkeit.getElement().getThemeList().add("badge pill");
                taetigkeitLayout.add(taetigkeit);
            }
        }

        return taetigkeitLayout;
    }

    private HorizontalLayout createAddstellenanzeigeArea(){
        createStellenanzeige = new HorizontalLayout();
        createStellenanzeige.addClassName("createStellenanzeige" );
        createStellenanzeige.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        createStellenanzeige.setWidthFull();
        if(getUserOverUrl().equals(getCurrentUserName())){
            createStellenanzeige.add(addStellenanzeige());
        }

        return createStellenanzeige;
    }


    private Button deleteStellenanzeige(StellenanzeigeDTO stellenanzeige){
        Button deleteStellenanzeige = new Button(new Icon("lumo", "cross"));
        deleteStellenanzeige.addClassName("deleteStellenanzeige");
        deleteStellenanzeige.addClickListener(e -> {
            stellenanzeigeSet.remove(stellenanzeige);
            updateView();
        });
        return deleteStellenanzeige;
    }



    private Button addStellenanzeige(){
        addStellenanzeige = new Button("Erstellen");
        addStellenanzeige.setIcon(new Icon("lumo", "plus"));
        addStellenanzeige.addClassName("addStellenanzeige");
        addStellenanzeige.addClassName("editSaveButton");
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
        addStellenanzeigeButton.addClassName("editSaveButton");
        addStellenanzeigeButton.addClickListener(e -> {
            if(addStellenanzeigeFormComponent.getBezeichnung().getValue().isEmpty()
               || addStellenanzeigeFormComponent.getBeschreibung().getValue().isEmpty()
                  || addStellenanzeigeFormComponent.getBeschaeftigungsverhaeltnis().getValue().isEmpty()
                    || addStellenanzeigeFormComponent.getStart().getValue() == null){
                Notification.show("Bitte füllen Sie alle erforderlichen Felder aus!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            } else {
                StellenanzeigeDTO stellenanzeige = new StellenanzeigeDTO();
                stellenanzeige.setBezeichnung(addStellenanzeigeFormComponent.getBezeichnung().getValue());
                stellenanzeige.setBeschreibung(addStellenanzeigeFormComponent.getBeschreibung().getValue());
                stellenanzeige.setEnde(addStellenanzeigeFormComponent.getEnde().getValue());
                stellenanzeige.setStart(addStellenanzeigeFormComponent.getStart().getValue());
                stellenanzeige.setTaetigkeitsfelder(addStellenanzeigeFormComponent.getTaetigkeitsfelder());
                stellenanzeige.setBeschaeftigungsverhaeltnis(addStellenanzeigeFormComponent.getBeschaeftigungsverhaeltnis().getValue());
                stellenanzeige.setBezahlung(addStellenanzeigeFormComponent.getBezahlung().getValue());
                stellenanzeige.setErstellungsdatum(LocalDate.now());
                System.out.println(stellenanzeige);
                System.out.println("vor"+stellenanzeigeSet.size());
                stellenanzeigeSet.add(stellenanzeige);
                System.out.println("nach"+stellenanzeigeSet.size());
                try {
                    updateUnternehmenProfileDTO();
                } catch (ProfileException ex) {
                    throw new RuntimeException(ex);
                }
                updateView();
                addDialog.close();
            }
        });
        return addStellenanzeigeButton;
    }

    private String getUserOverUrl(){
        String[] split = url.split("/");
        String user = split[split.length-1];
        return user;
    }

}
