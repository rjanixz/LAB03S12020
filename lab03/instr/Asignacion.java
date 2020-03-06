package lab03.instr;

import lab03.exp.Expresion;

public class Asignacion extends Instruccion {

    public String id;
    public Expresion exp;

    public Asignacion(String id, Expresion exp) {
        this.id = id;
        this.exp = exp;
    }


    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Asignacion\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + id + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        cont = exp.toDOT(builder, nodo, cont);
        return cont;
    }
}
