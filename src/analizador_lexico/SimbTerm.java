package analizador_lexico;


public class SimbTerm {
    public String Simbolo;
    public int Token;
    
    public SimbTerm(String simb, int token)
    {
        Simbolo = simb;
        Token = token;
    }
    
    public SimbTerm()
    {
        Simbolo = "";
        Token = -1;
    }
}
