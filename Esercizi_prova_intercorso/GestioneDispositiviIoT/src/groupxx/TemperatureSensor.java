package groupxx;

import java.time.LocalDate;

public class TemperatureSensor extends Sensor{
    private final double tempResolution;
    private final double outputSensitivity;
    private final double tempError;
    private final int bits;

    //costruttore per sensori digitali
    public TemperatureSensor(String partNumber, String manufacturer, double price,
                             Range<Double> vs, SensorOutputType sot, LocalDate manifacturingDate, double
                                      tempResolution, double tempError, int bits) throws BadArgumentsForSensorTypeException{

        super(partNumber, manufacturer, price, vs, sot, manifacturingDate);

        this.tempResolution = tempResolution;
        this.outputSensitivity = 0.0;
        this.tempError = tempError;
        this.bits = bits;

    }

    //costruttore per sensori analogici
    public TemperatureSensor(String partNumber, String manufacturer, double price,
                             Range<Double> vs, SensorOutputType sot, LocalDate manifacturingDate, double tempError,
                             double outputSensitivity){

        super(partNumber, manufacturer, price, vs, sot, manifacturingDate);

        this.tempResolution = 0.0;
        this.outputSensitivity = outputSensitivity;
        this.tempError = tempError;
        this.bits = 0;

    }

    public double getTempResolution() {
        return tempResolution;
    }

    public double getTempError() {
        return tempError;
    }

    public int getBits() {
        return bits;
    }

    public double getOutputSensitivity() {
        return outputSensitivity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(this.getSot().equals(SensorOutputType.ANALOG)){
            sb.append("***Analog Temperature Sensor***");
            sb.append("\nTemperature Error(Deg)= ").append(this.getTempError());
            sb.append("\nOutputSensitivity(mV/°C)= ").append(this.getOutputSensitivity());
        }
        else if (this.getSot().equals(SensorOutputType.DIGITAL)){
            sb.append("***Digital Temperature Sensor***");
            sb.append("\nTemperature Resolution(°/LSB)= ").append(this.getTempResolution());
            sb.append("\nTemperature Error(Deg)= ").append(this.getTempError());
            sb.append("\nBits= ").append(this.getBits());
        }

        sb.append(super.toString());

        return sb.toString();
    }

    @Override
    public boolean hasValidPartNumber() {
        return this.getPartNumber().matches("^AD\\d{4}$") || this.getPartNumber().matches(("^ADT\\d{4}$"));
    }
}
