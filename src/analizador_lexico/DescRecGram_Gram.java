package analizador_lexico;

import java.util.HashSet;

public class DescRecGram_Gram {

    public String Gramatica;
    public AnalizLexico L;
    public ElemArreglo[] ArrReglas = new ElemArreglo[100];
    public int NumReglas = 0;

    HashSet<String> Vn = new HashSet<>();
    HashSet<String> Vt = new HashSet<>();

    public DescRecGram_Gram(String sigma, AFD afd) {
        Gramatica = sigma;
        L = new AnalizLexico(sigma, afd);
        Vn.clear();
        Vt.clear();
    }

    public boolean SetGramatica(String sigma) {
        Gramatica = sigma;
        L.SetSigma(sigma);
        return true;
    }

    public boolean AnalizarGramatica() {
        int token;
        if (G()) {
            token = L.yylex();
            if (token == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean G() {
        return ListaReglas();
    }

    public boolean ListaReglas() {
        int token;
        if (Reglas()) {
            token = L.yylex();
            if (token == TokensGram_Gram.PC) {
                if (ListaReglasP()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ListaReglasP() {
        int token;
        EstadoAnalizLexico e;
        e = L.GetEdoAnalizLexico();

        if (Reglas()) {
            token = L.yylex();
            if (token == TokensGram_Gram.PC) {
                if (ListaReglasP()) {
                    return true;
                }
            }
            return false;
        }
        //Epsilon
        L.SetEdoAnalizLexico(e);

        return true;
    }

    public boolean Reglas() {
        int token;
        string Simbolo = new string();
        Simbolo.cadena = "";
        if (LadoIzquierdo(Simbolo)) {
            Vn.add(Simbolo.cadena);
            token = L.yylex();
            if (token == TokensGram_Gram.FLECHA) {
                if (LadosDerechos(Simbolo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean LadoIzquierdo(string Simbolo) {
        int token = L.yylex();

        if (token == TokensGram_Gram.SIMBOLO) {
            Simbolo.cadena = L.Lexema;
            return true;
        }
        return false;
    }

    public boolean LadosDerechos(string Simbolo) {
        if (LadoDerecho(Simbolo)) {
            if (LadosDerechosP(Simbolo)) {
                return true;
            }
        }
        return false;
    }

    public boolean LadosDerechosP(string Simbolo) {
        int token = L.yylex();
        if (token == TokensGram_Gram.OR) {
            if (LadoDerecho(Simbolo)) {
                if (LadosDerechosP(Simbolo)) {
                    return true;
                }
            }
            return false;
        }
        //Epsilon
        L.UndoToken();
        return true;
    }

    boolean LadoDerecho(string Simbolo) {
        Lista lista = new Lista();
        if (SecSimbolos(lista)) {
            ArrReglas[NumReglas] = new ElemArreglo();
            ArrReglas[NumReglas].InfSimbolo = new Nodo(Simbolo.cadena);
            ArrReglas[NumReglas++].ListaLadoDerecho.lista = lista.lista;
            return true;
        }
        return false;
    }

    boolean SecSimbolos(Lista lista) {
        int token = L.yylex();
        Nodo N;
        if (token == TokensGram_Gram.SIMBOLO) {
            N = new Nodo(L.Lexema);
            if (SecSimbolosP(lista)) {
                lista.lista.add(0, N);
                return true;
            }
        }
        return false;
    }

    boolean SecSimbolosP(Lista lista) {
        int token = L.yylex();
        Nodo N;
        if (token == TokensGram_Gram.SIMBOLO) {
            N = new Nodo(L.Lexema);
            if (SecSimbolosP(lista)) {
                lista.lista.add(0, N);
                return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    void IdentificarTerminales() {
        for (int i = 0; i < NumReglas; i++) {
            for (Nodo N : ArrReglas[i].ListaLadoDerecho.lista) {
                if (!Vn.contains(N.Simbolo)) {
                    N.Terminal = true;
                    Vt.add(N.Simbolo);
                }
            }
        }
    }

    public HashSet<String> First(Lista lista) {
        int i, j;
        Nodo N;
        HashSet<String> R = new HashSet<>();
        R.clear();

        if (lista.lista.isEmpty()) {
            return R;
        }
        for (j = 0; j < lista.lista.size(); j++) {
            N = lista.lista.get(j);
            if (N.Terminal || N.Simbolo.equals("£")) {
                R.add(N.Simbolo);
                return R;
            }
            //N es no terminal. Se calcula el first de cada lado derecho de este no terminal
            for (i = 0; i < NumReglas; i++) {
                if (ArrReglas[i].ListaLadoDerecho.lista.get(0).Simbolo.equals(N.Simbolo)) // Ejemplo: E -> E + T
                {
                    continue;
                }
                if (ArrReglas[i].InfSimbolo.Simbolo.equals(N.Simbolo)) {
                    R.addAll(First(ArrReglas[i].ListaLadoDerecho));
                }
            }
            if (R.contains("£")) {
                if (j == (lista.lista.size() - 1)) {
                    continue;
                }
                R.remove("£");
            } else {
                break;
            }
        }
        return R;
    }

    public HashSet<String> Follow(String simbNoTerm) {
        HashSet<String> R = new HashSet<>();
        HashSet<String> aux = new HashSet<>();
        Lista ListaPost = new Lista();

        R.clear();

        int i, j, k;

        if (ArrReglas[0].InfSimbolo.Simbolo.equals(simbNoTerm)) {
            R.add("$");
        }

        for (i = 0; i < NumReglas; i++) //Se busca SimbNoTerm en los lados derechos de todas las reglas
        {
            //Se recorre la lista del lado derecho buscando el simbolo SimbNoTerm
            for (j = 0; j < ArrReglas[i].ListaLadoDerecho.lista.size(); j++) {

                if (ArrReglas[i].ListaLadoDerecho.lista.get(j).Simbolo.equals(simbNoTerm)) {

                    ListaPost.lista.clear();
                    //Obtenemos la lista que corresponden a los simbolos que estan despues de SimbNoTerm
                    for (k = j + 1; k < ArrReglas[i].ListaLadoDerecho.lista.size(); k++) {
                        ListaPost.lista.add(ArrReglas[i].ListaLadoDerecho.lista.get(k));
                    }

                    //Si no hay mas simbolos despues de SimbNoTerm, se calcula el Follow del lado izquierdo de la regla
                    if (ListaPost.lista.isEmpty()) {
                        //Si el simbolo del lado izquiero es igual al simbolo del que queremos calcular
                        //el follow, omitimos la regla
                        if (!ArrReglas[i].InfSimbolo.Simbolo.equals(simbNoTerm)) {
                            R.addAll(Follow(ArrReglas[i].InfSimbolo.Simbolo));
                        }
                        break;
                    }

                    //Se calcula el first de la lista 1 que esta despues del elemento j
                    aux.clear();
                    aux = First(ListaPost);

                    if (aux.contains("£")) {
                        aux.remove("£");
                        R.addAll(aux);
                        if (!ArrReglas[i].InfSimbolo.Simbolo.equals(simbNoTerm)) {
                            R.addAll(Follow(ArrReglas[i].InfSimbolo.Simbolo));
                        }
                    } else {
                        R.addAll(aux);
                    }
                }
            }
        }
        return R;
    }
}
