/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystudentlistah;

/**
 *
 * @author laywork
 */
//la tableview gestisce osservabili
public class Studente {
    //private StringProperty name;
    private String name;
    private String surname;
    private String code;

    public Studente(String name, String surname, String code) {
        this.name = name;
        //this.name = new SimpleStringProperty(name);
        this.surname = surname;
        this.code = code;
    }

    public String getName() {
        return name;
        
        //return name.get();
    }
    
    /*
    public StringProperty nameProperty() {return name;}
    */
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Studente{" + "name=" + name + ", surname=" + surname + ", code=" + code + "}";
    }
    
}
