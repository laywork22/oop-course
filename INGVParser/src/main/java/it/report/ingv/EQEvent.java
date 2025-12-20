package it.report.ingv;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leonardorundo
 */
import java.time.LocalDateTime;
import java.util.Objects;


public class EQEvent implements Comparable<EQEvent> {
    private int eventID;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double depthKm;
    private String author;
    private String catalog;
    private  String contributor;
    private String contributorID;
    private String magType;
    private double magnitude;
    private String magAuthor;
    private String eventLocationName;


    public EQEvent(int eventID, LocalDateTime time, double latitude,  double longitude, double depthKm,
                   String author, String catalog, String contributor, String contributorID, String magType, double magnitude,
                   String magAuthor, String eventLocationName) {
        this.eventID = eventID;
        this.time = time;
        this.latitude = latitude;
        this.catalog = catalog;
        this.longitude = longitude;
        this.depthKm = depthKm;
        this.author = author;
        this.contributor = contributor;
        this.contributorID = contributorID;
        this.magType = magType;
        this.magnitude = magnitude;
        this.magAuthor = magAuthor;
        this.eventLocationName = eventLocationName;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDepthKm() {
        return depthKm;
    }

    public void setDepthKm(double depthKm) {
        this.depthKm = depthKm;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getContributorID() {
        return contributorID;
    }

    public void setContributorID(String contributorID) {
        this.contributorID = contributorID;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getMagAuthor() {
        return magAuthor;
    }

    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash*31 + Integer.hashCode(eventID);

        return hash;
    }

    @Override
    public int compareTo(EQEvent o) {
        return Integer.compare(this.eventID, o.getEventID());
    }

    @Override
    public String toString() {
        return "it.report.ingv.EQEvent{" + "eventID=" + eventID + ", \ntime=" + time + ", \nlatitude=" + latitude + ", \nlongitude=" + longitude + ", \ndepthkm=" + depthKm + ", \nauthor=" + author + ", \ncontributor=" + contributor + ", \ncontributorID=" + contributorID + ", \nmagType=" + magType + ", \nmagnitude=" + magnitude + ", \nmagAuthor=" + magAuthor + ", \neventLocationName=" + eventLocationName + "}\n";
    }

}
