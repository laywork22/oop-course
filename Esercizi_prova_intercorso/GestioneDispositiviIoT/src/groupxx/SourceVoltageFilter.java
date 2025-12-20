package groupxx;

public class SourceVoltageFilter implements SensorFilter {
    private final double vs;

    public SourceVoltageFilter(double vs){
        this.vs = vs;
    }

    public double getVs() {
        return vs;
    }

    @Override
    public boolean checkSensor(Sensor s) {
        return s.getVs().contains(vs);
    }
}
