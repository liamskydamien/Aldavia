package org.hbrs.se2.project.aldavia.util;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UIUtils {

    private UIUtils() {
    }

    public static String[] splitOnSpaces(String input) {
        return input.split(" ");
    }

    public static boolean checkIfTextFieldIsEmpty(TextField input){
        return input.getValue() == null || input.getValue().isEmpty();
    }

    public static boolean checkIfTextAreaIsEmpty(TextArea input){
        return input == null || input.isEmpty();
    }

    public static Image getImage(String profilbild) {
        Image profileImg;
        if(profilbild == null || profilbild.equals("")){
            profileImg = new Image("images/defaultProfileImg.png","defaultProfilePic");
        } else {
            String imagePath = "./src/main/webapp/profile-images/" + profilbild;
            StreamResource streamResource = new StreamResource(profilbild, () -> {
                try {
                    return new FileInputStream(imagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return InputStream.nullInputStream();
                }
            });
            profileImg = new Image(streamResource, "Profilbild");
        }
        return profileImg;
    }
}
