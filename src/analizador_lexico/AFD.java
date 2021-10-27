package analizador_lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class AFD {

    public static HashSet<AFD> ConjAFDs = new HashSet<AFD>();
    public int NumEstados;
    public int CardAlfabeto;
    public char[] Alfabeto;
    public int[][] TransicionesAFD;
    public int[][] TablaAFD;
    public int IdAFD;

    public AFD() {
        IdAFD = -1;
    }

    public AFD(int NumeroDeEstados, int IdAutFD) {
        TablaAFD = new int[NumeroDeEstados][257];
        for (int i = 0; i < NumeroDeEstados; i++) {
            for (int j = 0; j < 257; j++) {
                TablaAFD[i][j] = -1;
            }
        }
        NumEstados = NumeroDeEstados;
        IdAFD = IdAutFD;
        AFD.ConjAFDs.add(this);
    }

    public void GuardarAFN(String NombArchivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            fichero = new FileWriter("C:\\Users\\Betuc\\Documents\\ESCOM\\5 SEMESTRE\\COMPILADORES\\AFDs\\" + NombArchivo + ".txt");
            pw = new PrintWriter(fichero);

            pw.println(this.NumEstados);
            for (int i = 0; i < this.NumEstados; i++) {
                for (int j = 0; j < 257; j++) {
                    pw.print(this.TablaAFD[i][j]);
                    if (j != 256);
                    pw.print(";");
                }
                pw.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void ImportarAFD(String Archivo, int ID) {
        int IdEdo;
        int k;
        String Renglon;
        String[] ValoresRenglon = new String[257];

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(Archivo));
            IdEdo = 0;
            String renglon = br.readLine();
            this.NumEstados = Integer.parseInt(renglon);
            System.out.println("Numero de estados: "+this.NumEstados);
            this.TablaAFD = new int[this.NumEstados][257];
            while (IdEdo < this.NumEstados) {
                Renglon = br.readLine();
                ValoresRenglon = Renglon.split(String.valueOf(';'));
                for (k = 0; k < 257; k++) {
                    this.TablaAFD[IdEdo][k] = Integer.parseInt(ValoresRenglon[k]);
                }
                IdEdo++;
            }
            this.NumEstados = IdEdo;
            this.IdAFD = ID;
            AFD.ConjAFDs.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != br) {
                    br.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}