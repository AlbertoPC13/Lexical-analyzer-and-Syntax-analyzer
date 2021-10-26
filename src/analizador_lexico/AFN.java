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
    //Indica los AFN que seran agregados al analizador lexico
    boolean SeAgregoAFNUnionLexico;
    //Identificador del AFN
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

        //Se crea una transicion epsilon del nuevo estado inicial al estado inicial del AFN invocante
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni));

        //Por cada estado de aceptacion en el AFN invocante, se le agrega una transicion epsilon al nuevo estado de aceptacion
        //se agrega una transicion epsilon al estado inicial del AFN invocante
        //ademas se pone en false el atributo de estado de aceptacion
        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni));
            e.setEdoAcept(false);
        });

        //Actualizacion de automata tras cerradura positiva
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

        //Se crea una transicion epsilon del nuevo estado inicial al estado inicial del AFN invocante
        //Y se agrega una transicion epsilon del nuevo estado inicial al nuevo estado final
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));

        //Por cada estado de aceptacion en el AFN invocante, se le agrega una transicion epsilon al nuevo estado de aceptacion
        //se agrega una transicion epsilon al estado inicial del AFN invocante
        //ademas se pone en false el atributo de estado de aceptacion
        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
            e.setEdoAcept(false);
        });

        //Actualizacion de automata tras cerradura de Kleen
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

        //Se crea una transicion epsilon del nuevo estado inicial al estado inicial del AFN invocante
        //Y se agrega una transicion epsilon del nuevo estado inicial al nuevo estado final
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, EdoIni));
        ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));

        //Por cada estado de aceptacion en el AFN invocante se le agrega una transicion epsilon al nuevo estado de aceptacion
        //ademas se pone en false el atributo de estado de aceptacion
        EdosAcept.forEach((e)
                -> {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, fin));
            e.setEdoAcept(false);
        });

        //Actualizacion de automata tras operacion opcional        
        EdoIni = ini;
        fin.setEdoAcept(true);
        EdosAcept.clear();
        EdosAcept.add(fin);
        EdosAFN.add(ini);
        EdosAFN.add(fin);

        return this;
    }

    public HashSet<Estado> CerrEpsilon(Estado e) {
        //Se crea el conjunto de estados que indica los estados alcanzables por epsilon
        HashSet<Estado> R = new HashSet<>();
        //Se crea una pila para los estados por analizar
        Stack<Estado> Stack = new Stack<>();
        Estado aux;

        //Se limpian el hashset y la pila para el inicio del analisis 
        R.clear();
        Stack.clear();

        //Se agrega el estado recibido a la pila
        Stack.push(e);
        //Se realiza el ciclo while hasta que no queden elementos por analizar en la pila
        while (!Stack.isEmpty()) {
            //Se saca el estado que esta en el tope de la pila
            aux = Stack.pop();
            //Se agrega el estado a el hashset de estados alcanzables por epsilon
            R.add(aux);

            //Se compureban todas las transiciones del estado auxiliar que se analiza
            aux.getTrans().forEach((t)
                    -> {
                Estado Edo;
                //Se comprueba si la transicion es con el simbolo epsilon
                if (t.GetEdoTrans(SimbolosEspeciales.EPSILON) != null) {
                    Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON);
                    //Se comprueba si el estado no se ha agregado a la lista de estados alcanzables por epsilon
                    if (!R.contains(Edo)) {
                        //Si el estado no se encontraba en la lista, se agrega a la pila
                        Stack.push(Edo);
                    }
                }
            });
        }

        return R;
    }

    public HashSet<Estado> CerrEpsilon(HashSet<Estado> Edos) {
        //Se crea el conjunto de estados que indica los estados alcanzables por epsilon
        HashSet<Estado> R = new HashSet<>();
        //Se crea una pila para los estados por analizar
        Stack<Estado> Stack = new Stack<>();
        Estado aux, Edo;

        //Se limpian el hashset y la pila para el inicio del analisis 
        R.clear();
        Stack.clear();

        //Se agrega cada estado del hashset recibido a la pila
        for (Estado e : Edos) {
            Stack.push(e);
        }

        //Se realiza el ciclo while hasta que no queden elementos por analizar en la pila
        while (!Stack.empty()) {
            //Se saca el estado que esta en el tope de la pila
            aux = Stack.pop();
            //Se agrega el estado a el hashset de estados alcanzables por epsilon
            R.add(aux);

            //Se compureban todas las transiciones del estado auxiliar que se analiza
            for (Transicion t : aux.getTrans()) {
                //Se comprueba si la transicion es con el simbolo epsilon
                if (t.GetEdoTrans(SimbolosEspeciales.EPSILON) != null) {
                    Edo = t.GetEdoTrans(SimbolosEspeciales.EPSILON);
                    //Se comprueba si el estado no se ha agregado a la lista de estados alcanzables por epsilon
                    if (!R.contains(Edo)) {
                        //Si el estado no se encontraba en la lista, se agrega a la pila
                        Stack.push(Edo);
                    }
                }
            }
        }
        return R;
    }

    public HashSet<Estado> Mover(Estado Edo, char S) {
        //Se crea un hashset para los estados a los cuales se puede llegar con el simbolo dado
        HashSet<Estado> C = new HashSet<>();
        //Se limpia el hashset para iniciar analisis
        C.clear();

        //Se recorren todas las trancisiones del estado recibido
        Edo.getTrans().forEach((t)
                -> {
            //Se comprueba si la transicion analizada tiene el simbolo recibido
            Estado Aux = t.GetEdoTrans(S);
            if (Aux != null) {
                //Si la transicion si era con el simbolo, se agrega al hashset de estados alcanzables
                C.add(Aux);
            }
        });

        return C;
    }

    public HashSet<Estado> Mover(HashSet<Estado> Edos, char S) {
        //Se crea un hashset para los estados a los cuales se puede llegar con el simbolo dado
        HashSet<Estado> C = new HashSet<>();
        //Se limpia el hashset para iniciar analisis
        C.clear();

        //Se recorren todas los estados del hashset de estados recibido
        Edos.forEach((Edo)
                -> {
            //Se recorren todas las trancisiones del estado actual analizado
            Edo.getTrans().forEach((t)
                    -> {
                //Se comprueba si la transicion analizada tiene el simbolo recibido
                Estado Aux = t.GetEdoTrans(S);
                if (Aux != null) {
                    //Si la transicion si era con el simbolo, se agrega al hashset de estados alcanzables
                    C.add(Aux);
                }
            });
        });

        return C;
    }

    public HashSet<Estado> Ir_A(HashSet<Estado> Edos, char S) {
        //Se crea un hashset para los estados a los cuales se puede llegar con la operacion Ir A
        //de los estados dados con el simbolo recibido
        HashSet<Estado> C = new HashSet<>();
        //Se limpia el hashset para iniciar analisis
        C.clear();
        //Se realiza la cerradura epsilon del conjunto de estados retornado por la operacion
        //Mover aplicada a los estados recibidos con el simbolo recibido
        C = CerrEpsilon(Mover(Edos, S));

        return C;
    }

    public AFD ConvAFNaAFD() {
        //Se crean variables para la cardinalidad del alfabeto del nuevo AFD y # de estados de AFD
        int CardAlfabeto, NumEdosAFD;
        //Iteradores para numero de Conjuntos de estado
        int i, j;
        //Arreglo para almacenar el alfabeto del AFN
        char[] ArrAlfabeto;
        //Conjuntos de estados para la conversion
        ConjIj Ij, Ik;
        //Bandera para indicar si los esados ya estan en el conjunto de estados
        boolean existe;

        //Se crea un hashset para asignar los tokens a los estados de aceptacion del AFD
        HashSet<Estado> ConjAux = new HashSet<>();
        //Se crea un hashset para el conjunto de estados del nuevo AFD
        HashSet<ConjIj> EdosAFD = new HashSet<>();
        //Se crea una pila para los conjuntos de estados por analizar
        Queue<ConjIj> EdosSinAnalizar = new LinkedList<>();

        //Se limpia el hashset de estados del AFD y la pila
        EdosAFD.clear();
        EdosSinAnalizar.clear();

        //Se asigna la cardinalidad del alfabeto del AFD
        CardAlfabeto = Alfabeto.size();
        //Se crea el alfabeto del AFD con la cardinalidad del alfabeto del AFN
        ArrAlfabeto = new char[CardAlfabeto];

        i = 0;
        //Se copia el alfabeto del AFN al alfabeto del nuevo AFD
        for (char c : Alfabeto) {
            ArrAlfabeto[i++] = c;
        }

        //Se inicializa j en 0 para el conjunto I0
        j = 0;
        //Se crea un nuevo conjunto de estados Ij
        Ij = new ConjIj(CardAlfabeto);
        //Se realiza la cerradura epsilon del estado inicial
        Ij.ConjI = CerrEpsilon(this.EdoIni);
        //Se asigna el valor de j como identificador del conjunto de estados Ij
        Ij.j = j;

        //Se agrega el conjunto I0 a los estados del nuevo AFD
        EdosAFD.add(Ij);
        //Se agrega el conjunto de estados I0 a la pila para ser analizados
        EdosSinAnalizar.add(Ij);
        //Se incrementa j para la creacion del siguiente conjunto Ij
        j++;

        //Se realiza el ciclo while hasta que la pila de estados sin analizar quede vacia
        while (!EdosSinAnalizar.isEmpty()) {
            //Se saca el conjunto de estados en el tope de la pila
            Ij = EdosSinAnalizar.poll();

            //Se recorren todos los simbolos del alfabeto
            for (char c : ArrAlfabeto) {
                //Se crea un nuevo estado Ik
                Ik = new ConjIj(CardAlfabeto);
                //Se obtienen los estados para el AFD realizando la operacion Ir A con el simbolo actual del alfabeto
                Ik.ConjI = Ir_A(Ij.ConjI, c);

                //Si el conjunto Ik es es vacio tras la operacion Ir A
                //Se continua en el ciclo con el siguiente simbolo del alfabeto 
                if (Ik.ConjI.isEmpty()) {
                    continue;
                }
                //Se asigna la bandera en falso suponiendo que el conjunto no existe
                existe = false;

                //Se recorren todos los conjuntos de estados que pertenecen a los estados del nuevo AFD
                for (ConjIj I : EdosAFD) {
                    //Se compara si el conjunto actual del AFD es igual al conjunti Ik
                    if (I.ConjI.equals(Ik.ConjI)) {
                        //Si son iguales indica que el conjunto ya existe con la bandera
                        existe = true;
                        //Se agrega al conjunto Ij una transicion con el simbolo actual con el conjunto Ij
                        Ij.TransicionesAFD[c] = I.j;
                        break;
                    }
                }

                //Si comprueba si la bander aindica que el conjunto de estados Ik no se encuentran en el conjunto de estados del AFD
                if (!existe) {
                    //Se asigna el valor de j al conjunto Ik
                    Ik.j = j;
                    //Se agrega al conjunto Ij una transicion con el simbolo actual con el conjunto Ik
                    Ij.TransicionesAFD[c] = Ik.j;
                    //Se agrega el conjunto Ik a el conjunto de estados del nuevo AFD
                    EdosAFD.add(Ik);
                    //Se agrega el conjunto Ik a la pila de estados sin analizar
                    EdosSinAnalizar.add(Ik);
                    //Se incrementa j para el siguiente conjunto de estados Ij
                    j++;
                }
            }
        }
        
        //Se asigna el valor de j como el numero de estados del AFD
        NumEdosAFD = j;

        //Se recorren los conjuntos de estados Ij que conforman a los estados del AFD
        for (ConjIj I : EdosAFD) {
            //Se limpia el conjunto de estados auxiliar
            ConjAux.clear();
            //Se agrega el conjunto actual de estados de Ij al conjunto auxiliar
            ConjAux.addAll(I.ConjI);
            //Se realiza la operacion interseccion de conjuntos entre:
            //Auxiliar y los estados de aceptacion del AFN invocante
            ConjAux.retainAll(this.EdosAcept);
            //Se comprueba si la interseccion no es vacia
            if (!ConjAux.isEmpty()) {
                //Si la interseccion no es vacia
                //Se recorren todos los estados que quedaron en el conjunto auxiliar
                for (Estado EdoAcept : ConjAux) {
                    //Se asigna en la utlima columna de transiciones el Token del estado de aceptacion
                    //Indica que el conjunto Ij tiene estado de aceptacion
                    I.TransicionesAFD[256] = EdoAcept.getToken();
                    break;
                }
            } else {
                //Si la interseccion es vacia, se pone -1 indicando que no es estado de aceptacion
                I.TransicionesAFD[256] = -1;
            }
        }

        //Se crea un nuevo AFD
        AFD AutFD = new AFD();
        //Se asigna la cardinalidad del alfabeto del AFD
        AutFD.CardAlfabeto = CardAlfabeto;
        //Se crea el arreglo para almacenar el alfabeto
        AutFD.Alfabeto = new char[CardAlfabeto];
        
        //Se crea una nueva tabla de transiciones para el AFD con el numero de estados del AFD y el tamano del codigo ASCII
        AutFD.TablaAFD = new int[EdosAFD.size()][257];

        //Se llena la tabla de transiciones con -1 para inicializar sin transiciones
        for (i = 0; i < EdosAFD.size(); i++) {
            for (j = 0; j < 257; j++) {
                AutFD.TablaAFD[i][j] = -1;
            }
        }

        //Se copian a la tabla las transiciones de cada conjunto de estados Ij que pertenecen a los estados del AFD 
        for (ConjIj I : EdosAFD) {
            for (j = 0; j < 257; j++) {
                AutFD.TablaAFD[I.j][j] = I.TransicionesAFD[j];
            }
        }

        //Se agrega el alfabeto del AFD
        AutFD.Alfabeto = ArrAlfabeto;
        //Se agrega el numero de estados del AFD
        AutFD.NumEstados = NumEdosAFD;

        return AutFD;
    }

    public AFN UnionLexico() {
        //Se crea un nuevo estado inicial
        Estado ini = new Estado();
        HashSet<AFN> a = new HashSet();

        //Se recorren todos los AFN almacenados
        for (AFN A : ConjDeAFNs) {
            //Se comprueba si el AFN fue seleccionado para ser unido
            if (A.SeAgregoAFNUnionLexico) {
                //Se agrega una transicion epsilon del nuevo estado inicial al estado inicial del AFN actual
                ini.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, A.EdoIni));
                //Se agregan los estados del AFN, los estados de aceptacion y el alfabeto a el AFN invocante
                this.EdosAFN.addAll(A.EdosAFN);
                this.EdosAcept.addAll(A.EdosAcept);
                this.Alfabeto.addAll(A.Alfabeto);
                //Se agrega el estado actual al hashset de los estados que se eliminaran
                a.add(A);
            }
        }

        //Se elimina del conjunto de AFNs los AFN que entran al AFN del analizador lexico
        ConjDeAFNs.removeAll(a);
        //Se agrega el nuevo estado inicial al conjunto de estados del AFN invocante
        this.EdosAFN.add(ini);
        //Se asigna el nuevo estado inicial al AFN invocante
        this.EdoIni = ini;
        return this;
    }
}
