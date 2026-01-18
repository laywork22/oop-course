package librarymanager.persistence;

import librarymanager.models.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchivioCsvIO implements ArchivioIO<Archiviabile> {
    private void salvaLibriCSV(ArchivioDati archivio, File nomefile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomefile)))) {
            pw.println("TITOLO;AUTORE;ANNO;ISBN;COPIE TOTALI;COPIE DISPONIBILI;STATO");

            for (Libro l : archivio.getListaLibri()) {
                pw.append(l.getTitolo()).append(";").append(l.getAutori()).append(";");
                pw.print(l.getAnno());
                pw.append(";").append(l.getIsbn()).append(";");
                pw.print(l.getCopieTotali());
                pw.append(";");
                pw.print(l.getCopieDisponibili());
                pw.append(";");
                pw.println(l.getStato());
            }
        } catch (IOException e) {
            System.out.println("Scrittura Area Libri non conclusa correttamente");
        }
    }

    private void salvaUtentiCSV(ArchivioDati archivio, File nomefile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomefile)))) {
            pw.println("NOME;COGNOME;EMAIL;MATRICOLA;PRESTITI ATTIVI;STATO");

            for (Utente u : archivio.getListaUtenti()) {
                pw.append(u.getNome()).append(";").
                        append(u.getCognome()).append(";").
                        append(u.getEmail()).append(";")
                        .append(u.getMatricola()).append(";");
                pw.print(u.getPrestitiAttivi());
                pw.append(";");
                pw.println(u.getStato());
            }
        } catch (IOException e) {
            System.out.println("Scrittura Area Utenti non conclusa correttamente");
        }

    }

    private void salvaPrestitiCSV(ArchivioDati archivio, File nomefile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomefile)))) {
            pw.println("ID;MATRICOLA;NOME UTENTE;COGNOME;TITOLO;AUTORI;ISBN;DATA INIZIO;DATA FINE PRESTABILITA;DATA FINE EFFETTIVA;STATO");

            for (Prestito p : archivio.getListaPrestiti()) {
                pw.print(p.getId());
                pw.append(";").
                        append(p.getUtente().getMatricola()).append(";")
                        .append(p.getUtente().getNome()).append(";");
                pw.append(p.getUtente().getCognome()).append(";").
                        append(p.getLibro().getTitolo()).append(";").
                        append(p.getLibro().getAutori()).append(";").
                        append(p.getLibro().getIsbn()).append(";");
                pw.print(p.getDataInizio());
                pw.append(";");
                pw.print(p.getDataFinePrestabilita());
                pw.append(";");
                pw.print(p.getDataFineEffettiva());
                pw.append(";");
                pw.println(p.getStato());
            }
        } catch (IOException e) {
            System.out.println("Scrittura Area Prestiti non conclusa correttamente");
        }
    }


    /**
     * @brief Metodo di salvataggio archivio in formato CSV
     * @param archivio
     * @param directory
     */
    private void salvaArchivioCSV(ArchivioDati archivio, File directory) {
        if (!directory.exists()) directory.mkdirs();

        File fileLibri = new File(directory, "libri.csv");
        File fileUtenti = new File(directory, "utenti.csv");
        File filePrestiti = new File(directory, "prestiti.csv");

        salvaLibriCSV(archivio, fileLibri);
        salvaUtentiCSV(archivio, fileUtenti);
        salvaPrestitiCSV(archivio, filePrestiti);
    }


    private List<Libro> caricaLibriCSV (File fileLibri) {
        List<Libro> listaLibri = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileLibri))) {
            if (br.readLine() == null) return null;

            String line;

            while((line = br.readLine()) != null) {
                String campi[] = line.split(";");

                Libro l = new Libro(campi[0], campi[1], Integer.parseInt(campi[2]), campi[3], Integer.parseInt(campi[4]));
                l.setCopieDisponibili(Integer.parseInt(campi[5]));
                l.setStato(Libro.StatoLibro.valueOf(campi[6]));
                listaLibri.add(l);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inesistente");
        } catch (IOException e) {
            System.out.println("Errore di I/O");
        }

        return listaLibri;
    }

    private List<Utente> caricaUtentiCSV(File fileUtenti) {
        List<Utente> listaUtenti = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileUtenti))) {
            if (br.readLine() == null) return null;

            String line;

            while((line = br.readLine()) != null) {
                String campi[] = line.split(";");

                Utente u = new Utente(campi[0], campi[1], campi[2], campi[3]);

                u.setPrestitiAttivi(Integer.parseInt(campi[4]));
                u.setStato(Utente.StatoUtente.valueOf(campi[5]));

                listaUtenti.add(u);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inesistente");
        } catch (IOException e) {
            System.out.println("Errore di I/O");
        }

        return listaUtenti;
    }

    private List<Prestito> caricaPrestitiCSV(File filePrestiti, List<Libro> listaLibri, List<Utente> listaUtenti) {
        List<Prestito> listaPrestiti = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePrestiti))) {
            if (br.readLine() == null ) return null;

            String line;

            while ((line = br.readLine()) != null) {
                String campi[] = line.split(";");

                Utente dummyUser = new Utente(campi[2], campi[3],"b.sere@studenti.unisa.it", campi[1]);

                if (listaUtenti.contains(dummyUser)) {
                    int index = listaUtenti.indexOf(dummyUser);

                    if (index != -1) {
                        dummyUser = listaUtenti.get(index);
                    }
                }

                Libro dummyBook = new Libro(campi[4], campi[5], 2000, campi[6], 4);

                if (listaLibri.contains(dummyBook)) {
                    int index = listaLibri.indexOf(dummyBook);

                    if (index != -1) {
                        dummyBook = listaLibri.get(index);
                    }
                    else {
                        System.out.println("Errore nella lettura della lista libri");
                    }
                }

                Prestito p = new Prestito(dummyUser, dummyBook, LocalDate.parse(campi[8]));

                p.setDataInizio(LocalDate.parse(campi[7]));
                if (campi[9] != null && !campi[9].isEmpty() && !campi[9].equals("null")) {
                    p.setDataFineEffettiva(LocalDate.parse(campi[9]));
                }
                p.setStato(Prestito.StatoPrestito.valueOf(campi[10]));

                listaPrestiti.add(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Errore nel caricamento della lista prestiti");
        } catch (IOException e) {
            System.out.println("Errore di I/O");
        }

        return listaPrestiti;
    }

    private ArchivioDati caricaArchivioCSV(File directory) {
        if (!directory.exists())  return  null;

        File fileLibri = new File(directory, "libri.csv");
        File fileUtenti = new File(directory, "utenti.csv");
        File filePrestiti = new File(directory, "prestiti.csv");

        if (!fileLibri.exists() || !fileUtenti.exists() || !filePrestiti.exists()) return null;

        Prestito.setNextId(1);

        List<Libro> libri = caricaLibriCSV(fileLibri);
        List<Utente> utenti = caricaUtentiCSV(fileUtenti);
        List<Prestito> prestiti = caricaPrestitiCSV(filePrestiti, libri, utenti);

        ArchivioDati ad = new ArchivioDati(libri, utenti, prestiti);

        return ad;
    }

    @Override
    public void scriviArchivio(Archiviabile data, String filename) {
        File directory = new File(filename);
        salvaArchivioCSV((ArchivioDati) data, directory);
    }

    @Override
    public ArchivioDati leggiArchivio(String filename) {
        File directory = new File(filename);
        return caricaArchivioCSV(directory);
    }
}
