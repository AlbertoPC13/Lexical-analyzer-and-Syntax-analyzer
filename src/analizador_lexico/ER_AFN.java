package analizador_lexico;

public class ER_AFN {
    String ExprRegular;
    public AFN result;
    public AnalizLexico L;
    
    public ER_AFN(String sigma, AFD AutFD)
    {
        ExprRegular = sigma;
        L = new AnalizLexico(ExprRegular, AutFD);
    }
    
    public ER_AFN(String sigma, String FileAFD, int IdAFD)
    {
        ExprRegular = sigma;
        L = new AnalizLexico(ExprRegular, FileAFD, IdAFD);
    }
    
    public ER_AFN(String FileAFD, int IdAFD)
    {
        L = new AnalizLexico(FileAFD, IdAFD);
    }
    
    public void SetExpresion(String sigma)
    {
        ExprRegular = sigma;
        L.SetSigma(sigma);
    }
    
    public boolean IniConversion()
    {
        int Token;
        AFN f;
        f = new AFN();
        if(E(f))
        {
            Token = L.yylex();
            if(Token == 0)
            {
                this.result = f;
                return true;
            }
        }
        return false;
    }
    
    public boolean E(AFN f)
    {
        if(T(f))
            if(Ep(f))
                return true;
        return false;
    }
    
    public boolean Ep(AFN f)
    {
        int Token;
        AFN f2 = new AFN();
        
        Token = L.yylex();
        if(Token == 10) //OR
        {
            if(T(f2))
            {
                f.UnirAFN(f2);
                if(Ep(f))
                    return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }
    
    public boolean T(AFN f)
    {
        if(C(f))
            if(Tp(f))
                return true;
        return false;
    }
    
    public boolean Tp(AFN f)
    {
        int Token;
        AFN f2 = new AFN();
        
        Token = L.yylex();
        if(Token == 20) //Concatenacion
        {
            if(C(f2))
            {
                f.ConcAFN(f2);
                if(Tp(f))
                    return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }
    
    public boolean C(AFN f)
    {
        if(F(f))
            if(Cp(f))
                return true;
        return false;
    }
    
    public boolean Cp(AFN f)
    {
        int Token;
        
        Token = L.yylex();
        
        switch(Token)
        {
            case 30:    //Cerradura positiva
                f.CerrPos();
                if(Cp(f))
                    return true;
                return false;
                
            case 40:    //Cerradura de Kleen
                f.CerrKleen();
                if(Cp(f))
                    return true;
                return false;
                
            case 50:    //Operador opcional
                f.Opcional();
                if(Cp(f))
                    return true;
                return false;
        }
        L.UndoToken();
        return true;
    }
    
    public boolean F(AFN f)
    {
        int Token;
        char simbolo1, simbolo2;
        Token = L.yylex();
        
        switch(Token)
        {
            case 60:    //Parentesis izquierdo
                if(E(f))
                {
                    Token = L.yylex();
                    if(Token == 70) //Parentesis derecho
                        return true;
                }
                return false;
                
            case 80:    //Corchete izquierdo
                Token = L.yylex();
                if(Token == 110)    //Simbolo
                {
                    simbolo1 = (L.Lexema.charAt(0) == '\\') ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                    Token = L.yylex();
                    if(Token == 100)    //Guion
                    {
                        Token = L.yylex();
                        if(Token == 110)    //Simbolo
                        {
                            simbolo2 = (L.Lexema.charAt(0) == '\\') ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                            Token = L.yylex();
                            if(Token == 90) //Corchete derecho
                            {
                                f = new AFN();
                                f.CrearAFNBasico(simbolo1, simbolo2);
                                return true;
                            }
                        }
                    }
                }
                return false;
                
            case 110:   //Simbolo
                simbolo1 = (L.Lexema.charAt(0) == '\\') ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                f = new AFN();
                f.CrearAFNBasico(simbolo1);
                return true;
        }
        return false;
    }
}
