import java.io.*;
import java.util.Set;

public class CharacterCourseStore implements  CourseStore{
    @Override
    public void save(String fileName, Course c) {
        if (fileName == null) return;

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
            pw.println("nome;matricola;esamiSostenuti;votoMedio");

            Set<String> matricole = c.matricole();

            for (String m : matricole) {
                Student s = c.get(m);

                pw.append(s.getNome()).append(";").append(s.getMatricola()).append(";");
                pw.print(s.getEsamiSostenuti());
                pw.append(";");
                pw.println(s.getVotoMedio());
            }

        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file di testo.");
        }
    }

    @Override
    public Course load(String fileName) {
        Course c = new Course();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            if (br.readLine() == null) return c;

            String line;

            while((line = br.readLine()) != null) {
                String[] campi = line.split("[;|\\n]");

                Student s = new Student(campi[0], campi[1], Integer.parseInt(campi[2]), Double.parseDouble(campi[3]));
                c.put(s.getMatricola(), s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (EOFException e) {
            System.out.println("Lettura da file di testo completata con successo.");
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file di testo");
        }

        return c;
    }
}
