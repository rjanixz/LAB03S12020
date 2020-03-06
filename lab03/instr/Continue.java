package lab03.instr;

public class Continue extends Instruccion {


    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Continue\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");
        return cont;
    }
}
