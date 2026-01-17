import java.io.*;

public class SerializedCourseStore implements CourseStore {

    @Override
    public void save(String fileName, Course c) {
        if (fileName == null) return;

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            oos.writeObject(c);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato.");
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file serializzato.");
        }
    }

    @Override
    public Course load(String fileName) {
        Course c = new Course();

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            c = (Course) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato.");
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file serializzato.");
        } catch (ClassNotFoundException e) {
            System.out.println("Errore nella serializzazione: la classe non è stata correttamente letta.");
        }

        return c;
    }
}
