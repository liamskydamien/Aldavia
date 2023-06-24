package org.hbrs.se2.project.aldavia.test.StellenanzeigenSuchenTest;

import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.TaetigkeitsfeldService;
import org.hbrs.se2.project.aldavia.service.UnternehmenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaetigkeitsfeldStellenanzeigenTest {
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Autowired
    private TaetigkeitsfeldService taetigkeitsfeldService;

    @Autowired
    private UnternehmenService unternehmenService;

    @Autowired
    private StellenanzeigenService stellenanzeigeService;


    @Test
    public void testTaetigkeitsfeldStellenanzeigen() {

    }
}
