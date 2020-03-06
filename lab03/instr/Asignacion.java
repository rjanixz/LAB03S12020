package lab03.instr;

import lab03.exp.Expresion;

public class Asignacion extends Instruccion {

    public String id;
    public Expresion exp;

    public Asignacion(String id, Expresion exp) {
        this.id = id;
        this.exp = exp;
    }
}
