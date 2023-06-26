package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;

import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.UnternehmenProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.service.AdresseService;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
public class UnternehmenProfileControlTest {


    UnternehmenProfileControl unternehmenProfileControl;


    @Mock
    UnternehmenService unternehmenServiceMock;

    @Mock
    UnternehmenProfileDTOFactory factoryMock;
    @Mock
    StellenanzeigenService stellenanzeigenServiceMock;
    @Mock
    AdresseService adresseServiceMock;

    @BeforeEach
    void setUp() {
        unternehmenProfileControl = new UnternehmenProfileControl(unternehmenServiceMock, factoryMock, stellenanzeigenServiceMock, adresseServiceMock);

    }

    @Test
    public void testGetUnternehmenProfileDTO() throws ProfileException {

        //given
        Unternehmen unternehmenMock = new Unternehmen();
        UnternehmenProfileDTO dto = new UnternehmenProfileDTO();
        String userId = "abc";
        given(unternehmenServiceMock.getUnternehmen(userId)).willReturn(unternehmenMock);
        given(factoryMock.createUnternehmenProfileDTO(unternehmenMock)).willReturn(dto);

        //when
        UnternehmenProfileDTO await = unternehmenProfileControl.getUnternehmenProfileDTO(userId);

        //then
        assertThat(await).hasSameClassAs(dto);
    }

    @Test
    public void testcreateAndUpdateUnternehmenProfile() throws ProfileException {
        //given
        StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();
        Set<StellenanzeigeDTO> stellenanzeigeDTOs = new HashSet<>();
        stellenanzeigeDTOs.add(stellenanzeigeDTO);

        Stellenanzeige stellenanzeige = new Stellenanzeige();
        Set<Stellenanzeige> stellenanzeigen = new HashSet<>();
        stellenanzeigen.add(stellenanzeige);

        Set<AdresseDTO> adressenDTO = new HashSet<>();
        AdresseDTO adresseDTO = AdresseDTO.builder().land("Deutschland").build();
        adressenDTO.add(adresseDTO);

        Set<Adresse> adressen = new HashSet<>();
        Adresse adresse = Adresse.builder().land("Germany").build();
        adressen.add(adresse);

        String userName = "Tom";
        UnternehmenProfileDTO dto = UnternehmenProfileDTO.builder()
                .stellenanzeigen(stellenanzeigeDTOs)
                .adressen(adressenDTO)
                .build();
        Unternehmen unternehmenMock = new Unternehmen();
        unternehmenMock.setUser(User.builder()
                .userid(userName)
                .email("email")
                .password("password")
                .build());
        unternehmenMock.setStellenanzeigen(stellenanzeigen);
        unternehmenMock.setAdressen(adressen);

        given(unternehmenServiceMock.getUnternehmen(userName)).willReturn(unternehmenMock);


        //when
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(dto, userName);

        verify(unternehmenServiceMock,times(1)).getUnternehmen(userName);
        verify(unternehmenServiceMock, times(1)).createOrUpdateUnternehmen(unternehmenMock);

        //then -> fällt weg, da void Methode
    }

    @Test
    public void testcreateAndUpdateUnternehmenProfileNull() throws ProfileException {
        //given
        String userName = "Tom";
        UnternehmenProfileDTO dto = new UnternehmenProfileDTO();
        Unternehmen unternehmenMock = new Unternehmen();
        unternehmenMock.setUser(User.builder()
                .userid(userName)
                .email("email")
                .password("password")
                .build());

        given(unternehmenServiceMock.getUnternehmen(userName)).willReturn(unternehmenMock);


        //when
        unternehmenProfileControl.createAndUpdateUnternehmenProfile(dto, userName);

        verify(unternehmenServiceMock,times(1)).getUnternehmen(userName);
        verify(unternehmenServiceMock, times(1)).createOrUpdateUnternehmen(unternehmenMock);

        //then -> fällt weg, da void Methode
    }

}
