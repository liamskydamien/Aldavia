package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
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
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
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

        Button bewerben = new Button("Bewerben");
        bewerben.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        HorizontalLayout pictureBewerben = new HorizontalLayout();
        pictureBewerben.add(profileImg,bewerben);
        pictureBewerben.setSpacing(true);
        pictureBewerben.setSizeFull();

        // Header(Bezeichnung und Von-Bis)
        FlexLayout stellenanzeigeCardHeader = new FlexLayout();
        stellenanzeigeCardHeader.setWidth("450px");
        stellenanzeigeCardHeader.addClassName("stellenanzeigeCardHeader");
        stellenanzeigeCardHeader.setJustifyContentMode(JustifyContentMode.EVENLY);
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
        HorizontalLayout layout = new HorizontalLayout();
        Span bezahlung = new Span(stellenanzeigeDTO.getBezahlung());
        bezahlung.addClassName("bezahlung");
        layout.setSizeFull();
        layout.setSpacing(true);
        //layout.add(bezahlung, bewerben);

        bewerben.addClickListener(e -> {
            if (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER) == null) {
                UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);

            } else {
                UserDTO userDTO = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
                // TODO: Why? Check for role instead
                if (userDTO.getRoles().equals("Student")) {
                    bewerbungsDialog = new Dialog();
                    bewerbungErstellenComponent = new BewerbungErstellenComponent(bewerbungsControl, stellenanzeigeDTO, userDTO.getUserid());
                    bewerbungsDialog.add(bewerbungErstellenComponent);
                    bewerbungsDialog.open();

                } else {
                    Notification.show("Nur Studenten können sich auf Stellenanzeigen bewerben!");
                }

            }

        });

        //Taetigkeitsfelder
        if(stellenanzeigeDTO.getTaetigkeitsfelder() != null) {
            List<Span> taetigkeitsfelder = createBadges(stellenanzeigeDTO.getTaetigkeitsfelder());
            HorizontalLayout taetigkeitsfelderLayout = new HorizontalLayout();
            for (Span s : taetigkeitsfelder) {
                taetigkeitsfelderLayout.add(s);
            }
            displayStellenanzeigen.add(stellenanzeigeCardHeader,pictureBewerben, institution, beschaeftigungsverhaeltnis, beschreibung, bezahlung,
                                       taetigkeitsfelderLayout);
        } else {
            displayStellenanzeigen.add(stellenanzeigeCardHeader, pictureBewerben, institution, beschaeftigungsverhaeltnis, beschreibung, bezahlung);
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
        selectJob.setLabel("Sort by");
        selectJob.setItems("Alle", "Praktikum", "Festanstellung", "Werkstudent", "Teilzeit");
        selectJob.setValue("Alle");


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



        headerLayout.add(selectJob, unternehmenSearch);

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
}
