package lab03.instr;

public class Definicion extends Instruccion {

    public String id;

    public Definicion(String id) {
        this.id = id;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"Definicion\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp = "nodo" + ++cont;
        builder.append(nodoOp).append(" [label=\"" + id + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp).append(";\n");

        return cont;
    }
}
