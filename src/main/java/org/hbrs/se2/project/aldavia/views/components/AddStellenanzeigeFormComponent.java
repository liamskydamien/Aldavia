package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.util.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AddStellenanzeigeFormComponent extends VerticalLayout {
    private FormLayout formLayout;
    private TextField bezeichnung;
    private TextArea beschreibung;
    private Select<String> beschaeftigungsverhaeltnis;
    private DatePicker start;
    private DatePicker ende;
    private TextField bezahlung;
    private FlexLayout displayTaetigkeitsfeld;
    private VerticalLayout taetigkeitsfeldArea;
    private List<TaetigkeitsfeldDTO> taetigkeitsfelder;
    private  HorizontalLayout addTaetigkeitsArea;
    private Map<TaetigkeitsfeldDTO, TextField> interestFields = new HashMap<>();

    //Mit Stellenanzeige als Parameter (für Edit)
    private Stellenanzeige stellenanzeige;

    public AddStellenanzeigeFormComponent() {
        formLayout = new FormLayout();
        bezeichnung = new TextField("Bezeichnung");
        bezeichnung.setPlaceholder("Bezeichnung");
        bezeichnung.setRequired(true);
        bezeichnung.setRequiredIndicatorVisible(true);
        beschreibung = new TextArea("Beschreibung");
        beschreibung.setPlaceholder("Beschreibe die Stelelnanzeige...");
        beschreibung.setRequired(true);
        beschreibung.setRequiredIndicatorVisible(true);
        beschaeftigungsverhaeltnis = new Select<>();
        beschaeftigungsverhaeltnis.setLabel("Beschäftigungsverhältnis");
        beschaeftigungsverhaeltnis.setPlaceholder("Beschäftigungsverhältnis");
        beschaeftigungsverhaeltnis.setItems(Globals.Beschaefting.PRAKTIKUM, Globals.Beschaefting.WERKSTUDENT, Globals.Beschaefting.VOLLZEIT, Globals.Beschaefting.TEILZEIT, Globals.Beschaefting.FESTANSTELLUNG);
        beschaeftigungsverhaeltnis.setRequiredIndicatorVisible(true);
        start = new DatePicker("Start");
        start.setRequired(true);
        start.setRequiredIndicatorVisible(true);
        ende = new DatePicker("Ende");
        bezahlung = new TextField("Bezahlung");
        bezahlung.setPlaceholder("Bezahlung");
        displayTaetigkeitsfeld = new FlexLayout();
        displayTaetigkeitsfeld.addClassName("displayTaetigkeitsfeld");
        taetigkeitsfelder = new ArrayList<>();
        addTaetigkeitsArea = new HorizontalLayout();
        taetigkeitsfeldArea = new VerticalLayout();
        formLayout.add(bezeichnung, beschreibung, beschaeftigungsverhaeltnis,bezahlung, start, ende);
        add(formLayout, taetigkeitsfeldArea);
        setUpHeader();

    }

    //Für Edit

    public AddStellenanzeigeFormComponent(Stellenanzeige stellenanzeige){
        this.stellenanzeige = stellenanzeige;
        new AddStellenanzeigeFormComponent();
        bezeichnung.setValue(stellenanzeige.getBezeichnung());
        beschreibung.setValue(stellenanzeige.getBeschreibung());
        beschaeftigungsverhaeltnis.setValue(stellenanzeige.getBeschaeftigungsverhaeltnis());
        start.setValue(stellenanzeige.getStart());
        ende.setValue(stellenanzeige.getEnde());
        bezahlung.setValue(stellenanzeige.getBezahlung());


    }

    private void setUpHeader(){
        displayTaetigkeitsfeld.setSizeFull();
        displayTaetigkeitsfeld.setWrapMode(FlexLayout.WrapMode.WRAP);



        //Header
        taetigkeitsfeldArea.setWidthFull();
        taetigkeitsfeldArea.addClassName("taetigkeitsfeldArea");
        addTaetigkeitsArea.setWidthFull();
        addTaetigkeitsArea.addClassName("addTaetigkeitsArea");

        TextField addTaetigkeitsField = new TextField();
        addTaetigkeitsField.addClassName("add-Taetigkeit-Field");
        addTaetigkeitsField.setPlaceholder("Füge neue Tätigkeitsfelder hinzu...");
        addTaetigkeitsField.setClearButtonVisible(true);
        taetigkeitsfeldArea.add(addTaetigkeitsArea);
        addTaetigkeitsArea.add(addTaetigkeitsField);
        addTaetigkeitsArea.add(addTaetikeitButton(addTaetigkeitsField));
        taetigkeitsfeldArea.add(displayTaetigkeitsfeld);
    }

    private void getTaetigkeitAndCreatefield(){
        // Clear any existing fields before re-creating them
        displayTaetigkeitsfeld.removeAll();
        interestFields.clear();

        for(TaetigkeitsfeldDTO t : taetigkeitsfelder){
            TextField taetigkeit = new TextField();
            taetigkeit.setValue(t.getName());
            taetigkeit.addClassName("Skill");
            taetigkeit.getElement().getStyle().set("margin-right", "10px");

            // Store this TextField in the map
            interestFields.put(t, taetigkeit);


            taetigkeit.setSuffixComponent(deleteSkillButton(t));
            taetigkeit.setReadOnly(false);

            displayTaetigkeitsfeld.add(taetigkeit);
        }
    }

    private Button deleteSkillButton(TaetigkeitsfeldDTO taetigkeitsfeld){
        Button deleteTaetigkeit = new Button(new Icon("lumo","cross"));
        deleteTaetigkeit.setClassName("deleteButton");

        deleteTaetigkeit.addClickListener(buttonClickEvent -> {
            // Remove the KenntnisDTO from the list
            taetigkeitsfelder.remove(taetigkeitsfeld);

            // Also remove the associated TextField from the display and from the map
            TextField skillField = interestFields.get(taetigkeitsfeld);
            displayTaetigkeitsfeld.remove(skillField);
            interestFields.remove(taetigkeitsfeld);
        });
        return deleteTaetigkeit;
    }

    private Button addTaetikeitButton(TextField addTaetigkeitTextField){

        Button addTaetikeits = new Button();
        addTaetikeits.setIcon(new Icon("lumo","plus"));

        addTaetikeits.addClickListener(buttonClickEvent ->{
            if(taetigkeitsfelder.size()<11) {
                if (!addTaetigkeitTextField.getValue().isEmpty()) {
                    TaetigkeitsfeldDTO newTaetigkeit = new TaetigkeitsfeldDTO();
                    newTaetigkeit.setName(addTaetigkeitTextField.getValue());
                    taetigkeitsfelder.add(newTaetigkeit);
                    getTaetigkeitAndCreatefield();
                    addTaetigkeitTextField.clear();
                }
            } else {
                Notification.show("Du hast die maximale Kenntnis-Anzahl erreicht.", 20, Notification.Position.MIDDLE);
            }

        });
        return addTaetikeits;
    }
}
