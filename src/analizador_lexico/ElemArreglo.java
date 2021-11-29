package analizador_lexico;

public class ElemArreglo {
    public Nodo InfSimbolo;
    public Lista ListaLadoDerecho;
    
    public ElemArreglo()
    {
        InfSimbolo = new Nodo();
        ListaLadoDerecho = new Lista();
    }
}
