package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsOverviewUnternehmen;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigenDataDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BewerbungsOverviewUnternehmenTest {

    private static final String TEST = "test";
    @Mock
    private UnternehmenService unternehmenServiceMock;

    private BewerbungsOverviewUnternehmen bewerbungsOverviewUnternehmen;

    @BeforeEach
    public void setup() {
        bewerbungsOverviewUnternehmen = new BewerbungsOverviewUnternehmen(unternehmenServiceMock);
    }

    @Test
    public void testGetBewerbungenStellenanzeige() throws ProfileException {
        // Setup

        Map<String, Object> map = BewerbungsSetup.setup();
        String userid = (String) map.get("userid");
        Unternehmen unternehmen = (Unternehmen) map.get("unternehmen");

        given(unternehmenServiceMock.getUnternehmen(userid)).willReturn(unternehmen);

        // Run the test
        final List<StellenanzeigenDataDTO> result = bewerbungsOverviewUnternehmen.getBewerbungenStellenanzeige(userid);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getBewerbungen().size());
        assertEquals(TEST, result.get(0).getBewerbungen().get(0).getBewerbungsSchreiben());
        assertEquals(TEST, result.get(0).getBewerbungen().get(1).getBewerbungsSchreiben());
    }

}
