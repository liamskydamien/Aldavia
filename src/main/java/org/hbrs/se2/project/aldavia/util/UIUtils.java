package org.hbrs.se2.project.aldavia.util;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class UIUtils {
    public static String[] splitOnSpaces(String input) {
        return input.split(" ");
    }

    public static boolean checkIfTextFieldIsEmpty(TextField input){
        return input.getValue() == null || input.getValue().isEmpty();
    }

    public static boolean checkIfTextAreaIsEmpty(TextArea input){
        return input == null || input.isEmpty();
    }
}
