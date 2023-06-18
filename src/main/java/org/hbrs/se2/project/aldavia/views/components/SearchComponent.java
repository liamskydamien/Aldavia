package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.hbrs.se2.project.aldavia.control.SearchControl;;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@CssImport("./styles/views/profile/studentProfile.css")
public class SearchComponent extends VerticalLayout {

    @Autowired
    SearchControl searchControl;

    private List<StellenanzeigeDTO> stellenanzeigeList;

    private VerticalLayout displayStellenanzeigen;
    private ListDataProvider<StellenanzeigeDTO> dataProvider;

    private final Logger logger = LoggerFactory.getLogger(SearchComponent.class);



    public SearchComponent(SearchControl searchControl) {
        this.searchControl = searchControl;
        displayStellenanzeigen = new VerticalLayout();

        stellenanzeigeList = searchControl.getAllStellenanzeigen();

        setUpUI(stellenanzeigeList);


    }

    private void createCard(List<StellenanzeigeDTO> stellenanzeigeList){
        displayStellenanzeigen.addClassName("qualificationCardLayout");
        displayStellenanzeigen.setWidthFull();
        System.out.println("In render");
        System.out.println("Listen Anzahl: " + stellenanzeigeList.size());
        if (displayStellenanzeigen != null) {
            displayStellenanzeigen.removeAll();
        }

        for(StellenanzeigeDTO stellenanzeigeDTO : stellenanzeigeList){
            addStellenanzeigeToCard(stellenanzeigeDTO);
        }
    }

    private void addStellenanzeigeToCard(StellenanzeigeDTO stellenanzeigeDTO){

        // Header(Bezeichnung und Von-Bis)
        FlexLayout stellenanzeigeCardHeader = new FlexLayout();
        stellenanzeigeCardHeader.addClassName("stellenanzeigeCardHeader");
        stellenanzeigeCardHeader.setJustifyContentMode(JustifyContentMode.EVENLY);
        stellenanzeigeCardHeader.setWidthFull();
        H3 bezeichnung = new H3(stellenanzeigeDTO.getBezeichnung());
        Span vonBis = new Span(stellenanzeigeDTO.getStart() + " - " + stellenanzeigeDTO.getEnde());
        stellenanzeigeCardHeader.add(bezeichnung, vonBis);


        // Institution
        Span institution = new Span(stellenanzeigeDTO.getUnternehmen().getName());
        institution.addClassName("institution");
        //ToDO: Profilbild es Unternehmens hinzufügen

        // Beschaeftigungsverhaeltnis
        Span beschaeftigungsverhaeltnis = new Span(stellenanzeigeDTO.getBeschaeftigungsverhaeltnis());

        // Beschreibung
        Span beschreibung = new Span(stellenanzeigeDTO.getBeschreibung());
        beschreibung.addClassName("beschreibung");

        // Bezahlung
        Span bezahlung = new Span(stellenanzeigeDTO.getBezahlung());
        bezahlung.addClassName("bezahlung");

        //Taetigkeitsfelder
        if(stellenanzeigeDTO.getTaetigkeitsfelder() != null) {
            List<Span> taetigkeitsfelder = createBadges(stellenanzeigeDTO.getTaetigkeitsfelder());
            HorizontalLayout taetigkeitsfelderLayout = new HorizontalLayout();
            for (Span s : taetigkeitsfelder) {
                taetigkeitsfelderLayout.add(s);
            }
            displayStellenanzeigen.add(stellenanzeigeCardHeader, institution, beschaeftigungsverhaeltnis, bezahlung, beschreibung, taetigkeitsfelderLayout);
        } else {
            displayStellenanzeigen.add(stellenanzeigeCardHeader, institution, beschaeftigungsverhaeltnis, bezahlung, beschreibung);
        }



    }

    private List<Span> createBadges(List<TaetigkeitsfeldDTO> list) {

        List<Span> badgeList = new ArrayList<>();
        for (TaetigkeitsfeldDTO t : list) {
            Span span = new Span(t.getName());
            span.getElement().getThemeList().add("badge");
            badgeList.add(span);
        }
        return badgeList;
    }

    public void setUpUI(List<StellenanzeigeDTO> list) {
        H2 header = new H2("Stellenanzeigen");
        this.add(header);
        createCard(list);
        this.add(displayStellenanzeigen);
    }

    public void updateList(String bezeichnung) {

        displayStellenanzeigen.removeAll();
        List<StellenanzeigeDTO> listNeu = new ArrayList<>();
        for (StellenanzeigeDTO dto : stellenanzeigeList) {
            if (dto.getBezeichnung().toLowerCase().contains(bezeichnung.toLowerCase())) {
                listNeu.add(dto);
            }
        }
        logger.info("Found:" + listNeu.size() + " Items");




        createCard(listNeu);
        remove(displayStellenanzeigen);
        add(displayStellenanzeigen);

    }
}
