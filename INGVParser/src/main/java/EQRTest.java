/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.report.ingv.EQReport;
import it.report.ingv.MagnitudeComparator;

import java.io.IOException;

/**
 *
 * @author lucagreco
 */
public class EQRTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {


        EQReport eq = it.report.ingv.EQReport.readFromTextFile("src/main/query");

        System.out.println(eq);

        eq.sort(new MagnitudeComparator());

        EQReport.printToTextFile(eq, "sorted");


        // TODO code application logic heres
    }

}
