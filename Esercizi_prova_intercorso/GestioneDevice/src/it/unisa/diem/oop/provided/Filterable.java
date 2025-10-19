package it.unisa.diem.oop.provided;


import it.unisa.diem.oop.developed.groupxx.Device;
import it.unisa.diem.oop.developed.groupxx.DeviceStore;

import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucagreco
 */
public interface Filterable {
    
   public DeviceStore filter(DeviceFilter d, Comparator<Device> c);
    
}
