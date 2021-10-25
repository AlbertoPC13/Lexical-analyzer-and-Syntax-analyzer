package analizador_lexico;

public class Transicion 
{
    //Se declaran dos simbolos para generar AFN basicos de un rango de caracteres
    private char SimbInf;
    private char SimbSup;
    //Se crea un estado que es el estado destino de la transicion
    private Estado Edo;
    
    //Constructot para transicion con un solo caracter, se igualan los dos simbolos
    public Transicion(char simb, Estado e)
    {
        SimbInf = simb;
        SimbSup = simb;
        Edo = e;
    }
    
    //Constructor para transicion de un rango de caracteres
    public Transicion(char simb1, char simb2, Estado e)
    {
        SimbInf = simb1;
        SimbSup = simb2;
        Edo = e;
    }
    
    //Metodo set de transicion de un rango de caracteres
    public void SetTransicion(char s1, char s2, Estado e)
    {
        SimbInf = s1;
        SimbSup = s2;
        Edo = e;
    }
    
    //Metodo set de transicion de un caracter
    public void SetTransicion(char s1, Estado e)
    {
        SimbInf = s1;
        SimbSup = s1;
        Edo = e;
    }

    //Metodo get del simbolo inferior
    public char getSimbInf() {
        return SimbInf;
    }

    //Metodo set del simbolo inferior
    public void setSimbInf(char SimbInf) {
        this.SimbInf = SimbInf;
    }

    //Metodo get del simbolo superior
    public char getSimbSup() {
        return SimbSup;
    }

    //Metodo set del simbolo superior
    public void setSimbSup(char SimbSup) {
        this.SimbSup = SimbSup;
    }
    
    //Metodo get del Edo de transicion (Debe validar que sea un rango de caracteres o el mismo en ambos atributos)
    public Estado GetEdoTrans(char s)
    {
        if(SimbInf <= s && s <= SimbSup)
            return Edo;
        return null;
    }
}