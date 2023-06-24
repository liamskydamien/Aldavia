package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;

import org.hbrs.se2.project.aldavia.control.UnternehmenProfileControl;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.control.factories.UnternehmenProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
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
        verifyNoMoreInteractions(unternehmenServiceMock);

        //then -> fällt weg, da void Methode
    }

}
