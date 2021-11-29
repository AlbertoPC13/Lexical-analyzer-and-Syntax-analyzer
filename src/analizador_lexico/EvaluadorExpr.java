package analizador_lexico;

public class EvaluadorExpr {
    public String Expresion; 
    public float result;
    public String ExprPost;
    public AnalizLexico L;
    
    public EvaluadorExpr(String sigma, AFD AutFD)
    {
        Expresion = sigma;
        L = new AnalizLexico(Expresion, AutFD);
    }
    
    public EvaluadorExpr(String sigma, String FileAFD, int IdAFD)
    {
        Expresion = sigma;
        L = new AnalizLexico(Expresion, FileAFD, IdAFD);
    }
    
    public EvaluadorExpr(String FileAFD,int IdAFD)
    {
        L = new AnalizLexico(FileAFD, IdAFD);
    }
    
    public void SetExpresion(String sigma)
    {
        Expresion = sigma;
        L.SetSigma(sigma);
    }
    
    public boolean IniEval()
    {
        int Token;
        string Postfijo = new string();
        Flotante v = new Flotante();
        
        if(E(v,Postfijo))
        {
            Token = L.yylex();
            if(Token == 0)
            {
                this.result = v.valor;
                this.ExprPost = Postfijo.cadena;
                return true;
            }
        }
        return false;
    }
    
    boolean E(Flotante v, string Post)
    {
        if(T(v,Post))
            if(Ep(v,Post))
                return true;
        return false;
    }
    
    boolean Ep(Flotante v, string Post)
    {
        int Token;
        Flotante v2 = new Flotante();
        string Post2 = new string();
        Token = L.yylex();
        if(Token == 10 || Token == 20)  //+ o -
        {
            if(T(v2,Post2))
            {
                v.valor = v.valor + (Token == 10 ? v2.valor : -v2.valor);
                Post.cadena = Post.cadena + " " + Post2.cadena + " " + (Token == 10 ? "+" : "-");
                if(Ep(v,Post))
                    return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }
    
    boolean T(Flotante v, string Post)
    {
        if(F(v,Post))
            if(Tp(v,Post))
                return true;
        return false;
    }
    
    boolean Tp(Flotante v, string Post)
    {
        int Token;
        Flotante v2 = new Flotante();
        string Post2 = new string();
        Token = L.yylex();
        if(Token == 30 || Token == 40)  // * o /
        {
            if(F(v2,Post2))
            {
                v.valor = v.valor * (Token == 30 ? v2.valor : 1/v2.valor);
                Post.cadena = Post.cadena + " " + Post2.cadena + " " + (Token == 30 ? "*" : "/");
                if(Tp(v,Post))
                    return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }
    
    boolean F(Flotante v, string Post)
    {
        int Token;
        Token = L.yylex();
        switch(Token)
        {
            case 50:    // Parentesis izquierdo
                if(E(v, Post))
                {
                    Token = L.yylex();
                    if(Token == 60) // Parentesis derecho
                        return true;
                }
                return false;
            case 70:    // NUM
                v.valor = Float.parseFloat(L.Lexema);
                Post.cadena = L.Lexema;
                return true;
        }
        return false;
    }
}
