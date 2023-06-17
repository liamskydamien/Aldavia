package org.hbrs.se2.project.aldavia.views.components;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;

public interface ProfileComponent {
    void switchViewMode(String userName) throws PersistenceException, ProfileException;
    void switchEditMode();
}
