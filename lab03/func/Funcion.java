package lab03.func;

import lab03.instr.Instruccion;

import java.util.List;
import java.util.Set;

public class Funcion {

    public String id;
    public Set<String> params;
    public List<Instruccion> instrucciones;

    public Funcion(String id, Set<String> params, List<Instruccion> instrucciones) {
        this.id = id;
        this.params = params;
        this.instrucciones = instrucciones;
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "id='" + id + '\'' +
                ", params=" + params +
                '}';
    }
}
