package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.control.SearchControl;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@CssImport("./styles/views/profile/studentProfile.css")
public class SearchComponentNew extends VerticalLayout {
        private final SearchControl searchControl;

        private final BewerbungsControl bewerbungsControl;

        private List<StellenanzeigeDTO> stellenanzeigeList;

        private VerticalLayout displayStellenanzeigen;

        private final Logger logger = LoggerFactory.getLogger(org.hbrs.se2.project.aldavia.views.components.SearchComponentNew.class);

        private Image profileImg;

        Select<String> selectJob = new Select<>();

        Select<String> selectRecommended = new Select<>();

        TextField unternehmenSearch = new TextField();

        @Autowired
        public SearchComponentNew(SearchControl searchControl) {
            this.searchControl = searchControl;
            this.bewerbungsControl = searchControl.getBewerbungsControl();
            displayStellenanzeigen = new VerticalLayout();
            setUpUI();
        }

        private void setUpCards(List<StellenanzeigeDTO> stellenanzeigeList){
            displayStellenanzeigen.addClassName("stellenanzeigenLayout");
            displayStellenanzeigen.setWidthFull();
            System.out.println("In render");
            System.out.println("Listen Anzahl: " + stellenanzeigeList.size());
            if (displayStellenanzeigen != null) {
                displayStellenanzeigen.removeAll();
            }

            for(StellenanzeigeDTO stellenanzeigeDTO : stellenanzeigeList){
                assert displayStellenanzeigen != null;
                displayStellenanzeigen.add(createStellenanzeigenCard(stellenanzeigeDTO));
            }
        }

        private Div createStellenanzeigenCard(StellenanzeigeDTO stellenanzeigeDTO){
            Div stellenanzeigeCard = new Div();
            stellenanzeigeCard.addClassName("stellenanzeigeCard");
            stellenanzeigeCard.setWidth("100%");
            stellenanzeigeCard.setHeight("100%");
            stellenanzeigeCard.add(createVerticalStellenanzeigenLayout(stellenanzeigeDTO));
            return stellenanzeigeCard;
        }

        private VerticalLayout createVerticalStellenanzeigenLayout(StellenanzeigeDTO stellenanzeigeDTO){
            VerticalLayout stellenanzeigenLayout = new VerticalLayout();
            stellenanzeigenLayout.addClassName("stellenanzeigeLayout");
            stellenanzeigenLayout.setSpacing(true);
            stellenanzeigenLayout.setAlignItems(Alignment.CENTER);
            stellenanzeigenLayout.setJustifyContentMode(JustifyContentMode.START);
            stellenanzeigenLayout.setWidth("100%");
            stellenanzeigenLayout.setHeight("100%");
            stellenanzeigenLayout.add(new H2(stellenanzeigeDTO.getBezeichnung()));
            stellenanzeigenLayout.add(createProfilBildAndName(stellenanzeigeDTO));
            stellenanzeigenLayout.add(new Text(stellenanzeigeDTO.getBeschreibung()));
            stellenanzeigenLayout.add(createInformationLayout(stellenanzeigeDTO));
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
            informationLayout.add(createVonBisLayout(stellenanzeigeDTO));
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

        private UserDTO getUserDTO() {
            return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        }


        public void setUpUI() {
            HorizontalLayout headerLayout = new HorizontalLayout();
            HorizontalLayout unternehmenSearchLayout = new HorizontalLayout();
            unternehmenSearch.setPlaceholder("Unternehmen");
            unternehmenSearch.setLabel("Filter nach Unternehmen");
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

            selectRecommended.setLabel("Sortieren nach");
            selectRecommended.setItems("Empfehlung", "Nicht sortiert");
            selectRecommended.setValue("Nicht sortiert");

            selectRecommended.addValueChangeListener(e -> {
                if (selectRecommended.getValue().equals("Empfehlung")) {
                    stellenanzeigeList = searchControl.getRecommendedStellenanzeigen(getUserDTO().getUserid());
                }
                else {
                    stellenanzeigeList = searchControl.getAllStellenanzeigen();
                }
            });

            List<StellenanzeigeDTO> listToAdd = new ArrayList<>();
            List<StellenanzeigeDTO> filteredList = new ArrayList<>();

            selectJob.addValueChangeListener(e -> {
                listToAdd.addAll(filterForArbeitsverhaeltnis(stellenanzeigeList, selectJob.getValue()));
            });

            unternehmenSearch.addValueChangeListener(e -> {
                filteredList.addAll(filterForUnternehmen(listToAdd, unternehmenSearch.getValue()));
            });

            setUpCards(filteredList);
        }


        private List<StellenanzeigeDTO> filterForArbeitsverhaeltnis(List<StellenanzeigeDTO> stellenanzeigeList, String arbeitsverhaeltnis) {
            List<StellenanzeigeDTO> listToAdd = new ArrayList<>();
            switch (selectJob.getValue()) {
                case "Praktikum":
                    for (StellenanzeigeDTO dto : stellenanzeigeList) {
                        if (dto.getBeschaeftigungsverhaeltnis().equals("Praktikum")) {
                            listToAdd.add(dto);
                        }
                    }
                    break;
                case "Festanstellung":
                    for (StellenanzeigeDTO dto : stellenanzeigeList) {
                        if (dto.getBeschaeftigungsverhaeltnis().equals("Festanstellung")) {
                            listToAdd.add(dto);
                        }
                    }
                    break;
                case "Werkstudent":
                    for (StellenanzeigeDTO dto : stellenanzeigeList) {
                        if (dto.getBeschaeftigungsverhaeltnis().equals("Werkstudent")) {
                            listToAdd.add(dto);
                        }
                    }
                    break;
                case "Teilzeit":
                    for (StellenanzeigeDTO dto : stellenanzeigeList) {
                        if (dto.getBeschaeftigungsverhaeltnis().equals("Teilzeit")) {
                            listToAdd.add(dto);
                        }
                    }
                    break;
                default:
                    listToAdd.addAll(stellenanzeigeList);
                    break;
            }
            return listToAdd;
        }

        private List<StellenanzeigeDTO> filterForUnternehmen(List<StellenanzeigeDTO> stellenanzeigeList ,String bezeichnung) {
            List<StellenanzeigeDTO> listToAdd = new ArrayList<>();
            for (StellenanzeigeDTO dto : stellenanzeigeList) {
                if (dto.getUnternehmen().getName().equals(bezeichnung)) {
                    listToAdd.add(dto);
                }
            }
            return listToAdd;
        }

        public void updateList(String string){

        }
}

