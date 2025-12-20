package it.report.ingv;

import java.util.Comparator;

public class MagnitudeComparator implements Comparator<EQEvent> {
    @Override
    public int compare(EQEvent o1, EQEvent o2) {
        return Double.compare(o1.getMagnitude(), o2.getMagnitude());
    }
}
