package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewStudent;
import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class BewerbungsOverviewStudentTest {

    private BewerbungsOverviewStudent bewerbungsOverviewStudent;
    @Mock
    private StudentService studentServiceMock;
    @Mock
    private BewerbungsService bewerbungsServiceMock;
    @BeforeEach
    public void setup() {
        bewerbungsOverviewStudent = new BewerbungsOverviewStudent(studentServiceMock, bewerbungsServiceMock);
    }

    @Test
    public void testGetBewerbungen() throws ProfileException {

        // Setup
        Map<String, Object> map = BewerbungsSetup.setup();
        String userid = (String) map.get("userid");
        Student student = (Student) map.get("student");

        given(studentServiceMock.getStudent(userid)).willReturn(student);

       // Run the test
        List<BewerbungsDTO> result = bewerbungsOverviewStudent.getBewerbungenStudent(userid);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    public void testDelete() throws BewerbungsException {
        // Setup
        BewerbungsDTO bewerbungsDTO = BewerbungsDTO.builder()
                .id(1)
                .build();
        bewerbungsOverviewStudent.removeBewerbung(bewerbungsDTO);
    }
}
