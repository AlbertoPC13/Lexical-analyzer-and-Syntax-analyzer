package analizador_lexico;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AFN {

    //Se declara un HashSet donde se almacenaran todos los estados que sean parte del AFN completo
    public static HashSet<AFN> ConjDeAFNs = new HashSet<AFN>();

    //Se asigna un atributo para indicar el estado inicial del AFN
    Estado EdoIni;
    //Se asigna un HashSet para almacenar los estados que conformen a un AFN
    HashSet<Estado> EdosAFN = new HashSet<>();
    //Se asigna un HashSet para almacenar los estados de aceptacion de un AFN
    HashSet<Estado> EdosAcept = new HashSet<>();
    //Se asigna un HashSet para almacenar el Alfabeto perteneciente a un AFN
    HashSet<Character> Alfabeto = new HashSet<>();
    //STANDING BY//
    boolean SeAgregoAFNUnionLexico;
    public int IdAFN;

    //Constructor de un AFN
    public AFN() {
        IdAFN = 0;
        EdoIni = null;
        EdosAFN.clear();
        EdosAcept.clear();
        Alfabeto.clear();
        SeAgregoAFNUnionLexico = false;
    }

    //Se crea el AFN basico para un caracter
    public AFN CrearAFNBasico(char s) {
        //Se crean 2 estados y una transicion
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        //Se cre una transicion con el simbolo "s" al estado "e2"
        t = new Transicion(s, e2);
        //Se agrega "t" al conjunto de transiciones de "e1"
        e1.getTrans().add(t);
        //Se asigna "e2" como estado de acpetacion del AFN 
        e2.setEdoAcept(true);
        //Se agrega el simbolo "s" al alfabeto del AFN
        Alfabeto.add(s);
        //Se asigna "e1" como estado inicial
        EdoIni = e1;
        //Se agregan ambos estados al conjunto de estados del AFN
        EdosAFN.add(e1);
        EdosAFN.add(e2);
        //Se agrega "e2" al conjunto de estados de aceptacion del AFN
        EdosAcept.add(e2);
        SeAgregoAFNUnionLexico = false;
        return this;
    }

    //Se crea el AFN básico para un rango de simbolos
    public AFN CrearAFNBasico(char s1, char s2) {
        //Se comprueba que "s1" sea menor o igual que "s2" (Rango valido)
        if (s1 <= s2) {
            //Se crean 2 estados y una transicion
            Transicion t;
            Estado e1, e2;
            e1 = new Estado();
            e2 = new Estado();
            //Se crea una transicion con el rango de simbolos al estado "e2"
            t = new Transicion(s1, s2, e2);
            //Se agrega "t" al conjunto de transiciones de "e1"
            e1.getTrans().add(t);
            //Se asigna "e2" como estado de aceptacion
            e2.setEdoAcept(true);

            //Se agrega cada simbolo que pertenesca al rango de [s1,s2]
            for (char i = s1; i <= s2; i++) {
                Alfabeto.add(i);
            }

            //Se asigna e1 como estado inicial
            EdoIni = e1;
            //Se agregan ambos estados al conjunto de estados del AFN
            EdosAFN.add(e1);
            EdosAFN.add(e2);
            //Se agrega "e2" al conjunto de estados de aceptacion del AFN
            EdosAcept.add(e2);
            SeAgregoAFNUnionLexico = false;
            return this;
        } else {
            return null;
        }
    }

    public AFN UnirAFN(AFN f2) {
        //Se crea un nuevo estado inicial "e1" y un estado de aceptacion "e2" 
        Estado e1 = new Estado();
        Estado e2 = new Estado();

        //Se crea una transicion epsilon a el estado inicial del AFN invocante y el AFN "f2"
        Transicion t1 = new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni);
        Transicion t2 = new Transicion(SimbolosEspeciales.EPSILON, f2.EdoIni);
        //Se agrega al conjunto de transiciones de "e1" las transiciones "t1" y "t2"
        e1.getTrans().add(t1);
        e1.getTrans().add(t2);

        //Por cada estado de aceptacion en el AFN invocante se le agrega una transicion epsilon al nuevo estado de aceptacion "e2"
        //ademas se pone en false el atributo de estado de aceptacion 
        this.EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
        });

        //Por cada estado de aceptacion en el AFN "f2" se le agrega una transicion epsilon al nuevo estado de aceptacion "e2"
        //ademas se pone en false el atributo de estado de aceptacion
        f2.EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
        });

        //Se eliminan todos los estados de aceptacion del AFN invocante y AFN "f2" 
        this.EdosAcept.clear();
        f2.EdosAcept.clear();

        //Se asigna como estado inicial del AFN invocante el estado "e1"
        this.EdoIni = e1;
        //Se asigna "e2" como estado de aceptacion
        e2.setEdoAcept(true);
        //Se agrega a los estados de aceptacion del AFN invocante el estado "e2" 
        this.EdosAcept.add(e2);
        //Se unen los estados del AFN "f2" con los estados del AFN invocante 
        this.EdosAFN.addAll(f2.EdosAFN);
        //Se agregan los estados "e1" y "e2" al AFN invocante
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        this.Alfabeto.addAll(f2.Alfabeto);

        return this;
    }

    public AFN ConcAFN(AFN f2) {
        //Fusion de estado de aceptacion de "this" con estado inicial de f2
        //Se conserva el estado de aceptacion de this

        f2.EdoIni.getTrans().forEach((t)
                -> {
            this.EdosAcept.forEach((e)
                    -> {
                e.getTrans().add(t);
                e.setEdoAcept(false);
            });
        });

        //Se elimina el estado inicial de "f2" de su lista de estados 
        f2.EdosAFN.remove(f2.EdoIni);

        //Actualizacion de automata tras concatenacion
        this.EdosAcept = f2.EdosAcept;
        this.EdosAFN.addAll(f2.EdosAFN);
        this.Alfabeto.addAll(f2.Alfabeto);

        return this;
    }

    public AFN CerrPos() {
        //Se crea nuevo estado inicial y uno de aceptacion
        Estado ini = new Estado();
        Estado fin = new Estado();

        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni));

        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni));
            e.setEdoAcept(false);
        });

        this.EdoIni = ini;
        fin.setEdoAcept(true);
        this.EdosAcept.clear();
        this.EdosAcept.add(fin);
        this.EdosAFN.add(ini);
        this.EdosAFN.add(fin);

        return this;
    }

    public AFN CerrKleen() {
        //Se crea nuevo estado inicial y uno de aceptacion
        Estado ini = new Estado();
        Estado fin = new Estado();

        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));

        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
            e.setEdoAcept(false);
        });

        EdoIni = ini;
        fin.setEdoAcept(true);
        EdosAcept.clear();
        EdosAcept.add(fin);
        EdosAFN.add(ini);
        EdosAFN.add(fin);

        return this;
    }

    public AFN Opcional() {
        //Se crea nuevo estado inicial y uno de aceptacion
        Estado ini = new Estado();
        Estado fin = new Estado();

        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));

        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.setEdoAcept(false);
        });

        EdoIni = ini;
        fin.setEdoAcept(true);
        EdosAcept.clear();
        EdosAcept.add(fin);
        EdosAFN.add(ini);
        EdosAFN.add(fin);

        return this;
    }

    public HashSet<Estado> CerrEpsilon(Estado e) {
        HashSet<Estado> R = new HashSet<>();
        Stack<Estado> Stack = new Stack<>();
        Estado aux;

        R.clear();
        Stack.clear();

        Stack.push(e);
        while (!Stack.isEmpty()) {
            aux = Stack.pop();
            R.add(aux);

            aux.getTrans().forEach((t)
                    -> {
                Estado Edo;
                if (t.GetEdoTrans(SimbolosEspeciales.EPSILON) != null) {
                    Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON);
                    if (!R.contains(Edo)) {
                        Stack.push(Edo);
                    }
                }
            });
        }

        return R;
    }

    public HashSet<Estado> CerrEpsilon(HashSet<Estado> Edos) {
        HashSet<Estado> R = new HashSet<>();
        Stack<Estado> Stack = new Stack<>();
        Estado aux, Edo;

        R.clear();
        Stack.clear();

        for (Estado e : Edos) {
            Stack.push(e);
        }

        while (!Stack.empty()) {
            aux = Stack.pop();
            R.add(aux);

            for (Transicion t : aux.getTrans()) {
                if (t.GetEdoTrans(SimbolosEspeciales.EPSILON) != null) {
                    Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON);
                    if (!R.contains(Edo)) {
                        Stack.push(Edo);
                    }
                }
            }
        }
        return R;
    }

    public HashSet<Estado> Mover(Estado Edo, char S) {
        HashSet<Estado> C = new HashSet<>();
        C.clear();

        Edo.getTrans().forEach((t)
                -> {
            Estado Aux = t.GetEdoTrans(S);
            if (Aux != null) {
                C.add(Aux);
            }
        });

        return C;
    }

    public HashSet<Estado> Mover(HashSet<Estado> Edos, char S) {
        HashSet<Estado> C = new HashSet<>();
        C.clear();

        Edos.forEach((Edo)
                -> {
            Edo.getTrans().forEach((t)
                    -> {
                Estado Aux = t.GetEdoTrans(S);
                if (Aux != null) {
                    C.add(Aux);
                }
            });
        });

        return C;
    }

    public HashSet<Estado> Ir_A(HashSet<Estado> Edos, char S) {
        HashSet<Estado> C = new HashSet<>();
        C.clear();
        C = CerrEpsilon(Mover(Edos, S));

        return C;
    }

    public AFD ConvAFNaAFD() {
        int CardAlfabeto, NumEdosAFD;
        int i, j;
        char[] ArrAlfabeto;
        ConjIj Ij, Ik;
        boolean existe;

        HashSet<Estado> ConjAux = new HashSet<>();
        HashSet<ConjIj> EdosAFD = new HashSet<>();
        Queue<ConjIj> EdosSinAnalizar = new LinkedList<>();

        EdosAFD.clear();
        EdosSinAnalizar.clear();

        CardAlfabeto = Alfabeto.size();
        ArrAlfabeto = new char[CardAlfabeto];

        i = 0;
        for (char c : Alfabeto) {
            ArrAlfabeto[i++] = c;
        }

        j = 0;
        Ij = new ConjIj(CardAlfabeto);
        Ij.ConjI = CerrEpsilon(this.EdoIni);
        Ij.j = j;

        EdosAFD.add(Ij);
        EdosSinAnalizar.add(Ij);
        j++;

        while (!EdosSinAnalizar.isEmpty()) {
            Ij = EdosSinAnalizar.poll();

            for (char c : ArrAlfabeto) {
                Ik = new ConjIj(CardAlfabeto);
                Ik.ConjI = Ir_A(Ij.ConjI, c);

                if (Ik.ConjI.isEmpty()) {
                    continue;
                }
                existe = false;

                for (ConjIj I : EdosAFD) {
                    if (I.ConjI.equals(Ik.ConjI)) {
                        existe = true;
                        Ij.TransicionesAFD[c] = I.j;
                        break;
                    }
                }
                if (!existe) {
                    Ik.j = j;
                    Ij.TransicionesAFD[c] = Ik.j;
                    EdosAFD.add(Ik);
                    EdosSinAnalizar.add(Ik);
                    j++;
                }
            }
        }
        NumEdosAFD = j;

        for (ConjIj I : EdosAFD) {
            ConjAux.clear();
            ConjAux.addAll(I.ConjI);
            ConjAux.retainAll(this.EdosAcept);
            if (!ConjAux.isEmpty()) {
                for (Estado EdoAcept : ConjAux) {
                    I.TransicionesAFD[256] = EdoAcept.getToken();
                    break;
                }
            } else {
                I.TransicionesAFD[256] = -1;
            }
        }

        AFD AutFD = new AFD();
        AutFD.CardAlfabeto = CardAlfabeto;

        AutFD.TablaAFD = new int[EdosAFD.size()][257];

        for (i = 0; i < EdosAFD.size(); i++) {
            for (j = 0; j < 257; j++) {
                AutFD.TablaAFD[i][j] = -1;
            }
        }

        for (ConjIj I : EdosAFD) {
            for (j = 0; j < 257; j++) {
                AutFD.TablaAFD[I.j][j] = I.TransicionesAFD[j];
            }
        }

        ArrAlfabeto = AutFD.Alfabeto;
        AutFD.NumEstados = NumEdosAFD;

        return AutFD;
    }

    public AFN UnionLexico() {
        Estado ini = new Estado();
        HashSet<AFN> a = new HashSet();

        for (AFN A : ConjDeAFNs) {
            if (A.SeAgregoAFNUnionLexico) {
                ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, A.EdoIni));
                this.EdosAFN.addAll(A.EdosAFN);
                this.EdosAcept.addAll(A.EdosAcept);
                this.Alfabeto.addAll(A.Alfabeto);
                a.add(A);
            }
        }

        ConjDeAFNs.removeAll(a);
        this.EdosAFN.add(ini);
        this.EdoIni = ini;
        return this;
    }
}
