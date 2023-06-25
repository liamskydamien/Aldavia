package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Component
@CssImport("./styles/views/profile/studentProfile.css")
public class SearchComponent extends VerticalLayout {

    private final SearchControl searchControl;

    private final BewerbungsControl bewerbungsControl;

    private List<StellenanzeigeDTO> stellenanzeigeList;

    private VerticalLayout displayStellenanzeigen;

    private final Logger logger = LoggerFactory.getLogger(SearchComponent.class);

    private BewerbungErstellenComponent bewerbungErstellenComponent;
    private Dialog bewerbungsDialog;

    private Image profileImg;

    Select<String> selectJob = new Select<>();

    Select<String> selectRecommendet = new Select<>();

    TextField unternehmenSearch = new TextField();





    public SearchComponent(SearchControl searchControl) {
        this.searchControl = searchControl;
        this.bewerbungsControl = searchControl.getBewerbungsControl();
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
        displayStellenanzeigen.add(createStellenanzeigenCard(stellenanzeigeDTO));
    }

    public void setUpUI(List<StellenanzeigeDTO> list) {
        HorizontalLayout headerLayout = new HorizontalLayout();
        HorizontalLayout unternehmenSearchLayout = new HorizontalLayout();
        unternehmenSearch.setPlaceholder("Unternehmen");
        unternehmenSearch.setLabel("Unternehmen Filter");
        HorizontalLayout usLayout = new HorizontalLayout();
        usLayout.add(unternehmenSearch);
        usLayout.setPadding(true);
        unternehmenSearchLayout.add(usLayout);
        unternehmenSearchLayout.setWidth("700px");
        unternehmenSearchLayout.setPadding(true);
        unternehmenSearchLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        H2 header = new H2("Stellenanzeigen");
        headerLayout.add(header);
        //Erstellen des Filters für Arbeitsverhaletnis
        selectJob.setLabel("Filter nach Arbeitsverhältnis");
        selectJob.setItems("Alle", "Praktikum", "Festanstellung", "Werkstudent", "Teilzeit");
        selectJob.setValue("Alle");

        selectRecommendet.setLabel("Sortiere nach Empfehlung");
        selectRecommendet.setItems("Empfohlen", "Nicht sortieren");
        selectRecommendet.setValue("Nicht sortieren");


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


        headerLayout.add(selectRecommendet, selectJob, unternehmenSearch);


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
                if (selectRecommendet.getValue().equals("Nicht sortieren")) {
                    selectJob.setValue("Alle");
                }

            });
        } else {
            selectRecommendet.addValueChangeListener(e -> {
                Notification.show("Sie müssen eingeloggt sein,um dieses Feature nutzen zu können!");
            });
        }

        this.add(headerLayout);
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
        profilBildAndName.addClassName("profilBildAndName");
        profilBildAndName.setSpacing(true);
        profilBildAndName.setAlignItems(Alignment.CENTER);
        profilBildAndName.setJustifyContentMode(JustifyContentMode.START);
        profilBildAndName.setWidth("100%");
        profilBildAndName.setHeight("100%");
        UnternehmenProfileDTO unternehmenProfileDTO = stellenanzeigeDTO.getUnternehmen();
        if(unternehmenProfileDTO.getProfilbild() == null || unternehmenProfileDTO.getProfilbild().equals("")){
            profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
        } else {
            String file = unternehmenProfileDTO.getProfilbild();
            String imagePath = "./src/main/webapp/profile-images/" + file;
            StreamResource streamResource = new StreamResource(file, () -> {
                try {
                    return new FileInputStream(imagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return InputStream.nullInputStream();
                }
            });
            profileImg = new Image(streamResource, "Profilbild");
        }
        profileImg.setWidth("50px");
        profileImg.setHeight("50px");

        profileImg.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.COMPANY_PROFILE);

        });
        profilBildAndName.add(profileImg);
        Span name = new Span(unternehmenProfileDTO.getName());
        Anchor anchor = new Anchor();
        anchor.add(name);
        anchor.setHref(Globals.Pages.COMPANY_PROFILE + "/" + unternehmenProfileDTO.getUsername());
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
