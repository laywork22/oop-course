// Importare ulteriori package, ove necessario

import java.time.LocalDateTime;

public class LightSensorMeasure implements Comparable<LightSensorMeasure> {
    private final LocalDateTime time;
    private final double value;
    private final int sensorId;

    // Costruttore

    public LightSensorMeasure(LocalDateTime time, double value, int sensorId) {
        this.time = time;
        this.value = value;
        this.sensorId = sensorId;
    }

    // Getter

    public LocalDateTime getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }

    public int getSensorId() {
        return sensorId;
    }

    // Override dei metodi
    @Override
    public int compareTo(LightSensorMeasure o) {
        int comp = Integer.compare(this.sensorId, o.sensorId);

        if (comp == 0) {
            comp = this.time.compareTo(o.getTime());
        }

        return comp;
    }

    @Override
    public String toString() {
        return "Time: " + time + ", Value (lux): " + value + ", Sensor ID: " + sensorId + "\n";
    }

}
