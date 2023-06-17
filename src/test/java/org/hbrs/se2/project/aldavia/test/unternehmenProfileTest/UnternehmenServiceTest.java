package org.hbrs.se2.project.aldavia.test.unternehmenProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UnternehmenServiceTest {

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
    void testUpdateUnternehmenInformationen() throws ProfileException {
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
    void testDeleteUnternehmen() throws ProfileException {
        //given
        Unternehmen unternehmenMock = new Unternehmen();

        //when
        unternehmenService.deleteUnternehmen(unternehmenMock);
        verify(unternehmenRepositoryMock,times(1)).delete(unternehmenMock);
    }
}
