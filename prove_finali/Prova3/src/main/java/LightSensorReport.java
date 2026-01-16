// Importare ulteriori package, ove necessario


import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class LightSensorReport  {
    private final Set<LightSensorMeasure> report;

    public LightSensorReport() {
        this.report = new TreeSet<>();
    }

    public void addMeasure(LightSensorMeasure lsm) {
        if (lsm != null) {
            report.add(lsm);
        }
        else {
            System.out.println("La misura è illeggibile");
        }
    }


// Metodi
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("--- Visita Report: Sensori di luminosità ---\n");

        for (LightSensorMeasure lsm : report) {
            sb.append(lsm.toString());
        }

        return sb.toString();
    }

    public void exportReport(String nomeFile) {
        try(DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)))) {
            for(LightSensorMeasure lsm : report) {
                dos.writeUTF(lsm.getTime().toString());
                dos.writeDouble(lsm.getValue());
                dos.writeInt(lsm.getSensorId());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato. Scrittura impossibile.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public LightSensorReport importReport(String nomeFile) {
        LightSensorReport lsp = new LightSensorReport();

        try(DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeFile)))) {
            while(true) {
                LightSensorMeasure lsm = new LightSensorMeasure(LocalDateTime.parse(dis.readUTF()), dis.readDouble(), dis.readInt());
                lsp.addMeasure(lsm);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato. Lettura impossibile.");
        } catch(EOFException e) {
            System.out.println("Lettura completata.");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lsp;
    }


    public void populateReport(int n) {

        Random r = new Random();

        for (int i = 0; i < n; i++) {
            this.addMeasure(new LightSensorMeasure(LocalDateTime.of(2023, r.nextInt(11) + 1, r.nextInt(27) + 1, r.nextInt(23), r.nextInt(59)), r.nextDouble() * 100, r.nextInt(5)));
        }
    }

}
