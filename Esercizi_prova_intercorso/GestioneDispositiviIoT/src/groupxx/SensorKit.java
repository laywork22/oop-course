package groupxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;

public class SensorKit implements Filterable {
    private final String name;
    private final List<Sensor> sensorList;

    public SensorKit(String name){
        this.name = name;
        this.sensorList = new ArrayList<>();
    }

    public void add(Sensor s) {
        if (!s.hasValidPartNumber()) throw new BadSensorPartNumberException();

        this.sensorList.add(s);
    }

    public void remove(Sensor s){
        if (this.sensorList.isEmpty()) {
            System.out.println("Impossibile rimuovere il sensore: il kit è vuoto\n");
        }

        this.sensorList.remove(s);
    }

    public void sort(Comparator<Sensor> c){
            this.sensorList.sort(c);
    }

    public Iterator<Sensor> iterator(){
        return this.sensorList.iterator();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("This Sensor Kit contains ").append(this.sensorList.size()).append(" sensors.\n");

        Iterator<Sensor> i = this.iterator();

        while(i.hasNext()){
            Sensor s = i.next();
            sb.append(s);
        }

        return sb.toString();
    }

    @Override
    public SensorKit filter(SensorFilter sf, Comparator<Sensor> c) {
        SensorKit sk = new SensorKit("SubSensorKit " + this.name);

        Iterator<Sensor> i = this.iterator();
        while(i.hasNext()){
            Sensor s = i.next();

            if(sf.checkSensor(s)){
                sk.add(s);
            }

        }

        sk.sort(c);

        return sk;
    }
}
