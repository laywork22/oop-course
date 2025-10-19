package it.unisa.diem.oop.developed.groupxx;

import it.unisa.diem.oop.provided.MobileCPUType;
import javafx.beans.binding.StringBinding;

import java.time.LocalDate;

public class Smartphone extends Device{
    private final MobileCPUType cpu;
    private final float screenSizeInch;

    public Smartphone(MobileCPUType cpu, float screenSizeInch,String serialNumber, int year, int month, int dayOfMonth, int ramSize, int storageCapacity) {
        super(serialNumber, year, month, dayOfMonth, ramSize, storageCapacity);

        this.cpu = cpu;
        this.screenSizeInch = screenSizeInch;
    }

    public MobileCPUType getCpu(){
        return cpu;
    }

    public float getScreenSizeInch(){
        return screenSizeInch;
    }

    @Override
    public String toString(){
        StringBuffer bf = new StringBuffer("\nSmartphone\n");
        bf.append(super.toString());
        bf.append("\nCPU= ").append(cpu.toString());
        bf.append("\nscreenSizeInch= ").append(screenSizeInch);

        return bf.toString();
    }

    @Override
    public boolean hasTouchScreen(){
        return true;
    }
}
