package lab03.instr;

import lab03.exp.ExpFuncion;

public class LlamadaFuncion extends Instruccion {

    public ExpFuncion expFuncion;

    public LlamadaFuncion(ExpFuncion expFuncion) {
        this.expFuncion = expFuncion;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        return cont;
    }
}
