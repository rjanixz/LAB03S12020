package lab03.instr;

import lab03.exp.ExpBoolean;

import java.util.List;

public class While extends Instruccion {

    public ExpBoolean expBoolean;

    public List<Instruccion> instrucciones;

    public While(ExpBoolean expBoolean, List<Instruccion> instrucciones) {
        this.expBoolean = expBoolean;
        this.instrucciones = instrucciones;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"While\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        for (Instruccion instr : instrucciones) {
            cont = instr.toDOT(builder, nodo, cont);
        }
        return cont;
    }
}
