package analizador_lexico;

public class Nodo {
    public String Simbolo;
    public boolean Terminal;
    
    public Nodo()
    {
        Simbolo = "";
        Terminal = false;
    }
    
    public Nodo(String simb)
    {
        Simbolo = simb;
        Terminal = false;
    }
    
    public Nodo(String Simb, boolean EsTerminal)
    {
        Simbolo = Simb;
        Terminal = EsTerminal;
    }
}