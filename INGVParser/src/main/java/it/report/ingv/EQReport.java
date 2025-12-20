package it.report.ingv;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class EQReport {
    private final String name;
    private final List<EQEvent> eventList;

    public EQReport(String name) {
        this.name = name;
        this.eventList = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<EQEvent> getEventList() {
        return eventList;
    }

    public void addEvent(EQEvent e) {
        if (e != null){
            eventList.add(e);
        }
    }

    public void sort() {
        eventList.sort(null);
    }

    public void sort(Comparator<EQEvent> c) {
        eventList.sort(new MagnitudeComparator());
    }

    public static EQReport readFromTextFile(String filename) throws IOException {
        String name = filename.split("\\.")[0].toUpperCase();

        EQReport newRep = new EQReport(name);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            if (br.readLine() == null) return null;
            String line;

            while((line = br.readLine()) != null) {

                String campi[] = line.split("\\|");

                newRep.addEvent(new EQEvent(Integer.parseInt(campi[0]), LocalDateTime.parse(campi[1]), Double.parseDouble(campi[2]),
                       Double.parseDouble(campi[3]), Double.parseDouble(campi[4]), campi[5], campi[6], campi[7],
                        campi[8], campi[9], Double.parseDouble(campi[10]), campi[11], campi[12]));
            }
        }catch (IOException ex) {
            System.out.println("IO exceptiion");
            throw ex;
        }

        return newRep;
    }

    public static void printToTextFile(EQReport eqr, String filename)  {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.println("EventID|Time|Latitude|Longitude|Depth/Km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName");

            for (EQEvent e : eqr.eventList) {
                pw.print(e.getEventID());
                pw.append("|");
                pw.print(e.getTime());
                pw.append("|");

                pw.print(e.getLatitude());
                pw.append("|");

                pw.print(e.getLongitude());
                pw.append("|");

                pw.print(e.getDepthKm());
                pw.append("|");

                pw.append(e.getAuthor());
                pw.append("|");

                pw.append(e.getCatalog());
                pw.append("|");

                pw.append(e.getContributor()).append("|");

                pw.append(e.getContributorID()).append("|").append(e.getMagType()).append("|");

                pw.print(e.getMagnitude());
                pw.append("|");

                pw.append(e.getMagAuthor()).append("|");
                pw.println(e.getEventLocationName());

            }
        } catch (IOException ex) {
            System.out.println("Errore nella scrittura su file del report");
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(name);

        for(EQEvent e : eventList) {
            sb.append(e);
        }

        return sb.toString();
    }
}
