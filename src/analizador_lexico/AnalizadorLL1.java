package analizador_lexico;

import java.util.HashSet;
import java.util.Stack;

public class AnalizadorLL1 {

    public DescRecGram_Gram DescRecG;
    public AnalizLexico LexGram;
    public String Gram;
    public String Sigma;
    public int[][] TablaLL1;
    public SimbTerm[] Vt;
    public String[] Vt2;
    public int[] Vt3;
    public String[] Vn;
    String ArchAFD = "C:\\Users\\Betuc\\Documents\\ESCOM\\5 SEMESTRE\\COMPILADORES\\AFDs\\Gram_gram.txt";

    public AnalizadorLL1(String CadGramatica) {
        Gram = CadGramatica;
        AFD afd = new AFD();
        afd.ImportarAFD(ArchAFD, 0);
        DescRecG = new DescRecGram_Gram(CadGramatica, afd);
    }

    public void SetLexico(String sigma, AFD AutFD) {
        LexGram = new AnalizLexico(sigma, AutFD);
    }

    public void CrearTablaLL1() {
        int j;
        HashSet<String> ResultFirst = new HashSet<>();
        HashSet<String> ResultFollow = new HashSet<>();

        DescRecG.AnalizarGramatica();
        DescRecG.IdentificarTerminales();

        Vt = new SimbTerm[DescRecG.Vt.size() + 1]; //Numero de columnas de la tabla LL1
        Vt2 = new String[DescRecG.Vt.size() + 1]; //Arreglo de terminales para asociarlos a las columnas de la tabla LL1

        Vn = new String[DescRecG.Vn.size() + 1];
        j = 0;
        // Se llenan los 2 arreglos de terminales
        for (String s : DescRecG.Vt) {
            Vt[j] = new SimbTerm(s, -1); // Falta asignarle el Token
            Vt2[j++] = s;
        }
        Vt[j] = new SimbTerm("$", -1);
        Vt2[j++] = "$";

        j = 0;
        // Se llena el arreglo de no terminales
        for (String s : DescRecG.Vn) {
            Vn[j++] = s;
        }
        Vn[j++] = "$";

        TablaLL1 = new int[DescRecG.Vn.size() + 1][DescRecG.Vt.size() + 1];

        for (int k = 0; k <= DescRecG.Vn.size(); k++) {
            for (int l = 0; l <= DescRecG.Vt.size(); l++) {
                TablaLL1[k][l] = -1;
            }
        }

        int renglon, columna;
        columna = renglon = -1;

        for (int NumRegla = 0; NumRegla < DescRecG.NumReglas; NumRegla++) {
            ResultFirst.clear();
            ResultFollow.clear();

            int aux = 0;
            for (String s : Vn) {
                if (s.equals(DescRecG.ArrReglas[NumRegla].InfSimbolo.Simbolo)) {
                    renglon = aux;
                    break;
                }
                aux++;
            }

            ResultFirst = DescRecG.First(DescRecG.ArrReglas[NumRegla].ListaLadoDerecho);

            for (String s : ResultFirst) {
                aux = 0;
                for (String c : Vt2) {
                    if (c.equals(s)) {
                        columna = aux;
                        break;
                    }
                    aux++;
                }

                if (columna >= 0) {
                    TablaLL1[renglon][columna] = NumRegla + 1;
                }
            }

            //Si hay epsilon en el first, se calcula el Follow del lado izquierdo
            if (ResultFirst.contains("£")) {
                ResultFollow = DescRecG.Follow(DescRecG.ArrReglas[NumRegla].InfSimbolo.Simbolo);

                for (String s : ResultFollow) {
                    aux = 0;
                    for (String c : Vt2) {
                        if (c.equals(s)) {
                            columna = aux;
                            break;
                        }
                        aux++;
                    }

                    TablaLL1[renglon][columna] = NumRegla + 1;
                }
            }
        }
    }

    public void setVt3() {
        Vt3 = new int[Vt.length];

        for (int i = 0; i < Vt.length; i++) {
            Vt3[i] = Vt[i].Token;
        }
    }

    public boolean AnalizSintacLL1(String Cadena) {
        int Tok, TokTerm;
        int renglon = 0, columna = 0;
        Stack<SimbTerm> Pila = new Stack<>();
        Stack<SimbTerm> Pila2 = new Stack<>();
        SimbTerm simbolo, elemPila;
        Sigma = Cadena;
        String CadenaAnaliz = Sigma;
        LexGram.SetSigma(Sigma);
        int Accion;

        Pila.clear();
        simbolo = new SimbTerm(Vn[0], false, -1);
        Pila.push(simbolo);
        Tok = LexGram.yylex();
        do {
            if (Pila.size() == 0 && Tok == 0) {
                return true;
            }
            
            System.out.println("Entre LL1");
            elemPila = Pila.pop();
            
            if (elemPila.Terminal) {
                if (elemPila.Token == Tok) {
                    Tok = LexGram.yylex();
                    continue;
                } else {
                    return false;
                }
            } else {
                for (int i = 0; i < Vn.length; i++) {
                    if (Vn[i].equals(elemPila.Simbolo)) {
                        renglon = i;
                    }
                }

                for (int i = 0; i < Vt3.length; i++) {
                    if (Vt3[i] == Tok) {
                        columna = i;
                    }
                }

                Accion = TablaLL1[renglon][columna];
                Pila2.clear();
                if (Accion != -1) {
                    for (Nodo n : DescRecG.ArrReglas[Accion - 1].ListaLadoDerecho.lista) {
                        TokTerm = -1;
                        if (n.Simbolo.equals("£")) {
                            continue;
                        }
                        if (n.Terminal) {
                            int ind = 0;

                            for (int i = 0; i < Vt2.length; i++) {
                                if (Vt2[i].equals(n.Simbolo)) {
                                    ind = i;
                                }
                            }
                            if (ind < 0) {
                                return false;
                            }
                            TokTerm = Vt3[ind];
                        }
                        simbolo = new SimbTerm(n.Simbolo, n.Terminal, TokTerm);
                        Pila2.push(simbolo);
                    }
                    while (Pila2.size() > 0) {
                        Pila.push(Pila2.pop());
                    }
                } else {
                    return false;
                }
            }
        } while (true);
    }
}
