package groupxx;

import java.util.Comparator;

public class ManifacturingDateComparator implements Comparator<Sensor> {
    public int compare(Sensor o1, Sensor o2){
        if (o1.getManifacturingDate().equals(o2.getManifacturingDate())) return o1.compareTo(o2);

        return o1.getManifacturingDate().compareTo(o2.getManifacturingDate());
    }
}
