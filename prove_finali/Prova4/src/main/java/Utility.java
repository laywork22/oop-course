import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author leonardorundo
 */
public class Utility {
    
    public static void leggiCSV(String nomeFile) {
    
        try(BufferedReader bf = new BufferedReader(new FileReader(nomeFile))) {
            
            String l;
            
            while((l = bf.readLine()) != null) {
            
                System.out.println(l);
            
            
            }
            

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        catch (IOException ex) {
            System.out.println(ex);
        } 
    
    }
    
}
