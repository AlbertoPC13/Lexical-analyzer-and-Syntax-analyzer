package analizador_lexico;

import java.util.HashSet;

public class ConjIj 
{
    public int j;
    public HashSet<Estado> ConjI;
    public int[] TransicionesAFD;
    
    public ConjIj(int CardAlf)
    {
        j = -1;
        ConjI = new HashSet<>();
        ConjI.clear();
        TransicionesAFD = new int[257];
        for(int k = 0; k < 257; k++)
            TransicionesAFD[k] = -1;
    }
}