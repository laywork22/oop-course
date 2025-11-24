package it.unisa.diem.oop.developed.groupxx;
import it.unisa.diem.oop.provided.*;

public class Notebook extends Device{
    private final CPUType type;
    private final NBScreenType screenType;
    private final boolean touchScreen;

    //costruttore per i Notebook
    public Notebook(CPUType type, NBScreenType screenType, boolean touchScreen,String serialNumber, int year, int month, int dayOfMonth, int ramSize, int storageCapacity) {
        super(serialNumber, year, month, dayOfMonth, ramSize, storageCapacity);

        this.type = type;
        this.screenType = screenType;
        this.touchScreen = touchScreen;
    }

    //costruttore per i notebook sprovvisti di touchscreen
    public Notebook(CPUType type, NBScreenType screenType,String serialNumber, int year, int month, int dayOfMonth, int ramSize, int storageCapacity) {
        this(type, screenType, false, serialNumber, year, month, dayOfMonth, ramSize, storageCapacity);
    }

    public CPUType getType(){
        return type;
    }

    public NBScreenType getScreenType(){
        return screenType;
    }

    @Override
    public boolean hasTouchScreen(){
        return touchScreen;
    }

    @Override
    public String toString(){
        StringBuffer bf = new StringBuffer("\nNotebook");

        bf.append(super.toString());
        bf.append("\nCPU=").append(type.toString());
        bf.append("\nscreenType=").append(screenType.toString());
        if (touchScreen){
            bf.append("\ntouchscreen ").append("available");
        }

        return bf.toString();
    }
}
