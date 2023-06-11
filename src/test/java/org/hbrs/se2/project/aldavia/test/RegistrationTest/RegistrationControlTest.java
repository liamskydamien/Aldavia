package org.hbrs.se2.project.aldavia.test.RegistrationTest;

import org.hbrs.se2.project.aldavia.control.RegistrationControl;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.hbrs.se2.project.aldavia.dtos.RegistrationResult;
import org.hbrs.se2.project.aldavia.repository.RolleRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.Registration;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class RegistrationControlTest {

    RegistrationControl registrationControl;

    @Mock
    StudentRepository studentRepositoryMock;
    @Mock
    UnternehmenRepository unternehmenRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    RolleRepository rolleRepositoryMock;

    @BeforeEach
    void setUp() {
        registrationControl = new RegistrationControl(studentRepositoryMock,unternehmenRepositoryMock,userRepositoryMock,rolleRepositoryMock);
    }

    @Test
    void testCreateStudent() {
        //given
        RegistrationDTOStudent dto = new RegistrationDTOStudent();
        dto.setMail("123@web.de");
        dto.setUserName("Thomsas");

        given(userRepositoryMock.findUserByEmail("123@web.de")).willReturn(Optional.empty());
        given(userRepositoryMock.findUserByUserid("Thomas")).willReturn(Optional.empty());


        //when

        RegistrationResult awaitResult = registrationControl.createStudent(dto);
        verify(rolleRepositoryMock,times(1)).save(any());
        verify(userRepositoryMock,times(1)).save(any());
        verify(studentRepositoryMock,times(1)).save(any());


        //then
        assertEquals (awaitResult.getReason(),RegistrationResult.REGISTRATION_SUCCESSFULL);

    }

    @Test
    void testCreateUnternehmen() {
        //given
        RegistrationDTOCompany dto = new RegistrationDTOCompany();
        dto.setMail("CompanyMail@gmail.com");
        dto.setUserName("Telekom");

        given(userRepositoryMock.findUserByEmail("CompanyMail@gmail.com")).willReturn(Optional.empty());
        given(userRepositoryMock.findUserByUserid("Telekom")).willReturn(Optional.empty());

        //when

        RegistrationResult awaitResult = registrationControl.createUnternehmen(dto);
        verify(rolleRepositoryMock,times(1)).save(any());
        verify(userRepositoryMock,times(1)).save(any());
        verify(unternehmenRepositoryMock,times(1)).save(any());

        //then
        assertEquals (awaitResult.getReason(),RegistrationResult.REGISTRATION_SUCCESSFULL);
    }
}
