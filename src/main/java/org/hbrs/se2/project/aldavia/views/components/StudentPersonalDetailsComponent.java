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
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import org.hbrs.se2.project.aldavia.control.StudentProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.service.*;
import org.hbrs.se2.project.aldavia.util.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static org.hbrs.se2.project.aldavia.views.LoggedInStateLayout.getCurrentUserName;

@CssImport("./styles/views/profile/studentProfile.css")
public class StudentPersonalDetailsComponent extends HorizontalLayout implements ProfileComponent {

    //TODO: Control als Singleton
    private final StudentProfileControl studentProfileControl;

    private StudentProfileDTO studentProfileDTO;
    private Div profilePicture = new Div();
    private Div introduction = new Div();
    private TextField firstNameAndLastName;
    private TextField studiengang;
    private TextArea description;
    private Image profileImg;



    public StudentPersonalDetailsComponent(StudentProfileDTO studentProfileDTO) {
        this.studentProfileDTO = studentProfileDTO;
        studentProfileControl = new StudentProfileControl();
        addClassName("student-personal-details-component");
        firstNameAndLastName = new TextField();
        firstNameAndLastName.addClassName("first-name-and-last-name");
        studiengang = new TextField();
        description = new TextArea();
        setUpUI();

    }

    private void setUpUI(){
        VerticalLayout introductionLayout = new VerticalLayout();
        introductionLayout.setClassName("introductionLayout");
        introductionLayout.add(firstNameAndLastName,studiengang,description);
        introduction.add(introductionLayout);

        profilePicture.setClassName("profile-picture");
        introduction.setClassName("introductionField");
        introduction.addClassName("card");
        updateViewMode();
        add(profilePicture);
        add(introduction);
    }



    public void updateViewMode() {
        firstNameAndLastName.setValue(studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
        studiengang.setValue(studentProfileDTO.getStudiengang());
        description.setValue(studentProfileDTO.getBeschreibung());
        firstNameAndLastName.setReadOnly(true);
        studiengang.setReadOnly(true);
        description.setReadOnly(true);

        if(studentProfileDTO.getProfilbild() != null){
            profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
            profilePicture.add(profileImg);}


    }

    public void updateEditMode() {
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
        System.out.println("Übergebene studentProfileDTO: " + studentProfileDTO.getNachname() + " " + studentProfileDTO.getVorname() + " " + studentProfileDTO.getStudiengang() + " " + studentProfileDTO.getBeschreibung());
        System.out.println("Übergebene username: " + userName);
        studentProfileControl.updateStudentProfile(studentProfileDTO, userName);
    }

    private VerticalLayout uploadProfilePicture(Dialog dialogUploadPic) {
        VerticalLayout uploadLayout = new VerticalLayout();
        uploadLayout.setClassName("uploadLayout");
        uploadLayout.add(new H2("Profilbild hochladen"));

        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setDropAllowed(true);
        upload.addSucceededListener(event -> {
            // Generate a unique name for the image file
            String uniqueFileName = getCurrentUserName()+"-"+event.getFileName();
            Path imagePath = Paths.get("./src/main/webapp/profile-images", uniqueFileName);
            String path = imagePath.toAbsolutePath().toString();

            // Save the image on the disk
            try (InputStream inputStream = memoryBuffer.getInputStream()) {
                Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save the path to the image in the database
            studentProfileDTO.setProfilbild(path);

            // Update the UI to display the new image
            StreamResource resource = new StreamResource(uniqueFileName, () -> {
                try {
                    return new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return InputStream.nullInputStream(); // In case of an error return an empty stream
                }
            });
            Image newProfileImg = new Image(resource, "Profilbild");

            profilePicture.removeAll();
            profilePicture.add(newProfileImg);
            dialogUploadPic.close();
        });
        uploadLayout.add(upload);
        return uploadLayout;
    }

}
