package analizador_lexico;

import java.util.HashSet;

public class AFN_AFD {
    public int j;
    public HashSet<Estado> ConjI;
    public int[] TransicionesAFD;
    
    //Cardinalidad del alfabeto (número de simbolos)
    public AFN_AFD(int CardAlf)
    {
        j = -1;
        ConjI = new HashSet();
        ConjI.clear();
        TransicionesAFD = new int[CardAlf+1];
        for(int k = 0; k <= CardAlf; k++)
            TransicionesAFD[k] = -1;
    }
}
