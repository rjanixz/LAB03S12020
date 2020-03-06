package lab03.exp;

public class ExpId extends Expresion {

    public String id;

    public ExpId(String val) {
        this.id = val;
    }

    @Override
    public int toDOT(StringBuilder builder, String parent, int cont) {
        String nodo = "nodo" + ++cont;
        builder.append(nodo).append(" [label=\"ExpId\"];\n");
        builder.append(parent).append(" -> ").append(nodo).append(";\n");

        String nodoOp1 = "nodo" + ++cont;
        builder.append(nodoOp1).append(" [label=\""+ id + "\"];\n");
        builder.append(nodo).append(" -> ").append(nodoOp1).append(";\n");


        return cont;
    }
}
