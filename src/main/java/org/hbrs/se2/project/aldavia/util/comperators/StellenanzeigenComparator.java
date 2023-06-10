package org.hbrs.se2.project.aldavia.util.comperators;


import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;

import java.util.Comparator;
import java.util.List;

public class StellenanzeigenComparator implements Comparator<StellenanzeigeDTO> {

    private List<String> interessen;
    private List<String> kenntnisse;

    public StellenanzeigenComparator(List<String> interessen, List<String> kenntnisse){
        this.interessen = interessen;
        this.kenntnisse = kenntnisse;
    }

    @Override
    public int compare(StellenanzeigeDTO o1, StellenanzeigeDTO o2) {
        return calculateScore(o2.getTaetigkeitsfelder()) - calculateScore(o1.getTaetigkeitsfelder());
    }

    /**
     * Calculates the score of a Stellenanzeige for the sorting algorithm
     * @param input List of Taetigkeitsfelder
     * @return The score
     */
    private int calculateScore(List<TaetigkeitsfeldDTO> input){
        // Starts with 0
        int i = 0;
        for (TaetigkeitsfeldDTO taetigkeitsfeldDTO: input){
            // if its an interest increase by 2
            if (interessen.contains(taetigkeitsfeldDTO.getName())){
                i = i + 2;
            }
            // if its an skill increase by 1
            if (kenntnisse.contains(taetigkeitsfeldDTO.getName())){
                i = i + 1;
            }
        }
        return i;
    }
}
