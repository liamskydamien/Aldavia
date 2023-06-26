package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UnternehmenServiceTest {

    public static final String TESTUSER = "testuser";
    public static final String TEST_COMPANY = "Test Company";
    UnternehmenService unternehmenService;

    @Mock
    UnternehmenRepository unternehmenRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;


    @BeforeEach
    void setUp() {
        unternehmenService = new UnternehmenService(unternehmenRepositoryMock, userRepositoryMock);
    }

    @Test
    void testGetUnternehmen() throws ProfileException {

        //given
        String userName = "Thomas";
        User userMock = new User();
        Unternehmen unternehmenMock = new Unternehmen();
        Optional<Unternehmen> unternehmenOpt = Optional.of(unternehmenMock);
        Optional<User> userOpt = Optional.of(userMock);

        given(userRepositoryMock.findByUserid(userName)).willReturn(userOpt);
        given(unternehmenRepositoryMock.findByUser(userMock)).willReturn(unternehmenOpt);

        //when
        Unternehmen await = unternehmenService.getUnternehmen(userName);

        //then
        assertEquals(unternehmenMock,await);

    }

    @Test
    void testGetUnternehmenProfileException() {
        when(userRepositoryMock.findByUserid(TESTUSER)).thenReturn(Optional.empty());

        assertThrows(ProfileException.class, () -> unternehmenService.getUnternehmen(TESTUSER));

        verify(userRepositoryMock, times(1)).findByUserid(TESTUSER);
        verify(unternehmenRepositoryMock, never()).findByUser(any());
    }

    @Test
    void testUpdateUnternehmenInformationen() throws ProfileException {
        //given
        User userMock = new User();
        Unternehmen unternehmenMock = new Unternehmen();
        unternehmenMock.setUser(userMock);

        Set<AdresseDTO> adressenDTO = new HashSet<>();
        AdresseDTO adresseDTO = AdresseDTO.builder().land("Deutschland").build();
        adressenDTO.add(adresseDTO);

        Set<Adresse> adressen = new HashSet<>();
        Adresse adresse = Adresse.builder().land("Germany").build();
        adressen.add(adresse);
        unternehmenMock.setAdressen(adressen);

        UnternehmenProfileDTO dto = UnternehmenProfileDTO.builder()
                .name("testUnternehmenProfile")
                .email("123@abc.de")
                .telefonnummer("1234567890")
                .profilbild("/dir/profilbild.png")
                .password("iamapassword123")
                .ap_nachname("Nguyen")
                .ap_vorname("Hong Quang")
                .beschreibung("Test")
                .webside("uhm Tippfehler")
                .adressen(adressenDTO)
                .build();


        //when
        unternehmenService.updateUnternehmenInformation(unternehmenMock,dto);
        verify(unternehmenRepositoryMock,times(1)).save(unternehmenMock);
        verifyNoMoreInteractions(unternehmenRepositoryMock);


        //then -> fällt weg, da void Methode
    }

    @Test
    void testUpdateUnternehmenInformationenNull() throws ProfileException {
        //given
        User userMock = new User();
        Unternehmen unternehmenMock = new Unternehmen();
        unternehmenMock.setUser(userMock);
        UnternehmenProfileDTO dto = new UnternehmenProfileDTO();

        //when
        unternehmenService.updateUnternehmenInformation(unternehmenMock,dto);
        verify(unternehmenRepositoryMock,times(1)).save(unternehmenMock);
        verifyNoMoreInteractions(unternehmenRepositoryMock);


        //then -> fällt weg, da void Methode
    }

    @Test
    void testDeleteUnternehmen() {
        //given
        Unternehmen unternehmenMock = new Unternehmen();

        Set<Stellenanzeige> stellenanzeigen = new HashSet<>();
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        stellenanzeigen.add(stellenanzeige);
        unternehmenMock.setStellenanzeigen(stellenanzeigen);

        Set<Adresse> adressen = new HashSet<>();
        Adresse adresse = new Adresse();
        adressen.add(adresse);
        unternehmenMock.setAdressen(adressen);

        //when
        unternehmenService.deleteUnternehmen(unternehmenMock);
        verify(unternehmenRepositoryMock,times(1)).delete(unternehmenMock);
    }

    @Test
    void testDeleteUnternehmenNullLists(){
        User user = new User();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setUser(user);
        unternehmen.setName(TEST_COMPANY);
        unternehmen.setStellenanzeigen(null);
        unternehmen.setAdressen(null);

        when(unternehmenRepositoryMock.findByUserID(TESTUSER)).thenReturn(Optional.of(unternehmen));

        unternehmenService.deleteUnternehmen(unternehmen);

        verify(unternehmenRepositoryMock, times(1)).delete(unternehmen);
    }

    @Test
    void testCreateOrUpdateUnternehmen() {
        User user = new User();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setUser(user);
        unternehmen.setName(TEST_COMPANY);

        when(unternehmenRepositoryMock.save(unternehmen)).thenReturn(unternehmen);

        unternehmenService.createOrUpdateUnternehmen(unternehmen);

        verify(unternehmenRepositoryMock, times(1)).save(unternehmen);
    }

    @Test
    void testCreateOrUpdateUnternehmenException() {
        User user = new User();
        Unternehmen unternehmen = new Unternehmen();
        unternehmen.setUser(user);
        unternehmen.setName(TEST_COMPANY);

        when(unternehmenRepositoryMock.save(unternehmen)).thenThrow(new RuntimeException("Database connection failed"));

        assertThrows(ProfileException.class, () -> unternehmenService.createOrUpdateUnternehmen(unternehmen));

        verify(unternehmenRepositoryMock, times(1)).save(unternehmen);
    }
}
