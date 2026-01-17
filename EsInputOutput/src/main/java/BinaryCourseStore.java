import java.io.*;
import java.util.Set;

public class BinaryCourseStore implements CourseStore {
    @Override
    public void save(String fileName, Course c) {
        if (fileName == null) return;

        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            Set<String> matricole = c.matricole();

            for (String m : matricole) {
                Student s = c.get(m);

                dos.writeUTF(s.getNome());
                dos.writeUTF(s.getMatricola());
                dos.writeInt(s.getEsamiSostenuti());
                dos.writeDouble(s.getVotoMedio());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course load(String fileName) {
        Course c = new Course();

        try (DataInputStream dos = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            Student s = new Student(dos.readUTF(), dos.readUTF(), dos.readInt(), dos.readDouble());

            c.put(s.getMatricola(), s);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return c;
    }
}
