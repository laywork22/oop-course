package groupxx;

public class I2CSensorFilter implements SensorFilter {
    public I2CSensorFilter(){

    }

    @Override
    public boolean checkSensor(Sensor s) {
        return s.getSot() == SensorOutputType.I2C;
    }
}
