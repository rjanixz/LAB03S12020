package lab03.instr;

import lab03.exp.Expresion;

public class Imprimir extends Instruccion {

    public Expresion exp;

    public Imprimir(Expresion exp) {
        this.exp = exp;
    }

}
