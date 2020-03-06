package lab03.instr;

import lab03.exp.ExpBoolean;

import java.util.List;

public class If extends Instruccion {

    public ExpBoolean expBoolean;
    public List<Instruccion> instruccionesIfTrue;
    public List<Instruccion> instruccionesIfFalse;

    public If(ExpBoolean expBoolean, List<Instruccion> instruccionesIfTrue, List<Instruccion> instruccionesIfFalse) {
        this.expBoolean = expBoolean;
        this.instruccionesIfTrue = instruccionesIfTrue;
        this.instruccionesIfFalse = instruccionesIfFalse;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"If-Else\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoIf = "nodo" + ++cont;
        builder.append(nodoIf).append(" [label=\"If\"];\n");
        builder.append(nodo).append(" -> ").append(nodoIf).append(";\n");

        for (Instruccion instr : instruccionesIfTrue) {
            cont = instr.toDOT(builder, nodoIf, cont);
        }

        String nodoElse = "nodo" + ++cont;
        builder.append(nodoElse).append(" [label=\"Else\"];\n");
        builder.append(nodo).append(" -> ").append(nodoElse).append(";\n");

        for (Instruccion instr : instruccionesIfFalse) {
            cont = instr.toDOT(builder, nodoElse, cont);
        }

        return cont;
    }
}
