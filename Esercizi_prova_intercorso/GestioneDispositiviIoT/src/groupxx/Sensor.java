package groupxx;

import java.time.LocalDate;

public abstract class Sensor implements Comparable<Sensor> {
    private final String partNumber;
    private final String manufacturer;
    private final double price;
    private Range<Double> vs;
    private final SensorOutputType sot;
    private final LocalDate manifacturingDate;

    public Sensor(String partNumber, String manufacturer, double price, Range<Double> vs, SensorOutputType sot, LocalDate manifacturingDate) {
        this.partNumber = partNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.vs = vs;
        this.sot = sot;
        this.manifacturingDate = manifacturingDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        if (this == other) return true;

        Sensor s = (Sensor) other;

        return this.getPartNumber().equals(s.getPartNumber());
    }

    @Override
    public int hashCode() {
        return (this.getPartNumber() == null) ? -1 : this.getPartNumber().hashCode();
    }

    @Override
    public int compareTo(Sensor o) {
        return this.getPartNumber().compareToIgnoreCase(o.getPartNumber());
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDate getManifacturingDate() {
        return manifacturingDate;
    }

    public SensorOutputType getSot() {
        return sot;
    }

    public double getPrice() {
        return price;
    }

    public Range<Double> getVs() {
        return vs;
    }

    public abstract boolean hasValidPartNumber();

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer();

        bf.append("Part Number= ").append(partNumber).append("\n");
        bf.append("Manifacturer= ").append(manufacturer).append("\n");
        bf.append("Price($)= ").append(price).append("\n");
        bf.append("Source voltage range (V)= ").append(vs.getLow()).append(" - ").append(vs.getHigh()).append("\n");
        bf.append("Output type= ").append(sot.toString()).append("\n");
        bf.append("Manifacturing Date= ").append(manifacturingDate.toString()).append("\n");

        return bf.toString();
    }
}
