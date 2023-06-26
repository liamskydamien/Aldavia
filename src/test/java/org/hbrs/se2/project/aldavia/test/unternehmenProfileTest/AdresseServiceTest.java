package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.AdresseRepository;
import org.hbrs.se2.project.aldavia.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AdresseServiceTest {
    @Mock
    private AdresseRepository adresseRepository;

    @InjectMocks
    private AdresseService adresseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testAddUnternehmenToAdresse() {
        User user = new User();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setUser(user);

        AdresseDTO adresseDTO = AdresseDTO.builder()
                .strasse("Efertzstraße")
                .hausnummer("15")
                .plz("53122")
                .ort("Bonn")
                .land("Deutschland")
                .build();

        Adresse existingAdresse = Adresse.builder()
                .strasse("Hermann-Wandersleb-Ring")
                .hausnummer("6")
                .plz("53121")
                .ort("Bonn")
                .land("Deutschland")
                .build();
        Optional<Adresse> adresseOpt = Optional.of(existingAdresse);

        given(adresseRepository.findById(adresseDTO.getId())).willReturn(adresseOpt);
        given(adresseRepository.save(any(Adresse.class))).willReturn(existingAdresse);

        Adresse result = adresseService.addUnternehmenToAdresse(adresseDTO, unternehmen);

        // Verify that the repository's findById method was called
        verify(adresseRepository, times(1)).findById(adresseDTO.getId());

        // Verify that the repository's save method was called
        verify(adresseRepository, times(1)).save(existingAdresse);

        // Verify the returned result
        assertEquals(existingAdresse, result);
    }

    @Test
    public void testAddUnternehmenToBuiltAdresse() {
        User user = new User();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setUser(user);

        AdresseDTO adresseDTO = AdresseDTO.builder()
                .strasse("Efertzstraße")
                .hausnummer("15")
                .plz("53122")
                .ort("Bonn")
                .land("Deutschland")
                .build();
        Adresse builtAdresse = Adresse.builder()
                .id(adresseDTO.getId())
                .strasse(adresseDTO.getStrasse())
                .hausnummer(adresseDTO.getHausnummer())
                .plz(adresseDTO.getPlz())
                .ort(adresseDTO.getOrt())
                .land(adresseDTO.getLand())
                .build();

        given(adresseRepository.findById(adresseDTO.getId())).willReturn(Optional.empty());
        given(adresseRepository.save(any(Adresse.class))).willReturn(builtAdresse);

        Adresse result = adresseService.addUnternehmenToAdresse(adresseDTO, unternehmen);

        // Verify that the repository's findById method was called
        verify(adresseRepository, times(1)).findById(adresseDTO.getId());

        // Verify the returned result
        assertEquals(builtAdresse, result);
    }

    @Test
    void testRemoveUnternehmenFromAdresse() {
        Adresse adresse = new Adresse();

        Unternehmen unternehmen = new Unternehmen();

        when(adresseRepository.findById(adresse.getId())).thenReturn(Optional.of(adresse));

        adresseService.removeUnternehmenFromAdresse(adresse, unternehmen);

        verify(adresseRepository, times(1)).delete(adresse);
    }

    @Test
    void testRemoveUnternehmenFromAdresse_NotFound() {
        Adresse adresse = new Adresse();

        Unternehmen unternehmen = new Unternehmen();

        when(adresseRepository.findById(adresse.getId())).thenReturn(Optional.empty());

        assertThrows(PersistenceException.class, () -> adresseService.removeUnternehmenFromAdresse(adresse, unternehmen));

        verify(adresseRepository, never()).delete(adresse);
    }

}
