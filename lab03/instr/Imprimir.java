package lab03.instr;

import lab03.exp.Expresion;

public class Imprimir extends Instruccion {

    public Expresion exp;

    public Imprimir(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Imprimir\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        cont = exp.toDOT(builder, nodo, cont);
        return cont;
    }
}
