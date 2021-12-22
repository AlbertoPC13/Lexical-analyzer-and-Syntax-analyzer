package analizador_lexico;


public class SimbTerm {
    public String Simbolo;
    public int Token;
    public boolean Terminal;
    
    public SimbTerm(String simb, int token)
    {
        Simbolo = simb;
        Token = token;
    }
    
    public SimbTerm(String simb, boolean term ,int token)
    {
        Simbolo = simb;
        Token = token;
        Terminal = term;
    }
    
    public SimbTerm()
    {
        Simbolo = "";
        Token = -1;
    }
}
