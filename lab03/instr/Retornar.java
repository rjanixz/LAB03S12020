package lab03.instr;

import lab03.exp.Expresion;

public class Retornar extends Instruccion {

    public Expresion exp;

    public Retornar(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        return 0;
    }
}
