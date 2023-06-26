package org.hbrs.se2.project.aldavia.views.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class EditAndSaveProfileButton extends HorizontalLayout {
    Button editButton;
    Button saveButton;

    public EditAndSaveProfileButton() {
        this.addClassName("edit-and-save-profile-button");
        addClassName("topButtons");
        editButton = new Button("Bearbeiten");
        saveButton = new Button("Speichern");
        editButton.addClassName("editSaveButton");
        saveButton.addClassName("editSaveButton");
        setUpUI();

    }
    public void setUpUI() {
        this.setWidthFull();
        add(editButton);
        add(saveButton);
        setJustifyContentMode(JustifyContentMode.END);
    }

    public void setEditButtonVisible(boolean visible) {
        editButton.setVisible(visible);
    }

    public void setSaveButtonVisible(boolean visible) {
        saveButton.setVisible(visible);
    }

    public void addListenerToEditButton(ComponentEventListener<ClickEvent<Button>> listener) {
        editButton.addClickListener(listener);
    }

    public void addListenerToSaveButton(ComponentEventListener<ClickEvent<Button>> listener) {
        saveButton.addClickListener(listener);
    }

}
