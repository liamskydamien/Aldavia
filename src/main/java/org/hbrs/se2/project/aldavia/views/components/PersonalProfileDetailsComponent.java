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
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.util.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.*;


@CssImport("./styles/views/profile/studentProfile.css")
public class PersonalProfileDetailsComponent extends HorizontalLayout implements ProfileComponent {


    public static final String DEFAULT_PROFILE_PIC = "defaultProfilePic";
    public static final String IMAGES_DEFAULT_PROFILE_IMG_PNG = "images/defaultProfileImg.png";
    public static final String SRC_MAIN_WEBAPP_PROFILE_IMAGES = "./src/main/webapp/profile-images/";
    public static final String PROFILBILD = "Profilbild";
    private UnternehmenProfileControl unternehmenProfileControl;
    private UnternehmenProfileDTO unternehmenProfileDTO;
    private TextField companyName;
    private TextField website;
    private TextField email;

    private StudentProfileControl studentProfileControl;
    private StudentProfileDTO studentProfileDTO;
    private final Div profilePicture = new Div();
    private final Div introduction = new Div();
    private TextField firstNameAndLastName;
    private TextField studiengang;
    private TextArea description;
    private Image profileImg;
    private final String url;

    private final Logger logger = LoggerFactory.getLogger(PersonalProfileDetailsComponent.class);


    public PersonalProfileDetailsComponent(StudentProfileDTO studentProfileDTO, StudentProfileControl studentProfileControl, String url) {
        this.studentProfileDTO = studentProfileDTO;
        this.studentProfileControl = studentProfileControl;
        this.url = url;
        addClassName("personal-details-component");
        firstNameAndLastName = new TextField();
        firstNameAndLastName.addClassName("first-name-and-last-name");
        studiengang = new TextField();
        description = new TextArea();
        setUpUI();

    }

    public PersonalProfileDetailsComponent(UnternehmenProfileDTO unternehmenProfileDTO, UnternehmenProfileControl unternehmenProfileControl, String url) {
        this.unternehmenProfileDTO = unternehmenProfileDTO;
        this.url = url;
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
        if(getUserOverUrl().equals(getCurrentUserName())){
            if(checkIfUserIsStudent()){
                introductionLayout.add(firstNameAndLastName,studiengang,description);
            } else if (checkIfUserIsUnternehmen()) {
                introductionLayout.add(companyName,website,email);
            }
        } else if(!getUserOverUrl().equals(getCurrentUserName())){
            if(getProfileType().equals(Globals.Pages.PROFILE_VIEW)){
                introductionLayout.add(firstNameAndLastName,studiengang,description);
            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW) || getProfileType().equals(Globals.Pages.NOT_LOGIN_COMPANY_VIEW)) {
                introductionLayout.add(companyName,website,email);
            }
        }

        introduction.add(introductionLayout);

        profilePicture.setClassName("profile-picture");
        introduction.setClassName("introductionField");
        introduction.addClassName("card");
        updateViewMode();
        add(profilePicture);
        add(introduction);
    }


    private String getUserOverUrl(){
            String[] parts = url.split("/");
        return parts[parts.length - 1];

    }
    private String getProfileType(){
            String[] parts = url.split("/");
        return parts[parts.length - 2];
    }



    public void updateViewMode() {
        if(getUserOverUrl().equals(getCurrentUserName())){
            if(checkIfUserIsStudent()){
                updateViewModeStudent();
            } else if (checkIfUserIsUnternehmen()) {
                updateViewModeCompany();
            }
        } else if (!getUserOverUrl().equals(getCurrentUserName())) {
            if(getProfileType().equals(Globals.Pages.PROFILE_VIEW)){
               updateViewModeStudent();
            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW) || getProfileType().equals(Globals.Pages.NOT_LOGIN_COMPANY_VIEW)) {
                updateViewModeCompany();
            }
        }


        profilePicture.removeAll();
        String fileName;

        if (getUserOverUrl().equals(getCurrentUserName())) {
            if (checkIfUserIsStudent()) {
               logger.info("CheckPoint 1");
                if (studentProfileDTO.getProfilbild() == null || studentProfileDTO.getProfilbild().equals("")) {
                    System.out.println("Default Profilbild");
                    profileImg = new Image(IMAGES_DEFAULT_PROFILE_IMG_PNG, DEFAULT_PROFILE_PIC);

                } else {
                    // lade das Profilbild aus studentProfileDTO.getProfilbild()

                    fileName = studentProfileDTO.getProfilbild();
                    String path = SRC_MAIN_WEBAPP_PROFILE_IMAGES + fileName;
                    StreamResource resource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(path);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return InputStream.nullInputStream(); // In case of an error return an empty stream
                        }
                    });
                    profileImg = new Image(resource, PROFILBILD);
                }
                profilePicture.add(profileImg);
            } else if (checkIfUserIsUnternehmen()) {
                if (unternehmenProfileDTO.getProfilbild() == null || unternehmenProfileDTO.getProfilbild().equals("")) {
                    profileImg = new Image(IMAGES_DEFAULT_PROFILE_IMG_PNG, DEFAULT_PROFILE_PIC);
                } else {
                    // laden Sie das Profilbild aus studentProfileDTO.getProfilbild()
                    logger.info("CheckPoint 2");
                    fileName = unternehmenProfileDTO.getProfilbild();
                    String path = SRC_MAIN_WEBAPP_PROFILE_IMAGES + fileName;
                    StreamResource resource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(path);
                        } catch (FileNotFoundException e) {
                            logger.error("File not found");
                            return InputStream.nullInputStream(); // In case of an error return an empty stream
                        }
                    });
                    profileImg = new Image(resource, PROFILBILD);

                }
                profilePicture.add(profileImg);
            }
        } else if (!getUserOverUrl().equals(getCurrentUserName())) {
            if (getProfileType().equals(Globals.Pages.PROFILE_VIEW) ) {
                if (studentProfileDTO.getProfilbild() == null || studentProfileDTO.getProfilbild().equals("")) {
                    profileImg = new Image(IMAGES_DEFAULT_PROFILE_IMG_PNG, DEFAULT_PROFILE_PIC);
                } else {
                    // lade das Profilbild aus studentProfileDTO.getProfilbild()
                    fileName = studentProfileDTO.getProfilbild();
                    String path = SRC_MAIN_WEBAPP_PROFILE_IMAGES + fileName;
                    StreamResource resource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(path);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return InputStream.nullInputStream(); // In case of an error return an empty stream
                        }
                    });
                    profileImg = new Image(resource, PROFILBILD);
                }
                profilePicture.add(profileImg);
            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW) || getProfileType().equals(Globals.Pages.NOT_LOGIN_COMPANY_VIEW)) {
                if (unternehmenProfileDTO.getProfilbild() == null || unternehmenProfileDTO.getProfilbild().equals("")) {
                    profileImg = new Image(IMAGES_DEFAULT_PROFILE_IMG_PNG, DEFAULT_PROFILE_PIC);
                } else {
                    // laden Sie das Profilbild aus studentProfileDTO.getProfilbild()
                    fileName = unternehmenProfileDTO.getProfilbild();
                    String path = SRC_MAIN_WEBAPP_PROFILE_IMAGES + fileName;
                    StreamResource resource = new StreamResource(fileName, () -> {
                        try {
                            return new FileInputStream(path);
                        } catch (FileNotFoundException e) {
                            logger.error("File not found");
                            return InputStream.nullInputStream(); // In case of an error return an empty stream
                        }
                    });
                    profileImg = new Image(resource, PROFILBILD);
                }
                profilePicture.add(profileImg);
            }
        }

    }

    private void updateViewModeStudent() {
        firstNameAndLastName.setValue(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
        studiengang.setValue(studentProfileDTO.getStudiengang());
        description.setValue(studentProfileDTO.getBeschreibung());
        firstNameAndLastName.setReadOnly(true);
        studiengang.setReadOnly(true);
        description.setReadOnly(true);
    }

    private void updateViewModeCompany() {
        companyName.setValue(unternehmenProfileDTO.getName());
        website.setValue(unternehmenProfileDTO.getWebside());
        email.setValue(unternehmenProfileDTO.getEmail());
        companyName.setReadOnly(true);
        website.setReadOnly(true);
        email.setReadOnly(true);
    }



    public void updateEditMode() {
        if(getUserOverUrl().equals(getCurrentUserName())) {
            if (checkIfUserIsStudent()) {
                updateEditModeStudent();

            } else if (checkIfUserIsUnternehmen()) {
                updateEditModeCompany();
            }
        } else if (!getUserOverUrl().equals(getCurrentUserName())) {
            if(getProfileType().equals(Globals.Pages.PROFILE_VIEW)){
               updateEditModeStudent();


            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW)) {
                updateEditModeCompany();
            }
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
    private void updateEditModeStudent(){
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
        description.addValueChangeListener(e -> e.getSource()
                .setHelperText(e.getValue().length() + "/" + 500));
    }

    private void updateEditModeCompany(){
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
        if(getUserOverUrl().equals(getCurrentUserName())) {
            if(checkIfUserIsStudent()){
                updateProfileDTOStudent(userName);
            }
            else if (checkIfUserIsUnternehmen()){
                updateProfileDTOCompany(userName);
            }
        } else if (!getUserOverUrl().equals(getCurrentUserName())) {
            if(getProfileType().equals(Globals.Pages.PROFILE_VIEW)){
                updateProfileDTOStudent(userName);
            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW) || getProfileType().equals(Globals.Pages.NOT_LOGIN_COMPANY_VIEW)){
                updateProfileDTOCompany(userName);
            }
        }


    }
    private void updateProfileDTOStudent(String userName) throws PersistenceException, ProfileException {
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

    private void updateProfileDTOCompany(String userName) throws ProfileException {
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

    private VerticalLayout uploadProfilePicture(Dialog dialogUploadPic) {

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

            logger.info("Starting file upload: " + path);

            // Save the image on the disk
            saveProfilePicture(memoryBuffer, uniqueFileName, imagePath, logger);

            // Update the UI to display the new image
            StreamResource resource = new StreamResource(uniqueFileName, () -> {
                try {
                    return new FileInputStream(path);
                } catch (FileNotFoundException e) {
                        logger.error("Error while uploading file");
                    return InputStream.nullInputStream(); // In case of an error return an empty stream
                }
            });

            logger.info("File upload successful");

            profileImg = new Image(resource, PROFILBILD);

            profilePicture.removeAll();
            profilePicture.add(profileImg);
            dialogUploadPic.close();

        });
        uploadLayout.add(upload);
        return uploadLayout;
    }

    private void saveProfilePicture(MemoryBuffer memoryBuffer, String uniqueFileName, Path imagePath, Logger logger) {
        // Save the image on the disk
        try (InputStream inputStream = memoryBuffer.getInputStream()) {
            Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.info("Error while uploading file");

        }

        if(getUserOverUrl().equals(getCurrentUserName())){
            if (checkIfUserIsStudent()){
                // Save the path to the image in the database
                studentProfileDTO.setProfilbild(uniqueFileName);
            } else if (checkIfUserIsUnternehmen()) {
                unternehmenProfileDTO.setProfilbild(uniqueFileName);
            }
        } else if (!getUserOverUrl().equals(getCurrentUserName())) {
            if (getProfileType().equals(Globals.Pages.PROFILE_VIEW)){
                studentProfileDTO.setProfilbild(uniqueFileName);
            } else if (getProfileType().equals(Globals.Pages.COMPANY_PROFILE_VIEW) || getProfileType().equals(Globals.Pages.NOT_LOGIN_COMPANY_VIEW)){
                unternehmenProfileDTO.setProfilbild(uniqueFileName);
            }
        }
    }

}
