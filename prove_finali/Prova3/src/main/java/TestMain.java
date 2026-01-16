// Non modificare questo file

public class TestMain {


    public static void main(String[] args) {


        LightSensorReport report = new LightSensorReport();

        report.populateReport(6);

        System.out.println("\n--- Visita Report: Sensori di luminosità ---");
        System.out.println(report.toString());

        report.exportReport("export.bin");

        LightSensorReport report_backup = report.importReport("export.bin");

        System.out.println("\n--- Import Back-up ---");
        System.out.println(report_backup.toString());

    }

}
