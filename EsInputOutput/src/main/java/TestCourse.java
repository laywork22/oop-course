public class TestCourse {
    public static void main(String[] args) {
        Course c = new Course();

        Student s1 = new Student("Luigi Bartolini", "0612703214", 10, 19.2);
        Student s2 = new Student("Carlo Arena", "0612700264", 14, 26.32);
        Student s3 = new Student("Martina Sorrento", "0612743300", 10, 29.0);

        c.put(s1.getMatricola(), s1);
        c.put(s2.getMatricola(), s2);
        c.put(s3.getMatricola(), s3);

        System.out.println("--- STAMPA DEGLI STUDENTI DEL CORSO ---");
        System.out.println(c);

        CourseStore salvataggioBinario = new BinaryCourseStore();
        CourseStore salvataggioSerializzato = new SerializedCourseStore();
        CourseStore salvataggioCaratteri = new CharacterCourseStore();

        //Test salvataggio
        salvataggioBinario.save("corsi.bin", c);
        salvataggioSerializzato.save("corsi.ser", c);
        salvataggioCaratteri.save("corsi.csv", c);

        //Test caricamento
        Course c2 = salvataggioBinario.load("corsi.bin");
        Course c3 = salvataggioSerializzato.load("corsi.ser");
        Course c4 = salvataggioCaratteri.load("corsi.csv");

        System.out.println("\n\n--- STAMPA STUDENTI DA FILE BINARIO ---\n" + c2);
        System.out.println("\n\n--- STAMPA STUDENTI DA FILE SERIALIZZATO ---\n" + c3);
        System.out.println("\n\n--- STAMPA STUDENTI DA FILE DI TESTO ---\n" + c4);

    }

}
