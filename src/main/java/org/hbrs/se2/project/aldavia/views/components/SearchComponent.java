package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.util.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@CssImport("./styles/views/SearchView/SearchView.css")
public class SearchComponent extends VerticalLayout {

    public static final String NICHT_SORTIEREN = "Nicht sortieren";
    private final SearchControl searchControl;

    private final BewerbungsControl bewerbungsControl;

    private List<StellenanzeigeDTO> stellenanzeigeList;

    private VerticalLayout displayStellenanzeigen;

    private final Logger logger = LoggerFactory.getLogger(SearchComponent.class);

    Select<String> selectJob = new Select<>();

    Select<String> selectRecommendet = new Select<>();

    TextField unternehmenSearch = new TextField();


    public SearchComponent(SearchControl searchControl) {
        this.searchControl = searchControl;
        this.bewerbungsControl = searchControl.getBewerbungsControl();
        displayStellenanzeigen = new VerticalLayout();

        stellenanzeigeList = searchControl.getAllStellenanzeigen();

        this.addClassName("search-component");

        setUpUI(stellenanzeigeList);



    }

    private void createCard(List<StellenanzeigeDTO> stellenanzeigeList){
        displayStellenanzeigen.addClassName("qualificationCardLayout");
        displayStellenanzeigen.setWidthFull();
        logger.info("Stellenanzeigen: " + stellenanzeigeList.size());
        logger.info("To render: " + stellenanzeigeList.size());
        if (displayStellenanzeigen != null) {
            displayStellenanzeigen.removeAll();
        }

        for(StellenanzeigeDTO stellenanzeigeDTO : stellenanzeigeList){
            addStellenanzeigeToCard(stellenanzeigeDTO);
        }
    }

    private void addStellenanzeigeToCard(StellenanzeigeDTO stellenanzeigeDTO){
        displayStellenanzeigen.add(createStellenanzeigenCard(stellenanzeigeDTO));
    }

    private VerticalLayout createHeadCard(){
        VerticalLayout headCard = new VerticalLayout();
        headCard.addClassName("headCardLayout");
        headCard.setWidthFull();
        HorizontalLayout headerLayout = new HorizontalLayout();
        unternehmenSearch.setPlaceholder("Aldavia GmbH");
        unternehmenSearch.setLabel("Suche nach Unternehmen");
        H2 header = new H2("Stellenanzeigen");
        //Erstellen des Filters für Arbeitsverhaletnis
        selectJob.setLabel("Filter nach Arbeitsverhältnis");
        selectJob.setItems("Alle", "Praktikum", "Festanstellung", "Werkstudent", "Teilzeit");
        selectJob.setValue("Alle");

        selectRecommendet.setLabel("Sortiere nach Empfehlung");
        selectRecommendet.setItems("Empfohlen", NICHT_SORTIEREN);
        selectRecommendet.setValue(NICHT_SORTIEREN);

        headerLayout.add( unternehmenSearch ,selectJob, selectRecommendet);
        headCard.add(header,headerLayout);
        return headCard;
    }

    public void setUpUI(List<StellenanzeigeDTO> list) {
        this.add(createHeadCard());

        List<StellenanzeigeDTO> selectedAnzeigen = new ArrayList<>();

        unternehmenSearch.addValueChangeListener(e -> {
            List<StellenanzeigeDTO> listNeu = new ArrayList<>();
            if (selectedAnzeigen.size() == 0) {
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    if (dto.getUnternehmen().getName().toLowerCase().contains(unternehmenSearch.getValue().toLowerCase())) {
                        listNeu.add(dto);
                    }

                }
            } else {
                for (StellenanzeigeDTO dto : selectedAnzeigen) {
                    if (dto.getUnternehmen().getName().toLowerCase().contains(unternehmenSearch.getValue().toLowerCase())) {
                        listNeu.add(dto);
                    }
                }
            }

            displayStellenanzeigen.removeAll();
            createCard(listNeu);
            remove(displayStellenanzeigen);
            add(displayStellenanzeigen);


        });
        unternehmenSearch.setValueChangeMode(ValueChangeMode.EAGER);


        if( UI.getCurrent() != null && UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER)!= null) {
            UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);

            selectRecommendet.addValueChangeListener(e -> {
                List<StellenanzeigeDTO> recommendetStellenanzeigen = searchControl.getRecommendedStellenanzeigen(userDTO.getUserid());
                if (selectRecommendet.getValue().equals("Empfohlen")) {
                    if (selectedAnzeigen.size() > 0) {
                        for (int i = 0; i < selectedAnzeigen.size(); i++) {
                            selectedAnzeigen.remove(i);
                        }
                    }
                    for (StellenanzeigeDTO dto : recommendetStellenanzeigen) {
                        selectedAnzeigen.add(dto);
                    }
                    displayStellenanzeigen.removeAll();
                    createCard(selectedAnzeigen);
                    remove(displayStellenanzeigen);
                    add(displayStellenanzeigen);

                }
                if (selectRecommendet.getValue().equals(NICHT_SORTIEREN)) {
                    selectJob.setValue("Alle");
                }

            });
        } else {
            selectRecommendet.addValueChangeListener(e -> Notification.show("Sie müssen eingeloggt sein,um dieses Feature nutzen zu können!"));
        }

        createCard(list);
        this.add(displayStellenanzeigen);

        selectJob.addValueChangeListener(e -> {
            if (selectJob.getValue().equals("Alle")) {
                displayStellenanzeigen.removeAll();
                if (selectedAnzeigen.size() > 0) {
                    for (int i = 0; i < selectedAnzeigen.size(); i++) {
                        selectedAnzeigen.remove(i);
                    }
                }
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    selectedAnzeigen.add(dto);
                }
                createCard(stellenanzeigeList);
            } else if (selectJob.getValue().equals("Praktikum")) {
                displayStellenanzeigen.removeAll();
                if (selectedAnzeigen.size() > 0) {
                    for (int i = 0; i < selectedAnzeigen.size(); i++) {
                        selectedAnzeigen.remove(i);
                    }
                }
                List<StellenanzeigeDTO> listNeu = new ArrayList<>();
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    if (dto.getBeschaeftigungsverhaeltnis().toLowerCase().contains("praktikum")) {
                        listNeu.add(dto);
                        selectedAnzeigen.add(dto);
                    }
                }
                createCard(listNeu);
                remove(displayStellenanzeigen);
                add(displayStellenanzeigen);

            } else if (selectJob.getValue().equals("Festanstellung") || selectJob.getValue().equals("Vollzeit")) {
                displayStellenanzeigen.removeAll();
                if (selectedAnzeigen.size() > 0) {
                    for (int i = 0; i < selectedAnzeigen.size(); i++) {
                        selectedAnzeigen.remove(i);
                    }
                }
                List<StellenanzeigeDTO> listNeu = new ArrayList<>();
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    if (dto.getBeschaeftigungsverhaeltnis().toLowerCase().contains("festanstellung")) {
                        listNeu.add(dto);
                        selectedAnzeigen.add(dto);
                    }
                }
                createCard(listNeu);
                remove(displayStellenanzeigen);
                add(displayStellenanzeigen);

            } else if (selectJob.getValue().equals("Werkstudent")) {
                displayStellenanzeigen.removeAll();
                if (stellenanzeigeList.size() > 0) {
                    for (int i = 0; i < selectedAnzeigen.size(); i++) {
                        selectedAnzeigen.remove(i);
                    }
                }
                List<StellenanzeigeDTO> listNeu = new ArrayList<>();
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    if (dto.getBeschaeftigungsverhaeltnis().toLowerCase().contains("werkstudent")) {
                        listNeu.add(dto);
                        selectedAnzeigen.add(dto);
                    }
                }
                createCard(listNeu);
                remove(displayStellenanzeigen);
                add(displayStellenanzeigen);

            }else if (selectJob.getValue().equals("Teilzeit")) {
                displayStellenanzeigen.removeAll();
                if (selectedAnzeigen.size() > 0) {
                    for (int i = 0; i < selectedAnzeigen.size(); i++) {
                        selectedAnzeigen.remove(i);
                    }
                }
                List<StellenanzeigeDTO> listNeu = new ArrayList<>();
                for (StellenanzeigeDTO dto : stellenanzeigeList) {
                    if (dto.getBeschaeftigungsverhaeltnis().toLowerCase().contains("teilzeit")) {
                        listNeu.add(dto);
                        selectedAnzeigen.add(dto);
                    }
                }
                createCard(listNeu);
                remove(displayStellenanzeigen);
                add(displayStellenanzeigen);

            }
        });
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

    private Div createStellenanzeigenCard(StellenanzeigeDTO stellenanzeigeDTO){
        Div stellenanzeigeCard = new Div();
        stellenanzeigeCard.addClassName("card");
        stellenanzeigeCard.setWidth("100%");
        stellenanzeigeCard.setHeight("100%");
        stellenanzeigeCard.add(createVerticalStellenanzeigenLayout(stellenanzeigeDTO));
        return stellenanzeigeCard;
    }

    private VerticalLayout createVerticalStellenanzeigenLayout(StellenanzeigeDTO stellenanzeigeDTO){
        VerticalLayout stellenanzeigenLayout = new VerticalLayout();
        stellenanzeigenLayout.addClassName("stellenanzeigeLayout");
        stellenanzeigenLayout.setSpacing(true);
        stellenanzeigenLayout.setAlignItems(Alignment.START);
        stellenanzeigenLayout.setWidth("100%");
        stellenanzeigenLayout.setHeight("100%");
        stellenanzeigenLayout.add(new H2(stellenanzeigeDTO.getBezeichnung()));
        stellenanzeigenLayout.add(createProfilBildAndName(stellenanzeigeDTO));
        stellenanzeigenLayout.add(new Text(stellenanzeigeDTO.getBeschreibung()));
        stellenanzeigenLayout.add(createInformationLayout(stellenanzeigeDTO));
        stellenanzeigenLayout.add(createVonBisLayout(stellenanzeigeDTO));
        stellenanzeigenLayout.add(createTaetigkeitenLayout(stellenanzeigeDTO));
        stellenanzeigenLayout.add(createBewerbenButton(stellenanzeigeDTO));
        return stellenanzeigenLayout;
    }

    private Button createBewerbenButton(StellenanzeigeDTO stellenanzeigeDTO){
        Button bewerbenButton = new Button("Bewerben");
        bewerbenButton.addClassName("bewerbenButton");
        bewerbenButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        bewerbenButton.addClickListener(e -> {
            if (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER) == null) {
                UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);

            } else {
                UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
                for (RolleDTO r : userDTO.getRoles()) {
                    if (r.getBezeichhnung().equals(Globals.Roles.STUDENT)) {
                        new BewerbungErstellenComponent(bewerbungsControl, stellenanzeigeDTO ,userDTO.getUserid());
                    } else {
                        Notification.show("Nur Studenten können sich auf Stellenanzeigen bewerben!");
                    }
                }

            }
        });
        return bewerbenButton;
    }

    private HorizontalLayout createProfilBildAndName(StellenanzeigeDTO stellenanzeigeDTO){
        HorizontalLayout profilBildAndName = new HorizontalLayout();
        Image profileImg;
        profilBildAndName.addClassName("profilBildAndName");
        profilBildAndName.setSpacing(true);
        profilBildAndName.setAlignItems(Alignment.CENTER);
        profilBildAndName.setJustifyContentMode(JustifyContentMode.START);
        profilBildAndName.setWidth("100%");
        profilBildAndName.setHeight("100%");
        UnternehmenProfileDTO unternehmenProfileDTO = stellenanzeigeDTO.getUnternehmen();
        logger.warn("Unternehmen: " + unternehmenProfileDTO.getProfilbild());
        profileImg = UIUtils.getImage(unternehmenProfileDTO.getProfilbild());
        profileImg.setWidth("50px");
        profileImg.setHeight("50px");
        profileImg.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.COMPANY_PROFILE);

        });
        profilBildAndName.add(profileImg);
        Span name = new Span(unternehmenProfileDTO.getName());
        Anchor anchor = new Anchor();
        anchor.add(name);
        if(UI.getCurrent() != null && UI.getCurrent().getSession() != null && UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER) != null ) {

            anchor.setHref(Globals.Pages.COMPANY_PROFILE_VIEW + "/" + unternehmenProfileDTO.getUsername());
        } else {
            anchor.setHref(Globals.Pages.NOT_LOGIN_COMPANY_VIEW + "/" + unternehmenProfileDTO.getUsername());
        }

        profilBildAndName.add(anchor);
        return profilBildAndName;
    }

    private HorizontalLayout createInformationLayout(StellenanzeigeDTO stellenanzeigeDTO){
        HorizontalLayout informationLayout = new HorizontalLayout();
        informationLayout.addClassName("informationLayout");
        informationLayout.setSpacing(true);
        informationLayout.setAlignItems(Alignment.CENTER);
        informationLayout.setJustifyContentMode(JustifyContentMode.START);
        informationLayout.setWidth("100%");
        informationLayout.setHeight("100%");
        informationLayout.add(new Label("Beschäftigungsart: " + stellenanzeigeDTO.getBeschaeftigungsverhaeltnis()));
        informationLayout.add(new Label("Bezahlung: " + stellenanzeigeDTO.getBezahlung()));
        return informationLayout;
    }

    private HorizontalLayout createTaetigkeitenLayout(StellenanzeigeDTO stellenanzeigeDTO){
        HorizontalLayout taetigkeitenLayout = new HorizontalLayout();
        taetigkeitenLayout.addClassName("taetigkeitenLayout");
        taetigkeitenLayout.setSpacing(true);
        taetigkeitenLayout.setAlignItems(Alignment.CENTER);
        taetigkeitenLayout.setJustifyContentMode(JustifyContentMode.START);
        taetigkeitenLayout.setWidth("100%");
        taetigkeitenLayout.setHeight("100%");
        taetigkeitenLayout.add(new Label("Tätigkeiten: "));
        for (TaetigkeitsfeldDTO taetigkeit : stellenanzeigeDTO.getTaetigkeitsfelder()) {
            Span div = new Span();
            div.addClassName("taetigkeit");
            div.getElement().getThemeList().add("badge pill");
            div.setText(taetigkeit.getName());
            taetigkeitenLayout.add(div);
        }
        return taetigkeitenLayout;
    }

    private HorizontalLayout createVonBisLayout(StellenanzeigeDTO stellenanzeigeDTO){
        HorizontalLayout vonBisLayout = new HorizontalLayout();
        vonBisLayout.addClassName("vonBisLayout");
        vonBisLayout.setSpacing(true);
        vonBisLayout.setAlignItems(Alignment.CENTER);
        vonBisLayout.setJustifyContentMode(JustifyContentMode.START);
        vonBisLayout.setWidth("100%");
        vonBisLayout.setHeight("100%");
        String von = stellenanzeigeDTO.getStart() != null ? stellenanzeigeDTO.getStart().toString() : "Ab sofort!";
        String bis = stellenanzeigeDTO.getEnde() != null ? stellenanzeigeDTO.getEnde().toString() : "Unbefristet!";
        vonBisLayout.add(new Label("Von: " + von));
        vonBisLayout.add(new Label("Bis: " + bis));
        return vonBisLayout;
    }
}
