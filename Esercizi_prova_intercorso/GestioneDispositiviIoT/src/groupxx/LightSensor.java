package groupxx;

import java.time.LocalDate;

public class LightSensor extends Sensor {
    private final int wavelength;
    private final boolean proximityDetection;
    private final Range<Double> operatingTemperature;
    private final LightSensorType lst;

    public LightSensor(String partNumber, String manufacturer, double price, Range<Double> vs, SensorOutputType sot, LocalDate manifacturingDate, int wavelength, boolean proximityDetection, Range<Double> operatingTemperature, LightSensorType lst) {
        super(partNumber, manufacturer, price, vs, sot, manifacturingDate);

        this.wavelength = wavelength;
        this.proximityDetection = proximityDetection;
        this.operatingTemperature = operatingTemperature;
        this.lst = lst;
    }

    public LightSensorType getLst() {
        return lst;
    }

    public int getWavelength() {
        return wavelength;
    }

    public boolean isProximityDetection() {
        return proximityDetection;
    }

    public Range<Double> getOperatingTemperature() {
        return operatingTemperature;
    }

    @Override
    public boolean hasValidPartNumber() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("***LightSensor***");

        sb.append("\nWavelength(nm)= ").append(wavelength);
        sb.append("\nProximityDetection= ").append(proximityDetection);
        sb.append("\nOperating Temperature(°C)= ").append(operatingTemperature.toString());
        sb.append("\nLight Sensor Type= ").append(lst.toString());
        sb.append("\n").append(super.toString());

        return sb.toString();
    }

}
