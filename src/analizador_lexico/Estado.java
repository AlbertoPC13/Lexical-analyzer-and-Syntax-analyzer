package analizador_lexico;

import java.util.HashSet;

public class Estado 
{
    //Se declara un contador estatico para que todos las instancias puedan acceder a el y llevar registro de todos los estados creados
    static int ContadorIdEstado = 0;
    private int IdEstado;
    //Se declara un booleano que representa si el estado es de aceptacion (true) o no (false)
    private boolean EdoAcept;
    //Se asigna un atributo Token que identificara la pertenencia a cierta clase lexica
    private int Token;
    //Se asigna un HashSet (conjunto de objetos con distribucion Hash) para almacenar las transiciones del estado
    private HashSet<Transicion> Trans = new HashSet<>();
    
    //Constructor del estado predeterminado 
    public Estado()
    {
        EdoAcept = false;
        Token = -1;
        IdEstado = ContadorIdEstado++;
        Trans.clear();
    }

    
    //Metodos set y get de los atributos
    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public boolean isEdoAcept() {
        return EdoAcept;
    }

    public void setEdoAcept(boolean EdoAcept) {
        this.EdoAcept = EdoAcept;
    }

    public int getToken() {
        return Token;
    }

    public void setToken(int Token) {
        this.Token = Token;
    }

    public HashSet<Transicion> getTrans() {
        return Trans;
    }

    public void setTrans(HashSet<Transicion> Trans) {
        this.Trans = Trans;
    }
    
    
}
