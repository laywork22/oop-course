package groupxx;

import java.util.Objects;

public class LSMinOperatingTemperatureFilter implements SensorFilter {
    double minTemperature;

    public LSMinOperatingTemperatureFilter(double minTemperature){
        this.minTemperature = minTemperature;
    }

    public double getMinTemperature(){
        return minTemperature;
    }

    @Override
    public boolean checkSensor(Sensor s) {
        if (!(s instanceof LightSensor)) return false;

        LightSensor ls = (LightSensor) s;

        return ls.getOperatingTemperature().getLow().equals(this.getMinTemperature());
    }
}
