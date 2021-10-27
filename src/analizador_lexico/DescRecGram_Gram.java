package analizador_lexico;

public class DescRecGram_Gram {
    public String Gramatica;
    public AnalizLexico L;
    
    public DescRecGram_Gram(String sigma, AFD afd)
    {
        Gramatica = sigma;
        L = new AnalizLexico(sigma,afd);
    }
    
    public boolean AnalizarGramatica()
    {
        int token;
        if(G())
        {
            token = L.yylex();
            if(token == 0)
                return true;
        }
        return false;
    }
    
    public boolean G()
    {
        return ListaReglas();
    }
    
    public boolean ListaReglas()
    {
        int token;
        if(Reglas())
        {
            token = L.yylex();
            if(token == TokensGram_Gram.PC)
                if(ListaReglasP())
                    return true;
        }
        return false;
    }
    
    public boolean ListaReglasP()
    {
        int token;
        EstadoAnalizLexico e;
        e = L.GetEdoAnalizLexico();
        
        if(Reglas())
        {
            token = L.yylex();
            if(token == TokensGram_Gram.PC)
            {
                if(ListaReglasP())
                    return true;
            }
            return false;
        }
        //Epsilon
        L.SetEdoAnalizLexico(e);
        
        return true;
    }
    
    public boolean Reglas()
    {
        int token;
        if(LadoIzquierdo())
        {
            token = L.yylex();
            if(token == TokensGram_Gram.FLECHA)
                if(LadosDerechos())
                    return true;
        }
        return false;
    }
    
    public boolean LadoIzquierdo()
    {
        int token = L.yylex();
        return token == TokensGram_Gram.SIMBOLO;
    }
    
    public boolean LadosDerechos()
    {
        if(LadoDerecho())
            if(LadosDerechosP())
                return true;
        return false;
    }
    
    public boolean LadosDerechosP()
    {
        int token = L.yylex();
        if(token == TokensGram_Gram.OR)
        {
            if(LadoDerecho())
                if(LadosDerechosP())
                    return true;
            return false;
        }
        //Epsilon
        L.UndoToken();
        return true;
    }
    
    boolean LadoDerecho()
    {
        return SecSimbolos();
    }
    
    boolean SecSimbolos()
    {
        int token = L.yylex();
        if(token == TokensGram_Gram.SIMBOLO)
            if(SecSimbolosP())
                return true;
        return false;
    }
    
    boolean SecSimbolosP()
    {
        int token = L.yylex();
        if(token == TokensGram_Gram.SIMBOLO)
        {
            return SecSimbolosP();
        }
        L.UndoToken();
        return true;
    }
}
