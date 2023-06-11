package org.hbrs.se2.project.aldavia.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@Route(value = "profileUnternehmen")
@PageTitle("ProfilUnternehmen")
public class UnternehmenProfileView extends Div {

    @Autowired
    UnternehmenProfileControl control;

    private TextField name = new TextField("Name Ihres Unternehmens");

    private TextField beschreibung = new TextField("Beschreibung");
    private TextField ap_vorname = new TextField("Vorname Ansprechpartner");

    private TextField ap_nachname = new TextField("Nachname Ansprechpartner");

    private TextField adressen = new TextField("Adressen");

    private TextField email = new TextField("Email-Adresse");

    private TextField webside = new TextField("Webseite");

    private Button save = new Button("Save");

    private Button saveAdress = new Button("Save Adress");

    private Button plusAdresse = new Button("+ Adresse");

    private Binder<UnternehmenProfileDTO> binder = new Binder<>(UnternehmenProfileDTO.class);

    //Varibles for Custom Field
    private TextField strasse = new TextField("Strasse");
    private TextField hausnr = new TextField("HausNr");
    private TextField plz = new TextField ("PLZ");
    private TextField ort = new TextField("Ort");
    private TextField land = new TextField("Land");
    private Button checkBoxDeleteButton = new Button("Delete");

    CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();

    HorizontalLayout layout = new HorizontalLayout();

    HorizontalLayout hlAdresse = new HorizontalLayout();
    VerticalLayout vlAdresse = new VerticalLayout();





    public UnternehmenProfileView(UnternehmenProfileControl control) throws ProfileException {

        if (getCurrentUser() == null) {
            System.out.println("LOG: In Constructor of App View - No User given!");
        } else {
            binder.bind(name, "name");
            binder.bind(beschreibung, "beschreibung");
            binder.bind(ap_vorname, "ap_vorname");
            binder.bind(ap_nachname, "ap_nachname");
            binder.bind(email, "email");
            binder.bind(webside, "webside");
            binder.setBean(new UnternehmenProfileDTO());

            Component adressePicker = createAdressPicker();

            vlAdresse.add(adressePicker, saveAdress);
            hlAdresse.add(vlAdresse);

            layout.add(plusAdresse, save, hlAdresse);


            UserDTO userDTO = getCurrentUser();
            UnternehmenProfileDTO unternehmenDto = control.getUnternehmenProfileDTO(userDTO.getUserid());


            add(createFormLayout(control, unternehmenDto));
            add(layout);
            add(updateCheckBox(unternehmenDto.getAdressen()));
            add(checkBoxDeleteButton);
            checkBoxDeleteButton.setVisible(true);
            checkBoxDeleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);



            save.setVisible(false);
            saveAdress.setVisible(false);
            adressePicker.setVisible(false);


            plusAdresse.addClickListener(e -> {
                adressePicker.setVisible(true);
                saveAdress.setVisible(true);
            });


            //Save Button erscheint erst, wenn änderung vorgenommen wirds
            name.addKeyDownListener(e -> save.setVisible(true));
            beschreibung.addKeyDownListener(e -> save.setVisible(true));
            ap_vorname.addKeyDownListener(e -> save.setVisible(true));
            ap_nachname.addKeyDownListener(e -> save.setVisible(true));
            email.addKeyDownListener(e -> save.setVisible(true));
            webside.addKeyDownListener(e -> save.setVisible(true));

            saveAdress.addClickListener(e -> {
                Adresse adresseNeu;
                if (strasse.getValue() == "" || hausnr.getValue() == "" || plz.getValue() == "" || ort.getValue() == ""
                        || land.getValue() == "") {
                    Notification.show("Bitte alle Adress-Felder ausfüllen!");
                } else {
                    adresseNeu = Adresse.builder()
                            .strasse(strasse.getValue())
                            .hausnummer(hausnr.getValue())
                            .plz(plz.getValue())
                            .ort(ort.getValue())
                            .land(land.getValue())
                            .build();



                    //Adresse zu vorhandenen Adressen hinzufügen
                    Set<Adresse> vorhandeneAdressen = unternehmenDto.getAdressen();
                    vorhandeneAdressen.add(adresseNeu);
                    unternehmenDto.setAdressen(vorhandeneAdressen);
                    try {
                        control.createAndUpdateUnternehmenProfile(unternehmenDto, userDTO.getUserid());
                    } catch (ProfileException ex) {
                        throw new RuntimeException(ex);
                    }


                    //Speichern der Werte

                    adressePicker.setVisible(false);
                    saveAdress.setVisible(false);
                    strasse.clear();
                    hausnr.clear();
                    plz.clear();
                    ort.clear();
                    land.clear();

                    //reloading Page
                    UI.getCurrent().getPage().reload();


                    updateCheckBox(unternehmenDto.getAdressen());

                    checkboxGroup.setVisible(true);
                    checkBoxDeleteButton.setVisible(true);
                }
            });


            save.addClickListener(e -> {
                UnternehmenProfileDTO dto = binder.getBean();

                if(dto == null) {
                    throw new RuntimeException("DTO IS NULL!");
                }
                try {
                    control.createAndUpdateUnternehmenProfile(dto, userDTO.getUserid());
                } catch (ProfileException ex) {
                    throw new RuntimeException(ex);
                }
                save.setVisible(false);

            });


            /*checkboxGroup.addSelectionListener(e -> {
                if(checkboxGroup.getSelectedItems() == null) {
                    checkBoxDeleteButton.setVisible(false);
                }
                checkBoxDeleteButton.setVisible(true);
            });*/

            checkBoxDeleteButton.addClickListener(e -> {
                Set<String> toDelete = checkboxGroup.getSelectedItems();
                List<Adresse> deleteAdressen = new ArrayList<>();
                Set<Adresse> adressenResult = new HashSet<>();
                for (String item : toDelete) {
                    deleteAdressen.add(reverseInput(item));
                }
                for (Adresse a : unternehmenDto.getAdressen()) {
                    for (Adresse b : deleteAdressen) {
                        if (!a.equals(b)) {
                            adressenResult.add(a);
                        }
                    }

                    unternehmenDto.setAdressen(adressenResult);
                    try {
                        control.createAndUpdateUnternehmenProfile(unternehmenDto, userDTO.getUserid());
                    } catch (ProfileException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                //reloading Page
                UI.getCurrent().getPage().reload();
                updateCheckBox(unternehmenDto.getAdressen());
                checkboxGroup.setVisible(true);
                checkBoxDeleteButton.setVisible(false);
                checkboxGroup.deselectAll();
            });

        }

    }


    private Component createFormLayout(UnternehmenProfileControl control, UnternehmenProfileDTO dto) {
        FlexLayout layout = new FlexLayout();
        layout.setId("flex-layout");
        layout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        layout.setAlignItems(FlexLayout.Alignment.START);

        if(dto.getName() != null) {
            name.setValue(dto.getName());
        }
        if(dto.getBeschreibung() != null) {
            beschreibung.setValue(dto.getBeschreibung());
        }
        if(dto.getEmail() != null) {
            email.setValue(dto.getEmail());
       }
        if(dto.getAp_vorname() != null) {
            ap_vorname.setValue(dto.getAp_vorname());
        }
        if(dto.getAp_nachname() != null) {
            ap_nachname.setValue(dto.getAp_nachname());
        }
        if(dto.getWebside() != null) {
            webside.setValue(dto.getWebside());
        }
        if(dto.getAdressen() != null) {
            Set<Adresse> adresses = dto.getAdressen();
            String result = "";
            int counter = 1;
            for(Adresse a : adresses) {
                result += "["+counter++ + "]" + " " + a.getStrasse() + " " + a.getHausnummer() + ", " +
                        a.getPlz() + " " + a.getOrt() + " " +  a.getLand() + " ";
            }
            adressen.setValue(result);

        }
        //Adressen dürfen nur über den Add-Adressen Button hinzugefügt werden
        adressen.setReadOnly(true);




        FormLayout formLayout = new FormLayout();
        formLayout.add(name, adressen, beschreibung, email, ap_vorname, ap_nachname, webside);
        layout.add(formLayout);

        return layout;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }

    private Component createAdressPicker() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(strasse,hausnr,plz,ort,land);
        layout.setWidth("50px");
        return layout;
    }

    public Component updateCheckBox(Set<Adresse> adressen) {
        checkboxGroup.setLabel("Adressen");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        if (adressen != null) {
            Collection<String> result = new ArrayList<>();
            for (Adresse adresse : adressen) {
                String adresseString = "" + adresse.getStrasse() + " " + adresse.getHausnummer() + " " + adresse.getPlz()
                        + " " + adresse.getOrt() + " " + adresse.getLand();
                result.add(adresseString);
            }
            checkboxGroup.setItems(result);
            checkboxGroup.deselectAll();
            return checkboxGroup;

        }
        return checkboxGroup;
    }

    public Adresse reverseInput(String adresseString) {
      String[] split = adresseString.split(" ");
      Adresse adresseDelete = Adresse.builder()
              .strasse(split[0])
              .hausnummer(split[1])
              .plz(split[2])
              .ort(split[3])
              .land(split[4])
              .build();

      return adresseDelete;

    }


}
