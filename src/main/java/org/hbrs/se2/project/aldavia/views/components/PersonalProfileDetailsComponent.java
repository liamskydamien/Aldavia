package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.util.UIUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.*;


@CssImport("./styles/views/profile/studentProfile.css")
public class PersonalProfileDetailsComponent extends HorizontalLayout implements ProfileComponent {



    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private TextField companyName;
    private TextField website;
    private TextField email;

    private StudentProfileControl studentProfileControl;
    private StudentProfileDTO studentProfileDTO;
    private Div profilePicture = new Div();
    private Div introduction = new Div();
    private TextField firstNameAndLastName;
    private TextField studiengang;
    private TextArea description;
    private Image profileImg;


    public PersonalProfileDetailsComponent(StudentProfileDTO studentProfileDTO, StudentProfileControl studentProfileControl) {
        this.studentProfileDTO = studentProfileDTO;
        this.studentProfileControl = studentProfileControl;
        addClassName("personal-details-component");
        firstNameAndLastName = new TextField();
        firstNameAndLastName.addClassName("first-name-and-last-name");
        studiengang = new TextField();
        description = new TextArea();
        setUpUI();

    }

    public PersonalProfileDetailsComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl){
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        this.unternehmenProfileControl = unternehmenProfileControl;
        addClassName("personal-details-component");
        companyName = new TextField();
        companyName.addClassName("first-name-and-last-name");
        website = new TextField();
        email = new TextField();
        setUpUI();
    }

    private void setUpUI(){
        VerticalLayout introductionLayout = new VerticalLayout();
        introductionLayout.setClassName("introductionLayout");
        if(checkIfUserIsStudent()){
            introductionLayout.add(firstNameAndLastName,studiengang,description);
        } else if (checkIfUserIsUnternehmen()) {
            introductionLayout.add(companyName,website,email);
        }

        introduction.add(introductionLayout);

        profilePicture.setClassName("profile-picture");
        introduction.setClassName("introductionField");
        introduction.addClassName("card");
        updateViewMode();
        add(profilePicture);
        add(introduction);
    }



    public void updateViewMode() {
        if(checkIfUserIsStudent()){
            firstNameAndLastName.setValue(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
            studiengang.setValue(studentProfileDTO.getStudiengang());
            description.setValue(studentProfileDTO.getBeschreibung());
            firstNameAndLastName.setReadOnly(true);
            studiengang.setReadOnly(true);
            description.setReadOnly(true);
        } else if (checkIfUserIsUnternehmen()) {
            companyName.setValue(unternehmenProfileDTO.getName());
            website.setValue(unternehmenProfileDTO.getWebside());
            email.setValue(unternehmenProfileDTO.getEmail());
            companyName.setReadOnly(true);
            website.setReadOnly(true);
            email.setReadOnly(true);
        }


        profilePicture.removeAll();

        if(checkIfUserIsStudent()){
            if(studentProfileDTO.getProfilbild() == null || studentProfileDTO.getProfilbild().equals("")){
                profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
            } else {
                // laden Sie das Profilbild aus studentProfileDTO.getProfilbild()
                String fileName = studentProfileDTO.getProfilbild();
                String path = "./src/main/webapp/profile-images/" + fileName;
                StreamResource resource = new StreamResource(fileName, () -> {
                    try {
                        return new FileInputStream(path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return InputStream.nullInputStream(); // In case of an error return an empty stream
                    }
                });
                profileImg = new Image(resource, "Profilbild");
            }
        } else if(checkIfUserIsUnternehmen()){
            if(unternehmenProfileDTO.getProfilbild() == null || unternehmenProfileDTO.getProfilbild().equals("")){
                profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
            } else {
                // laden Sie das Profilbild aus studentProfileDTO.getProfilbild()
                String fileName = unternehmenProfileDTO.getProfilbild();
                String path = "./src/main/webapp/profile-images/" + fileName;
                StreamResource resource = new StreamResource(fileName, () -> {
                    try {
                        return new FileInputStream(path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return InputStream.nullInputStream(); // In case of an error return an empty stream
                    }
                });
                profileImg = new Image(resource, "Profilbild");
            }
        }
        profilePicture.add(profileImg);


    }

    public void updateEditMode() {
        if(checkIfUserIsStudent()){
            firstNameAndLastName.setReadOnly(false);
            studiengang.setReadOnly(false);
            description.setReadOnly(false);

            //Name
            if(UIUtils.checkIfTextFieldIsEmpty(firstNameAndLastName)){
                firstNameAndLastName.setPlaceholder("Vor- und Nachname");
            }firstNameAndLastName.setClearButtonVisible(true);

            //Studiengang
            if (UIUtils.checkIfTextFieldIsEmpty(studiengang)){
                studiengang.setPlaceholder("Studiengang");
            }
            studiengang.setClearButtonVisible(true);

            //Description
            if (UIUtils.checkIfTextAreaIsEmpty(description)){
                description.setPlaceholder("Schreibe etwas über dich...");
            }
            description.setClearButtonVisible(true);
            description.setMaxLength(500);
            description.setValueChangeMode(ValueChangeMode.EAGER);
            description.setHelperText("0/500");
            description.addValueChangeListener(e -> {
                e.getSource()
                        .setHelperText(e.getValue().length() + "/" + 500);
            });

        } else if (checkIfUserIsUnternehmen()) {
            companyName.setReadOnly(false);
            website.setReadOnly(false);
            email.setReadOnly(false);

            //Name
            if(UIUtils.checkIfTextFieldIsEmpty(companyName)){
                companyName.setPlaceholder("Name");
            }companyName.setClearButtonVisible(true);

            //Website
            if (UIUtils.checkIfTextFieldIsEmpty(website)){
                website.setPlaceholder("Website");
            }
            website.setClearButtonVisible(true);

            //Email
            if (UIUtils.checkIfTextFieldIsEmpty(email)){
                email.setPlaceholder("Email");
            }
            email.setClearButtonVisible(true);
        }




        //Profile Picture
        Icon profilePiceditIcon = new Icon("lumo","edit");
        profilePiceditIcon.setClassName("profilePicEditIcon");
        Button editProfilePicButton = new Button(profilePiceditIcon,e -> {

            Dialog dialogUploadPic = new Dialog();
            dialogUploadPic.open();
            HorizontalLayout dialogHeader = new HorizontalLayout();
            dialogHeader.setJustifyContentMode(JustifyContentMode.END);
            Button closeButton = new Button(new Icon("lumo", "cross"),
                    (event) -> dialogUploadPic.close());
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            dialogHeader.add(closeButton);
            dialogUploadPic.add(dialogHeader);
            dialogUploadPic.add(uploadProfilePicture(dialogUploadPic));
        });
        editProfilePicButton.setClassName("edit-profile-pic-button");
        profilePicture.add(editProfilePicButton);


    }

    @Override
    public void switchViewMode(String userName) throws PersistenceException, ProfileException {
        updateProfileDTO(userName);
        updateViewMode();

    }

    @Override
    public void switchEditMode() {
        updateEditMode();
    }

    private void updateProfileDTO(String userName) throws PersistenceException, ProfileException {
        if(checkIfUserIsStudent()){
            if(firstNameAndLastName.getValue().equals("")){
                Notification.show("Name darf nicht leer sein!");
                return;
            } else if (firstNameAndLastName.getValue().split(" ").length < 2){
                Notification.show("Bitte Vor- und Nachname angeben!");
                return;
            }
            String firstname = UIUtils.splitOnSpaces(firstNameAndLastName.getValue())[0];
            String lastname = UIUtils.splitOnSpaces(firstNameAndLastName.getValue())[1];
            studentProfileDTO.setVorname(firstname);
            studentProfileDTO.setNachname(lastname);
            studentProfileDTO.setStudiengang(studiengang.getValue());
            studentProfileDTO.setBeschreibung(description.getValue());
            studentProfileControl.updateStudentProfile(studentProfileDTO, userName);
        }
        else if (checkIfUserIsUnternehmen()){
            if(companyName.getValue().equals("")){
                Notification.show("Name darf nicht leer sein!");
                return;
            } else if (website.getValue().equals("")){
                Notification.show("Website darf nicht leer sein!");
                return;
            } else if (email.getValue().equals("")){
                Notification.show("Email darf nicht leer sein!");
                return;
            }
            unternehmenProfileDTO.setName(companyName.getValue());
            unternehmenProfileDTO.setWebside(website.getValue());
            unternehmenProfileDTO.setEmail(email.getValue());
            unternehmenProfileControl.createAndUpdateUnternehmenProfile(unternehmenProfileDTO, userName);
        }

    }

    private VerticalLayout uploadProfilePicture(Dialog dialogUploadPic) {
        Logger LOGGER = Logger.getLogger(PersonalProfileDetailsComponent.class.getName());

        VerticalLayout uploadLayout = new VerticalLayout();
        uploadLayout.setClassName("uploadLayout");
        uploadLayout.add(new H2("Profilbild hochladen"));

        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setDropAllowed(true);

        upload.addFailedListener(event -> {
            // handle the error here
            Notification.show("Upload fehlgeschlagen: " + event.getReason());
        });

        upload.addSucceededListener(event -> {
            // Generate a unique name for the image file
            String uniqueFileName = getCurrentUserName()+"-"+event.getFileName();
            Path imagePath = Paths.get("./src/main/webapp/profile-images", uniqueFileName);
            String path = imagePath.toAbsolutePath().toString();

            LOGGER.info("Starting file upload: " + path);

            // Save the image on the disk
            try (InputStream inputStream = memoryBuffer.getInputStream()) {
                Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();

            }

            if (checkIfUserIsStudent()){
                // Save the path to the image in the database
                studentProfileDTO.setProfilbild(uniqueFileName);
            } else if (checkIfUserIsUnternehmen()) {
                unternehmenProfileDTO.setProfilbild(uniqueFileName);
            }


            // Update the UI to display the new image
            StreamResource resource = new StreamResource(uniqueFileName, () -> {
                try {
                    return new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return InputStream.nullInputStream(); // In case of an error return an empty stream
                }
            });

            LOGGER.info("File upload successful");

            profileImg = new Image(resource, "Profilbild");

            profilePicture.removeAll();
            profilePicture.add(profileImg);
            dialogUploadPic.close();

        });
        uploadLayout.add(upload);
        return uploadLayout;
    }

}
