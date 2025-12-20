package groupxx;

import java.time.LocalDate;

public class Accelerometer extends Sensor{
    private final double range;
    private final int sensingAxis;
    private final double bandwidth;

    public Accelerometer(String partNumber, String manufacturer, double price, Range<Double> vs, SensorOutputType sot, LocalDate manifacturingDate, double range, int sensingAxis, double bandwidth) {
        super(partNumber, manufacturer, price, vs, sot, manifacturingDate);

        this.range = range;
        this.sensingAxis = sensingAxis;
        this.bandwidth = bandwidth;
    }

    public double getRange() {
        return range;
    }

    public int getSensingAxis() {
        return sensingAxis;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    @Override
    public boolean hasValidPartNumber() {
        String firstMatch = "^ADXL\\d{3}$";
        String secondMatch = "^ADIS\\d{5}$";

        return super.getPartNumber().matches(firstMatch) || super.getPartNumber().matches(secondMatch);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("***Accelerometer***").append("\nRange(g)= ").append(getRange());
        sb.append("\nNumber of sensing Axis= ").append(getSensingAxis());
        sb.append("\nBandwidth= ").append(getBandwidth()).append("\n");
        sb.append(super.toString());

        return sb.toString();
    }
}
